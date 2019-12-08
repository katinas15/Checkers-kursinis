package sample;

import java.util.List;

public interface PieceControllerStrategy {
    int [][] diagonalArray = {
            { 1, 1},
            { 1,-1},
            {-1, 1},
            {-1,-1}
    };

    boolean checkHit(Piece piece, int toX, int toY);
    void processHit(Piece piece, int toX, int toY);
    void checkSecondHit(Piece selectedPiece);
    boolean checkStep(Piece selectedPiece, int toX, int toY);
    void processStep(Piece selectedPiece, int toX, int toY);
    List<Piece> findOpponents(Piece selectedPiece);
}
