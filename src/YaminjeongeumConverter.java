import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class YaminjeongeumConverter {
    private final YaminjeongeumData yaminjeongeumData;
    private final boolean convertHanja;

    private SelectionStrength selectionStrength;

    private StringBuffer result;

    private YaminjeongeumConverter(Builder builder, YaminjeongeumData yaminjeongeumData) {
        this.yaminjeongeumData = yaminjeongeumData;
        this.convertHanja = builder.convertHanja;
        this.selectionStrength = builder.selectionStrength;
    }

    public static class Builder {
        private String filePath = "yamin2.json";
        private boolean convertHanja = false;
        private SelectionStrength selectionStrength = SelectionStrength.MODERATE;

        public Builder() {}

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

        public YaminjeongeumConverter build() {
            return new YaminjeongeumConverter(this, getFile(filePath));
        }


        private YaminjeongeumData getFile(String filePath) {
            File file = new File("yamin2.json");
            return getFile(file);
        }

        private YaminjeongeumData getFile(File file) {
            StringBuilder stringBuilder = new StringBuilder();
            try{
                FileReader file_reader = new FileReader(file);
                int cur = 0;
                while((cur = file_reader.read()) != -1){
                    stringBuilder.append((char)cur);
                }
                file_reader.close();
            }catch (FileNotFoundException e) {
                e.getStackTrace();
            }catch(IOException e){
                e.getStackTrace();
            }

            Gson gson = new Gson();
            return gson.fromJson(stringBuilder.toString(), YaminjeongeumData.class);
        }
    }

    public void setSelectionStrength(SelectionStrength selectionStrength) {
        this.selectionStrength = selectionStrength;
    }

    public String convert(String str) {
        result = new StringBuffer(str);

        System.out.println(result);

        for (int i = 0; i < yaminjeongeumData.getDatas().length; i++) {
            RuleData ruleData = yaminjeongeumData.getDatas()[i];
            for (int j = 0; j < ruleData.getRuleStrings().length; j++) {
                String ruleString = ruleData.getRuleStrings()[j];

                int index = result.indexOf(ruleString);
                if(index != -1) {
                    convertVerb(index, ruleData, ruleString);
                }

                while (index >= 0) {
                    index = result.indexOf(ruleString, index + 1);
                    if(index == -1) break;
                    convertVerb(index, ruleData, ruleString);
                }
            }
        }

        return result.toString().replaceAll("\u001B", "");
    }

    private void convertVerb(int index, RuleData ruleData, String ruleString) {
        String resultRuleData;
        char[] utf8Check = new char[1];

        if(result.length() < index + 1)
            result.getChars(index + 1, index + 2, utf8Check, 0);

        if(utf8Check[0] == '\u001B') return;

        resultRuleData = joinString(ruleData.getData(selectionStrength.getStrength(), convertHanja), "\u001B");

        //System.out.println("Found " + ruleString + " in " + index + ", best result is " + resultRuleData);

        result.delete(index, index + ruleString.length());
        //System.out.println(result);
        result.insert(index, resultRuleData);
        //System.out.println(result);
    }

    private String joinString(String str, String sep) {
        StringBuilder builder = new StringBuilder(str);
        for (int i = builder.length(); i > 0; i--)
            builder.insert(i, sep);

        return builder.toString();
    }

}
