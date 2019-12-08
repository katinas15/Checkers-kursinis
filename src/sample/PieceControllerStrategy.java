package sample;

import java.util.List;

public interface PieceControllerStrategy {
    int [][] diagonalArray = {
            { 1, 1},
            { 1,-1},
            {-1, 1},
            {-1,-1}
    };

    public boolean checkHit(Piece piece, int toX, int toY);
    public void processHit(Piece piece, int toX, int toY);
    public void checkSecondHit(Piece selectedPiece);
    public boolean checkStep(Piece selectedPiece, int toX, int toY);
    public void processStep(Piece selectedPiece, int toX, int toY);
    public List<Piece> findOpponents(Piece selectedPiece);
}
