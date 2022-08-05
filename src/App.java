import java.util.Scanner;

import org.json.JSONObject;

public class App {
    public static void main(String[] args) throws Exception {
        //Get the username
        System.out.print("Enter the username:\n");
        Scanner input = new Scanner(System.in);
        String user = input.next();

        //Fetch the user activity(json)
        JSONObject json = JsonFetcher.fetchJson(user);
        //Save the user activity(json)
        if (saveToFile("json",input)){
            String filenameJson = input.next();
            JsonFetcher.toFile(json, "./output/"+extensionCheck(filenameJson,"json"));//(json, "../"+filenameJson);
            System.out.println("JSON object saved to \""+extensionCheck(filenameJson,"json")+"\"");
        }

        //Fetch the user activity(formated text)
        String output=JsonReader.printJson(json);
        //Save the user activity(formated text)
        if (saveToFile("formated text",input)){
            String filenameOutput = input.next();
            JsonReader.toFile(output, "./output/"+extensionCheck(filenameOutput,"txt"));//(json, "../"+filenameJson);
            System.out.println("Output saved to \""+extensionCheck(filenameOutput,"txt")+"\"");
        }

        input.close();


        
    }

    // private static boolean extensionCheck(String filename, String extention){//True if the extention is the same
    private static String extensionCheck(String filename, String extention){
        String[] filenameArr=filename.split(".");
        // filename=filenameArr[filenameArr.length-1].toLowerCase();
        // extention=extention.toLowerCase();
        if (filenameArr.length<2)
            return filename+"."+extention;
        if (filenameArr[filenameArr.length-1].toLowerCase()!=extention.toLowerCase())
            return filename+"."+extention;
        return filename;

    }

    private static boolean saveToFile(String filetype,Scanner input){
        // Scanner input = new Scanner(System.in);

        System.out.print("Save "+filetype+" to a file?(Y/N)\n");
        char saveopt = input.next().charAt(0);
        System.out.println(saveopt);
        
        // input.close();

        if (saveopt=='y'||saveopt=='Y'){
            System.out.println("Insert the filename or path:");
            // String filenameJson = input.next();
            return true;
        }else{return false;} 
            

        // if (saveopt=='y'||saveopt=='Y'){
        //     System.out.println("Insert the filename or path:");
        //     String filenameJson = input.next();

        //     if()
            
        
            
        // }

    }

    // private void saveToJsonFile(){
    //     JsonFetcher.toFile(json, filenameJson);
    //     System.out.println("JSON object saved to \""+filenameJson+"\"");
    // }
    // private void saveToFormatedTextFile(){
    //     JsonReader.toFile(output, filenameOutput);
    //     System.out.println("Output saved to \""+filenameOutput+"\"");
    // }
}
