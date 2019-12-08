package sample;

import static sample.GameBoard.*;

public class StandardPieceMethods {
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

    public boolean checkInbetween(Piece selectedPiece, int toX, int toY){
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

    public boolean isInbounds(Piece selectedPiece, int i){
        if(selectedPiece.getPosX()-i >= 0) return true;
        if(selectedPiece.getPosX()+i < tableWidth) return true;
        if(selectedPiece.getPosY()-i >= 0) return true;
        if(selectedPiece.getPosY()+i < tableHeight) return true;

        return false;
    }

    public int toSingle(int number){
        if(number > 0)return 1;
        else if (number < 0)return -1;
        else return 0;
    }
}
