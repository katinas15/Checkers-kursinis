package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;

import static sample.GameBoard.*;

public class FxMain extends Application {

    public static String[] playerPieceColor = {"#ede6e4", "#fc3903"};
    public static String[] tileColor = {"#a88132", "#32a881"};
    public static String selectedTileColor = "#3882c2";
    public static int tileSize = 50;

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
            {1, 0, 1, 0, 1, 0, 1, 0},
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
        for(int i = 0; i<tableHeight; i++) {
            for (int j = 0; j < tableWidth; j++) {
                if (gameBoard.getTile(j,i) == 1 || gameBoard.getTile(j,i) == 2) {
                    Piece piece = new Piece(j,i,gameBoard.getTile(j,i)%2 == 1);
                    allPieces.add(piece);
                }
            }
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
            if(p.isQueen()){
                gridPane.add(p.getQueenSprite(), p.getPosX(), p.getPosY());
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