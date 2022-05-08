import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class GameServer {
    public static void main(String[] args) throws IOException {
        final int PORT = 4040;
        ServerSocket serverSocket = new ServerSocket(PORT);
        Map<Socket, String> clients = new java.util.HashMap<>();
        System.out.println("Game Server started ...");
        System.out.println("Waiting for Players ...");
        int playerCount = 0;

        while (true) {
            Socket clientSocket = serverSocket.accept();
            StateManager stateManager = new StateManager();
            stateManager.setState("");
            playerCount++;
            System.out.println("New Player connected. Total Players: " + playerCount);
            Game game = new Game();
            game.setGameState(stateManager.getState());
            Thread t = new Thread() {
                String[] name = new String[1];

                public void run() {
                    try {
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        Scanner in = new Scanner(clientSocket.getInputStream());
                        String currentState = game.getGameState();
                        
                        while (in.hasNextLine()) {
                            out.println(currentState);
                            String input = in.nextLine();
                            handleIncomingStream(name, input, out, in, clients, game, stateManager, clientSocket);
                            if (input.equalsIgnoreCase("exit")) {
                                break;
                            }

                        }
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    clients.remove(clientSocket);
                    System.out.println(name[0] + " has disconnected.");
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            };
            t.start();
        }
    }

    public static void handleIncomingStream(String[] name, String input, PrintWriter out, Scanner in,
            Map<Socket, String> clients, Game game, StateManager stateManager, Socket clientSocket) throws IOException {
        if (input.equalsIgnoreCase("exit")) {
            out.println("Exiting game for ..." + name[0]);
        } else if (input.startsWith("Name:")) {
            name[0] = input.substring(5);
            System.out.println(name[0] + " has connected.");
            clients.put(clientSocket, name[0]);
            out.println("state:" + game.getGameState());
        } else if (input.startsWith("move:")) {
            String move = input.substring(5);
            stateManager.appendState(move);
            game.setGameState(stateManager.getState());
            System.out.println("Received input from client  " + name[0] + ": " + input);

            // check if input is a number
            int number;
            int playerSelection, computerSelection;
            try {
                number = Integer.parseInt(move);
                playerSelection = number;
                // use playerSelection to make a move

                computerSelection = new Random().nextInt(9) + 1;
                out.println("You Selected : " + playerSelection + " " + "Computer Selected : " + computerSelection);

                out.flush();

            } catch (NumberFormatException e) {
                out.println("Invalid number");
                out.flush();
            }
        }

        else if (input.startsWith("GetState")) {
            // send the state to the client
            System.out.println("Sending state to " + name[0]);
            out.println("state :" + stateManager.getState());
        } else if (input.startsWith("GetAllPlayers")) {
            // iterate through the clients and send the names to the client
            System.out.println("Sending all players to " + name[0]);
            String info = "There are " + clients.size() + " players: " + clients.values();
            StringBuilder sb = new StringBuilder();
            sb = sb.append(info);
            sb.append(" | ");
            for (Socket key : clients.keySet()) {
                sb.append("Socket : " + key);
                sb.append(", Player : " + clients.get(key) + " ; ");
            }
            out.println(sb.toString());
        }

    }

    public static void setName(String name) {
    }
}
