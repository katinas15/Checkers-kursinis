package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static sample.GameBoard.*;

public class FxMain extends Application {

    public static String[] playerPieceColor = {"#ede6e4", "#fc3903"};
    public static String[] tileColor = {"#a88132", "#32a881"};
    public static String selectedTileColor = "#3882c2";
    public static int tileSize = 50;
    public static int strokeSize = 3;

    private GridPane gridPane = new GridPane();
    public static Piece selectedPiece;
    public static boolean currentPlayer = true;
    public static boolean secondHit = false;
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

    private GameBoard gameBoard = new GameBoard(board);

    public static ArrayList<Piece> allPieces = new ArrayList();

    public static void changePlayer(){
        secondHit = false;
        selectedPiece = null;
        currentPlayer = !currentPlayer;
    }

    private void paintBoard(){
        boolean color = false;

        for(int i = 0; i<tableHeight; i++) {
            for (int j = 0; j < tableWidth; j++) {
                paintTile(j,i,color);
                color = !color;
            }
            color = !color;
        }
    }

    private void paintTile(int x,int y,boolean color){
        Rectangle rect = new Rectangle(0, 0, tileSize, tileSize);
        rect.setFill(Color.web(tileColor[color? 1 : 0]));

        if(selectedPiece != null){
            if(selectedPiece.getPosX() == x && selectedPiece.getPosY() == y) {
                rect.setFill(Color.web(selectedTileColor));
            }
        }

        gridPane.add(rect, x, y);
    }

    private void setPieces(){
        GetPieceFactory pieceFactory = new GetPieceFactory();
        for(int i = 0; i<tableHeight; i++) {
            for (int j = 0; j < tableWidth; j++) {
                int pieceType = gameBoard.getTile(j,i);
                Piece piece = pieceFactory.getPiece(pieceType,j,i);
                if(piece != null){
                    allPieces.add(piece);
                }
            }
        }
    }

    private Text text = new Text();
    private Text gameOverText = new Text();
    private Button endTurnButton = new Button();
    private Button resetButton = new Button();

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
        paintBoard();

        for(Piece p: allPieces){
            gridPane.add(p.getSprite(), p.getPosX(), p.getPosY());
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
                        addGridEvent();
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
        if(p == null){
            return;
        }

        if(p.getColor() == currentPlayer){
            selectedPiece = p;
            paintAll();
            addGridEvent();
        }
    }

    private void resetGame(){
        gameBoard.setBoard(board);
        secondHit = false;
        selectedPiece = null;
        currentPlayer = true;
        allPieces.removeAll(allPieces);
        gameOverText.setText("");
        setPieces();
        paintAll();
        addGridEvent();
    }

    @Override
    public void start(Stage primaryStage){
        gridPane.setMinSize(tileSize*tableWidth, tileSize*tableHeight);

        Group root = new Group(text, endTurnButton, gridPane, resetButton, gameOverText);

        primaryStage.setTitle("Checkers");
        primaryStage.setScene(new Scene(root, tileSize*tableWidth + 100,  tileSize*tableHeight));
        primaryStage.show();

        setPieces();
        paintAll();
        addGridEvent();
    }

    public static void main(String[] args) {
        launch(args);
    }
}