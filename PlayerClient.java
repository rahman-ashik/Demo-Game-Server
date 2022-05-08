
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.GarbageCollectorMXBean;
import java.net.Socket;
import java.util.Scanner;

public class PlayerClient {

    private int[][] board = new int[5][9];
    private static String playerName;

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

            // start game
            Connect4 game = new Connect4();

            // play game
            while (!game.getWinner()) {
                game.play(in, out);
            


                /*
                System.out.print("Input: ");
                String input = s.nextLine();

                // input should be a number between 1 and 9
                while (!(input.matches("[1-9]"))) {
                    System.err.println("Invalid input. Please enter a number between 1 and 9.");
                    System.out.print("Input: ");
                    input = s.nextLine();
                }

                out.println(input);
                if (input.equalsIgnoreCase("exit"))
                    break;

                String response = in.nextLine();
                System.out.println("Gameserver Response: " + response);

                if (response.startsWith("You Selected :")) {
                    String GameStatus = response.substring(response.indexOf("Game Status :") + 14);
                    System.out.println("Game Status: " + GameStatus);
                    Game game = new Game();
                    game.syncState(GameStatus);
                    game.showBoard(GameStatus);
                }

                System.out.println("");

                // Game.printBoard();

                */
            }
        }
    }

}