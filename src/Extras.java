import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
// import java.io.BufferedInputStream;
// import java.io.InputStream;
// import java.nio.file.Files;
// import java.nio.file.Paths;
import java.util.Scanner;

// import java.awt.event.*;

// // import java.awt.event;
// import java.io.Console;
// import java.lang.System;

// import javax.xml.crypto.dsig.keyinfo.KeyInfo;

public class Extras {

    public static final String textOutputColorReset = "\u001B[0m";
    public static final String textOutputColorBlue = "\u001B[34m";// Post titles and statistics.
    public static final String textOutputColorRed = "\u001B[31m";// Error messages.
    public static final String textOutputColorYellow = "\u001B[33m";// Comments.

    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int[] getPoint() {
            int[] coordinates = { x, y };
            return coordinates;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public static String extensionCheck(String filename, String extention) {
        String[] filenameArr = filename.split(".");
        if (filenameArr.length < 2)
            return filename + "." + extention;
        if (filenameArr[filenameArr.length - 1].toLowerCase() != extention.toLowerCase())
            return filename + "." + extention;
        return filename;

    }

    public static int statsWc(JSONObject post) {
        switch (post.getString("kind")) {
            case "t1":
                // output+=printT1(post);
                return post.getJSONObject("data").getString("body").split("[ \n]").length;
            case "t2":
                System.out.println(textOutputColorRed + "An error has occurred. kind=" + post.getString("kind")
                        + textOutputColorReset + "\t\tI don't know how to handle t2 yet.");
                return -1;
            case "t3":
                return post.getJSONObject("data").getString("selftext").split("[ \n]").length;
            // output+=printT3(post);
            default:
                System.out.println(textOutputColorRed + "An error has occurred. kind=" + post.getString("kind")
                        + textOutputColorReset);
                return -1;
        }
        // return post.getJSONObject("data").getString("selftext").split("[
        // \n]").length;
    }

    public static String simpleGraph(Point[] data) {
        return simpleGraph(data, "", "");
    }

    public static String simpleGraph(Point[] data, String structureColor, String graphColor) {
        return simpleGraph(data, structureColor, graphColor, 2);
    }

    public static String simpleGraph(Point[] data, String structureColor, String graphColor, int line) {
        // ◯■⨷
        // https://www.compart.com/en/unicode/block/U+2580
        char mark;
        switch (line) {
            case 1:
                mark = '◯';
                break;
            case 2:
                mark = '■';
                break;
            default:
                mark = '⨷';
        }

        return simpleGraph(data, structureColor, graphColor, mark);
    }

    public static String simpleGraph(Point[] data, String structureColor, String graphColor, char line) {
        final int MAXCONSOLELENGTH = 150;// 203;// 8;
        final int MAXCONSOLEHIGHT = 40;// 45;// 8;

        return simpleGraph(data, structureColor, graphColor, line, MAXCONSOLELENGTH, MAXCONSOLEHIGHT);
    }

    public static String simpleGraph(Point[] data, String structureColor, String graphColor, char line,
            int maxConsoleLength, int maxConsoleHight) {
        String graph = "";
        // ||||+——————
        int minX, minY, maxX, maxY;

        // limits
        minX = maxX = data[0].getX();
        minY = maxY = data[0].getY();
        for (int i = 0; i != data.length; i++) {
            if (data[i].getX() > maxX)
                maxX = data[i].getX();
            if (data[i].getX() < minX)
                minX = data[i].getX();
            // if(data[i].getY()>maxY)maxY=data[i].getY();
            // if(data[i].getY()<minY)minY=data[i].getY();
        }
        int Xsize = maxX - minX + 1;
        int limitedXsize = Math.min(Xsize, maxConsoleLength);

        // group the points
        int[] values = new int[limitedXsize];
        for (int i = 0; i != data.length; i++)// {System.out.println(data[i].getX());System.out.println(data[i].getX()-minX);System.out.println((data[i].getX()-minX)*limitedXsize);System.out.println((data[i].getX()-minX)*limitedXsize/Xsize);
            // values[(data[i].getX()-minX)*limitedXsize/Xsize]+=data[i].getY();
            values[graphCompression(data[i].getX(), minX, limitedXsize, Xsize)] += data[i].getY();
        // }
        for (int i = 0; i != values.length; i++) {
            // values[(data[i].getX()-minX)*limitedXsize/Xsize]+=data[i].getY();
            if (values[i] > maxY)
                maxY = values[i];
            if (values[i] < minY)
                minY = values[i];
        }

        int Ysize = maxY - minY + 1;
        int limitedYsize = Math.min(Ysize, maxConsoleHight);
        int paddingAmount = Math.max(String.valueOf(maxY).length(), String.valueOf(minY).length());

        // draw the graph limits.
        //// vertical
        for (int j = 0; j != paddingAmount; j++)
            graph += " ";
        graph += "^\n" + printPadded(maxY, paddingAmount) + "|";
        for (int j = 0; j != limitedXsize; j++)
            graph += " ";// "░";//"█";
        graph += "\n";
        for (int i = 1; i != limitedYsize; i++) {
            for (int j = 0; j != paddingAmount; j++)
                graph += " ";
            graph += "|";
            for (int j = 0; j != limitedXsize; j++)
                graph += " ";// "░";//"█";
            graph += "\n";
        }
        //// horizontal
        graph += printPadded(minY, paddingAmount) + "+";
        for (int i = 0; i != limitedXsize; i++)
            graph += "-";
        graph += ">\n";
        graph += "Graph proportions:\nVertical: " + String.valueOf((float) Ysize / limitedYsize)
                + " words\nHorizontal: " + String.valueOf((float) Xsize / limitedXsize) + "seconds = "
                + String.valueOf((float) Xsize / limitedXsize / 3600) + "hours = "
                + String.valueOf((float) Xsize / limitedXsize / 3600 / 24) + "days = "
                + String.valueOf((float) Xsize / limitedXsize / 3600 / 24 / 7) + "weeks = "
                + String.valueOf((float) Xsize / limitedXsize / 3600 / 24 / 30.4) + "months";

        // draw the graph points.
        char[] charGraph = graph.toCharArray();
        // for (int i=0;i!=values.length;i++)drawPoint(charGraph, values[i]);
        drawPoints(charGraph, values, line, paddingAmount, limitedXsize, minY, limitedYsize, Ysize);

        graph = new String(charGraph);
        graph += "\n\nThis graph may not be acurate with some specific amounts of data and graph sizes.\n";
        // graph+="a";

        // graph+="\n\nGRAPH HERE\n\n";

        System.out.println(graph);
        // // System.out.println(charGraph);

        return graph;
    }

    public static String printPadded(int toPrint, int size) {
        assert (String.valueOf(toPrint).length() <= size) : "Tried to apply negative padding.";

        String space = "";
        for (int i = String.valueOf(toPrint).length(); i != size; i++)
            space += " ";
        return space + String.valueOf(toPrint);
    }

    public static void printLogo() {

        // ███████████████████████████████████████████████████████████████████████████████████████████████████████████
        // ███████████████████████████████████████████████████████████████████████████████████████████████████████████
        // ███████████████████████████████████████████████████████████████████████████████████████████████████████████
        // ████████
        // ████████ █████████████████████ ██████████████████████████████████
        // ████████ ████████████████████████ ██████████████████████████████████
        // ████████ ██████ █████ ██████
        // ████████ ██████ █████ ██████
        // ████████ ██████ █████ ██████
        // ████████ ██████ █████ ██████
        // ████████ ██████ █████ ██████
        // ████████ ████████████████████████ ██████
        // ████████ █████████████████████ ██████
        // ████████ ██████ ██████
        // ████████ ██████ ██████
        // ████████ ██████ ██████
        // ████████ ██████ ██████
        // ████████ ██████ ██████
        // ████████ ██████ ██████

        // █████████████████████████████████████████████████████████████████████████████████████████████████████████████
        // ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
        // ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
        // ████████████████░░░░░░███████████████████████████████████████████████████████████████████████████████████████
        // ██░░░░░░██
        // ██░░░░░░██ ██████████████████████ ████████████████████████████████████
        // ██░░░░░░██ ██░░░░░░░░░░░░░░░░░░░░███ ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██
        // ██░░░░░░██ ██░░█████████████████░░░██ █████████████████░░█████████████████
        // ██░░░░░░██ ██░░██ ██░░██ ██░░██
        // ██░░░░░░██ ██░░██ ██░░██ ██░░██
        // ██░░░░░░██ ██░░██ ██░░██ ██░░██
        // ██░░░░░░██ ██░░██ ██░░██ ██░░██
        // ██░░░░░░██ ██░░██ ██░░██ ██░░██
        // ██░░░░░░██ ██░░█████████████████░░░██ ██░░██
        // ██░░░░░░██ ██░░░░░░░░░░░░░░░░░░░░███ ██░░██
        // ██░░░░░░██ ██░░██████████████████ ██░░██
        // ██░░░░░░██ ██░░██ ██░░██
        // ██░░░░░░██ ██░░██ ██░░██
        // ██░░░░░░██ ██░░██ ██░░██
        // ██░░░░░░██ ██░░██ ██░░██
        // ██░░░░░░██ ██░░██ ██░░██
        // ██████████ ██████ ██████

        // System.out.println("███████████████████████████████████████████████████████████████████████████████████████████████████████████\n███████████████████████████████████████████████████████████████████████████████████████████████████████████\n███████████████████████████████████████████████████████████████████████████████████████████████████████████\n
        // ████████\n ████████ █████████████████████
        // ██████████████████████████████████\n ████████ ████████████████████████
        // ██████████████████████████████████\n ████████ ██████ █████ ██████\n ████████
        // ██████ █████ ██████\n ████████ ██████ █████ ██████\n ████████ ██████ █████
        // ██████\n ████████ ██████ █████ ██████\n ████████ ████████████████████████
        // ██████\n ████████ █████████████████████ ██████\n ████████ ██████ ██████\n
        // ████████ ██████ ██████\n ████████ ██████ ██████\n ████████ ██████ ██████\n
        // ████████ ██████ ██████\n ████████ ██████ ██████");
        System.out.println(
                "█████████████████████████████████████████████████████████████████████████████████████████████████████████████\n██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██\n██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██\n████████████████░░░░░░███████████████████████████████████████████████████████████████████████████████████████\n              ██░░░░░░██\n              ██░░░░░░██      ██████████████████████         ████████████████████████████████████\n              ██░░░░░░██      ██░░░░░░░░░░░░░░░░░░░░███      ██░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░██\n              ██░░░░░░██      ██░░█████████████████░░░██     █████████████████░░█████████████████\n              ██░░░░░░██      ██░░██              ██░░██                    ██░░██\n              ██░░░░░░██      ██░░██              ██░░██                    ██░░██\n              ██░░░░░░██      ██░░██              ██░░██                    ██░░██\n              ██░░░░░░██      ██░░██              ██░░██                    ██░░██\n              ██░░░░░░██      ██░░██              ██░░██                    ██░░██\n              ██░░░░░░██      ██░░█████████████████░░░██                    ██░░██\n              ██░░░░░░██      ██░░░░░░░░░░░░░░░░░░░░███                     ██░░██\n              ██░░░░░░██      ██░░██████████████████                        ██░░██\n              ██░░░░░░██      ██░░██                                        ██░░██\n              ██░░░░░░██      ██░░██                                        ██░░██\n              ██░░░░░░██      ██░░██                                        ██░░██\n              ██░░░░░░██      ██░░██                                        ██░░██\n              ██░░░░░░██      ██░░██                                        ██░░██\n              ██████████      ██████                                        ██████\n\n\n\n");
        System.out.println("RedditViewer.\n\n\n\n");
    }

    public static void drawPoints(char[] charGraph, int[] values, char line, int paddingAmount, int graphlength,
            int minY, int limitedYSize, int Ysize) {
        for (int i = 0; i != values.length; i++)
            charGraph[graphCoordinates(i, values[i], paddingAmount, graphlength, minY, limitedYSize, Ysize)] = line;// drawPoint(charGraph,
                                                                                                                    // values[i]);
        // String data = graph[0];

        // graph[0]+="test";

        // charGraph[23]='s';

    }

    public static int graphCoordinates(int x, int y, int paddingAmount, int graphLenght, int minY, int limitedYSize,
            int Ysize) {
        int modifiedX = x;
        int modifiedY;
        modifiedY = limitedYSize - graphCompression(y, minY, limitedYSize, Ysize) - 1;
        // modifiedY=(int)((Math.sin(modifiedX*25*3)+1)*10);

        int temp;
        temp = paddingAmount + 1 + 1;// padding+"^"+"\n"
        temp += (paddingAmount + 1);// goes to the beggining of the graph
        temp += (paddingAmount + 1 + graphLenght + 1) * modifiedY;// modifiedY*graphLenght;
        temp += modifiedX;
        return temp;
    }

    public static int graphCompression(int current, int min, int limitedSize, int size) {

        return (int) (((long) current - min) * limitedSize / size);

    }

    public static void clearScreen(boolean enabled) {
        if (enabled) {
            System.out.print("\033[H\033[2J"); // clears the console(first move the cursor, then clear from the cursor down)
            System.out.flush(); // reset the cursor position
        }

    }

    public static Point getScreenSize(Scanner input,boolean clearScreen) {

        // new KeyListener(){};
        // KeyInfo=Console.
        char[] keypressed = { '0' };

        int x = 12;
        int y = 5;

        lable: while (keypressed.length != 0 && keypressed[0] != 'b') {
            clearScreen(clearScreen);
            // try {
            drawSquare(x, y);
            // } catch (InterruptedException e) {
            // System.err.println("Couldn't determine the screen size.");
            // break;
            // }
            keypressed = input.nextLine().toCharArray();
            for (int i = 0; i != keypressed.length; i++) {
                switch (keypressed[i]) {
                    case 'd':
                        x++;
                        break;
                    case 'a':
                        x--;
                        break;
                    case 's':
                        y++;
                        break;
                    case 'w':
                        y--;
                        break;
                    case 'b':
                        // Maybe should exit the comparasions.
                        break lable;
                    default:
                }
            }
        }

        System.out.println("x= " + x + "\ny= " + y);
        return new Point(x, y);
    }

    // public static void drawSquare(int x, int y) throws InterruptedException {
    public static void drawSquare(int x, int y) {

        // ■--------------------------------------------■
        // | |
        // | Press a/d to increase/decrease the length. |
        // | Press s/w to increase/decrease the height. |
        // | Press ENTER to finish. |
        // | |
        // ■--------------------------------------------■

        // ■----------■
        // |x+=d x-=a|
        // |y+=s y-=w|
        // |Done=ENTER|
        // ■----------■

        if (x >= 46 && y >= 7) {
            System.out.print("■");
            for (int i = 0; i != x - 2; i++) {
                System.out.print("-");
            }
            System.out.print("■\n");

            System.out.print("|");
            System.out.print(" Press d/a to increase/decrease the length. ");
            for (int i = 0; i != x - 46; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");
            System.out.print("|");
            System.out.print(" Press s/w to increase/decrease the height. ");
            for (int i = 0; i != x - 46; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");
            System.out.print("|");
            System.out.print(" Press b to finish.                         ");
            for (int i = 0; i != x - 46; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");

            for (int j = 0; j != y - 5; j++) {
                System.out.print("|");
                for (int i = 0; i != x - 2; i++) {
                    System.out.print(" ");
                }
                System.out.print("|\n");
            }

            System.out.print("■");
            for (int i = 0; i != x - 2; i++) {
                System.out.print("-");
            }
            System.out.print("■\n");
        } else if (x >= 12 && y >= 5) {
            System.out.print("■");
            for (int i = 0; i != x - 2; i++) {
                System.out.print("-");
            }
            System.out.print("■\n");

            System.out.print("|");
            System.out.print("x+=d  x-=a");
            for (int i = 0; i != x - 12; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");
            System.out.print("|");
            System.out.print("y+=s  y-=w");
            for (int i = 0; i != x - 12; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");
            System.out.print("|");
            System.out.print("  Done=b  ");
            for (int i = 0; i != x - 12; i++) {
                System.out.print(" ");
            }
            System.out.print("|\n");

            for (int j = 0; j != y - 5; j++) {
                System.out.print("|");
                for (int i = 0; i != x - 2; i++) {
                    System.out.print(" ");
                }
                System.out.print("|\n");
            }

            System.out.print("■");
            for (int i = 0; i != x - 2; i++) {
                System.out.print("-");
            }
            System.out.print("■\n");
        } else if (x > 1 && y > 1) {
            System.out.print("■");
            for (int i = 0; i != x - 2; i++) {
                System.out.print("-");
            }
            System.out.print("■\n");

            for (int j = 0; j != y - 2; j++) {
                System.out.print("|");
                for (int i = 0; i != x - 2; i++) {
                    System.out.print(" ");
                }
                System.out.print("|\n");
            }

            System.out.print("■");
            for (int i = 0; i != x - 2; i++) {
                System.out.print("-");
            }
            System.out.print("■\n");
            System.out.println("Width and hight values should be positive. press d and s to make them higher.");

        } else {
            x = 12;
            y = 5;
            System.out.println("■----------■\n|x+=d  x-=a|\n|y+=s  y-=w|\n|Done=ENTER|\n■----------■");
            // Thread.sleep(2000);
        }

    }

    public static String showStats(int posts, int wordsP, int comments, int wordsC) {
        String output = "Posts: " + posts + "\nWords: " + wordsP + "\nAverage: ";
        output += posts == 0 ? "---" : (wordsP / posts);
        output += "\n\nComments: " + comments + "\nWords: " + wordsC + "\nAverage: ";
        output += comments == 0 ? "---" : (wordsC / comments);
        return output;
        // return "Posts: "+posts+"\nWords: "+wordsP+"\nAverage:
        // "+(wordsP/posts)+"\n\nComments: "+comments+"\nWords: "+wordsC+"\nAverage:
        // "+(wordsC/comments);
    }

    public static void helpAndCredits() {

    }

    public static boolean saveToFile(String filetype, Scanner input) {

        System.out.print("Save " + filetype + " to a file?(Y/N)\n");
        char saveopt = input.next().charAt(0);
        System.out.println(saveopt);

        if (saveopt == 'y' || saveopt == 'Y') {
            System.out.println("Insert the filename or path:");
            return true;
        } else {
            return false;
        }

    }

    public static boolean isAfirmativeAnswer(String s) {
        String[] affirmatives = { "yes", "y", "true", "1","t" };
        s = s.toLowerCase();
        for (String affirmative : affirmatives) {
            if (affirmative.equals(s))
                return true;
        }
        return false;
    }

    public static List<String> addToArguments(String s){
        return addToArguments(s.split(" "));
    }

    public static List<String> addToArguments(String[] s){
        List<String> list= new LinkedList<String>();
        for (String arg : s)
                list.add(arg);
        return list;
    }

    public static boolean hasRequiredArguments(int existing, int needed, boolean print){
        if(existing<needed){
            if (print){
                System.out.println("Not enough arguments. needs " + needed + " but only has " + existing +" .");
                return false;
            }
        }
        return true;
    }

    // public static void temp_save(String url){
    // BufferedInputStream in = new BufferedInputStream(new
    // URL(FILE_URL).openStream())
    // InputStream in = new URL(FILE_URL).openStream();
    // Files.copy(in, Paths.get(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
    // }
    public static void main(String[] Args) throws InterruptedException {
        // KeyListener keylistener = new KeyListener();
        // getScreenSize(new Scanner(System.in));
        Point[] data = new Point[5];
        data[0] = new Point(0, 20);
        data[1] = new Point(8, 10);
        data[2] = new Point(5, 10);
        data[3] = new Point(10, 10);
        data[4] = new Point(200, 20);
        simpleGraph(data);
    }

}
