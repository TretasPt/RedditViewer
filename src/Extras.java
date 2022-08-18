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
        return simpleGraph(data, structureColor, graphColor, 2);
    }
    
    public static String simpleGraph(Point[] data,String structureColor, String graphColor,int line){
        //◯■⨷
        //https://www.compart.com/en/unicode/block/U+2580
        char mark;
        switch (line){
            case 1:
            mark='◯';
            break;
            case 2:
            mark='■';
            break;
            default:
            mark='⨷';
        }

        return simpleGraph(data, structureColor, graphColor, mark);
    }
    
    public static String simpleGraph(Point[] data,String structureColor, String graphColor,char line){
        int MAXCONSOLELENGTH =150;// 8;
        int MAXCONSOLEHIGHT =40;// 8;

        return simpleGraph(data, structureColor, graphColor, line,MAXCONSOLELENGTH,MAXCONSOLEHIGHT);
    }

    public static String simpleGraph(Point[] data,String structureColor, String graphColor,char line,int maxConsoleLength,int maxConsoleHight){
        String graph ="";
        // ||||+——————
        int minX,minY,maxX,maxY;

        //limits
        minX=maxX=data[0].getX();
        minY=maxY=data[0].getY();
        for (int i=0; i!= data.length;i++){
            if(data[i].getX()>maxX)maxX=data[i].getX();
            if(data[i].getX()<minX)minX=data[i].getX();
            // if(data[i].getY()>maxY)maxY=data[i].getY();
            // if(data[i].getY()<minY)minY=data[i].getY();
        }
        int Xsize =maxX-minX+1;
        int limitedXsize=Math.min(Xsize, maxConsoleLength);
        


        //group the points
        int[] values=new int[limitedXsize];
        for (int i=0; i!= data.length;i++)//{System.out.println(data[i].getX());System.out.println(data[i].getX()-minX);System.out.println((data[i].getX()-minX)*limitedXsize);System.out.println((data[i].getX()-minX)*limitedXsize/Xsize);
            // values[(data[i].getX()-minX)*limitedXsize/Xsize]+=data[i].getY();
            values[graphCompression(data[i].getX(), minX, limitedXsize, Xsize)]+=data[i].getY();
        //}
        for (int i=0; i!= values.length;i++){
            // values[(data[i].getX()-minX)*limitedXsize/Xsize]+=data[i].getY();
            if(values[i]>maxY)maxY=values[i];
            if(values[i]<minY)minY=values[i];
        }




        int Ysize =maxY-minY+1;
        int limitedYsize=Math.min(Ysize, maxConsoleHight);
        int paddingAmount=Math.max(String.valueOf(maxY).length(), String.valueOf(minY).length());

        //draw the graph limits.
        ////vertical
        for(int j = 0; j!=paddingAmount;j++)graph+=" ";
        graph+="^\n"+printPadded(maxY,paddingAmount)+"|";
        for(int j = 0; j!=limitedXsize;j++)graph+=" ";//"░";//"█";
        graph+="\n";
        for (int i=1; i!= limitedYsize;i++){
            for(int j = 0; j!=paddingAmount;j++)graph+=" ";
            graph+="|";
            for(int j = 0; j!=limitedXsize;j++)graph+=" ";//"░";//"█";
            graph+="\n";
        }
        ////horizontal
        graph+=printPadded(minY,paddingAmount)+"+";
        for(int i = 0; i!=limitedXsize;i++)graph+="-";
        graph+=">\n";
        graph+="Graph proportions:\nVertical: "+String.valueOf((float)Ysize/limitedYsize)+"\nHorizontal: "+String.valueOf((float)Xsize/limitedXsize);


        //draw the graph points.
        char[] charGraph=graph.toCharArray();
        // for (int i=0;i!=values.length;i++)drawPoint(charGraph, values[i]);
        drawPoints(charGraph, values,line,paddingAmount,limitedXsize,minY,limitedYsize,Ysize);




        graph=new String(charGraph);
        graph+="\n\nThis graph may not be acurate with some specific amounts of data and graph sizes.";
        // graph+="a";

        // graph+="\n\nGRAPH HERE\n\n";


        System.out.println(graph);
        // System.out.println(charGraph);


        return graph;
    }

    public static String printPadded(int toPrint,int size){
        assert(String.valueOf(toPrint).length()<=size):"Tried to apply negative padding.";

        String space="";
        for(int i = String.valueOf(toPrint).length();i != size;i++)space+=" ";
        return space+String.valueOf(toPrint);
    }

    public static void printLogo(){

        
        //███████████████████████████████████████████████████████████████████████████████████████████████████████████
        //███████████████████████████████████████████████████████████████████████████████████████████████████████████
        //███████████████████████████████████████████████████████████████████████████████████████████████████████████
        //              ████████
        //              ████████      █████████████████████          ██████████████████████████████████
        //              ████████      ████████████████████████       ██████████████████████████████████
        //              ████████      ██████              █████                   ██████
        //              ████████      ██████              █████                   ██████
        //              ████████      ██████              █████                   ██████
        //              ████████      ██████              █████                   ██████
        //              ████████      ██████              █████                   ██████
        //              ████████      ████████████████████████                    ██████
        //              ████████      █████████████████████                       ██████
        //              ████████      ██████                                      ██████
        //              ████████      ██████                                      ██████
        //              ████████      ██████                                      ██████
        //              ████████      ██████                                      ██████
        //              ████████      ██████                                      ██████
        //              ████████      ██████                                      ██████

        //█████████████████████████████████████████████████████████████████████████████████████████████████████████████
        //██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
        //██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
        //████████████████░░░░░░███████████████████████████████████████████████████████████████████████████████████████
        //              ██░░░░░░██
        //              ██░░░░░░██      ██████████████████████         ████████████████████████████████████
        //              ██░░░░░░██      ██░░░░░░░░░░░░░░░░░░░░███      ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
        //              ██░░░░░░██      ██░░█████████████████░░░██     █████████████████░░█████████████████
        //              ██░░░░░░██      ██░░██              ██░░██                    ██░░██
        //              ██░░░░░░██      ██░░██              ██░░██                    ██░░██
        //              ██░░░░░░██      ██░░██              ██░░██                    ██░░██
        //              ██░░░░░░██      ██░░██              ██░░██                    ██░░██
        //              ██░░░░░░██      ██░░██              ██░░██                    ██░░██
        //              ██░░░░░░██      ██░░█████████████████░░░██                    ██░░██
        //              ██░░░░░░██      ██░░░░░░░░░░░░░░░░░░░░███                     ██░░██
        //              ██░░░░░░██      ██░░██████████████████                        ██░░██
        //              ██░░░░░░██      ██░░██                                        ██░░██
        //              ██░░░░░░██      ██░░██                                        ██░░██
        //              ██░░░░░░██      ██░░██                                        ██░░██
        //              ██░░░░░░██      ██░░██                                        ██░░██
        //              ██░░░░░░██      ██░░██                                        ██░░██
        //              ██████████      ██████                                        ██████
        
        
        
        // System.out.println("███████████████████████████████████████████████████████████████████████████████████████████████████████████\n███████████████████████████████████████████████████████████████████████████████████████████████████████████\n███████████████████████████████████████████████████████████████████████████████████████████████████████████\n              ████████\n              ████████      █████████████████████          ██████████████████████████████████\n              ████████      ████████████████████████       ██████████████████████████████████\n              ████████      ██████              █████                   ██████\n              ████████      ██████              █████                   ██████\n              ████████      ██████              █████                   ██████\n              ████████      ██████              █████                   ██████\n              ████████      ██████              █████                   ██████\n              ████████      ████████████████████████                    ██████\n              ████████      █████████████████████                       ██████\n              ████████      ██████                                      ██████\n              ████████      ██████                                      ██████\n              ████████      ██████                                      ██████\n              ████████      ██████                                      ██████\n              ████████      ██████                                      ██████\n              ████████      ██████                                      ██████");
        System.out.println("█████████████████████████████████████████████████████████████████████████████████████████████████████████████\n██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██\n██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██\n████████████████░░░░░░███████████████████████████████████████████████████████████████████████████████████████\n              ██░░░░░░██\n              ██░░░░░░██      ██████████████████████         ████████████████████████████████████\n              ██░░░░░░██      ██░░░░░░░░░░░░░░░░░░░░███      ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██\n              ██░░░░░░██      ██░░█████████████████░░░██     █████████████████░░█████████████████\n              ██░░░░░░██      ██░░██              ██░░██                    ██░░██\n              ██░░░░░░██      ██░░██              ██░░██                    ██░░██\n              ██░░░░░░██      ██░░██              ██░░██                    ██░░██\n              ██░░░░░░██      ██░░██              ██░░██                    ██░░██\n              ██░░░░░░██      ██░░██              ██░░██                    ██░░██\n              ██░░░░░░██      ██░░█████████████████░░░██                    ██░░██\n              ██░░░░░░██      ██░░░░░░░░░░░░░░░░░░░░███                     ██░░██\n              ██░░░░░░██      ██░░██████████████████                        ██░░██\n              ██░░░░░░██      ██░░██                                        ██░░██\n              ██░░░░░░██      ██░░██                                        ██░░██\n              ██░░░░░░██      ██░░██                                        ██░░██\n              ██░░░░░░██      ██░░██                                        ██░░██\n              ██░░░░░░██      ██░░██                                        ██░░██\n              ██████████      ██████                                        ██████\n\n\n\n");
        System.out.println("RedditViewer.\n\n\n\n");
    }

    public static void drawPoints(char[] charGraph,int[] values, char line,int paddingAmount,int graphlength,int minY,int limitedYSize,int Ysize){
        for (int i=0;i!=values.length;i++)charGraph[graphCoordinates(i,values[i],paddingAmount,graphlength,minY,limitedYSize,Ysize)]=line;//drawPoint(charGraph, values[i]);
        // String data = graph[0];

        // graph[0]+="test";

        // charGraph[23]='s';

    }

    public static int graphCoordinates(int x,int y,int paddingAmount,int graphLenght,int minY,int limitedYSize,int Ysize){
        int modifiedX=x;
        int modifiedY;
        modifiedY=limitedYSize-graphCompression(y, minY, limitedYSize, Ysize)-1;
        // modifiedY=(int)((Math.sin(modifiedX*25*3)+1)*10);

        int temp;
        temp=paddingAmount+1+1;//padding+"^"+"\n"
        temp+=(paddingAmount+1);//goes to the beggining of the graph
        temp+=(paddingAmount+1+graphLenght+1)*modifiedY;//modifiedY*graphLenght;
        temp+=modifiedX;
        return temp;
    }

    public static int graphCompression(int current,int min,int limitedSize, int size){

        return(int)(((long)current-min)*limitedSize/size);

    }


    public static void main(String[] Args){
        Point[] data=new Point[5];
        data[0]=new Point(0, 20);
        data[1]=new Point(8, 10);
        data[2]=new Point(5, 10);
        data[3]=new Point(10, 10);
        data[4]=new Point(200,20);
        simpleGraph(data);
    }

}
