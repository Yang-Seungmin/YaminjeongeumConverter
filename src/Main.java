public class Main {
    public static void main(String[] args) {
        Converter converter = new Converter();
        converter.getFile();
        //System.out.println("Hello");

        System.out.println("Convert : " + converter.convert("괌", 3, false));
    }
}
