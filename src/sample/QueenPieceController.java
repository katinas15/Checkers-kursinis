package sample;

import java.util.List;

public class QueenPieceController implements PieceControllerStrategy {
    @Override
    public boolean checkHit(Piece piece, int toX, int toY) {
        return false;
    }

    @Override
    public void processHit(Piece piece, int toX, int toY) {

    }

    @Override
    public void checkSecondHit(Piece selectedPiece) {

    }

    @Override
    public boolean checkStep(Piece selectedPiece, int toX, int toY) {
        return false;
    }

    @Override
    public List<Piece> findOpponents(Piece selectedPiece) {
        return null;
    }
}
