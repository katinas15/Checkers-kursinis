package sample;

import static sample.FxMain.allPieces;
import static sample.GameBoard.tableHeight;
import static sample.GameBoard.tableWidth;

public class StandardChecks {
    public boolean checkBounds(int toX, int toY){
        if(toX < 0) return false;
        if(toX > tableWidth-1) return false;
        if(toY < 0) return false;
        if(toY > tableHeight-1) return false;

        return true;
    }

    public Piece checkTile(int toX,int toY){
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
