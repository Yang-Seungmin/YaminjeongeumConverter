package converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * 야민정음 변환기 core class입니다.<br>
 * [기본 사용 예시]<br>
 * <pre>
 *         YaminjeongeumConverter yaminjeongeumConverter = new YaminjeongeumConverter.Builder()
 *                 .setFilePath("./yamin2.json")
 *                 .setConvertHanja(false)
 *                 .setSelectionStrength(SelectionStrength.EXTREME)
 *                 .setDebugMode(true)
 *                 .build();
 *                 </pre>
 *
 * @author Yang-Seungmin
 * @since 1.0
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

        if (this.yaminjeongeumData == null) isAvaliable = false;
        else isAvaliable = true;
    }


    /**
     * YaminjeongeumConverter의 Build클래스입니다.<br>
     * 외부에서 YaminjeongeumConverter를 생성하기 위해서는 반드시 Builder 클래스를 거쳐야 합니다.
     */
    public static class Builder {
        private String filePath = "/resource/yamin2.json";
        private boolean convertHanja = false;
        private SelectionStrength selectionStrength = SelectionStrength.MODERATE;
        private boolean debugMode = false;

        public Builder() {
        }

        /**
         * json 파일을 불러올 경로를 설정합니다.
         *
         * @param filePath 파일 경로
         * @return Builder 객체(this)
         */
        public Builder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        /**
         * 한자로의 변환에 대한 허용 여부를 설정합니다.
         *
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
         * @param selectionStrength 변환 강도 Enum
         * @return Builder 객체(this)
         * @see SelectionStrength
         */
        public Builder setSelectionStrength(SelectionStrength selectionStrength) {
            this.selectionStrength = selectionStrength;
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
         *
         * @return YaminjeongeumConverter 객체
         */
        public YaminjeongeumConverter build() {
            return new YaminjeongeumConverter(this, getFile(filePath));

        }

        private YaminjeongeumData getFile(String filePath) {
            StringBuilder stringBuilder = new StringBuilder();

            InputStream inputStream = getClass().getResourceAsStream(filePath);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            int ch = 0;
            try {
                while ((ch = bufferedReader.read()) != -1) {
                    stringBuilder.append((char) ch);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            YaminjeongeumData yaminjeongeumData = null;
            try {
                yaminjeongeumData = gson.fromJson(stringBuilder.toString(), YaminjeongeumData.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
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
     * @param selectionStrength 변환 강도
     * @see SelectionStrength
     */
    public void setSelectionStrength(SelectionStrength selectionStrength) {
        this.selectionStrength = selectionStrength;
    }

    /**
     * 이 객체로 문자열 변환을 진행할 수 있는지를 반환합니다.
     *
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
        if (!isAvaliable) return -1;
        return yaminjeongeumData.getVersionInfo().getDbVersion();
    }

    /**
     * 변환을 수행합니다.
     *
     * @param str 변환할 string
     * @return 변환된 데이터
     */
    public String convert(String str) {
        if (!isAvaliable) return str;
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

        if (debugMode) System.out.println("Found " + ruleString + " in " + index +
                ", best result is " + resultRuleData.replaceAll("\u001B", ""));

        result.delete(index, index + ruleString.length());
        result.insert(index, resultRuleData);
        if (debugMode) System.out.println("  → " + result.toString().replaceAll("\u001B", ""));
    }

    private String joinString(String str, String sep) {
        StringBuilder builder = new StringBuilder(str);
        for (int i = builder.length(); i > 0; i--)
            builder.insert(i, sep);

        return builder.toString();
    }

}
