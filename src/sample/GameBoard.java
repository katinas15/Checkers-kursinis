package sample;

import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            moveQueen(selectedPiece,toX,toY);
        } else {
            moveStandard(selectedPiece, toX, toY);
        }
    }

    private void moveQueen(Piece selectedPiece, int toX, int toY){
        if(checkQueenHit(selectedPiece, toX, toY)){
            processQueenHit(selectedPiece, toX, toY);
        } else if(checkQueenStep(selectedPiece, toX, toY) && !secondHit) {
            if (checkIfTileEmpty(toX, toY)) {
                selectedPiece.setPosition(toX, toY);
                changePlayer();
            }
        }
    }

    private void processQueenHit(Piece selectedPiece, int toX, int toY){
        int singleX = toSingle(toX - selectedPiece.getPosX());
        int singleY = toSingle(toY - selectedPiece.getPosY());
        if(toX + singleX < 0  || toX + singleX > tableWidth-1 || toY + singleY < 0 || toY + singleY > tableHeight-1) return;

        selectedPiece.setPosition(toX + singleX, toY + singleY);
        if(allowSecondHit(selectedPiece)){
            secondHit = true;
        } else changePlayer();
    }

    private boolean checkQueenStep(Piece selectedPiece, int toX, int toY){
        int i=1;
        if(checkInbetween(selectedPiece,toX,toY)) return false;
        while(selectedPiece.getPosX()-i >= 0  || selectedPiece.getPosX()+i < tableWidth || selectedPiece.getPosY()-i >= 0  || selectedPiece.getPosY()+i < tableWidth) {
            if ((selectedPiece.getPosX() + i) == toX && (selectedPiece.getPosY() + i) == toY) return true;
            if ((selectedPiece.getPosX() - i) == toX && (selectedPiece.getPosY() + i) == toY) return true;
            if ((selectedPiece.getPosX() + i) == toX && (selectedPiece.getPosY() - i) == toY) return true;
            if ((selectedPiece.getPosX() - i) == toX && (selectedPiece.getPosY() - i) == toY) return true;
            i++;
        }
        System.out.println("incorrect destination");
        return false;
    }

    private boolean checkQueenHit(Piece selectedPiece, int toX, int toY){
        Piece hit = allPieces.stream()
                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
                .findFirst()
                .orElse(null);
        if(hit != null) {
            List<Piece> availableOppenents = findOpponentsDiagonally(selectedPiece);
            if(availableOppenents.size() > 0){  //tikrinama ar pasirinkta figura galima kirsti, t.y. ar nieko nera tarpe
                for(Piece p: availableOppenents){
                    if(p.getPosX() == toX && p.getPosY() == toY){
                        if(!checkInbetween(selectedPiece,p)){
                            int singleX = toSingle(toX - selectedPiece.getPosX());
                            int singleY = toSingle(toY - selectedPiece.getPosY());
                            if(checkIfTileEmpty(toX + singleX, toY + singleY)){
                                allPieces.remove(hit);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkInbetween(Piece selectedPiece, Piece hitPiece){
        int distance = Math.abs(selectedPiece.getPosX() - hitPiece.getPosX());
        for(int i=1;i<distance;i++){
            int singleX = toSingle(hitPiece.getPosX() - selectedPiece.getPosX()) * i;
            int singleY = toSingle(hitPiece.getPosY() - selectedPiece.getPosY()) * i;
            if(!checkIfTileEmpty(selectedPiece.getPosX() + singleX, selectedPiece.getPosY() + singleY)){
                return true;
            }
        }
        System.out.println("Something is in the way!");
        return false;
    }

    private boolean checkInbetween(Piece selectedPiece, int toX, int toY){
        int distance = Math.abs(selectedPiece.getPosX() - toX);
        for(int i=1;i<distance;i++){
            int singleX = toSingle(toX - selectedPiece.getPosX()) * i;
            int singleY = toSingle(toY - selectedPiece.getPosY()) * i;
            if(!checkIfTileEmpty(selectedPiece.getPosX() + singleX, selectedPiece.getPosY() + singleY)){
                return true;
            }
        }
        System.out.println("Something is in the way!");
        return false;
    }

    private int toSingle(int number){
        if(number > 0)return 1;
        else if (number < 0)return -1;
        else return 0;
    }

    private List<Piece> findOpponentsDiagonally(Piece selectedPiece){
        List<Piece> diagonalOpponents = findNearbyOpponents(selectedPiece);

        int i=2;
        while(selectedPiece.getPosX()-i >= 0  || selectedPiece.getPosX()+i < tableWidth || selectedPiece.getPosY()-i >= 0  || selectedPiece.getPosY()+i < tableWidth){
            int finalI = i;
            List<Piece> foundOpponents = allPieces.stream()
                    .filter(   piece -> piece.getPosX() == selectedPiece.getPosX() + finalI && piece.getPosY() == selectedPiece.getPosY() + finalI && piece.getColor() == !selectedPiece.getColor()
                            || piece.getPosX() == selectedPiece.getPosX() + finalI && piece.getPosY() == selectedPiece.getPosY() - finalI && piece.getColor() == !selectedPiece.getColor()
                            || piece.getPosX() == selectedPiece.getPosX() - finalI && piece.getPosY() == selectedPiece.getPosY() + finalI && piece.getColor() == !selectedPiece.getColor()
                            || piece.getPosX() == selectedPiece.getPosX() - finalI && piece.getPosY() == selectedPiece.getPosY() - finalI && piece.getColor() == !selectedPiece.getColor()
                    )
                    .collect(Collectors.toList());

            diagonalOpponents = Stream.concat(diagonalOpponents.stream(),foundOpponents.stream()).collect(Collectors.toList());
            i++;
        }
        return diagonalOpponents;
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
        if(toX + (toX - selectedPiece.getPosX()) < 0  || toX + (toX - selectedPiece.getPosX()) > tableWidth-1 || toY + (toY - selectedPiece.getPosY()) < 0 || toY + (toY - selectedPiece.getPosY()) > tableHeight-1) return;
        selectedPiece.setPosition(toX + (toX - selectedPiece.getPosX()), toY + (toY - selectedPiece.getPosY()));
            if(allowSecondHit(selectedPiece)){
                secondHit = true;
            } else changePlayer();
    }

    private boolean allowSecondHit(Piece selectedPiece){
        List<Piece> search = findNearbyOpponents(selectedPiece);
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

    private List<Piece> findNearbyOpponents(Piece selectedPiece){
        return allPieces.stream()
            .filter(   piece -> piece.getPosX() == selectedPiece.getPosX() + 1 && piece.getPosY() == selectedPiece.getPosY() + 1 && piece.getColor() == !selectedPiece.getColor()
                    || piece.getPosX() == selectedPiece.getPosX() + 1 && piece.getPosY() == selectedPiece.getPosY() - 1 && piece.getColor() == !selectedPiece.getColor()
                    || piece.getPosX() == selectedPiece.getPosX() - 1 && piece.getPosY() == selectedPiece.getPosY() + 1 && piece.getColor() == !selectedPiece.getColor()
                    || piece.getPosX() == selectedPiece.getPosX() - 1 && piece.getPosY() == selectedPiece.getPosY() - 1 && piece.getColor() == !selectedPiece.getColor()
            )
            .collect(Collectors.toList());
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
            List<Piece> availableOppenents = findNearbyOpponents(selectedPiece);
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