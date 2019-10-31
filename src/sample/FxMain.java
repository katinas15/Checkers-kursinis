package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static sample.GameBoard.*;

public class FxMain extends Application {

    public static int tileSize = 50;
    private GridPane gridPane = new GridPane();
    public int selectedX = -1, selectedY = -1;
    public boolean currentPlayer = false;
    private GameBoard gameBoard = new GameBoard();
    private Text text = new Text();

    ArrayList<Piece> allPieces = new ArrayList();
    ArrayList<Tile> allTiles = new ArrayList();

    private String[] tileColor = {"#a88132", "#32a881"};
    private String[] playerPiece = {"#ede6e4", "#fc3903"};
    private String selectedTile = "#3882c2";

    private void setTiles(){
        boolean color = false;

        for(int i = 0; i<tableHeight; i++) {
            color = !color;
            for (int j = 0; j < tableWidth; j++) {
                Rectangle rect = new Rectangle(0, 0, tileSize, tileSize);
                int val = color? 1 : 0;
                rect.setFill(Color.web(tileColor[val]));

                Tile tile = new Tile(i,j, rect);
                allTiles.add(tile);
                color = !color;
            }
        }
    }

    private void setCurrentPlayerText(){
        if(currentPlayer){
            text.setText("Player Red");
        } else text.setText("Player White");

        text.setX(tileSize*tableWidth + 10);
        text.setY(20);
    }

    private void setPieces(){
        Circle circle = new Circle();
        circle.setRadius(tileSize/2 - 1.5f);
        circle.setStrokeWidth(3);
        circle.setStroke(Color.BLACK);

        for(int i = 0; i<tableHeight; i++) {
            for (int j = 0; j < tableWidth; j++) {

                if (gameBoard.getTile(j,i) == 1) {
                    circle.setFill(Color.web(playerPiece[0]));
                } else if (gameBoard.getTile(j,i) == 2){
                    circle.setFill(Color.web(playerPiece[1]));
                } else continue;

                Piece piece = new Piece(i,j,circle);
                allPieces.add(piece);
            }
        }
    }

    private void paintAll(){
        for(Tile t: allTiles){
            gridPane.add(t.getSprite(), t.getPosX(), t.getPosY());
            for(Piece p:allPieces){
                if(t.getPosX() == p.getPosX() && t.getPosY() == p.getPosY()){
                    gridPane.add(p.getSprite(), p.getPosX(), p.getPosY());
                    break;
                }
            }
        }
    }


    private void paintTile(){
//
//        Circle queen = new Circle();
//        queen.setRadius(tileSize/4 - 1.5f);
//        if (gameBoard.getTile(j,i) == 3 || gameBoard.getTile(j,i) == 4){
//            queen.setFill(Color.BLACK);
//        } else return;
//        gridPane.add(queen, j, i);
//
    }

    private void addGridEvent() {
        gridPane.getChildren().forEach(item -> {
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println(GridPane.getColumnIndex(item) + " " + GridPane.getRowIndex(item));
                }
            });
        });
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        gridPane.setMinSize(tileSize*tableWidth, tileSize*tableHeight);

        Group root = new Group(text, gridPane);

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(new Scene(root, tileSize*tableWidth + 100,  tileSize*tableHeight));
        primaryStage.show();

        setTiles();
        setPieces();
        paintAll();
        addGridEvent();
        setCurrentPlayerText();
    }


    public static void main(String[] args) {
        launch(args);
    }
}