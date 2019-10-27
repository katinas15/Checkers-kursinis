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
    public int selectedX = 0, selectedY = 0;
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
                gridPane.add(rect, i, j);

                Circle circle = new Circle();
                circle.setRadius(tileSize/2 - 1.5f);

                if (gameBoard.getTile(j,i) == 2 ) {
                    circle.setFill(Color.web("#fc3903"));
                } else if (gameBoard.getTile(j,i) == 1 ){
                    circle.setFill(Color.web("#ede6e4"));
                } else continue;

                circle.setStrokeWidth(3);
                circle.setStroke(Color.BLACK);
                gridPane.add(circle, i, j);
            }
        }

    }

    private void addGridEvent() {
        gridPane.getChildren().forEach(item -> {
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    selectedX = GridPane.getColumnIndex(item);
                    selectedY = GridPane.getRowIndex(item);
                    System.out.println(selectedX + " " + selectedY);
                }
            });
        });
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
