import java.io.PrintWriter;
import java.util.Scanner;

public class Connect4 {
    char[][] grid = new char[6][7];
    StateManager stateManager = new StateManager();
    // Reset
    public static final String RESET = "\033[0m"; // Text Reset

    // Bold
    public static final String RED_BOLD = "\033[1;31m"; // RED
    public static final String BLUE_BOLD = "\033[1;34m"; // BLUE

    // Background
    public static final String WHITE_BACKGROUND = "\033[47m"; // WHITE

    // Bold High Intensity
    public static final String RED_BOLD_BRIGHT = "\033[1;91m"; // RED
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m"; // BLUE

    // High Intensity backgrounds
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m"; // WHITE

    boolean winner = false;

    public void play(Scanner response, PrintWriter out) {
        Scanner in = new Scanner(System.in);
        stateManager.resetState();

        // initialize array
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = ' ';
            }
        }

        int turn = 1;
        char player = 'R';

        // play a turn
        while (winner == false && turn <= 42) {
            boolean validPlay;
            int play;
            do {
                display(grid);
                System.out.println("Current State: " + stateManager.getState());

                String state = stateManager.getState();
                out.println(state);
                out.flush();

                // if it's Red's turn then client will play
                if (turn % 2 == 1) {
                    System.out.println("It's Red's turn.");
                    System.out.println("Enter a column number: ");
                    // get input from client as string
                    String input = in.nextLine();
                    // check if input is a number
                    try {
                        play = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number.");
                    }
                    // convert string to int
                    // play = Integer.parseInt(input);
                    play = in.nextInt() - 1;
                } else {
                    // if it's Blue's turn then server will play
                    System.out.println("It's Blue's turn.");
                    play = response.nextInt();
                }

                // System.out.print("Player " + player + ", choose a column: ");
                // play = in.nextInt() - 1;

                // validate play
                validPlay = validate(play, grid);
                // update state
                stateManager.updateState(grid);
            } while (validPlay == false);

            // drop the checker
            for (int row = grid.length - 1; row >= 0; row--) {
                if (grid[row][play] == ' ') {
                    grid[row][play] = player;
                    break;
                }
            }

            // determine if there is a winner
            winner = isWinner(player, grid);

            // switch players
            if (player == 'R') {
                player = 'B';
            } else {
                player = 'R';
            }

            turn++;
        }
        display(grid);

        if (winner) {
            if (player == 'R') {
                System.out.println("Blue won");
            } else {
                System.out.println("Red won");
            }
        } else {
            System.out.println("Tied game");
        }

    }

    Connect4() {
        // initialize array
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = ' ';
            }
        }
        stateManager.resetState();
    }

    public static void main(String[] args) {

        System.out.println(RED_BOLD + "Connect 4" + RESET);
        System.out.println(BLUE_BOLD + "Connect 4" + RESET);

        char[][] grid = new char[6][7];

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = ' ';
            }
        }

        // put random R and B in grid
        grid[0][0] = 'R';
        grid[2][1] = 'B';
        grid[0][2] = 'R';
        grid[4][3] = 'B';

        // display(grid);
    }

    public boolean getWinner() {
        return winner;
    }

    public void display(char[][] grid) {
        // System.out.println(" 0 1 2 3 4 5 6");
        // System.out.println(" - - - - - - - - - - - - - - -");
        System.out.println("┌ 1 ┬ 2 ┬ 3 ┬ 4 ┬ 5 ┬ 6 ┐");
        for (int row = 0; row < grid.length; row++) {
            System.out.print("| ");
            for (int col = 0; col < grid[0].length; col++) {

                if (grid[row][col] == 'R') {
                    System.out.print(RED_BOLD + 'x' + RESET);
                } else if (grid[row][col] == 'B') {
                    System.out.print(BLUE_BOLD + 'O' + RESET);
                } else {
                    System.out.print(grid[row][col]);
                }
                if (!(col == grid[0].length - 1)) {
                    System.out.print(" | ");
                }

            }
            System.out.println();
            // System.out.println(" - - - - - - - - - - - - - - -");

            if (!(row == grid.length - 1)) {
                System.out.println("├-─-┼-─-┼-─-┼-─-┼-─-┼-─-┤");
            } else {
                System.out.println("└-─-┴-─-┴-─-┴-─-┴-─-┴-─-┘");
            }
        }
        System.out.println();
    }

    public static boolean validate(int column, char[][] grid) {
        // valid column?
        if (column < 0 || column > grid[0].length) {
            return false;
        }

        // full column?
        if (grid[0][column] != ' ') {
            return false;
        }

        return true;
    }

    public static boolean isWinner(char player, char[][] grid) {
        // check for 4 across
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length - 3; col++) {
                if (grid[row][col] == player &&
                        grid[row][col + 1] == player &&
                        grid[row][col + 2] == player &&
                        grid[row][col + 3] == player) {
                    return true;
                }
            }
        }
        // check for 4 up and down
        for (int row = 0; row < grid.length - 3; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == player &&
                        grid[row + 1][col] == player &&
                        grid[row + 2][col] == player &&
                        grid[row + 3][col] == player) {
                    return true;
                }
            }
        }
        // check upward diagonal
        for (int row = 3; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length - 3; col++) {
                if (grid[row][col] == player &&
                        grid[row - 1][col + 1] == player &&
                        grid[row - 2][col + 2] == player &&
                        grid[row - 3][col + 3] == player) {
                    return true;
                }
            }
        }
        // check downward diagonal
        for (int row = 0; row < grid.length - 3; row++) {
            for (int col = 0; col < grid[0].length - 3; col++) {
                if (grid[row][col] == player &&
                        grid[row + 1][col + 1] == player &&
                        grid[row + 2][col + 2] == player &&
                        grid[row + 3][col + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setState(String input) {
        // convert string to state
        stateManager.setState(input);

    }

    public void setState(char[][] grid) {
        // convert string to state
        stateManager.setState(grid);

    }

    public char[][] getGrid() {
        return grid;
    }

    public static void showIntro() {
        System.out.print("\033[1;32m      ,-----.                                      ,--.     ,---. \033[0m\n");
        System.out.print("\033[1;32m     '  .--.| ,---. ,--,--, ,--,--,  ,---.  ,---.,-'  '-.  /    | \033[0m\n");
        System.out.print("\033[1;32m     |  |    | .-. ||      ||      |'  .-.:| .--''-.  .-' /  '  | \033[0m\n");
        System.out.print("\033[1;32m     '  '-- |  '-' '|  ||  ||  ||  ||'  --.|'`--.  |  |   '--|  | \033[0m\n");
        System.out.print("\033[1;32m      `-----' `---' `--''--'`--''--' `----' `----'  `--'     `--' \033[0m\n");
    }

    public static void showOutro() {
        System.out.print("\033[1;31m   _______                          _______                   			\033[0m\n");
        System.out.print("\033[1;31m  |     __|.---.-.--------.-----.  |       |.--.--.-----.----.			\033[0m\n");
        System.out.print("\033[1;31m  |    |  ||  _  |        |  -__|  |   -   ||  |  |  -__|   _|			\033[0m\n");
        System.out.print("\033[1;31m  |_______||___._|__|__|__|_____|  |_______|  ___/|_____|__|  			\033[0m\n");
        System.out
                .print("\033[1;31m   `------'  `--`--'`-`-`-' `----'   `-----'   `--' `----'`--'  		    \033[0m\n");
    }

}
