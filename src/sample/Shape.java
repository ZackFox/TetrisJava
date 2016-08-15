package sample;

import javafx.geometry.Point3D;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public  class Shape extends Pane {

    enum Figures {
        T(new int[][]{{0,0},{-1,0},{0,1},{1,0}}),
        O(new int[][]{{0,0},{-1,0},{-1,1},{0,1}}),
        Z(new int[][]{{0,0},{-1,1},{0,1},{1,0}}),
        S(new int[][]{{0,0},{-1,0},{0,1},{1,1}}),
        LINE(new int[][]{{0,0},{-1,0},{1,0},{2,0}}),
        L(new int[][]{{0,0},{-1,0},{1,0},{1,1}}),
        J(new int[][]{{0,0},{-1,0},{1,0},{1,-1}});

        public int [][] arr;

        Figures(int[][] arr) {
            this.arr = arr;
        }
    }


    //*********************************

    private int [][] matrix;
    private int posX;
    private int posY;

    public Shape(){
        matrix = new int[4][2];
        shapeRandom();
        posX = 8;
        posY = 1;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void restart(){
        posX = 8;
        posY = 1;
        shapeRandom();
    }

    public void setShape(Figures fig) {
        for(int i =0; i<4;i++){
            for(int j=0; j<2;j++) {
                matrix[i][j] = fig.arr[i][j];
            }
        }
    }

    public void rotate () {
        for(int i =0; i<4;i++){
            int temp = matrix[i][0];
            matrix[i][0] = matrix[i][1];
            matrix[i][1] = -temp;
        }
    }

    public void shapeRandom(){
        Random ran = new Random();
        int x = Math.abs(ran.nextInt())%7;
        Figures[] pieces = Figures.values();
        setShape(pieces[x]);
        System.out.println(pieces[x]);
    }

}
