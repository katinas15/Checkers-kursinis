package sample;

import javafx.scene.layout.GridPane;

import static sample.FxMain.*;

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

    public void update(Piece selectedPiece, int toX, int toY){
        if(selectedPiece.isQueen()){

        } else {
            moveStandard(selectedPiece, toX, toY);
        }
    }

    private void moveStandard(Piece selectedPiece, int toX, int toY){
        if(checkStep(selectedPiece, toX, toY)){
            if(checkHit(selectedPiece, toX, toY)){
                processHit(selectedPiece,toX,toY);
            } else {
                 if(checkIfTileEmpty(toX, toY)){
                    selectedPiece.setPosition(toX, toY);
                    changePlayer();
                }
            }
        } else if(checkHit(selectedPiece, toX, toY)){
            processHit(selectedPiece,toX,toY);
        }
    }

    private void processHit(Piece selectedPiece, int toX, int toY){
        selectedPiece.setPosition(toX + (toX - selectedPiece.getPosX()), toY + (toY - selectedPiece.getPosY()));
//                if(allowSecondHit(selectedPiece)){
//                    secondHit = true;
//                }
    }

//    private boolean allowSecondHit(Piece selectedPiece){
//
//        Piece hit = allPieces.stream()
//                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
//                .findFirst()
//                .orElse(null);
//
//        return false;
//    }

    private boolean checkIfTileEmpty(int toX,int toY){
        Piece tile = allPieces.stream()
                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
                .findFirst()
                .orElse(null);
        if(tile != null) {
            System.out.println("Opponent is blocking the way!");
            return false;
        }
        return true;
    }

    private boolean checkHit(Piece selectedPiece, int toX, int toY){
        Piece hit = allPieces.stream()
                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
                .findFirst()
                .orElse(null);
        if(hit != null) {
            if(checkIfTileEmpty(toX + (toX - selectedPiece.getPosX()), toY + (toY - selectedPiece.getPosY()))){
                allPieces.remove(hit);
                return true;
            }
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