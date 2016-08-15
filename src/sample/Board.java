package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class Board extends Pane {
    private static final int ROWS = 24;
    private static final int COLS = 18;

    Shape tetromino;
    private static int[][] board = new int[ROWS][COLS];
    private List<Rectangle> landed = new ArrayList<Rectangle>();
    private Rectangle rect;
    public boolean isFalling = true;

    //********************************* конструктор доски
    public Board(Shape tetromino){
        for(int i = 0; i<ROWS; i++){
            for(int j = 0; j<COLS; j++){
                board[i][j] = 0;
            }
        }
        this.tetromino = tetromino;
        board[23][7] = 9;
        board[23][8] = 9;
        board[23][9] = 9;
    }

    public int[][] getBoard (){
        return board;
    }


    public void update (){
        if(isFalling){
            if(isCanDown()) {
                setTetrominoState(0);
                tetromino.setPosY(tetromino.getPosY() + 1);
                setTetrominoState(1);
            }
            else{
                setTetrominoState(9);
                checkAndClear();
                tetromino.restart();
                setTetrominoState(1);//
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

        int dY = tetromino.getPosY();
        int count = 0;

        for (int i=0;i<tetromino.getMatrix().length;i++) {
            count = 0;
            int shiftY = tetromino.getMatrix()[i][0];
            for (int j = 0; j < COLS; j++) {
                if (board[dY + shiftY][j] == 9)
                    count++;
            }

            System.out.println("в ряду " + (dY + shiftY) + " заполненно " + count);
            if (count == COLS) {
                for (int j = 0; j < COLS; j++)
                    board[dY + shiftY][j] = 0;

                System.out.println("удалить ряд " + (dY + shiftY));
            }
        }
    }
}
