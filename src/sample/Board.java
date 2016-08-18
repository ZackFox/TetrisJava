package sample;

public class Board {
    private int rows ;
    private int cols ;
    private static int[][] board ;

    public Board(int r, int c){
        rows = r; cols = c;
        board = new int[rows][cols];
        clear();
    }

    public int[][] getBoard (){
        return board;
    }

    public int getRows (){
        return rows;
    }
    public int getCols (){
        return cols;
    }

    public void clear (){
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                board[i][j] = 0;
            }
        }
    }
}
