
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.GarbageCollectorMXBean;
import java.net.Socket;
import java.util.Scanner;

public class PlayerClient {

    private int[][] board = new int[5][9];
    private static String playerName;
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m"; // BLUE
    public static final String RED_BOLD_BRIGHT = "\033[1;91m"; // RED
    public static final String RESET = "\033[0m"; // Text Reset

    public static void main(String[] args) throws IOException {
        final String HOST = "127.0.0.1";
        final int PORT = 4040;

        System.out.println("Client started.");

        try (
                Socket socket = new Socket(HOST, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner in = new Scanner(socket.getInputStream());
                Scanner s = new Scanner(System.in);) {
            System.out.println("Connected to server.");

            // setup player name
            System.out.println("Enter your name: ");
            String name = s.nextLine();
            out.println("Name:" + name);
            playerName = name;

            // setup board
            boolean ready = false;

            while (!ready) {
                System.out.println("To start the game, enter 'start'");
                String input = s.nextLine();
                if (input.equalsIgnoreCase("start")) {
                    out.println("start");
                    ready = true;

                }
            }

            Connect4.showIntro();
            System.out.println("Game started. " + RED_BOLD_BRIGHT + "You are Red set to 'X' " + BLUE_BOLD_BRIGHT
                    + "and the computer is Blue set to 'O'." + RESET);
            // start game
            Connect4 game = new Connect4();

            // play game
            while (!game.getWinner()) {
                game.play(in, out);
            }

            // close socket
            System.out.println("Game Ended");
            out.println("exit");
            socket.close();
            Connect4.showOutro();
        }

    }

}