package sample;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static sample.FxMain.*;

public class GameBoard {
    public static int tableWidth;
    public static int tableHeight;
    public static Piece selectedPiece;
    public static boolean currentPlayer = true;
    public static boolean secondHit = false;
    public static ArrayList<Piece> allPieces = new ArrayList();

    private String[] tileColor = {"#a88132", "#32a881"};
    private String selectedTileColor = "#3882c2";

    private GridPane gridPane = new GridPane();
    private FxTextController textCTRL;
    private int[][] board;

    public GameBoard(int[][] board) {
        gridPane.setMinSize(tileSize*tableWidth, tileSize*tableHeight);
        this.board = board;
        tableWidth = board[0].length;
        tableHeight = board.length;
        setPieces();
    }

    public void setTextController(FxTextController controller){
        this.textCTRL = controller;
        textCTRL.setBoard(this);
    }

    public void update(Piece selectedPiece, int toX, int toY){
        ControllerContext context = new ControllerContext(selectedPiece);
        context.update(selectedPiece,toX,toY);
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

    private void paintPieces(){
        for(Piece p: allPieces)
            gridPane.add(p.getSprite(), p.getPosX(), p.getPosY());
    }

    public void paintAll(){
        if(textCTRL != null) textCTRL.update();
        gridPane.getChildren().clear();
        paintBoard();
        paintPieces();
    }

    public void addGridEvent() {
        gridPane.getChildren().forEach(item -> {
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println(GridPane.getColumnIndex(item) + " " + GridPane.getRowIndex(item));
                    selectPiece(item);

                    if(selectedPiece != null){
                        update(selectedPiece, GridPane.getColumnIndex(item), GridPane.getRowIndex(item));
                    }
                    paintAll();
                    addGridEvent();
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
        if(p == null) return;

        if(p.getColor() == currentPlayer) selectedPiece = p;
    }

    public GridPane getGridPane() { return gridPane; }

    public void resetGame(){
        secondHit = false;
        selectedPiece = null;
        currentPlayer = true;
        allPieces.removeAll(allPieces);
        setPieces();
        paintAll();
        addGridEvent();
    }

    public static void changePlayer(){
        secondHit = false;
        selectedPiece = null;
        currentPlayer = !currentPlayer;
    }

    private void setPieces(){
        GetPieceFactory pieceFactory = new GetPieceFactory();
        for(int i = 0; i<tableHeight; i++) {
            for (int j = 0; j < tableWidth; j++) {
                int pieceType = board[i][j];
                Piece piece = pieceFactory.getPiece(pieceType,j,i);
                if(piece != null) allPieces.add(piece);
            }
        }
    }
}