package sample;

import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;

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

        if(checkHit(selectedPiece, toX, toY)){
            processHit(selectedPiece,toX,toY);
        } else if(checkStep(selectedPiece, toX, toY) && !secondHit) {
            if(checkIfTileEmpty(toX, toY)){
                selectedPiece.setPosition(toX, toY);
                changePlayer();
            }
        }

        checkChangeToQueen(selectedPiece);

    }

    private void checkChangeToQueen(Piece selectedPiece){
        if(selectedPiece.getColor()){
            if(selectedPiece.getPosY() == 0) selectedPiece.changeToQueen();
        } else if(selectedPiece.getPosY() == tableHeight-1) selectedPiece.changeToQueen();
    }

    private void processHit(Piece selectedPiece, int toX, int toY){
        if(toX + (toX - selectedPiece.getPosX()) < 0  || toX + (toX - selectedPiece.getPosX()) > tableWidth-1 || toX + (toX - selectedPiece.getPosX()) < 0 || toX + (toX - selectedPiece.getPosX()) > tableHeight-1) return;
        selectedPiece.setPosition(toX + (toX - selectedPiece.getPosX()), toY + (toY - selectedPiece.getPosY()));
            if(allowSecondHit(selectedPiece)){
                secondHit = true;
            } else changePlayer();
    }

    private boolean allowSecondHit(Piece selectedPiece){
        List<Piece> search = allPieces.stream()
                .filter(   piece -> piece.getPosX() == selectedPiece.getPosX() + 1 && piece.getPosY() == selectedPiece.getPosY() + 1 && piece.getColor() == !selectedPiece.getColor()
                        || piece.getPosX() == selectedPiece.getPosX() + 1 && piece.getPosY() == selectedPiece.getPosY() - 1 && piece.getColor() == !selectedPiece.getColor()
                        || piece.getPosX() == selectedPiece.getPosX() - 1 && piece.getPosY() == selectedPiece.getPosY() + 1 && piece.getColor() == !selectedPiece.getColor()
                        || piece.getPosX() == selectedPiece.getPosX() - 1 && piece.getPosY() == selectedPiece.getPosY() - 1 && piece.getColor() == !selectedPiece.getColor()
                        )
                .collect(Collectors.toList());
        if(search.size() > 0){
            for(Piece p:search){
                if(checkIfTileEmpty(p.getPosX() + (p.getPosX() - selectedPiece.getPosX()), p.getPosY() + (p.getPosY() - selectedPiece.getPosY()))){
                    return true;
                } else return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkIfTileEmpty(int toX,int toY){
        if(toX < 0  || toX > tableWidth-1 || toY < 0 || toY > tableHeight-1) return false;
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
            List<Piece> availableOppenents = allPieces.stream()
                    .filter(   piece -> piece.getPosX() == selectedPiece.getPosX() + 1 && piece.getPosY() == selectedPiece.getPosY() + 1 && piece.getColor() == !selectedPiece.getColor()
                                     || piece.getPosX() == selectedPiece.getPosX() + 1 && piece.getPosY() == selectedPiece.getPosY() - 1 && piece.getColor() == !selectedPiece.getColor()
                                     || piece.getPosX() == selectedPiece.getPosX() - 1 && piece.getPosY() == selectedPiece.getPosY() + 1 && piece.getColor() == !selectedPiece.getColor()
                                     || piece.getPosX() == selectedPiece.getPosX() - 1 && piece.getPosY() == selectedPiece.getPosY() - 1 && piece.getColor() == !selectedPiece.getColor()
                    )
                    .collect(Collectors.toList());
            if(availableOppenents.size() > 0){  //tikrinama ar pasirinkta figura galima kirsti, t.y. ar ta figura yra salia pasirinktos
                for(Piece p: availableOppenents){
                    if(p.getPosX() == toX && p.getPosY() == toY){
                        if(checkIfTileEmpty(toX + (toX - selectedPiece.getPosX()), toY + (toY - selectedPiece.getPosY()))){
                            allPieces.remove(hit);
                            return true;
                        }
                    }
                }
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