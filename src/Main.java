import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        YaminjeongeumConverter yaminjeongeumConverter = new YaminjeongeumConverter.Builder()
                .setConvertHanja(false)
                .setSelectionStrength(SelectionStrength.EXTREME)
                .build();

        //yaminjeongeumConverter.setSelectionStrength(SelectionStrength.ORIGINAL);

        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();

        System.out.println("Convert : " + yaminjeongeumConverter.convert(str));
    }
}
