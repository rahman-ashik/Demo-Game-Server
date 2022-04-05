
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class PlayerClient {


    private int [][] board = new int[5][9];
    private static String playerName;
    public static void main(String[] args) throws IOException {
        final String HOST = "127.0.0.1";
        final int PORT = 4040;
        
        System.out.println("Client started.");
        
        try (
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());
            Scanner s = new Scanner(System.in);
        ) {
            System.out.println("Connected to server.");


            //setup player name
            System.out.println("Enter your name: ");
            String name = s.nextLine();
            out.println("Name:"+name);
            playerName = name;

            //setup board


            //play game
            while (true) {
                System.out.print("Input: ");
                String input = s.nextLine();
                out.println(input);
                if (input.equalsIgnoreCase("exit")) break;
                System.out.println("Gameserver Response: " + in.nextLine());
                System.out.println("");
                showBoard(null); // pass the board array to show the board
            }
        }
    }
    public static void showBoard (int [][] board) {
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
 
}