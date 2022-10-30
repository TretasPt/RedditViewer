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
        System.out.print("\n\n\n"+fetchJson("TretasPt",0,'u')+"\n\n\n");//My main reddit account.
    }
    

    private static JSONObject fetchJsonShort(String user,String startingPoint,char type) throws Exception {
        String after ="";
        if (startingPoint!=""){after="?after="+startingPoint;}
        String url = "https://reddit.com/"+type+"/"+user+".json"+after;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        // int responseCode = con.getResponseCode();
        // System.out.println("\nSending 'GET' request to URL : " + url);
        // System.out.println("Response Code : " + responseCode);
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

    public static JSONObject fetchJson(String user,long timeout,char type) throws Exception {//timeout in seconds

        boolean unlimited = timeout>0 ? false : true;
        long initialTime= System.nanoTime();
        // System.out.println(initialTime);
        
        JSONObject fullJSON = fetchJsonLatest(user,type);

        JSONObject fullData = fullJSON.getJSONObject("data");
        JSONObject tempData;
        JSONArray childrenArray;
        String after = fullData.optString("after");
        // System.out.println(after);

        
        System.out.println("Posts found: "+ fullData.getInt("dist"));

        while(after!="" && (unlimited || System.nanoTime()-initialTime <= timeout*1000000000)){
            // System.out.println(initialTime+"-"+System.nanoTime()+"="+(System.nanoTime()-initialTime)+"<="+timeout*1000000000+"-->"+(System.nanoTime()-initialTime <= timeout*1000000000));
            tempData=fetchJsonShort(user,after,type).getJSONObject("data");
            after=tempData.optString("after");
            childrenArray=tempData.getJSONArray("children");

            for (int i = 0 ; i != childrenArray.length();i++){
                fullData.accumulate("children", childrenArray.getJSONObject(i));
            }

            fullData.put("dist", fullData.getInt("dist")+tempData.getInt("dist"));
            
            fullData.putOpt("after", after);
            
            System.out.println("Posts found: "+ fullData.getInt("dist"));

            // System.out.println(after);
        }

        System.out.println(fullJSON);//temporary

        return fullJSON;
    }

    public static JSONObject fetchJsonLatest(String user,char type) throws Exception {
      return fetchJsonShort(user, "",type);
    }

    public static void toFile(JSONObject object, String filename) throws IOException{
        Writer output = new FileWriter(filename);
        object.write(output);
        output.flush();
        output.close();
    }

}
