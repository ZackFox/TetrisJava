package sample;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Controller {
    private Shape tetromino;
    private Board board ;

    //********************************* конструктор доски
    public Controller(Board board, Shape tetromino){
        this.board = board;
        this.tetromino = tetromino;
        setTetrominoState(1);
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
                    board.clear();
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
            board.getBoard()[dY+shiftY][dX+shiftX] = state;
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
                if(board.getBoard()[dY+shiftY][dX+shiftX] != 9)
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
                if (board.getBoard()[dY + shiftY+1][dX + shiftX] != 9) {
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
                if (board.getBoard()[dY + shiftY][dX + shiftX+x] != 9) {
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
        for (int i=0;i<board.getRows();i++) {
            count = 0;
            for (int j = 0; j < board.getCols(); j++) {
                //считаем кубики в строкее
                if (board.getBoard()[i][j] == 9) count++;
            }

            //если в строке заполнены все 18 кубиков
            if (count == board.getCols()) {
                // сдивигаем верхние вниз
                for(int ii = i; ii >0; ii--){
                    for(int j = 0; j<board.getCols(); j++) {
                        board.getBoard()[ii][j] = board.getBoard()[ii-1][j] ;
                    }
                }

                for(int j = 0; j<board.getCols(); j++) {
                    board.getBoard()[0][j] = 0;
                }
            }
        }

    }
}
