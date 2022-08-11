import org.json.JSONObject;

public class Extras {


    public static final String textOutputColorReset = "\u001B[0m";
    public static final String textOutputColorBlue = "\u001B[34m";//Post titles and statistics.
    public static final String textOutputColorRed = "\u001B[31m";//Error messages.
    public static final String textOutputColorYellow = "\u001B[33m";//Comments.




    public static class Point{
        int x;
        int y;

        public Point(int x, int y){
            this.x=x;
            this.y=y;
        }
        public int[] getPoint(){
            int[] coordinates={x,y};
            return coordinates;
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
    }

    public static String extensionCheck(String filename, String extention){
        String[] filenameArr=filename.split(".");
        if (filenameArr.length<2)
            return filename+"."+extention;
        if (filenameArr[filenameArr.length-1].toLowerCase()!=extention.toLowerCase())
            return filename+"."+extention;
        return filename;

    }

    public static int statsWc(JSONObject post){
        switch (post.getString("kind")){
            case "t1":
                // output+=printT1(post);
                return post.getJSONObject("data").getString("body").split("[ \n]").length;
            case "t2":
                System.out.println(textOutputColorRed+"An error has occurred. kind="+post.getString("kind")+textOutputColorReset+"\t\tI don't know how to handle t2 yet.");
                return -1;
            case "t3":
                return post.getJSONObject("data").getString("selftext").split("[ \n]").length;
                // output+=printT3(post);
            default:
                System.out.println(textOutputColorRed+"An error has occurred. kind="+post.getString("kind")+textOutputColorReset);
                return -1;
        }
        // return post.getJSONObject("data").getString("selftext").split("[ \n]").length;
    }

    public static String simpleGraph(Point[] data){
        return simpleGraph(data,"","");
    }
    
    public static String simpleGraph(Point[] data,String structureColor, String graphColor){
        return simpleGraph(data, structureColor, graphColor, 3);
    }
    
    public static String simpleGraph(Point[] data,String structureColor, String graphColor,int line){
        //◯■⨷
        //https://www.compart.com/en/unicode/block/U+2580
        String mark;
        switch (line){
            case 1:
            mark="◯";
            break;
            case 2:
            mark="■";
            break;
            default:
            mark="⨷";
        }

        return simpleGraph(data, structureColor, graphColor, mark);
    }
    
    public static String simpleGraph(Point[] data,String structureColor, String graphColor,String line){
        int MAXCONSOLELENGTH = 150;
        int MAXCONSOLEHIGHT = 40;

        return simpleGraph(data, structureColor, graphColor, line,MAXCONSOLELENGTH,MAXCONSOLEHIGHT);
    }

    public static String simpleGraph(Point[] data,String structureColor, String graphColor,String line,int maxConsoleLength,int maxConsoleHight){
        String graph ="";
        // ||||+——————
        int minX,minY,maxX,maxY;

        //limits
        minX=maxX=data[0].getX();
        minY=maxY=data[0].getY();
        for (int i=0; i!= data.length;i++){
            if(data[i].getX()>maxX)maxX=data[i].getX();
            if(data[i].getX()<minX)minX=data[i].getX();
            if(data[i].getY()>maxY)maxY=data[i].getY();
            if(data[i].getY()<minY)minY=data[i].getY();
        }
        int Xsize =maxX-minX;
        int Ysize =maxY-minY;
        int limitedXsize=Math.min(Xsize, maxConsoleLength);
        int limitedYsize=Math.min(Ysize, maxConsoleHight);
        int rangeSize=Math.max(String.valueOf(maxY).length(), String.valueOf(minY).length());
        



        // //draw the graph limits.

        // //vertical
        // for(int j = 0; j!=rangeSize;j++)graph+=" ";
        // graph+="^\n"+printPadded(maxY,rangeSize)+"|\n";
        // for (int i=0; i!= limitedYsize;i++){
        //     for(int j = 0; j!=rangeSize;j++)graph+=" ";
        //     graph+="|";
        //     for(int j = 0; j!=limitedXsize;j++)graph+=" ";//"░";//"█";
        //     graph+="\n";
        // }
        // //horizontal
        // graph+=printPadded(minY,rangeSize)+"+";
        // for(int i = 0; i!=limitedXsize;i++)graph+="-";
        // graph+=">";





        // //draw the graph points.
        // for (int i=0; i!= data.length;i++){
           
        // }





        graph+="\n\nThis graph may not be acurate with some specific amounts of data and graph sizes.";

        graph+="\n\nGRAPH HERE\n\n";


        System.out.println(graph);

        return graph;


    }

    public static String printPadded(int toPrint,int size){
        assert(String.valueOf(toPrint).length()<=size):"Tried to apply negative padding.";

        String space="";
        for(int i = String.valueOf(toPrint).length();i != size;i++)space+=" ";
        return space+String.valueOf(toPrint);
    }



    public static void main(String[] Args){
        Point[] data=new Point[2];
        data[0]=new Point(-5, -100);
        data[1]=new Point(10000,400);
        simpleGraph(data);
    }

}
