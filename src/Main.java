import converter.SelectionStrength;
import converter.YaminjeongeumConverter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        YaminjeongeumConverter yaminjeongeumConverter = new YaminjeongeumConverter.Builder()
                .setConvertHanja(false)
                .setSelectionStrength(SelectionStrength.EXTREME)
                .setDebugMode(false)
                .build();

        if(yaminjeongeumConverter.isAvaliable()) {
            System.out.println("Database version : " + yaminjeongeumConverter.getDatabaseVersion());
            Scanner scanner = new Scanner(System.in);
            System.out.print("Text input : ");
            String str = scanner.nextLine();

            System.out.println("Convert : " + yaminjeongeumConverter.convert(str));
        }
    }
}
