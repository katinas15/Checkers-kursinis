package sample;

import java.util.ArrayList;
import java.util.List;

import static sample.GameBoard.allPieces;
import static sample.GameBoard.secondHit;
import static sample.GameBoard.changePlayer;

public class QueenPieceController implements PieceControllerStrategy {
    StandardPieceMethods SPM = new StandardPieceMethods();
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

        if(SPM.checkInbetween(selectedPiece, hit.getPosX(), hit.getPosY())) return false;

        int behindHitX = toX + SPM.toSingle(toX - selectedPiece.getPosX());
        int behindHitY = toY + SPM.toSingle(toY - selectedPiece.getPosY());
        if(!SPM.checkBounds(behindHitX,behindHitY)) return false;

        if(SPM.checkTile(behindHitX, behindHitY) == null){
            return true;
        }

        return false;
    }

    @Override
    public void processHit(Piece selectedPiece, int toX, int toY){
        int behindHitX = toX + SPM.toSingle(toX - selectedPiece.getPosX());
        int behindHitY = toY + SPM.toSingle(toY - selectedPiece.getPosY());

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
        if (SPM.checkTile(toX, toY) != null) return false;
        if(SPM.checkInbetween(selectedPiece,toX,toY)) return false;

        int i=1;
        while(SPM.isInbounds(selectedPiece, i)) {
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
        while(SPM.isInbounds(selectedPiece, i)){
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

    @Override
    public void processStep(Piece selectedPiece, int toX, int toY){
        selectedPiece.setPosition(toX, toY);
        changePlayer();
    }


}
