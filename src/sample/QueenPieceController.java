package sample;

import java.util.ArrayList;
import java.util.List;

import static sample.FxMain.*;
import static sample.GameBoard.tableHeight;
import static sample.GameBoard.tableWidth;

public class QueenPieceController implements PieceControllerStrategy {
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

        if(checkInbetween(selectedPiece, hit.getPosX(), hit.getPosY())) return false;

        int behindHitX = toX + toSingle(toX - selectedPiece.getPosX());
        int behindHitY = toY + toSingle(toY - selectedPiece.getPosY());
        if(!checkBounds(behindHitX,behindHitY)) return false;

        if(checkTile(behindHitX, behindHitY) == null){
            return true;
        }

        return false;
    }

    @Override
    public void processHit(Piece selectedPiece, int toX, int toY){
        int behindHitX = toX + toSingle(toX - selectedPiece.getPosX());
        int behindHitY = toY + toSingle(toY - selectedPiece.getPosY());

        selectedPiece.setPosition(toX + behindHitX, toY + behindHitY);

        Piece hit = allPieces.stream()
                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
                .findFirst()
                .orElse(null);
        allPieces.remove(hit);

        selectedPiece.setPosition(behindHitX, behindHitY);
    }

    @Override
    public void checkSecondHit(Piece selectedPiece){
        List<Piece> search = findOpponents(selectedPiece);
        if(search.size() < 0){
            return ;
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
        if(checkInbetween(selectedPiece,toX,toY)) return false;

        int i=1;
        while(isInbounds(selectedPiece, i)) {
            int finalI = i;
            for (int t[]: diagonalArray) {
                if ((selectedPiece.getPosX() + t[0] * finalI) == toX
                        && (selectedPiece.getPosY() + t[1] * finalI) == toY) return true;
            }
            i++;
        }
        System.out.println("incorrect destination");
        return false;
    }

    @Override
    public List<Piece> findOpponents(Piece selectedPiece){
        List<Piece> diagonalOpponents = new ArrayList<>();

        int i=1;
        while(isInbounds(selectedPiece, i)){
            int finalI = i;
            for (int t[]: diagonalArray) {
                Piece found = allPieces.stream().filter(piece ->
                        piece.getPosX() == selectedPiece.getPosX() + t[0] * finalI
                                && piece.getPosY() == selectedPiece.getPosY() + t[1] * finalI
                                && piece.getColor() == !selectedPiece.getColor())
                        .findFirst().orElse(null);
                if(found != null) diagonalOpponents.add(found);
            }
            i++;
        }
        return diagonalOpponents;
    }

    private boolean isInbounds(Piece selectedPiece, int i){
        if(selectedPiece.getPosX()-i >= 0) return true;
        if(selectedPiece.getPosX()+i < tableWidth) return true;
        if(selectedPiece.getPosY()-i >= 0) return true;
        if(selectedPiece.getPosY()+i < tableHeight) return true;

        return false;
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

    private boolean checkBounds(int toX, int toY){
        if(toX < 0) return false;
        if(toX > tableWidth-1) return false;
        if(toY < 0) return false;
        if(toY > tableHeight-1) return false;

        return true;
    }

    private boolean checkInbetween(Piece selectedPiece, int toX, int toY){
        int distance = Math.abs(selectedPiece.getPosX() - toX);
        if(distance == 1) return false;

        for(int i=1;i<distance;i++){
            int singleX = toSingle(toX - selectedPiece.getPosX()) * i;
            int singleY = toSingle(toY - selectedPiece.getPosY()) * i;
            if(checkTile(selectedPiece.getPosX() + singleX, selectedPiece.getPosY() + singleY) != null){
                System.out.println("Something is in the way!");
                return true;
            }
        }
        return false;
    }

    private int toSingle(int number){
        if(number > 0)return 1;
        else if (number < 0)return -1;
        else return 0;
    }
}
