import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

    

    public static final String textOutputColorReset = "\u001B[0m";
    public static final String textOutputColorBlue = "\u001B[34m";//Post titles and statistics.
    public static final String textOutputColorRed = "\u001B[31m";//Error messages.
    public static final String textOutputColorYellow = "\u001B[33m";//Comments.

    private static int posts=0;
    private static int wordsP=0;
    private static int comments=0;
    private static int wordsC=0;


    public static void main(String[] args)throws JSONException, IOException{

        String data="";
        try {
            File myObj = new File("output/example.json");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
              data += myReader.nextLine();
            //   System.out.println(data);
            }
            myReader.close();
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }

        JSONObject obj = new JSONObject(data);

        printJson(obj);
    }

    public static String printJson(JSONObject obj){
        String output="";
        JSONArray children = obj.getJSONObject("data").getJSONArray("children");
        Extras.Point[] graphPoints=new Extras.Point[children.length()];

        for (int i = 0 ; i != children.length();i++){
            
            JSONObject post = children.getJSONObject(i);
            output+=print(post)+"\n\n------------------------------------------------------------------------------------------------------------------------------------------------------\n\n";
            // System.out.println(textOutputColorBlue+ statsWc(post)+textOutputColorReset);
            System.out.println("\n\n------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");


            int x=post.getJSONObject("data").getInt("created_utc");
            // int y=Extras.statsWc(new JSONObject(post.getJSONObject("data").getInt("created_utc")));
            int y=Extras.statsWc(post);

            graphPoints[i]=new Extras.Point(x,y);
            // post.getJSONObject("data").getString("created_utc");
        }

        //Graph
        System.out.println("\n\n" + Extras.simpleGraph(graphPoints,"textOutputColorBlue","textOutputColorYellow"));
        output+="\n\n" + Extras.simpleGraph(graphPoints);
    
        //Statistics
        System.out.println("Posts: "+posts+"\nWords: "+wordsP+"\nAverage: "+(wordsP/posts)+"\n\nComments: "+comments+"\nWords: "+wordsC+"\nAverage: "+(wordsC/comments));
        output+="Posts: "+posts+"\nWords: "+wordsP+"\nAverage: "+(wordsP/posts)+"\n\nComments: "+comments+"\nWords: "+wordsC+"\nAverage: "+(wordsC/comments);

        return output;
    }

    private static String print(JSONObject post){
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
    }

    private static String printT1(JSONObject post){
        System.out.println(textOutputColorYellow+post.getJSONObject("data").getString("body")+textOutputColorReset+ "\n"+Extras.statsWc(post)+textOutputColorReset);
        wordsC+=Extras.statsWc(post);
        comments++;
        // return post.getJSONObject("data").getString("title")+"\n\n"+post.getJSONObject("data").getString("selftext")+"\n"+statsWc(post);
        return post.getJSONObject("data").getString("body")+ "\n"+Extras.statsWc(post);
    }

    private static String printT3(JSONObject post){
        //selftext;title;subreddit;created;created_utc;type(text)
        System.out.println(textOutputColorBlue+post.getJSONObject("data").getString("title")+textOutputColorReset+"\n\n"+post.getJSONObject("data").getString("selftext")+"\n"+textOutputColorBlue+ Extras.statsWc(post)+textOutputColorReset);
        wordsP+=Extras.statsWc(post);
        posts++;
        return post.getJSONObject("data").getString("title")+"\n\n"+post.getJSONObject("data").getString("selftext")+"\n"+Extras.statsWc(post);
    }

    public static void toFile(String object, String filename) throws IOException{
        Writer output = new FileWriter(filename);
        // object.write(output);
        output.write(object);
        output.flush();
        output.close();
    }

}
