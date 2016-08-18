package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Board board;
    private Shape piece;
    private Shape pieceView;
    private Board nextField;
    private Pane root;
    private Pane gameFrame;
    private Pane nextView;
    private Label nextLabel;
    private Pane boardPane;
    private List<Rectangle> landed = new ArrayList<Rectangle>();
    private List<Rectangle> nextBlock = new ArrayList<Rectangle>();
    private Rectangle rect;
    private double time;
    double speed = 0.03;
    private boolean isPaused = false;

    @Override
    public void start(Stage window) throws Exception{

        root = new Pane();
        gameFrame = new Pane();
        boardPane = new Pane();
        nextView = new Pane();

        piece = new Shape();
        board = new Board(24,18,piece);
        board.setTetrominoState(1);

        boardPane.setPrefWidth(289);
        boardPane.setPrefHeight(384);
        boardPane.setTranslateX(10);
        boardPane.setStyle("-fx-background-color: black ;");

        nextLabel = new Label("Следующий");
        nextLabel.setTranslateY(20);
        nextLabel.setTranslateX(307);

        nextView.setPrefWidth(80);
        nextView.setPrefHeight(80);
        nextView.setTranslateY(50);
        nextView.setTranslateX(310);
        nextView.setStyle("-fx-background-color: black ;");


        gameFrame.getChildren().addAll(boardPane,nextView,nextLabel);
        root.getChildren().addAll(gameFrame);

        window.setTitle("Hello Tetris");
        Scene scene = new Scene(root, 600, 420);
        window.setScene(scene);

        //********************************* управление ****************

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case LEFT:
                        if(board.isCanSlide(event) && !isPaused) {
                            board.setTetrominoState(0);
                            piece.setPosX(piece.getPosX()-1);
                            board.setTetrominoState(1);
                        }
                        speed = 0.02;
                        render();
                        break;

                    case RIGHT:
                        if(board.isCanSlide(event)&& !isPaused) {
                            board.setTetrominoState(0);
                            piece.setPosX(piece.getPosX()+1);
                            board.setTetrominoState(1);
                        }
                        speed = 0.02;
                        render();
                        break;

                    case DOWN:
                        speed = 0.2;
                        break;

                    case P:
                        isPaused = !isPaused;
                        break;

                    case SPACE:
                        if(!isPaused) {
                            board.setTetrominoState(0);
                            piece.rotate();
                            board.setTetrominoState(1);
                        }
                        render();
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.DOWN || event.getCode()== KeyCode.LEFT||event.getCode()== KeyCode.RIGHT){
                    speed = 0.03;
                }

            }
        });

        window.show();

        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                time += speed;
                if(time>=0.5 && !isPaused){
                    board.update();
                    render();
                    time = 0;
                }
            }
        };
        animation.start();
    }

    public void render(){
        boardPane.getChildren().removeAll(landed);
        landed.removeAll(landed);

        for (int i = 0; i < board.getBoard().length ; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j] == 1) {
                    rect = new Rectangle(15, 15);
                    rect.setFill(Color.RED);
                    rect.setTranslateY(i * (15 + 1));
                    rect.setTranslateX(j  * (15 + 1)+1);
                    landed.add(rect);
                }
                else if (board.getBoard()[i][j] == 9) {
                    rect = new Rectangle(15, 15);
                    rect.setFill(Color.BEIGE);
                    rect.setTranslateY(i * (15 + 1));
                    rect.setTranslateX(j * (15 + 1)+1);
                    landed.add(rect);
                }
            }
        }

        boardPane.getChildren().addAll(landed);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
