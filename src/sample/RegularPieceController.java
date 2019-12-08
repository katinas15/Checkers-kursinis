package sample;

import java.util.ArrayList;
import java.util.List;

import static sample.FxMain.*;
import static sample.GameBoard.tableHeight;
import static sample.GameBoard.tableWidth;

public class RegularPieceController implements PieceControllerStrategy {
    StandardChecks check = new StandardChecks();

    @Override
    public boolean checkHit(Piece selectedPiece, int toX, int toY){
        if(!check.checkBounds(toX,toY)) return false;
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
        if(!check.checkBounds(behindHitX,behindHitY)) return false;

        if(check.checkTile(behindHitX, behindHitY) == null){
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
        if(!check.checkBounds(toX,toY)) return false;
        if (check.checkTile(toX, toY) != null) return false;

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

    @Override
    public void processStep(Piece selectedPiece, int toX, int toY){
        selectedPiece.setPosition(toX, toY);
        changePlayer();
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

        allPieces.remove(selectedPiece);
        GetPieceFactory pieceFactory = new GetPieceFactory();
        allPieces.add(pieceFactory.getPiece(selectedPiece));
        if(selectedPiece.getColor() == currentPlayer) changePlayer();
    }
}
