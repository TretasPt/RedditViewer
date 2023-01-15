import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONObject;

public class App {

    private boolean saveJason;
    private boolean saveOutput;
    private boolean showJason;
    private boolean showOutput;
    private boolean newUser;
    private boolean clearScreen;
    // private final boolean clearScreen;
    // private int screenHeight;
    // private int screenWidth;
    private boolean colorTerminal;
    private Extras.Point screenSize;
    
    private List<String> arguments = new LinkedList<String>();
    private final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        Extras.clearScreen(true);
        System.out.println();

        App thisApp = new App(args);

        Extras.printLogo();

        thisApp.appLoop();

    }

    public App(String[] args) {
        if (args.length == 0) {
            getAndSetDefaults();
            setScreenSize(Extras.getScreenSize(input, clearScreen));
            // this.arguments = new LinkedList<String>();
        } else {
            saveJason = false;
            saveOutput = false;
            showJason = false;
            showOutput = true;
            newUser = false;
            clearScreen = true;
            colorTerminal = false;
            screenSize = new Extras.Point(150, 40);
            // screenHeight = 40;
            // screenWidth = 150;
            // arguments = new LinkedList<String>();
            // for (String s : args)
            // arguments.add(s);
            arguments.addAll(Extras.addToArguments(args));

        }
    }

    private void fetch() {
        // Extras.clearScreen();

        // Get the type(subreddit or user)
        System.out.print("Do you want to search a subreddit or a user? (r/u):\n");
        char type = input.next().toCharArray()[0];
        type = type == 'r' ? 'r' : 'u';
        System.out.print("\n\n");

        // Get the the username/subreddit
        if (type == 'u') {
            System.out.print("Enter the username:\n");
        } else {
            System.out.print("Enter the subreddit:\n");
        }
        String user = input.next();
        System.out.print("\n\n");

        // Get the time limit
        System.out.print("Enter the time limit for fetching posts(use 0 for unlimited):\n");
        long timeout = input.nextLong();
        System.out.print("\n\n");

        // Fetch the user activity(json)
        JSONObject json;
        try {
            json = JsonFetcher.fetchJson(user, timeout, type);
        } catch (IOException e) {
            System.out.println("Unable to get the requested information of type " + type + " and query " + user);
            return;
        }
        // Print the user activity(json)

        // Save the user activity(json)
        if (Extras.saveToFile("json", input)) {
            String filenameJson = input.next();
            Extras.toFile(json, "../output/" + Extras.extensionCheck(filenameJson, "json"));// (json,
                                                                                            // "../"+filenameJson);
            System.out.println("JSON object saved to \"" + Extras.extensionCheck(filenameJson, "json") + "\"");
        }

        // Fetch the user activity(formated text)
        String output = JsonReader.printJson(json);
        // Print the user activity(formated text)

        // Save the user activity(formated text)
        if (Extras.saveToFile("formated text", input)) {
            String filenameOutput = input.next();
            Extras.toFile(output, "../output/" + Extras.extensionCheck(filenameOutput, "txt"));// (json,
                                                                                               // "../"+filenameJson);
            System.out.println("Output saved to \"" + Extras.extensionCheck(filenameOutput, "txt") + "\"");
        }

    }

    private void appLoop() {

        boolean exit = false;
        while (!exit) {
            exit = analiseArguments();
        }
    }

    private void getAndSetDefaults() {
        boolean wantsToChange = true;
        while (wantsToChange) {
            Extras.clearScreen(clearScreen);
            System.out.println("Save Jason files: " + saveJason + "\nSave output files: " + saveOutput
                    + "\nShow Jason: " + showJason + "\nShow output: " + showOutput + "\nShow hints: " + newUser
                    + "\nUse colored terminal: " + colorTerminal
                    + "\nClear screen(Disable if there are printing problems. You can test it by repeating this changes.): "
                    + clearScreen);
            System.out.println("Would you like to make any changes?");
            if (!Extras.isAfirmativeAnswer(input.nextLine()))
                break;
            // boolean[] options = new boolean[6];
            // System.out.println("Save Jason files?");
            // options[0]=Extras.isAfirmativeAnswer(input.nextLine());
            System.out.println("Save Jason files?");
            saveJason = Extras.isAfirmativeAnswer(input.nextLine());
            System.out.println("Save output files?");
            saveOutput = Extras.isAfirmativeAnswer(input.nextLine());
            System.out.println("Show Jason?");
            showJason = Extras.isAfirmativeAnswer(input.nextLine());
            System.out.println("Show output?");
            showOutput = Extras.isAfirmativeAnswer(input.nextLine());
            System.out.println("Show hints?");
            newUser = Extras.isAfirmativeAnswer(input.nextLine());
            System.out.println("Clear screen?");
            clearScreen = Extras.isAfirmativeAnswer(input.nextLine());
            System.out.println("Color terminal?");
            colorTerminal = Extras.isAfirmativeAnswer(input.nextLine());
        }

    }

    private boolean analiseArguments() {// returns true if the command specified is to exit
        if (arguments.isEmpty()) {
            System.out.print(">");
            arguments.addAll(Extras.addToArguments(input.nextLine()));
        } else {
            switch (arguments.get(0).toLowerCase()) {

                case "start":
                    // TODO Only a temporary fix
                    try {
                        fetch();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    arguments.remove(0);
                    break;
                case "exit":
                case "-q":
                case "quit":
                    if (arguments.size() != 1) {
                        System.out
                                .println("There are still commands to be ran. Use \"-q!\" to force close the program.");
                        arguments.remove(0);
                        break;
                    }
                case "-q!":
                case "quit!":
                    return true;

                case "-sdi":
                    // getAndSetDefaults();
                    // break;
                case "--set-default-interface":
                    getAndSetDefaults();
                    arguments.remove(0);
                    break;
                case "-sdss":
                case "--set-default-screen-size":
                    // getAndSetDefaults();
                    setScreenSize(Extras.getScreenSize(input, clearScreen,screenSize));
                    arguments.remove(0);
                    break;

                case "-sd-saj":
                    if (Extras.hasRequiredArguments(arguments.size(), 2, true)) {
                        saveJason = Extras.isAfirmativeAnswer(arguments.get(1));
                        arguments.remove(0);
                        arguments.remove(0);
                        System.out.println("saveJason set to " + saveJason);
                    } else {
                        System.out.println("Not enough arguments for: " + arguments.get(0) + "\nIgnoring it.");
                        arguments.remove(0);
                    }
                    break;
                case "-sd-sao":
                    if (Extras.hasRequiredArguments(arguments.size(), 2, true)) {
                        saveOutput = Extras.isAfirmativeAnswer(arguments.get(1));
                        arguments.remove(0);
                        arguments.remove(0);
                        System.out.println("saveOutput set to " + saveOutput);
                    } else {
                        System.out.println("Not enough arguments for: " + arguments.get(0) + "\nIgnoring it.");
                        arguments.remove(0);
                    }
                    break;
                case "-sd-shj":
                    if (Extras.hasRequiredArguments(arguments.size(), 2, true)) {
                        showJason = Extras.isAfirmativeAnswer(arguments.get(1));
                        arguments.remove(0);
                        arguments.remove(0);
                        System.out.println("showJason set to " + showJason);
                    } else {
                        System.out.println("Not enough arguments for: " + arguments.get(0) + "\nIgnoring it.");
                        arguments.remove(0);
                    }
                    break;
                case "-sd-sho":
                    if (Extras.hasRequiredArguments(arguments.size(), 2, true)) {
                        showOutput = Extras.isAfirmativeAnswer(arguments.get(1));
                        arguments.remove(0);
                        arguments.remove(0);
                        System.out.println("showOutput set to " + showOutput);
                    } else {
                        System.out.println("Not enough arguments for: " + arguments.get(0) + "\nIgnoring it.");
                        arguments.remove(0);
                    }
                    break;
                case "-sd-shelp":
                    if (Extras.hasRequiredArguments(arguments.size(), 2, true)) {
                        newUser = Extras.isAfirmativeAnswer(arguments.get(1));
                        arguments.remove(0);
                        arguments.remove(0);
                        System.out.println("isNewUser set to " + newUser);
                    } else {
                        System.out.println("Not enough arguments for: " + arguments.get(0) + "\nIgnoring it.");
                        arguments.remove(0);
                    }
                    break;
                case "-sd-cs":
                    if (Extras.hasRequiredArguments(arguments.size(), 2, true)) {
                        clearScreen = Extras.isAfirmativeAnswer(arguments.get(1));
                        arguments.remove(0);
                        arguments.remove(0);
                        System.out.println("clearScreen set to " + clearScreen);
                    } else {
                        System.out.println("Not enough arguments for: " + arguments.get(0) + "\nIgnoring it.");
                        arguments.remove(0);
                    }
                    break;

                default:

                    System.out.println("\tUnrecognized argument: " + arguments.get(0) + "\nIgnoring it.");
                    arguments.remove(0);
            }

        }
        return false;
    }

    private void setScreenSize(Extras.Point point) {
        // screenWidth = point.getX();
        // screenHeight = point.getY();
        screenSize = point;
    }

}
