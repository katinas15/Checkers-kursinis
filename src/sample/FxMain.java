package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.util.List;
import java.util.stream.Collectors;

import static sample.GameBoard.*;

public class FxMain extends Application {

    public static int tileSize = 50;
    private GridPane gridPane = new GridPane();
    public static Piece selectedPiece;
    public static boolean currentPlayer = true;
    int[][] board = {
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0}
    };


    private GameBoard gameBoard = new GameBoard(board);

    private Text text = new Text();
    private Text gameOverText = new Text();
    private Button endTurnButton = new Button();
    private Button resetButton = new Button();

    public static String[] playerPieceColor = {"#ede6e4", "#fc3903"};
    public static String[] tileColor = {"#a88132", "#32a881"};
    public static String selectedTile = "#3882c2";

    public static boolean secondHit = false;

    public static ArrayList<Piece> allPieces = new ArrayList();
    private ArrayList<Tile> allTiles = new ArrayList();

    public static void changePlayer(){
        secondHit = false;
        selectedPiece = null;
        currentPlayer = !currentPlayer;
    }

    private void setTiles(){
        boolean color = false;

        for(int i = 0; i<tableHeight; i++) {
            for (int j = 0; j < tableWidth; j++) {
                Rectangle rect = new Rectangle(0, 0, tileSize, tileSize);
                rect.setFill(Color.web(tileColor[color? 1 : 0]));

                Tile tile = new Tile(j,i, rect);
                allTiles.add(tile);
                color = !color;
            }
            color = !color;
        }
    }

    private void setPieces(){
        for(int i = 0; i<tableHeight; i++) {
            for (int j = 0; j < tableWidth; j++) {
                if (gameBoard.getTile(j,i) == 1 || gameBoard.getTile(j,i) == 2) {
                    Piece piece = new Piece(j,i,gameBoard.getTile(j,i)%2 == 1);
                    allPieces.add(piece);
                }
            }
        }
    }

    private void setCurrentPlayerText(){

        if(currentPlayer){
            text.setText("Player White");
        } else text.setText("Player Red");

        text.setX(tileSize*tableWidth + 10);
        text.setY(20);
    }

    private void setEndTurnButton(){
        endTurnButton.setText("End Turn");
        endTurnButton.setLayoutX(tileSize*tableWidth + 10);
        endTurnButton.setLayoutY(40);

        endTurnButton.setOnAction(actionEvent ->  {
            if(secondHit){
                changePlayer();
                paintAll();
            } else {
                System.out.println("You must move at least once!");
            }
        });
    }

    private void setResetButton(){
        resetButton.setText("Reset All");
        resetButton.setLayoutX(tileSize*tableWidth + 10);
        resetButton.setLayoutY(80);

        resetButton.setOnAction(actionEvent ->  {
                resetGame();
        });
    }

    private void resetGame(){
        gameBoard.setBoard(board);
        secondHit = false;
        selectedPiece = null;
        currentPlayer = true;
        allPieces.removeAll(allPieces);
        allTiles.removeAll(allTiles);
        gameOverText.setText("");
        setTiles();
        setPieces();
        paintAll();
        addGridEvent();
    }

    private void gameOver(){
        gameOverText.setX(tileSize*tableWidth);
        gameOverText.setY(140);

        List<Piece> pieces = allPieces.stream()
                .filter(   piece -> piece.getColor() == true)
                .collect(Collectors.toList());

        if(pieces.size() < 1){
            gameOverText.setText("Player Red Wins!!!");
            return;
        }

        pieces = allPieces.stream()
                .filter(   piece -> piece.getColor() == false)
                .collect(Collectors.toList());

        if(pieces.size() < 1){
            gameOverText.setText("Player White Wins!!!");
            return;
        }
    }

    private void paintAll(){
        setCurrentPlayerText();
        setEndTurnButton();
        setResetButton();
        gridPane.getChildren().clear();
        for(Tile t: allTiles){
            Piece p = allPieces.stream()
                    .filter(piece -> piece.getPosX() == t.getPosX() && piece.getPosY() == t.getPosY())
                    .findFirst()
                    .orElse(null);

            if(p != null && p == selectedPiece){
                Rectangle rect = new Rectangle(0, 0, tileSize, tileSize);
                rect.setFill(Color.web(selectedTile));
                gridPane.add(rect, t.getPosX(), t.getPosY());
            } else gridPane.add(t.getSprite(), t.getPosX(), t.getPosY());

            if(p != null) {
                gridPane.add(p.getSprite(), p.getPosX(), p.getPosY());
                if(p.isQueen()){
                    gridPane.add(p.getQueenSprite(), p.getPosX(), p.getPosY());
                }
            }
        }
    }

    private void addGridEvent() {
        gridPane.getChildren().forEach(item -> {
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println(GridPane.getColumnIndex(item) + " " + GridPane.getRowIndex(item));

                    selectPiece(item);

                    if(selectedPiece != null){
                        gameBoard.update(selectedPiece, GridPane.getColumnIndex(item), GridPane.getRowIndex(item));
                        paintAll();
                    }

                    gameOver();
                }
            });
        });
    }

    private void selectPiece(Node item){
        if(secondHit){
            System.out.println("Cannot select. Please hit or skip turn!");
            return;
        }

        Piece p = allPieces.stream()
                .filter(piece -> piece.getPosX() == GridPane.getColumnIndex(item) && piece.getPosY() == GridPane.getRowIndex(item))
                .findFirst()
                .orElse(null);
        if(p != null){
            if(p.getColor() == currentPlayer){
                selectedPiece = p;
                paintAll();
            }
        }
    }

    @Override
    public void start(Stage primaryStage){
        gridPane.setMinSize(tileSize*tableWidth, tileSize*tableHeight);

        Group root = new Group(text, endTurnButton, gridPane, resetButton,gameOverText);

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(new Scene(root, tileSize*tableWidth + 100,  tileSize*tableHeight));
        primaryStage.show();

        setTiles();
        setPieces();
        paintAll();
        addGridEvent();
    }


    public static void main(String[] args) {
        launch(args);
    }
}