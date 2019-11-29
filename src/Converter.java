import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Converter {

    public YaminjeongeumData yaminjeongeumData;

    public void getFile() {
        StringBuilder stringBuilder = new StringBuilder();
        try{
            //파일 객체 생성
            File file = new File("yamin2.json");
            //입력 스트림 생성
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

        //System.out.println(stringBuilder);

        Gson gson = new Gson();
        yaminjeongeumData = gson.fromJson(stringBuilder.toString(), YaminjeongeumData.class);
    }

    public String convert(String str, int selection, boolean convertHanja) {
        StringBuffer result = new StringBuffer(str);

        System.out.println(result);

        for (int i = 0; i < yaminjeongeumData.getDatas().length; i++) {
            RuleData ruleData = yaminjeongeumData.getDatas()[i];
            for (int j = 0; j < ruleData.getRuleStrings().length; j++) {
                String ruleString = ruleData.getRuleStrings()[j];

                int index = result.indexOf(ruleString);
                if(index != -1) {
                    convertVerb(selection, convertHanja, result, ruleData, ruleString, index);
                }

                while (index >= 0) {
                    index = result.indexOf(ruleString, index + 1);
                    if(index == -1) break;
                    convertVerb(selection, convertHanja, result, ruleData, ruleString, index);
                }
            }
        }

        return result.toString().replaceAll("\u001B", "");
    }

    private void convertVerb(int selection, boolean convertHanja, StringBuffer result, RuleData ruleData, String ruleString, int index) {
        String resultRuleData;
        char[] utf8Check = new char[1];

        if(result.length() < index + 1)
            result.getChars(index + 1, index + 2, utf8Check, 0);

        if(utf8Check[0] == '\u001B') return;

        resultRuleData = joinString(ruleData.getData(selection, convertHanja), "\u001B");

        System.out.println("Found " + ruleString + " in " + index +
                ", best result is " + resultRuleData);

        result.delete(index, index + ruleString.length());
        System.out.println(result);
        result.insert(index, resultRuleData);
        System.out.println(result);
    }

    public static String joinString(String str, String sep) {
        StringBuilder builder = new StringBuilder(str);
        for (int i = builder.length(); i > 0; i--)
            builder.insert(i, sep);

        return builder.toString();
    }

}
