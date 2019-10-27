package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import static sample.GameBoard.*;

public class FxMain extends Application {

    public static int tileSize = 50;
    private GridPane gridPane = new GridPane();
    public int selectedX = -1, selectedY = -1;
    public boolean currentPlayer = false;
    private GameBoard gameBoard = new GameBoard();

    private void setAllTiles(){
        for(int i = 0; i<tableHeight; i++) {
            for (int j = 0; j < tableWidth; j++) {
                Rectangle rect = new Rectangle(0, 0, tileSize, tileSize);
                if ((i + j) % 2 == 0) {
                    rect.setFill(Color.web("#a88132"));
                } else {
                    rect.setFill(Color.web("#32a881"));
                }
                gridPane.add(rect, j, i);

                Circle circle = new Circle();
                circle.setRadius(tileSize/2 - 1.5f);

                if (gameBoard.getTile(j,i) == 2 ) {
                    circle.setFill(Color.web("#fc3903"));
                } else if (gameBoard.getTile(j,i) == 1 ){
                    circle.setFill(Color.web("#ede6e4"));
                } else continue;

                circle.setStrokeWidth(3);
                circle.setStroke(Color.BLACK);
                gridPane.add(circle, j, i);
            }
        }
    }

    private void addGridEvent() {
        gridPane.getChildren().forEach(item -> {
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println(GridPane.getColumnIndex(item) + " " + GridPane.getRowIndex(item));
                    int getPiece = gameBoard.getTile(GridPane.getColumnIndex(item), GridPane.getRowIndex(item));
                    int toX = GridPane.getColumnIndex(item);
                    int toY = GridPane.getRowIndex(item);

                    if(getPiece == 1 || getPiece == 2){
                        if(currentPlayer && getPiece == 1){
                            gameBoard.movePiece(selectedX, selectedY, toX, toY);
                            reset();
                        } else if(!currentPlayer && getPiece == 2){
                            gameBoard.movePiece(selectedX, selectedY, toX, toY);
                            reset();
                        } else {
                            selectedX = GridPane.getColumnIndex(item);
                            selectedY = GridPane.getRowIndex(item);
                        }
                    } else if (selectedX != -1 && selectedY != -1){
                        gameBoard.movePiece(selectedX, selectedY, toX, toY);
                        reset();
                    }
                }
            });
        });
    }

    private void reset(){
        selectedX = -1;
        selectedY = -1;
        currentPlayer = !currentPlayer;
        setAllTiles();
        addGridEvent();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        gridPane.setMinSize(tileSize*tableWidth, tileSize*tableHeight);


        primaryStage.setTitle("Checkers");
        primaryStage.setScene(new Scene(gridPane, tileSize*tableWidth,  tileSize*tableHeight));
        primaryStage.show();

        setAllTiles();
        addGridEvent();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
