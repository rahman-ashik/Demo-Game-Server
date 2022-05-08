
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class PlayerClient {
    private static String playerName;

    public static void main(String[] args) throws IOException {
        final String HOST = "127.0.0.1";
        final int PORT = 4040;

        System.out.println("Client started.");

        try (Socket socket = new Socket(HOST, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner in = new Scanner(socket.getInputStream());
                Scanner s = new Scanner(System.in);)

        {
            System.out.println("Connected to server.");

            // setup player name
            System.out.println("Enter your name: ");
            String name = s.nextLine();
            out.println("Name:" + name);
            playerName = name;

            // setup board

            // play game
            while (true) {
                System.out.print("Input: ");
                String input = s.nextLine();
                String response = in.nextLine();
                // System.out.println(response);
                handleIncomingStream(response, out, in, s);

                if (input.equalsIgnoreCase("exit")) {
                    out.println(input);
                    break;
                } else if (input.equalsIgnoreCase("help")) {
                    System.out.println("Available commands: bla bla bla");
                } else if (input.equalsIgnoreCase("board")
                        || input.equalsIgnoreCase("GetState")
                        || input.equalsIgnoreCase("GetAllPlayers")) {
                    out.println(input);
                } else {
                    out.println("move:" + input);
                }

                System.out.println("");
                // showBoard(null); // pass the board array to show the board
            }
        }
    }

    public static void showBoard(int[][] board) {
        System.out.println("┌ 1 ┬ 2 ┬ 3 ┬ 4 ┬ 5 ┬ 6 ┬ 7 ┬ 8 ┬ 9 ┐");
        System.out.println("│   │   │   │   │   │   │   │   │   │");
        System.out.println("├-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┤");
        System.out.println("│   │   │   │   │   │   │   │   │   │");
        System.out.println("├-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┤");
        System.out.println("│   │   │   │   │   │   │   │   │   │");
        System.out.println("├-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┤");
        System.out.println("│   │ X │ X │   │   │   │   │   │   │");
        System.out.println("├-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┤");
        System.out.println("│ O │ O │ X │   │   │   │   │   │   │");
        System.out.println("└-─-┴-─-┴-─-┴-─-┴-─-┴-─-┴-─-┴-─-┴-─-┘");
    }

    public static void handleIncomingStream(String input, PrintWriter out, Scanner in, Scanner s) {
        if (input.equalsIgnoreCase("exit")) {
            System.out.println("Exiting...");
            System.exit(0);
        } else if (input.startsWith("board")) {
            showBoard(null);
        } else if (input.startsWith("state")) {
            showState(input.substring(input.indexOf(":") + 1));
        } else if (input.startsWith("move")) {
            askNextMove(in, out);
        } else {
            System.out.println("Incoming Stream: " + input);
        }
    }

    public static void showState(String state) {
        System.out.println("I got State: " + state);
    }

    public static void askNextMove(Scanner in, PrintWriter out) {
        System.out.println("Enter your next move (1-9) : ");
        String input = in.nextLine();
        out.println(input);
    }
}