import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class GameServer {
    public static void main(String[] args) throws IOException {
        final int PORT = 4040;
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("Game Server started ...");
        System.out.println("Wating for Players ...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread t = new Thread() {

                String name;

                public void run() {
                    try (
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                            Scanner in = new Scanner(clientSocket.getInputStream());) {

                        while (in.hasNextLine()) {
                            String input = in.nextLine();
                            if (input.equalsIgnoreCase("exit")) {
                                break;
                            }
                            if (input.startsWith("Name:")) {
                                name = input.substring(5);
                                System.out.println(name + " has connected.");
                                continue;
                            }
                            if (input.startsWith("start")) {
                                System.out.println(name + " has started the game.");
                                continue;
                            }
                            System.out.println("Received input from client: " + input);

                            // send a random number between 1 and 6
                            Random r = new Random();
                            int computerSelection = r.nextInt(6) + 1;
                            System.out.println("Computer selected: " + computerSelection);
                            out.println(computerSelection);

                            /*
                             * 
                             * int playerSelection = Integer.valueOf(input);
                             * // use playerSelection to make a move
                             * 
                             * // game.makeMove(playerSelection);
                             * 
                             * int computerSelection = new Random().nextInt(8) + 1;
                             * 
                             * String oldState = stateManager.getState();
                             * String newState = oldState.substring(0, playerSelection - 1) + "X"
                             * + oldState.substring(playerSelection);
                             * // newState = newState.substring(0, computerSelection - 1) + "O"
                             * // + newState.substring(computerSelection);
                             * stateManager.setState(newState);
                             * 
                             * // make move
                             * game.makeMove(playerSelection, "X");
                             * // game.makeMove(computerSelection, "O");
                             * 
                             * out.println("You Selected : " + playerSelection + " " +
                             * "Computer Selected : "
                             * + computerSelection + " Game Status : " + stateManager.getState());
                             * 
                             * System.out.println("");
                             * game.syncState(stateManager.getState());
                             * game.showBoard(stateManager.getState());
                             */
                        }
                    } catch (IOException e) {
                    }
                }
            };
            t.start();
        }
    }

}