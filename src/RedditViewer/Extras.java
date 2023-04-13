package RedditViewer;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    public static final String TEXT_OUTPUT_COLOR_RESET = "\u001B[0m";
    public static final String TEXT_OUTPUT_COLOR_BLUE = "\u001B[34m";// Post titles and statistics.
    public static final String TEXT_OUTPUT_COLOR_RED = "\u001B[31m";// Error messages.
    public static final String TEXT_OUTPUT_COLOR_YELLOW = "\u001B[33m";// Comments.

    public static class Point {
        // private final int x;
        // private final int y;
        private int x;
        private int y;

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

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

    }

    public static class Settings {
        private Map<String, Boolean> settings = new HashMap();
        private Map<String, List<String>> aliases = new HashMap();

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
                System.out.println(TEXT_OUTPUT_COLOR_RED + "An error has occurred. kind=" + post.getString("kind")
                        + TEXT_OUTPUT_COLOR_RESET + "\t\tI don't know how to handle t2 yet.");
                return -1;
            case "t3":
                return post.getJSONObject("data").getString("selftext").split("[ \n]").length;
            // output+=printT3(post);
            default:
                System.out.println(TEXT_OUTPUT_COLOR_RED + "An error has occurred. kind=" + post.getString("kind")
                        + TEXT_OUTPUT_COLOR_RESET);
                return -1;
        }
        // return post.getJSONObject("data").getString("selftext").split("[
        // \n]").length;
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

    public static void printLogo(boolean color) {
        if (color) {
            System.out.println(TEXT_OUTPUT_COLOR_BLUE);
            printLogo();
            System.out.println(TEXT_OUTPUT_COLOR_RESET);
        } else {
            printLogo();
        }
    }

    public static void clearScreen(boolean enabled) {
        if (enabled) {
            System.out.print("\033[H\033[2J"); // clears the console(first move the cursor, then clear from the cursor
                                               // down)
            System.out.flush(); // reset the cursor position
        }

    }

    public static Point getScreenSize(Scanner input, boolean clearScreen, Point screenSize) {

        // new KeyListener(){};
        // KeyInfo=Console.
        char[] keypressed = { '0' };

        int x = screenSize.getX();
        int y = screenSize.getY();

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

    public static Point getScreenSize(Scanner input, boolean clearScreen) {
        return getScreenSize(input, clearScreen, new Point(12, 5));
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
        String[] affirmatives = { "yes", "y", "true", "1", "t" };
        s = s.toLowerCase();
        for (String affirmative : affirmatives) {
            if (affirmative.equals(s))
                return true;
        }
        return false;
    }

    public static List<String> addToArguments(String s) {
        if (s.equals(""))
            return new LinkedList<String>();
        return addToArguments(s.split(" "));
    }

    public static List<String> addToArguments(String[] s) {
        List<String> list = new LinkedList<String>();
        for (String arg : s)
            list.add(arg);
        return list;
    }

    public static boolean hasRequiredArguments(int existing, int needed, boolean print) {
        if (existing < needed) {
            if (print) {
                System.out.println("Not enough arguments. needs " + needed + " but only has " + existing + " .");
                return false;
            }
        }
        return true;
    }

    public static void toFile(JSONObject object, String filename) {
        try {
            Writer output;
            output = new FileWriter(filename);

            object.write(output);
            output.flush();
            output.close();
        } catch (IOException e) {
            System.out.println("Unable to save" + filename + " to a file.");
        }
    }

    public static void toFile(String object, String filename) {
        try {
            Writer output;
            output = new FileWriter(filename);

            output.write(object);
            output.flush();
            output.close();
        } catch (IOException e) {
            System.out.println("Unable to save" + filename + " to a file.");
        }
    }

    private static void toFile(Object object, String filename) {

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
        GraphsTUI.simpleGraph(data);
    }

}
