import java.util.Scanner;

import org.json.JSONObject;

public class App {
    public static void main(String[] args) throws Exception {

        Extras.printLogo();



        //Get the username
        System.out.print("Enter the username:\n");
        Scanner input = new Scanner(System.in);
        String user = input.next();

        //Fetch the user activity(json)
        JSONObject json = JsonFetcher.fetchJson(user);
        //Save the user activity(json)
        if (saveToFile("json",input)){
            String filenameJson = input.next();
            JsonFetcher.toFile(json, "../output/"+Extras.extensionCheck(filenameJson,"json"));//(json, "../"+filenameJson);
            System.out.println("JSON object saved to \""+Extras.extensionCheck(filenameJson,"json")+"\"");
        }

        //Fetch the user activity(formated text)
        String output=JsonReader.printJson(json);
        //Save the user activity(formated text)
        if (saveToFile("formated text",input)){
            String filenameOutput = input.next();
            JsonReader.toFile(output, "../output/"+Extras.extensionCheck(filenameOutput,"txt"));//(json, "../"+filenameJson);
            System.out.println("Output saved to \""+Extras.extensionCheck(filenameOutput,"txt")+"\"");
        }

        input.close();
 
    }

    private static boolean saveToFile(String filetype,Scanner input){

        System.out.print("Save "+filetype+" to a file?(Y/N)\n");
        char saveopt = input.next().charAt(0);
        System.out.println(saveopt);
        

        if (saveopt=='y'||saveopt=='Y'){
            System.out.println("Insert the filename or path:");
            return true;
        }else{
            return false;
        } 
            

    }

}
