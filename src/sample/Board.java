package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Board extends Pane {
    private static final int ROWS = 24;
    private static final int COLS = 18;

    Shape tetromino;
    private static int[][] board = new int[ROWS][COLS];
    private List<Rectangle> landed = new ArrayList<Rectangle>();
    private Rectangle rect;
    public boolean isFalling;

    public Board(Shape tetromino){
        for(int i = 0; i<ROWS; i++){
            for(int j = 0; j<COLS; j++){
                board[i][j] = 0;
            }
        }
        this.tetromino = tetromino;
    }

    public void update (){
        // фигура падает ?
        if(isFalling){
            // проверить нижнюю позицию.
//            if(isCanDown()){
            //удалить фигуру
            tetromino.setPosY(tetromino.getPosY()+1);    //опуститьфигурку
            placedOnboard(true);    //разместить фигурку

            System.out.println();
            System.out.println(isCanDown());
        }
    }

    public void draw(){
//        clearBoard();

        for (int i = 0; i < ROWS; i++) {
            System.out.println();
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j]);
                if (board[i][j] == 1) {
                    rect = new Rectangle(15, 15);
                    rect.setFill(Color.RED);
                    rect.setTranslateY(i * (15 + 1));
                    rect.setTranslateX(j * (15 + 1));
                    landed.add(rect);
                }
            }
        }
        getChildren().addAll(landed);
    }

    public void placedOnboard(boolean vis){
        int dY = tetromino.getPosY();
        int dX = tetromino.getPosX();

        int visible = (vis)?1:0;

        for (int i=0;i<tetromino.getMatrix().length;i++){
            int shiftY = tetromino.getMatrix()[i][0];
            int shiftX = tetromino.getMatrix()[i][1];
            board[dY+shiftY][dX+shiftX] = visible;
        }
    }

    public boolean isCanDown (){
        int dY = tetromino.getPosY();
        int dX = tetromino.getPosX();
        int max = tetromino.getMaxShiftY();

        System.out.println("позиция :" + dY + " следующая :"+(dY+max+1));

        try{
            if(board[dY+max+1][dX] == 0){
                return true;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
        return false;
    }

    public boolean isAvailable(){
        int dY = tetromino.getPosY();
        int dX = tetromino.getPosX();
        for (int i=0;i<tetromino.getMatrix().length;i++){
            int shiftY = tetromino.getMatrix()[i][0];
            int shiftX = tetromino.getMatrix()[i][1];

            try {
                if(board[dY+shiftY][dX+shiftX] != 1)
                    return true;
                else
                    return false;

            }catch (ArrayIndexOutOfBoundsException e){
                return false;
            }
        }
        return false;

    }

//    public void clearBoard(){
//        for (int i = 0; i < ROWS; i++) {
//            for (int j = 0; j < COLS; j++) {
//                board[i][j] = 0;
//            }
//        }
//        getChildren().remove(landed);
//        landed.removeAll(landed);
//    }

    public int[][] getBoard (){
        return board;
    }
}
