package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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

        boardPane.getChildren().add(board);
        gameFrame.getChildren().add(boardPane);
        root.getChildren().addAll(gameFrame);

        window.setTitle("Hello Tetris");
        Scene scene = new Scene(root, 600, 420);
        window.setScene(scene);

        //****************************************************

        board.setTetrominoState(1);  // появление фигурки на старте
        board.draw();                // отрисовка доски
        System.out.println();

        //********************************* управление ****************

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case LEFT:
                        if(piece.getPosX()>=0 ||piece.getPosX()<= 17) {
                            // состояние 0 - очищение, от 1 до 7 - движение , 9 - фиксация на доске.
                            board.setTetrominoState(0);
                            piece.setPosX(piece.getPosX()-1);
                            piece.setPosY(piece.getPosY());
                            board.setTetrominoState(1);
                        }
                        break;

                    case RIGHT:
                        if(piece.getPosX()>=0 ||piece.getPosX()<= 17) {
                            // состояние 0 - очищение, от 1 до 7 - движение , 9 - фиксация на доске.
                            board.setTetrominoState(0);
                            piece.setPosX(piece.getPosX()+1);
                            piece.setPosY(piece.getPosY());
                            board.setTetrominoState(1);
                        }
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
                time += 0.017;

                if(time>=0.5){
                    time=0;

                    board.update();

                    //перерисовка//
                    board.draw();
                    render();
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
                    rect.setTranslateX(j * (15 + 1));
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
