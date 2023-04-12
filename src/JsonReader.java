import java.io.File;
import java.io.FileNotFoundException;
// import java.io.FileWriter;
import java.io.IOException;
// import java.io.Writer;
import java.util.HashSet;
// import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReader {

    // private static final String textOutputColorReset = "\u001B[0m";
    // private static final String textOutputColorBlue = "\u001B[34m";// Post titles
    // and statistics.
    // private static final String textOutputColorRed = "\u001B[31m";// Error
    // messages.
    // private static final String textOutputColorYellow = "\u001B[33m";// Comments.

    private static int posts = 0;
    private static int wordsP = 0;
    private static int comments = 0;
    private static int wordsC = 0;
    private static Set<String> images = new HashSet<String>();
    private static Set<String> videos = new HashSet<String>();
    private static Set<String> links = new HashSet<String>();
    private static Set<String> links_unknown = new HashSet<String>();
    private static Set<String> temp_types = new HashSet<String>();

    public static void main(String[] args) throws JSONException, IOException {

        String data = "";
        try {
            File myObj = new File("output/example.json");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
                // System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject(data);

        printJson(obj);
    }

    public static String printJson(JSONObject obj) {
        String output = "";
        JSONArray children = obj.getJSONObject("data").getJSONArray("children");
        Extras.Point[] graphPoints = new Extras.Point[children.length()];

        images.clear();
        videos.clear();
        links.clear();
        links_unknown.clear();
        temp_types.clear();

        for (int i = 0; i != children.length(); i++) {

            JSONObject post = children.getJSONObject(i);
            output += print(post);
            output += "\n\n------------------------------------------------------------------------------------------------------------------------------------------------------\n\n";
            // System.out.println(textOutputColorBlue+ statsWc(post)+textOutputColorReset);
            // System.out.println("\n\n------------------------------------------------------------------------------------------------------------------------------------------------------\n\n");

            int x = post.getJSONObject("data").getInt("created_utc");
            // int y=Extras.statsWc(new
            // JSONObject(post.getJSONObject("data").getInt("created_utc")));
            int y = Extras.statsWc(post);

            graphPoints[i] = new Extras.Point(x, y);
            // post.getJSONObject("data").getString("created_utc");
        }

        output += "\n\n" + images;
        output += "\n\n" + videos;
        output += "\n\n" + links;
        output += "\n\n" + links_unknown;
        output += "\n\n" + temp_types;

        // Graph
        // System.out.println("\n\n" +
        // Extras.simpleGraph(graphPoints,"textOutputColorBlue","textOutputColorYellow"));
        output += "\n\n" + Extras.simpleGraph(graphPoints);

        // Statistics
        // System.out.println("Posts: "+posts+"\nWords: "+wordsP+"\nAverage:
        // "+(wordsP/posts)+"\n\nComments: "+comments+"\nWords: "+wordsC+"\nAverage:
        // "+(wordsC/comments));

        // output+="Posts: "+posts+"\nWords: "+wordsP+"\nAverage:
        // "+(wordsP/posts)+"\n\nComments: "+comments+"\nWords: "+wordsC+"\nAverage:
        // "+(wordsC/comments);
        output += Extras.showStats(posts, wordsP, comments, wordsC);

        System.out.println(output);// temporary
        return output;
    }

    private static String print(JSONObject post) {
        String textOutputColorRed = Extras.TEXT_OUTPUT_COLOR_RED;
        String textOutputColorReset = Extras.TEXT_OUTPUT_COLOR_RESET;
        String output = "";
        switch (post.getString("kind")) {
            case "t1":
                output += printT1(post);
                break;
            case "t2":
                System.out.println(textOutputColorRed + "An error has occurred. kind=" + post.getString("kind")
                        + textOutputColorReset + "\t\tI don't know how to handle t2 yet.");
                output += "An error has occurred. kind=" + post.getString("kind")
                        + "\t\tI don't know how to handle t2 yet.";
                break;
            case "t3":
                output += printT3(post);
                break;
            default:
                System.out.println(textOutputColorRed + "An error has occurred. kind=" + post.getString("kind")
                        + textOutputColorReset);
                output += "An error has occurred. kind=" + post.getString("kind");
        }
        return output;
    }

    private static String printT1(JSONObject post) {
        // System.out.println(textOutputColorYellow+post.getJSONObject("data").getString("body")+textOutputColorReset+
        // "\n"+Extras.statsWc(post)+textOutputColorReset);
        wordsC += Extras.statsWc(post);
        comments++;
        // return
        // post.getJSONObject("data").getString("title")+"\n\n"+post.getJSONObject("data").getString("selftext")+"\n"+statsWc(post);
        return post.getJSONObject("data").getString("body") + "\n" + Extras.statsWc(post);
    }

    private static String printT3(JSONObject post) {
        // selftext;title;subreddit;created;created_utc;type(text)
        // System.out.println(textOutputColorBlue+post.getJSONObject("data").getString("title")+textOutputColorReset+"\n\n"+post.getJSONObject("data").getString("selftext")+"\n"+textOutputColorBlue+
        // Extras.statsWc(post)+textOutputColorReset);
        wordsP += Extras.statsWc(post);
        posts++;
        String media = "";
        temp_types.add(post.getJSONObject("data").optString("post_hint"));
        if (post.getJSONObject("data").optString("post_hint").equals("image")) {
            media += "IMAGE\nLink: " + post.getJSONObject("data").optString("url") + "\n\n";
            images.add(post.getJSONObject("data").optString("url"));
        } else if (post.getJSONObject("data").optString("post_hint").equals("rich:video")
                || post.getJSONObject("data").optString("post_hint").equals("hosted:video")) {
            media += "VIDEO\nLink: " + post.getJSONObject("data").optString("url") + "\n\n";
            videos.add(post.getJSONObject("data").optString("url"));
        } else if (post.getJSONObject("data").optString("post_hint").equals("link")) {
            media += "LINK\nLink: " + post.getJSONObject("data").optString("url") + "\n\n";
            links.add(post.getJSONObject("data").optString("url"));
        } else {
            media += "LINK\nLink: " + post.getJSONObject("data").optString("url") + "\n\n";
            links.add(post.getJSONObject("data").optString("url"));
        }
        ;
        return post.getJSONObject("data").getString("title") + "\n\n" + post.getJSONObject("data").getString("selftext")
                + "\n" + media + "\n\n" + Extras.statsWc(post);
    }

}
