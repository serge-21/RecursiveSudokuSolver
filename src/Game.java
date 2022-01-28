import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Game {
    int[][] board;
    static final int MAX_SIZE = 9;

    public Game() {
        this.board = new int[9][9];
        setBoard();
    }

    private void setBoard() {
        // read the board from a CSV file
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader("src/board.csv"));
            String line;
            int row = 0;
            while ((line = csvReader.readLine()) != null) {
                String[] data = line.split("[, ]+");
                for (int i = 0; i < data.length; i++) {
                    this.board[row][i] = Integer.parseInt(data[i]);
                }
                row++;
            }
            csvReader.close();
        } catch (IOException ioe) {
            System.out.println("file doesn't exist");
        }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public String toString(int[][] board) {
        StringBuilder newBoard = new StringBuilder();

        for (int row = 0; row < MAX_SIZE; row++) {
            if (row % 3 == 0 && row != 0) {
                newBoard.append("----------------------");
                newBoard.append("\n");
            }
            for (int col = 0; col < MAX_SIZE; col++) {
                if (col % 3 == 0 && col != 0) {
                    newBoard.append("|" + " ");
                }
                newBoard.append(board[row][col]).append(" ");
            }
            newBoard.append("\n");
        }
        return newBoard.toString();
    }

    private boolean checkVertical(int num, int row) {
        for (int col = 0; col < MAX_SIZE; col++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean checkHorizontal(int num, int col) {
        for (int row = 0; row < MAX_SIZE; row++) {
            if (board[row][col] == num) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSquare(int num, int row, int col) {
        row = row - (row % 3);
        col = col - (col % 3);

        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                if (this.board[i][j] == num) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidPlacement(int num, int row, int col) {
        return (!checkHorizontal(num, col) && !checkSquare(num, row, col) && !checkVertical(num, row));
    }

    private boolean isSolved(int[][] board) {
        int numOfZeros = 0;
        for (int row = 0; row < MAX_SIZE; row++) {
            for (int col = 0; col < MAX_SIZE; col++) {
                if (board[row][col] == 0) {
                    numOfZeros += 1;        // if the number of 0's isn't 0 then the board isn't solved.
                }
            }
        }
        return numOfZeros == 0;
    }

    public boolean solved(int[][] board) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        // go through the entire board we are given to see if we can change a 0 to none 0
        for (int row = 0; row < MAX_SIZE; row++) {
            for (int col = 0; col < MAX_SIZE; col++) {
                if (board[row][col] == 0) { // found a 0 so we need to replace it.
                    // here we just go through all available numbers.
                    for (int num : nums) {
                        if (isValidPlacement(num, row, col)) {
                            board[row][col] = num; // place a number in

                            if(solved(board)){ // does this solve the board?
                                return true;
                            }
                            board[row][col] = 0; // no? just delete the number u put in.
                        }
                        // can't backtrack here.
                    }
                    // now we backtrack.
                    return false;
                }
            }
        }
        if(isSolved(board)){
            System.out.println(toString(board));
            return true;
        }else{
            System.out.println("This puzzle has no solution stop trolling smh ");
            return false;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println(game.toString(game.getBoard()));
        System.out.println("give me a minute solving this rn");
        System.out.println();
        game.solved(game.getBoard());
    }
}
