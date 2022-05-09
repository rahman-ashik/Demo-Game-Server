public class StateManager {
    char[][] grid;

    StateManager() {
        char[][] grid = new char[6][7];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = ' ';
            }
        }
    }

    public String getState() {
        String state = "";
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                state += grid[row][col];
            }
        }
        return state;
    }

    public void setState(char[][] grid2) {
        grid = grid2;
    }

    public void resetState() {
        grid = new char[6][7];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = ' ';
            }
        }
    }

    public char[][] getBoard() {
        return grid;
    }

    public void updateBoard(String boardString) {
        int index = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = boardString.charAt(index);
                index++;
            }
        }

    }

    public void updateState(char[][] grid2) {
        setState(grid2);
    }

    public void setState(String input) {
        int index = 0;
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = input.charAt(index);
                index++;
            }
        }
    }

}