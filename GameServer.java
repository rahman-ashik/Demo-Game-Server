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
                        Scanner in = new Scanner(clientSocket.getInputStream());
                    ) {
                        while (in.hasNextLine()) {
                            String input = in.nextLine();
                            if (input.equalsIgnoreCase("exit")) {
                                break;
                            }
                            if (input.startsWith("Name:")){
                                name = input.substring(5);
                                System.out.println(name + " has connected.");
                                continue;
                            }
                            System.out.println("Received input from client: " + input);
                            
                            int playerSelection = Integer.valueOf(input);
                            // use playerSelection to make a move

                            int computerSelection = new Random().nextInt(9) + 1;
                            out.println("You Selected : "+playerSelection + " " + "Computer Selected : " + computerSelection);

                        }
                    } catch (IOException e) { }
                }
            };
            t.start();
        }
    }
    

}