import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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

    private static int posts=0;
    private static int words=0;


    public static void main(String[] args)throws JSONException, IOException{

        Path path = Paths.get("example.json");
 
               
        String ls = System.lineSeparator();

        StringBuilder contentBuilder = new StringBuilder();
        Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s).append(ls));
        stream.close();
        JSONObject obj = new JSONObject(contentBuilder.toString());

        printJson(obj);
        
    }

    
    public static String printJson(JSONObject obj){
        String output="";
        JSONArray children = obj.getJSONObject("data").getJSONArray("children");

        for (int i = 0 ; i != children.length();i++){
            
            JSONObject post = children.getJSONObject(i);
            output+=print(post)+"\n\n\n\n";
            // System.out.println(textOutputColorBlue+ statsWc(post)+textOutputColorReset);
            System.out.println("\n\n\n\n");
        }

        System.out.println("Posts: "+posts+"\nWords: "+words+"\nAverage: "+(words/posts));
        return output;
    }

    private static String print(JSONObject post){
        // System.out.println(post.getString("kind"));
        String output="";
        switch (post.getString("kind")){
            case "t1":
            output+=printT1(post);
            break;
            case "t2":
            System.out.println(textOutputColorRed+"An error has occurred. kind="+post.getString("kind")+textOutputColorReset+"\t\tI don't know how to handle t2 yet.");
            output+="An error has occurred. kind="+post.getString("kind")+"\t\tI don't know how to handle t2 yet.";
            break;
            case "t3":
            output+=printT3(post);
            break;
            default:
            System.out.println(textOutputColorRed+"An error has occurred. kind="+post.getString("kind")+textOutputColorReset);
            output+="An error has occurred. kind="+post.getString("kind");
        }
        return output;
        // if (!post.getString("kind").equals("t3")){
        //     System.out.println("1?");
        // }//{continue;}
        // // System.out.println("\n\n\n\n");
        // // System.out.println(children[i].getJSONObject("data").getJSONObject("title"));
        // // System.out.println(post);
        // // System.out.println(post.getJSONObject("data").getString("title")+"\n\n"+post.getJSONObject("data").getString("selftext"));
    }

    private static String printT1(JSONObject post){
        return "";
    }

    private static String printT3(JSONObject post){
        //selftext;title;subreddit;created;created_utc;type(text)
        System.out.println(textOutputColorBlue+post.getJSONObject("data").getString("title")+textOutputColorReset+"\n\n"+post.getJSONObject("data").getString("selftext")+"\n"+textOutputColorBlue+ statsWc(post)+textOutputColorReset);
        words+=statsWc(post);
        posts++;
        return post.getJSONObject("data").getString("title")+"\n\n"+post.getJSONObject("data").getString("selftext")+"\n"+statsWc(post);
    }

    private static int statsWc(JSONObject post){
        return post.getJSONObject("data").getString("selftext").split("[ \n]").length;
    }

    public static void toFile(String object, String filename) throws IOException{
        Writer output = new FileWriter(filename);
        // object.write(output);
        output.write(object);
        output.flush();
        output.close();
    }

}
