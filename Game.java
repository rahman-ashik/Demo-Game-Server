public class Game {
    // this is a connect 4 game
    // the game is played on a 5x9 board
    // the board is represented by a 2d array
    // the array is initialized with 0s
    // the array is filled with 1s and 2s
    // 1s are player 1's pieces
    // 2s are player 2's pieces
    // 0s are empty spaces

    String[][] board = new String[5][9];
    String boardString = "000000000000000000200000000000100000000000000";
    String playerName1 = "";
    String playerName2 = "";
    String currentPlayer = "1";
    StateManager stateManager = new StateManager();

    Game() {
        // initialize the board
        board = new String[5][9];
        stateManager = new StateManager();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = "0";
            }
        }
    }

    Game(String board) {
        // initialize the board
        this.board = new String[5][9];
        this.boardString = board;
        stateManager = new StateManager();
        stateManager.setState(board);
        String[] rows = board.split("\n");
        for (int i = 0; i < 5; i++) {
            String[] cols = rows[i].split(" ");
            for (int j = 0; j < 9; j++) {
                this.board[i][j] = cols[j];
            }
        }
    }

    public void syncState() {
        stateManager.setState(boardString);
        boardString = stateManager.getState();
    }

    public void setPlayerName1(String playerName1) {
        this.playerName1 = playerName1;
    }

    public void setPlayerName2(String playerName2) {
        this.playerName2 = playerName2;
    }

    public String getPlayerName1() {
        return this.playerName1;
    }

    public String getPlayerName2() {
        return this.playerName2;
    }

    public String[][] getBoard() {
        return this.board;
    }

    public String getBoardString() {
        return this.boardString;
    }

    public void setBoardString(String boardString) {
        this.boardString = boardString;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void printBoard() {
        syncState();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void togglePlayer() {
        if (this.currentPlayer.equals("1")) {
            this.currentPlayer = "2";
        } else {
            this.currentPlayer = "1";
        }
    }

    public void showBoard() {
        boardStringChanger();
        startLine();
        verticalLine(this.boardString.substring(0, 9));
        horizontalLine();
        verticalLine(this.boardString.substring(9, 18));
        horizontalLine();
        verticalLine(this.boardString.substring(18, 27));
        horizontalLine();
        verticalLine(this.boardString.substring(27, 36));
        horizontalLine();
        verticalLine(this.boardString.substring(36, 45));
        endLine();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.printBoard();
        game.setPlayerName1("Player 1");
        game.setPlayerName2("Player 2");
        game.setCurrentPlayer("1");
        game.togglePlayer();
        game.showBoard();
    }

    public void startLine() {
        System.out.println("┌ 1 ┬ 2 ┬ 3 ┬ 4 ┬ 5 ┬ 6 ┬ 7 ┬ 8 ┬ 9 ┐");
    }

    public void horizontalLine() {
        System.out.println("├-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┼-─-┤");
    }

    public void verticalLine(String row) {
        row = (row == null) ? "000000000" : row;
        // System.out.println("│ │ │ │ │ │ │ │ │ │");
        System.out.println("│ " + row.charAt(0) + " │ " + row.charAt(1) + " │ " + row.charAt(2) + " │ " + row.charAt(3)
                + " │ " + row.charAt(4) + " │ " + row.charAt(5) + " │ " + row.charAt(6) + " │ " + row.charAt(7) + " │ "
                + row.charAt(8) + " │");
    }

    public void endLine() {
        System.out.println("└-─-┴-─-┴-─-┴-─-┴-─-┴-─-┴-─-┴-─-┴-─-┘");
    }

    public void boardToStringConvert() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        this.boardString = sb.toString();
    }

    public void boardStringChanger() {
        this.boardString.replaceAll("1", "O");
        this.boardString.replaceAll("2", "X");
        this.boardString.replaceAll("0", " ");
    }

    public void setGameState(String state) {
        this.boardString = state;
    }

    public String getGameState() {
        return stateManager.getState();
    }

}
