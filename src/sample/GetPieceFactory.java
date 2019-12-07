package sample;

public class GetPieceFactory {
    public Piece getPiece(String pieceType, int toX, int toY, boolean color){
        if(pieceType == null){
            return null;
        }

        if(pieceType.equalsIgnoreCase("REGULAR")){
            return new RegularPiece(toX, toY, color);
        }

        if(pieceType.equalsIgnoreCase("QUEEN")){
            return new QueenPiece(toX, toY, color);
        }
        return null;
    }
}
