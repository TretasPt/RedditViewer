import java.util.Scanner;

import org.json.JSONObject;

public class App {
    public static void main(String[] args) throws Exception {
        // System.out.println("Hello, World!");
        System.out.print("Enter the username:\n");
        Scanner input = new Scanner(System.in);
        String user= input.next();

        JSONObject json = JsonFetcher.fetchJson(user);

        System.out.print("Save json to a file?(Y/N)\n");
        char saveJson = input.next().charAt(0);
        System.out.println(saveJson);
        
        if (saveJson=='y'||saveJson=='Y'){
            System.out.println("Insert the filename or path:");
            String filenameJson = input.next();
            JsonFetcher.toFile(json, filenameJson);
            System.out.println("JSON object saved to \""+filenameJson+"\"");
        }

        String output=JsonReader.printJson(json);

        System.out.print("Save output to a file?(Y/N)\n");
        char saveOut = input.next().charAt(0);
        System.out.println(saveOut);

        if (saveOut=='y'||saveOut=='Y'){
            System.out.println("Insert the filename or path:");
            String filenameOutput = input.next();
            JsonReader.toFile(output, filenameOutput);
            System.out.println("Output saved to \""+filenameOutput+"\"");
        }
        input.close();
        
    }

}
