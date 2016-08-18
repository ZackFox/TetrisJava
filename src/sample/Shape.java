package sample;

import javafx.scene.layout.Pane;
import java.util.Random;

public  class Shape {

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
    private Figures curShape;
    private Figures nextShape;
    private int posX;
    private int posY;
    private int toggle = 0;

    public Shape(){
        posX = 2;
        posY = 1;
        matrix = new int[4][2];
        setShape(shapeRandom());
        nextShape = shapeRandom();
        rotateRandom();
        System.out.println("Следующий " + nextShape);
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public Figures getCurShape() {
        return curShape;
    }

    public Figures getNextShape() {
        return nextShape;
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
        posX = 2;
        posY = 1;
        setShape(nextShape);
        nextShape = shapeRandom();
        System.out.println("Следующий " + nextShape);
        rotateRandom();
    }

    public void setShape(Figures fig) {
        for(int i =0; i<4;i++){
            for(int j=0; j<2;j++) {
                matrix[i][j] = fig.arr[i][j];
            }
        }
        curShape = fig;
    }

    public void rotate () {
        if(getCurShape()== Shape.Figures.O){
            return;
        }

        if(curShape == Shape.Figures.LINE || curShape== Shape.Figures.S || curShape== Shape.Figures.Z)
        toggle ^= 1;

        for(int i=0; i<4;i++){
            int temp = getMatrix()[i][toggle];
            getMatrix()[i][toggle] = getMatrix()[i][1-toggle];
            getMatrix()[i][1-toggle] = -temp;
        }
    }

    public Figures shapeRandom(){
        Random ran = new Random();
        int x = Math.abs(ran.nextInt())%7;
        Figures[] pieces = Figures.values();
        return pieces[x];
    }

    public void rotateRandom(){
        Random ran = new Random();
        int x = Math.abs(ran.nextInt())%4+1;
        for(int i=0; i<x;i++){
            rotate();
        }
    }

}
