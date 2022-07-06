import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

    public static final String textOutputColorReset = "\u001B[0m";
    public static final String textOutputColorBlue = "\u001B[34m";
    public static final String textOutputColorRed = "\u001B[31m";

    public static int posts=0;
    public static int words=0;


    public static void main(String[] args)throws JSONException, IOException{

        // Path path = Paths.get("C:/Users/Utilizador/Downloads/notmysecretacount.json");
        Path path = Paths.get("C:/Users/Utilizador/Desktop/reddit... - Cópia.txt");
 
               
        String ls = System.lineSeparator();

        StringBuilder contentBuilder = new StringBuilder();
        Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s).append(ls));
        // System.out.println(contentBuilder.toString());
        stream.close();
        JSONObject obj = new JSONObject(contentBuilder.toString());

        // System.out.println("\n\n\n\ntype: "+JSONObject.getNames(obj));  
        // System.out.println("\n\n\n\ndata:\n "+obj.getJSONObject("data").getJSONArray("children"));  

        printJson(obj);
        
    }

    
    public static void printJson(JSONObject obj){
        JSONArray children = obj.getJSONObject("data").getJSONArray("children");

        for (int i = 0 ; i != children.length();i++){
            
            JSONObject post = children.getJSONObject(i);
            print(post);
            // System.out.println(textOutputColorBlue+ statsWc(post)+textOutputColorReset);
            System.out.println("\n\n\n\n");
        }

        System.out.println("Posts: "+posts+"\nWords: "+words+"\nAverage: "+(words/posts));
    }

    static void print(JSONObject post){
        // System.out.println(post.getString("kind"));
        switch (post.getString("kind")){
            case "t1":
            printT1(post);
            break;
            case "t2":
            System.out.println(textOutputColorRed+"An error has occurred. kind="+post.getString("kind")+textOutputColorReset+"\t\tI don't know how to handle t2 yet.");
            break;
            case "t3":
            printT3(post);
            break;
            default:
            System.out.println(textOutputColorRed+"An error has occurred. kind="+post.getString("kind")+textOutputColorReset);
        }
        // if (!post.getString("kind").equals("t3")){
        //     System.out.println("1?");
        // }//{continue;}
        // // System.out.println("\n\n\n\n");
        // // System.out.println(children[i].getJSONObject("data").getJSONObject("title"));
        // // System.out.println(post);
        // // System.out.println(post.getJSONObject("data").getString("title")+"\n\n"+post.getJSONObject("data").getString("selftext"));
    }

    static void printT1(JSONObject post){

    }

    static void printT3(JSONObject post){
        System.out.println(textOutputColorBlue+post.getJSONObject("data").getString("title")+textOutputColorReset+"\n\n"+post.getJSONObject("data").getString("selftext"));
        System.out.println(textOutputColorBlue+ statsWc(post)+textOutputColorReset);
        words+=statsWc(post);
        posts++;
    }

    static int statsWc(JSONObject post){
        return post.getJSONObject("data").getString("selftext").split("[ \n]").length;
    }

}
