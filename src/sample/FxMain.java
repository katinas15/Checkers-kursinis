package sample;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static sample.GameBoard.*;

public class FxMain extends Application {
    public static int tileSize = 50;

    int[][] board = {
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 3, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0}
    };

    @Override
    public void start(Stage primaryStage){
        FxTextController textCTRL = new FxTextController();
        GameBoard gameBoard = new GameBoard(board);
        gameBoard.setTextController(textCTRL);
        Group root = new Group(textCTRL.getFields(), gameBoard.getGridPane());

        int screenWidth = tileSize*tableWidth + 100;
        int screenHeight = tileSize*tableHeight;

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(new Scene(root, screenWidth,  screenHeight));
        primaryStage.show();

        gameBoard.paintAll();
        gameBoard.addGridEvent();
    }

    public static void main(String[] args) {
        launch(args);
    }
}