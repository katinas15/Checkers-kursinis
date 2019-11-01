package sample;

import javafx.scene.layout.GridPane;

import static sample.FxMain.allPieces;
import static sample.FxMain.changePlayer;

public class GameBoard {

    public static int tableWidth = 8;
    public static int tableHeight = 8;

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

    public int getTile(int x,int y){
        return board[y][x];
    }

    public void movePiece(Piece selectedPiece, int toX, int toY){
        if(selectedPiece.isQueen()){

        } else {
            if(checkStep(selectedPiece, toX, toY)){
                if(checkHit(toX, toY)){
                    selectedPiece.setPosition(toX + (toX - selectedPiece.getPosX()), toY + (toY - selectedPiece.getPosY()));
                } else {
                    selectedPiece.setPosition(toX, toY);
                    changePlayer();
                }
            }
        }
    }

//    private void changeToQueen(int color, int pieceX,int pieceY){
//        if(color == 2 && pieceY == tableHeight-1) board[pieceY][pieceX] = 4;
//        if(color == 1 && pieceY == 0) board[pieceY][pieceX] = 3;
//    }
//
//    private boolean allowSecondHit(int color,int pieceX, int pieceY){
//        if(color == 1 || color == 2){
//            if((checkHit(color,pieceX, pieceY, pieceX + 1, pieceY + 1))) return true;
//            if((checkHit(color,pieceX, pieceY, pieceX + 1, pieceY - 1))) return true;
//            if((checkHit(color,pieceX, pieceY, pieceX - 1, pieceY - 1))) return true;
//            if((checkHit(color,pieceX, pieceY, pieceX - 1, pieceY + 1))) return true;
//        }
//
//        return false;
//    }
//
    private boolean checkHit(int toX, int toY){
        Piece hit = allPieces.stream()
                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
                .findFirst()
                .orElse(null);
        if(hit != null) {
            allPieces.remove(hit);
            return true;
        }

        return false;
    }


    private boolean checkStep(Piece selectedPiece, int toX, int toY){
        if(!selectedPiece.getColor()){
            if( (selectedPiece.getPosX() + 1) == toX && (selectedPiece.getPosY() + 1) == toY) return true;
            if( (selectedPiece.getPosX() - 1) == toX && (selectedPiece.getPosY() + 1) == toY) return true;
        } else {
            if( (selectedPiece.getPosX() + 1) == toX && (selectedPiece.getPosY() - 1) == toY) return true;
            if( (selectedPiece.getPosX() - 1) == toX && (selectedPiece.getPosY() - 1) == toY) return true;
        }
        System.out.println("incorrect destination");
        return false;
    }

}