package converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 야민정음 변환기 core class입니다.<br>
 * [기본 사용 예시]<br>
 *     <pre>
 *         YaminjeongeumConverter yaminjeongeumConverter = new YaminjeongeumConverter.Builder()
 *                 .setFilePath("./yamin2.json")
 *                 .setConvertHanja(false)
 *                 .setSelectionStrength(SelectionStrength.EXTREME)
 *                 .setErrorListener(errorListener)
 *                 .setDebugMode(true)
 *                 .build();
 *                 </pre>
 *
 * @since 1.0
 * @author Yang-Seungmin
 */
public class YaminjeongeumConverter {
    private final YaminjeongeumData yaminjeongeumData;
    private final boolean convertHanja;
    private final boolean isAvaliable;
    private final boolean debugMode;

    private SelectionStrength selectionStrength;

    private StringBuffer result;

    private YaminjeongeumConverter(Builder builder, YaminjeongeumData yaminjeongeumData) {
        this.yaminjeongeumData = yaminjeongeumData;
        this.convertHanja = builder.convertHanja;
        this.selectionStrength = builder.selectionStrength;
        this.debugMode = builder.debugMode;

        if(this.yaminjeongeumData == null) isAvaliable = false;
        else isAvaliable = true;
    }


    /**
     * YaminjeongeumConverter의 Build클래스입니다.<br>
     * 외부에서 YaminjeongeumConverter를 생성하기 위해서는 반드시 Builder 클래스를 거쳐야 합니다.
     */
    public static class Builder {
        private String filePath = "yamin2.json";
        private boolean convertHanja = false;
        private SelectionStrength selectionStrength = SelectionStrength.MODERATE;
        private ErrorListener errorListener = null;
        private boolean debugMode = false;

        /**
         * Builder 생성 중 발생하는 Error를 전달합니다.
         */
        public interface ErrorListener {
            void onErrorReceived(String errorString);
        }

        public Builder() {
        }

        /**
         * json 파일을 불러올 경로를 설정합니다.
         * @param filePath 파일 경로
         * @return Builder 객체(this)
         */
        public Builder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        /**
         * 한자로의 변환에 대한 허용 여부를 설정합니다.
         * @param convertHanja 한자 변환 허용 여부
         * @return Builder 객체(this)
         */
        public Builder setConvertHanja(boolean convertHanja) {
            this.convertHanja = convertHanja;
            return this;
        }

        /**
         * 변환 강도를 설정합니다.<br>
         * ORIGINAL : 변환 전 문자열로 변환합니다(야민정음 to 훈민정음)<br>
         * SLIGHT : 크게 변환이 이루어지지 않고 알아보기 어렵지 않은 선에서 변환을 진행합니다.<br>
         * MODERATE : 중간 단계의 변환을 진행합니다.<br>
         * EXTREME : 원본을 알아보기 어려울 정도의 많은 변환을 진행합니다.<br>
         *
         * @see SelectionStrength
         * @param selectionStrength 변환 강도 Enum
         * @return Builder 객체(this)
         */
        public Builder setSelectionStrength(SelectionStrength selectionStrength) {
            this.selectionStrength = selectionStrength;
            return this;
        }

        /**
         * 에러를 캐치할 리스너를 설정합니다.
         * @param errorListener 에러 리스너
         * @return Builder 객체(this)
         */
        public Builder setErrorListener(ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        /**
         * 디버그 모드를 설정합니다.<br>
         * 디버그 모드를 켜면 변환의 중간 과정을 표시합니다.
         *
         * @param debugMode 디버그 모드 여부
         * @return Builder 객체(this)
         */
        public Builder setDebugMode(boolean debugMode) {
            this.debugMode = debugMode;
            return this;
        }

        /**
         * 설정된 정보를 바탕으로 YaminjeongeumConverter 객체를 생성합니다.
         * @return YaminjeongeumConverter 객체
         */
        public YaminjeongeumConverter build() {
            return new YaminjeongeumConverter(this, getFile(filePath));
        }

        private YaminjeongeumData getFile(String filePath) {
            File file = new File(filePath);
            return getFile(file);
        }

        private YaminjeongeumData getFile(File file) {
            StringBuilder errorStringBuilder = new StringBuilder("Error | ");
            StringBuilder stringBuilder = new StringBuilder();
            try {
                FileReader file_reader = new FileReader(file);
                int cur = 0;
                while ((cur = file_reader.read()) != -1) {
                    stringBuilder.append((char) cur);
                }
                file_reader.close();
            } catch (FileNotFoundException e) {
                if (errorListener != null) errorStringBuilder.append("File not found. ");
                e.getStackTrace();
            } catch (IOException e) {
                if (errorListener != null) errorStringBuilder.append("IO exception. ");
                e.getStackTrace();
            }

            Gson gson = new Gson();
            YaminjeongeumData yaminjeongeumData = null;
            try {
                yaminjeongeumData = gson.fromJson(stringBuilder.toString(), YaminjeongeumData.class);
            } catch (JsonSyntaxException e) {
                if (errorListener != null) errorStringBuilder.append("Json syntax error. ");
            }

            if (yaminjeongeumData == null) {
                errorStringBuilder.append("Data not created.");
                if(errorListener != null) errorListener.onErrorReceived(errorStringBuilder.toString());
            }

            return yaminjeongeumData;
        }
    }

