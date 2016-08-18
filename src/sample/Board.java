package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Board {
    private int rows ;
    private int cols ;

    Shape tetromino;
    private static int[][] board ;

    //********************************* конструктор доски
    public Board(int r, int c,Shape tetromino){
        rows = r; cols = c;
        board = new int[rows][cols];

        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                board[i][j] = 0;
            }
        }
        this.tetromino = tetromino;
    }

    public int[][] getBoard (){
        return board;
    }


    public void update (){
        //если может падать
            if(isCanDown()) {
                setTetrominoState(0);
                tetromino.setPosY(tetromino.getPosY() + 1);
                setTetrominoState(1);
            }

            else{
                // иначе сделать частью доски
                setTetrominoState(9);
                //проверить заполнение и вернутся в начало
                checkAndClear();
                tetromino.restart();

                //если доска свободна появится
                if(isAvailable()){
                    setTetrominoState(1);
                }
                else {
                    //иначе очистить доску
                    for(int i = 0; i<rows; i++){
                        for(int j = 0; j<cols; j++){
                            board[i][j] = 0;
                        }
                    }
                    tetromino.restart();
                    setTetrominoState(1);
                }
            }

    }

    public void setTetrominoState(int state){
        int dY = tetromino.getPosY();
        int dX = tetromino.getPosX();

        for (int i=0;i<tetromino.getMatrix().length;i++){
            int shiftY = tetromino.getMatrix()[i][0];
            int shiftX = tetromino.getMatrix()[i][1];
            board[dY+shiftY][dX+shiftX] = state;
        }
    }

    //проверка позиции
    public boolean isAvailable(){
        int dY = tetromino.getPosY();
        int dX = tetromino.getPosX();
        for (int i=0;i<tetromino.getMatrix().length;i++){
            int shiftY = tetromino.getMatrix()[i][0];
            int shiftX = tetromino.getMatrix()[i][1];

            try {
                if(board[dY+shiftY][dX+shiftX] != 9)
                    return true;
                else
                    return false;

            }catch (ArrayIndexOutOfBoundsException e){
                return false;
            }
        }
        return false;

    }

    //проверка нижних клеток
    public boolean isCanDown(){
        int dY = tetromino.getPosY();
        int dX = tetromino.getPosX();

        for (int i=0;i<tetromino.getMatrix().length;i++){
            int shiftY = tetromino.getMatrix()[i][0];
            int shiftX = tetromino.getMatrix()[i][1];
            try {
                if (board[dY + shiftY+1][dX + shiftX] != 9) {
                    continue;
                }
                else
                    return false;

            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return true;

    }

    // проверка бокоых клеток
    public boolean isCanSlide (KeyEvent keycode){
        int dY = tetromino.getPosY();
        int dX = tetromino.getPosX();
        int x=0;

        if(keycode.getCode() == KeyCode.LEFT) x = -1;
        else if(keycode.getCode() == KeyCode.RIGHT) x = 1;

        for (int i=0;i<tetromino.getMatrix().length;i++){
            int shiftY = tetromino.getMatrix()[i][0];
            int shiftX = tetromino.getMatrix()[i][1];
            try {
                if (board[dY + shiftY][dX + shiftX+x] != 9) {
                    continue;
                }
                else
                    return false;

            } catch (ArrayIndexOutOfBoundsException e) {
                return false;
            }
        }
        return true;
    }

    public void checkAndClear() {
        int count = 0;
        for (int i=0;i<rows;i++) {
            count = 0;
            for (int j = 0; j < cols; j++) {
                //считаем кубики в строкее
                if (board[i][j] == 9) count++;
            }

            //если в строке заполнены все 18 кубиков
            if (count == cols) {
                // сдивигаем верхние вниз
                for(int ii = i; ii >0; ii--){
                    for(int j = 0; j<cols; j++) {
                        board[ii][j] = board[ii-1][j] ;
                    }
                }

                for(int j = 0; j<cols; j++) {
                    board[0][j] = 0;
                }
            }
        }

    }
}
