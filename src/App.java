import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");
        System.out.print("Escreva o nome de utilizador:\n");
        Scanner username = new Scanner(System.in);
        String user= username.next();
        username.close();
        JsonReader.printJson(JsonFetcher.fetchJson(user));
    }
}
