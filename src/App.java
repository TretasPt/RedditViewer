import java.util.Scanner;

import org.json.JSONObject;

public class App {
    public static void main(String[] args) throws Exception {

        Extras.clearScreen();
        
        Extras.printLogo();
        
        appLoop();

        
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

    private static void fetch() throws Exception{
        // Extras.clearScreen();   
        
        //Get the type(subreddit or user)
        System.out.print("Do you want to search a subreddit or a user? (r/u):\n");
        Scanner input = new Scanner(System.in);
        char type = input.next().toCharArray()[0];
        type=type=='r'?'r':'u';
        System.out.print("\n\n");


        //Get the the username/subreddit
        if (type=='u'){System.out.print("Enter the username:\n");}else{System.out.print("Enter the subreddit:\n");}
        String user = input.next();
        System.out.print("\n\n");


        //Get the time limit
        System.out.print("Enter the time limit for fetching posts(use 0 for unlimited):\n");
        long timeout = input.nextLong();
        System.out.print("\n\n");


        //Fetch the user activity(json)
        JSONObject json = JsonFetcher.fetchJson(user,timeout,type);
        //Print the user activity(json)

        //Save the user activity(json)
        if (saveToFile("json",input)){
            String filenameJson = input.next();
            JsonFetcher.toFile(json, "../output/"+Extras.extensionCheck(filenameJson,"json"));//(json, "../"+filenameJson);
            System.out.println("JSON object saved to \""+Extras.extensionCheck(filenameJson,"json")+"\"");
        }



        //Fetch the user activity(formated text)
        String output=JsonReader.printJson(json);
        //Print the user activity(formated text)

        //Save the user activity(formated text)
        if (saveToFile("formated text",input)){
            String filenameOutput = input.next();
            JsonReader.toFile(output, "../output/"+Extras.extensionCheck(filenameOutput,"txt"));//(json, "../"+filenameJson);
            System.out.println("Output saved to \""+Extras.extensionCheck(filenameOutput,"txt")+"\"");
        }

        input.close();
 
    }

    private static void appLoop() throws Exception{
        fetch();
    }

    private static void helpAndCredits(){

    }
}
