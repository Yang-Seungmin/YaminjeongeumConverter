package converter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

    public static class Builder {
        private String filePath = "yamin2.json";
        private boolean convertHanja = false;
        private SelectionStrength selectionStrength = SelectionStrength.MODERATE;
        private ErrorListener errorListener = null;
        private boolean debugMode = false;

        public interface ErrorListener {
            void onErrorReceived(String errorString);
        }

        public Builder() {
        }


        public Builder setFilePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder setConvertHanja(boolean convertHanja) {
            this.convertHanja = convertHanja;
            return this;
        }

        public Builder setSelectionStrength(SelectionStrength selectionStrength) {
            this.selectionStrength = selectionStrength;
            return this;
        }

        public Builder setErrorListener(ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public Builder setDebugMode(boolean debugMode) {
            this.debugMode = debugMode;
            return this;
        }

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

    public void setSelectionStrength(SelectionStrength selectionStrength) {
        this.selectionStrength = selectionStrength;
    }

    public boolean isAvaliable() {
        return isAvaliable;
    }

    public int getDatabaseVersion() {
        if(!isAvaliable) return -1;
        return yaminjeongeumData.getVersionInfo().getDbVersion();
    }

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
