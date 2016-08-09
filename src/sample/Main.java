package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Board board;
    private Shape piece;
    private Pane root;
    private Pane gameFrame;
    private Pane boardPane;
    private List<Rectangle> landed = new ArrayList<Rectangle>();
    private Rectangle rect;
    private double time;
    int x = 3;

    @Override
    public void start(Stage window) throws Exception{

        root = new Pane();
        gameFrame = new Pane();
        boardPane = new Pane();

        piece = new Shape();
        board = new Board(piece);

        boardPane.setPrefWidth(288);
        boardPane.setPrefHeight(384);
        boardPane.setStyle("-fx-background-color: black ;");

        board.draw();
        System.out.println();

        boardPane.getChildren().add(board);
        gameFrame.getChildren().add(boardPane);
        root.getChildren().addAll(gameFrame);

        window.setTitle("Hello Tetris");
        Scene scene = new Scene(root, 600, 420);
        window.setScene(scene);

        // управление
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case LEFT:
                        break;

                    case RIGHT:
                        break;

                    case SPACE:
                        break;
                }
            }
        });

        window.show();

        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += 0.01;

                if(time>=0.5){
                    time=0;

                    // логика
                    board.clearBoard();
                    board.update();

                    //перерисовка//
                    board.draw();
                }
            }
        };
        animation.start();
    }

    public void render(){}

    public static void main(String[] args) {
        launch(args);
    }
}
