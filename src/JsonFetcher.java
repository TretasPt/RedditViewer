import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonFetcher {

    public static void main(String[] args) throws Exception{
        System.out.print("\n\n\n"+fetchJson("notmysecretacount")+"\n\n\n");
        // fetchJson("notmysecretacount");
    }

    private static JSONObject fetchJsonShort(String user,String startingPoint) throws Exception {
        String after ="";
        if (startingPoint!=""){after="?after="+startingPoint;}
        String url = "https://reddit.com/u/"+user+".json"+after;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print in String
        // System.out.println(response.toString());
        //Read JSON response and print
        return new JSONObject(response.toString());

      }

    public static JSONObject fetchJson(String user) throws Exception {
        JSONObject fullJSON = fetchJsonLatest(user);

        JSONObject fullData = fullJSON.getJSONObject("data");
        JSONObject tempData;
        JSONArray childrenArray;
        String after = fullData.optString("after");
        // System.out.println(after);

        while(after!=""){
            tempData=fetchJsonShort(user,after).getJSONObject("data");
            after=tempData.optString("after");
            childrenArray=tempData.getJSONArray("children");

            for (int i = 0 ; i != childrenArray.length();i++){
                fullData.accumulate("children", childrenArray.getJSONObject(i));
            }

            
            fullData.putOpt("after", after);
            
            // System.out.println(after);
        }

        return fullJSON;
    }

    public static JSONObject fetchJsonLatest(String user) throws Exception {
      return fetchJsonShort(user, "");
    }

    public static void toFile(JSONObject object, String filename) throws IOException{
        Writer output = new FileWriter(filename);
        object.write(output);
        output.flush();
        output.close();
    }
    
}
