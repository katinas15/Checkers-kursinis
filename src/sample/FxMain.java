package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static sample.GameBoard.*;
import static sample.Tile.tileSize;

public class FxMain extends Application {

    private GridPane gridPane = new GridPane();

    private void setAllTiles(){
        for(int i = 0; i<tableHeight; i++){
            for(int j = 0; j<tableWidth; j++){
                Tile tile = new Tile((i+j)%2 == 0);
                gridPane.add(tile.getRect(),i,j);
            }
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        gridPane.setMinSize(tileSize*tableWidth, tileSize*tableHeight);
        setAllTiles();

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(new Scene(gridPane, tileSize*tableWidth,  tileSize*tableHeight));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
