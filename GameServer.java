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
        System.out.println("Waiting for Players ...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread t = new Thread() {

                String name;

                StateManager stateManager = new StateManager();

                public void run() {
                    try (
                            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                            Scanner in = new Scanner(clientSocket.getInputStream());) {
                        stateManager.resetState();
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
                            stateManager.setState(input);
                            // System.out.println("Board updated to: " + stateManager.getState());
                            // send a random number between 1 and 6
                            Random r = new Random();
                            int computerSelection = r.nextInt(5) + 1;
                            System.out.println("Computer selected: " + computerSelection);
                            out.println(computerSelection);
                        }
                    } catch (IOException e) {
                    }
                    System.out.println(name + " has disconnected.");
                }
            };
            t.start();
        }
    }

}