    /**
     * 변환 강도를 재지정합니다.<br>
     * ORIGINAL : 변환 전 문자열로 변환합니다(야민정음 to 훈민정음)<br>
     * SLIGHT : 크게 변환이 이루어지지 않고 알아보기 어렵지 않은 선에서 변환을 진행합니다.<br>
     * MODERATE : 중간 단계의 변환을 진행합니다.<br>
     * EXTREME : 원본을 알아보기 어려울 정도의 많은 변환을 진행합니다.
     *
     * @see SelectionStrength
     * @param selectionStrength 변환 강도
     */
    public void setSelectionStrength(SelectionStrength selectionStrength) {
        this.selectionStrength = selectionStrength;
    }

    /**
     * 이 객체로 문자열 변환을 진행할 수 있는지를 반환합니다.<br>
     * 만약 이 변수가 false일 경우 ErrorListener에서 나온 에러 내용을 확인 후 조치해 주십시오.<br>
     * 대부분은 정상적이지 않은 파일을 로드할 때 이 변수가 false가 됩니다.
     *
     * @see Builder.ErrorListener
     * @return isAvailable
     */
    public boolean isAvaliable() {
        return isAvaliable;
    }

    /**
     * 데이터베이스의 버전을 반환합니다.
     *
     * @return 데이터베이스 버전
     */
    public int getDatabaseVersion() {
        if(!isAvaliable) return -1;
        return yaminjeongeumData.getVersionInfo().getDbVersion();
    }

    /**
     * 변환을 수행합니다.
     * @param str 변환할 string
     * @return 변환된 데이터
     */
    public String convert(String str) {
        if(!isAvaliable) return str;
        result = new StringBuffer(str);

        for (int i = 0; i < yaminjeongeumData.getDatas().length; i++) {
            RuleData ruleData = yaminjeongeumData.getDatas()[i];
            for (int j = 0; j < ruleData.getRuleStrings().length; j++) {
                String ruleString = ruleData.getRuleStrings()[j];

                int index = result.indexOf(ruleString);
                if (index != -1) {
                    convertVerb(index, ruleData, ruleString);
                }

                while (index >= 0) {
                    index = result.indexOf(ruleString, index + 1);
                    if (index == -1) break;
                    convertVerb(index, ruleData, ruleString);
                }
            }
        }

        return result.toString().replaceAll("\u001B", "");
    }

    private void convertVerb(int index, RuleData ruleData, String ruleString) {
        String resultRuleData;
        char[] utf8Check = new char[1];

        if (result.length() > index + 1)
            result.getChars(index + 1, index + 2, utf8Check, 0);

        if (utf8Check[0] == '\u001B')
            return;

        resultRuleData = joinString(ruleData.getData(selectionStrength.getStrength(), convertHanja), "\u001B");

        if(debugMode) System.out.println("Found " + ruleString + " in " + index +
                ", best result is " + resultRuleData.replaceAll("\u001B", ""));

        result.delete(index, index + ruleString.length());
        result.insert(index, resultRuleData);
        if(debugMode) System.out.println("  → " + result.toString().replaceAll("\u001B", ""));
    }

    private String joinString(String str, String sep) {
        StringBuilder builder = new StringBuilder(str);
        for (int i = builder.length(); i > 0; i--)
            builder.insert(i, sep);

        return builder.toString();
    }

}
