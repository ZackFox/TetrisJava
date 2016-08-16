package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;


public class Board extends Pane {
    private static final int ROWS = 24;
    private static final int COLS = 18;

    Shape tetromino;
    private static int[][] board = new int[ROWS][COLS];

    //********************************* конструктор доски
    public Board(Shape tetromino){
        for(int i = 0; i<ROWS; i++){
            for(int j = 0; j<COLS; j++){
                board[i][j] = 0;
            }
        }
        this.tetromino = tetromino;
        board[0][0]= 9;
        board[1][0]= 9;
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
                for(int i = 0; i<ROWS; i++){
                    for(int j = 0; j<COLS; j++){
                        board[i][j] = 0;
                    }
                }
                tetromino.restart();
                tetromino.shapeRandom();
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
            int     shiftX = tetromino.getMatrix()[i][1];

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
        for (int i=0;i<ROWS;i++) {
            count = 0;
            for (int j = 0; j < COLS; j++) {
                //считаем кубики в строкее
                if (board[i][j] == 9) count++;
            }

            //если в строке заполнены все 18 кубиков
            if (count == COLS) {
                // сдивигаем верхние вниз
                for(int ii = i; ii >0; ii--){
                    for(int j = 0; j<COLS; j++) {
                        board[ii][j] = board[ii-1][j] ;
                        board[0][j] = 0;
                    }
                }
            }
        }
    }
}
