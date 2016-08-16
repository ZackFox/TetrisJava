package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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
    private Pane root;
    private Pane gameFrame;
    private Pane boardPane;
    private List<Rectangle> landed = new ArrayList<Rectangle>();
    private Rectangle rect;
    private double time;
    double speed = 0.03;

    HBox menu = new HBox();
    MenuBar menuBar = new MenuBar();

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

        board.setTetrominoState(1);

        menu.getChildren().add(menuBar);

        boardPane.getChildren().add(board);
        gameFrame.getChildren().add(boardPane);
        root.getChildren().addAll(gameFrame,menu );

        window.setTitle("Hello Tetris");
        Scene scene = new Scene(root, 600, 420);
        window.setScene(scene);

        //********************************* управление ****************

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case LEFT:
                        if(board.isCanSlide(event)) {
                            board.setTetrominoState(0);
                            piece.setPosX(piece.getPosX()-1);
                            board.setTetrominoState(1);
                        }
                        speed = 0.02;
                        render();
                        break;

                    case RIGHT:
                        if(board.isCanSlide(event)) {
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

                    case SPACE:
                        board.setTetrominoState(0);
                        piece.rotate();
                        board.setTetrominoState(1);
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
                if(time>=0.5){
                    board.update();
                    render();
                    time = 0;
                }
            }
        };
        animation.start();
    }

    public void render(){
        gameFrame.getChildren().removeAll(landed);
        landed.removeAll(landed);

        for (int i = 0; i < board.getBoard().length ; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.getBoard()[i][j] == 1) {
                    rect = new Rectangle(15, 15);
                    rect.setFill(Color.RED);
                    rect.setTranslateY(i * (15 + 1));
                    rect.setTranslateX(j  * (15 + 1));
                    landed.add(rect);
                }
                else if (board.getBoard()[i][j] == 9) {
                    rect = new Rectangle(15, 15);
                    rect.setFill(Color.BEIGE);
                    rect.setTranslateY(i * (15 + 1));
                    rect.setTranslateX(j * (15 + 1));
                    landed.add(rect);
                }
            }
        }

        gameFrame.getChildren().addAll(landed);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
