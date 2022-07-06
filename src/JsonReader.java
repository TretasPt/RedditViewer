import org.json.*;
// // import org.json.simple.*;
// // import java.nio.file.Files;
import java.nio.file.Paths;
// import java.nio.file.Path;
// // import java.nio.file.Paths;
// import java.io.File;
// import java.io.IOException;

// import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

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
        JSONArray children = obj.getJSONObject("data").getJSONArray("children");

        for (int i = 0 ; i != children.length();i++){
            
            JSONObject ppGood = children.getJSONObject(i);
            print(ppGood);
            // System.out.println(textOutputColorBlue+ statsWc(ppGood)+textOutputColorReset);
            System.out.println("\n\n\n\n");
        }

        System.out.println("Posts: "+posts+"\nWords: "+words+"\nAverage: "+(words/posts));
    }

    static void print(JSONObject ppGood){
        // System.out.println(ppGood.getString("kind"));
        switch (ppGood.getString("kind")){
            case "t1":
            printT1(ppGood);
            break;
            case "t2":
            System.out.println(textOutputColorRed+"An error has occurred. kind="+ppGood.getString("kind")+textOutputColorReset+"\t\tI don't know how to handle t2 yet.");
            break;
            case "t3":
            printT3(ppGood);
            break;
            default:
            System.out.println(textOutputColorRed+"An error has occurred. kind="+ppGood.getString("kind")+textOutputColorReset);
        }
        // if (!ppGood.getString("kind").equals("t3")){
        //     System.out.println("1?");
        // }//{continue;}
        // // System.out.println("\n\n\n\n");
        // // System.out.println(children[i].getJSONObject("data").getJSONObject("title"));
        // // System.out.println(ppGood);
        // // System.out.println(ppGood.getJSONObject("data").getString("title")+"\n\n"+ppGood.getJSONObject("data").getString("selftext"));
    }

    static void printT1(JSONObject ppGood){

    }

    static void printT3(JSONObject ppGood){
        System.out.println(textOutputColorBlue+ppGood.getJSONObject("data").getString("title")+textOutputColorReset+"\n\n"+ppGood.getJSONObject("data").getString("selftext"));
        System.out.println(textOutputColorBlue+ statsWc(ppGood)+textOutputColorReset);
        words+=statsWc(ppGood);
        posts++;
    }

    static int statsWc(JSONObject ppGood){
        return ppGood.getJSONObject("data").getString("selftext").split("[ \n]").length;
    }

}
