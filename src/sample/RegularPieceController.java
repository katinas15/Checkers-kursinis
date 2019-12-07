package sample;

import java.util.ArrayList;
import java.util.List;

import static sample.FxMain.*;
import static sample.GameBoard.tableHeight;
import static sample.GameBoard.tableWidth;

public class RegularPieceController implements PieceControllerStrategy {
    @Override
    public boolean checkHit(Piece selectedPiece, int toX, int toY){
        List<Piece> availableOppenents = findOpponents(selectedPiece);
        if(availableOppenents.size() < 0){
            return false;
        }

        Piece hit = availableOppenents.stream()
                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
                .findFirst()
                .orElse(null);

        if(hit == null) {
            return false;
        }

        int behindHitX = toX + (toX - selectedPiece.getPosX());
        int behindHitY = toY + (toY - selectedPiece.getPosY());
        if(!checkBounds(behindHitX,behindHitY)) return false;

        if(checkTile(behindHitX, behindHitY) == null){
            return true;
        }

        return false;
    }

    @Override
    public  void processHit(Piece selectedPiece, int toX, int toY){
        Piece hit = allPieces.stream()
                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
                .findFirst()
                .orElse(null);
        allPieces.remove(hit);

        int behindHitX = toX + (toX - selectedPiece.getPosX());
        int behindHitY = toY + (toY - selectedPiece.getPosY());
        selectedPiece.setPosition(behindHitX, behindHitY);
    }

    @Override
    public void checkSecondHit(Piece selectedPiece){
        List<Piece> search = findOpponents(selectedPiece);
        if(search.size() < 0){
            return;
        }

        for(Piece p:search){
            if(checkHit(selectedPiece, p.getPosX(), p.getPosY())){
                secondHit = true;
                return;
            }
        }

        changePlayer();
    }

    @Override
    public boolean checkStep(Piece selectedPiece, int toX, int toY){
        if (checkTile(toX, toY) != null) return false;

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

    @Override
    public List<Piece> findOpponents(Piece selectedPiece){
        List<Piece> foundOpponents = new ArrayList<>();
        for (int t[]: diagonalArray) {
            Piece found = allPieces.stream().filter(piece ->
                            piece.getPosX() == selectedPiece.getPosX() + t[0]
                            && piece.getPosY() == selectedPiece.getPosY() + t[1]
                            && piece.getColor() == !selectedPiece.getColor())
                    .findFirst().orElse(null);
            if(found != null) foundOpponents.add(found);
        }

        return foundOpponents;
    }

    private boolean checkBounds(int toX, int toY){
        if(toX < 0) return false;
        if(toX > tableWidth-1) return false;
        if(toY < 0) return false;
        if(toY > tableHeight-1) return false;

        return true;
    }

    private Piece checkTile(int toX,int toY){
        if(!checkBounds(toX,toY)) return null;

        Piece tile = allPieces.stream()
                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
                .findFirst()
                .orElse(null);
        if(tile == null) {
            return null;
        }
        return tile;
    }
}
