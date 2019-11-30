import converter.SelectionStrength;
import converter.YaminjeongeumConverter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        YaminjeongeumConverter yaminjeongeumConverter = new YaminjeongeumConverter.Builder()
                //.setFilePath("./README.md")
                .setConvertHanja(false)
                .setSelectionStrength(SelectionStrength.EXTREME)
                .setErrorListener(new YaminjeongeumConverter.Builder.ErrorListener() {
                    @Override
                    public void onErrorReceived(String errorString) {
                        System.out.println(errorString);
                    }
                })
                //.setDebugMode(true)
                .build();

        if(yaminjeongeumConverter.isAvaliable()) {
            System.out.println("Database version : " + yaminjeongeumConverter.getDatabaseVersion());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Text input : ");
            String str = scanner.nextLine();

            System.out.println("Convert : " + yaminjeongeumConverter.convert(str));
        }


    }
}
