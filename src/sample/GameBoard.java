package sample;

import java.util.ArrayList;
import java.util.List;

import static sample.FxMain.*;

public class GameBoard {

    public static int tableWidth;
    public static int tableHeight;

    int[][] board;

    public GameBoard(int[][] board) {
        this.board = board;
        tableWidth = board[0].length;
        tableHeight = board.length;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getTile(int x, int y){
        return board[y][x];
    }

    public void update(Piece selectedPiece, int toX, int toY){
        ControllerContext context = new ControllerContext(selectedPiece);

        checkChangeToQueen(selectedPiece);
    }

    private void checkChangeToQueen(Piece selectedPiece){
        if(selectedPiece.getColor()){
            if(selectedPiece.getPosY() != 0) {
                return;
            }
        } else if(selectedPiece.getPosY() != tableHeight-1){
            return;
        }

        GetPieceFactory pieceFactory = new GetPieceFactory();
        selectedPiece = pieceFactory.getPiece("QUEEN",selectedPiece.getPosX(),selectedPiece.getPosY(),selectedPiece.getColor());
        if(selectedPiece.getColor() == currentPlayer) changePlayer();
    }

}