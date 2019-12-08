package sample;

public class GetPieceFactory {
    public Piece getPiece(int pieceType, int toX, int toY){
        boolean color = pieceType%2 == 1;
        if(pieceType == 1 || pieceType == 2){
            return new RegularPiece(toX, toY, color);
        }

        if(pieceType == 3 || pieceType == 4){
            return new QueenPiece(toX, toY, color);
        }
        return null;
    }

    public Piece getPiece(Piece piece){
        return new QueenPiece(piece.getPosX(), piece.getPosY(), piece.getColor());
    }
}
