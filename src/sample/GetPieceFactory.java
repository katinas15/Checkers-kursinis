package sample;

public class GetPieceFactory {
    public Piece getPiece(String pieceType){
        if(pieceType == null){
            return null;
        }

        if(pieceType.equalsIgnoreCase("REGULAR")){
            return new RegularPiece()
        }
    }
}
