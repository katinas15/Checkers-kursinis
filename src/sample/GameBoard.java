package sample;

import java.util.ArrayList;
import java.util.List;

import static sample.FxMain.*;

public class GameBoard {

    public static int tableWidth;
    public static int tableHeight;

    int[][] board;

    public GameBoard(int[][] board) {
        this.board = board;
        tableWidth = board[0].length;
        tableHeight = board.length;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getTile(int x, int y){
        return board[y][x];
    }

    public void update(Piece selectedPiece, int toX, int toY){
//        if(selectedPiece.isQueen()){
//            moveQueen(selectedPiece,toX,toY);
//        } else {
            moveStandard(selectedPiece, toX, toY);
//        }
    }

    private void moveQueen(Piece selectedPiece, int toX, int toY){
        if(checkQueenHit(selectedPiece, toX, toY)){
            processQueenHit(selectedPiece, toX, toY);
            allowSecondQueenHit(selectedPiece);

        } else if(checkQueenStep(selectedPiece, toX, toY)) {
            selectedPiece.setPosition(toX, toY);
            changePlayer();
        }
    }

    private boolean checkQueenHit(Piece selectedPiece, int toX, int toY){
        List<Piece> availableOppenents = findOpponentsDiagonally(selectedPiece);
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

        if(checkInbetween(selectedPiece, hit.getPosX(), hit.getPosY())) return false;

        int behindHitX = toX + toSingle(toX - selectedPiece.getPosX());
        int behindHitY = toY + toSingle(toY - selectedPiece.getPosY());
        if(!checkBounds(behindHitX,behindHitY)) return false;

        if(checkTile(behindHitX, behindHitY) == null){
            return true;
        }

        return false;
    }

    private void processQueenHit(Piece selectedPiece, int toX, int toY){
        int behindHitX = toX + toSingle(toX - selectedPiece.getPosX());
        int behindHitY = toY + toSingle(toY - selectedPiece.getPosY());

        selectedPiece.setPosition(toX + behindHitX, toY + behindHitY);

        Piece hit = allPieces.stream()
                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
                .findFirst()
                .orElse(null);
        allPieces.remove(hit);

        selectedPiece.setPosition(behindHitX, behindHitY);
    }

    private void allowSecondQueenHit(Piece selectedPiece){
        List<Piece> search = findOpponentsDiagonally(selectedPiece);
        if(search.size() < 0){
            return ;
        }

        for(Piece p:search){
            if(checkQueenHit(selectedPiece, p.getPosX(), p.getPosY())){
                secondHit = true;
                return;
            }
        }

        changePlayer();
    }

    private boolean checkQueenStep(Piece selectedPiece, int toX, int toY){
        if (checkTile(toX, toY) != null) return false;
        if(checkInbetween(selectedPiece,toX,toY)) return false;

        int i=1;
        while(isInbounds(selectedPiece, i)) {
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

    private List<Piece> findOpponentsDiagonally(Piece selectedPiece){
        List<Piece> diagonalOpponents = new ArrayList<>();

        int i=1;
        while(isInbounds(selectedPiece, i)){
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

    private boolean isInbounds(Piece selectedPiece, int i){
        if(selectedPiece.getPosX()-i >= 0) return true;
        if(selectedPiece.getPosX()+i < tableWidth) return true;
        if(selectedPiece.getPosY()-i >= 0) return true;
        if(selectedPiece.getPosY()+i < tableHeight) return true;

        return false;
    }

    private boolean checkInbetween(Piece selectedPiece, int toX, int toY){
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

    private int toSingle(int number){
        if(number > 0)return 1;
        else if (number < 0)return -1;
        else return 0;
    }

    private void moveStandard(Piece selectedPiece, int toX, int toY){
        if(!checkBounds(toX,toY)) return;

        if(checkHit(selectedPiece, toX, toY)){
            processHit(selectedPiece,toX,toY);
            checkSecondHit(selectedPiece);

        } else if(checkStep(selectedPiece, toX, toY)) {
            selectedPiece.setPosition(toX, toY);
            changePlayer();

        }
        checkChangeToQueen(selectedPiece);
    }

    private boolean checkHit(Piece selectedPiece, int toX, int toY){
        List<Piece> availableOppenents = findNearbyOpponents(selectedPiece);
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
        if(!checkBounds(behindHitX,behindHitY)) return false;

        if(checkTile(behindHitX, behindHitY) == null){
            return true;
        }

        return false;
    }

    private void processHit(Piece selectedPiece, int toX, int toY){
        Piece hit = allPieces.stream()
                .filter(piece -> piece.getPosX() == toX && piece.getPosY() == toY)
                .findFirst()
                .orElse(null);
        allPieces.remove(hit);

        int behindHitX = toX + (toX - selectedPiece.getPosX());
        int behindHitY = toY + (toY - selectedPiece.getPosY());
        selectedPiece.setPosition(behindHitX, behindHitY);
    }

    private void checkSecondHit(Piece selectedPiece){
        List<Piece> search = findNearbyOpponents(selectedPiece);
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

    private void checkChangeToQueen(Piece selectedPiece){
        if(selectedPiece.getColor()){
            if(selectedPiece.getPosY() != 0) {
                return;
            }
        } else if(selectedPiece.getPosY() != tableHeight-1){
            return;
        }

        GetPieceFactory pieceFactory = new GetPieceFactory();
        selectedPiece = pieceFactory.getPiece("QUEEN",selectedPiece.getPosX(),selectedPiece.getPosY(),selectedPiece.getColor());
        if(selectedPiece.getColor() == currentPlayer) changePlayer();
    }

    private List<Piece> findNearbyOpponents(Piece selectedPiece){
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

    private Piece checkTile(int toX,int toY){
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

    private boolean checkStep(Piece selectedPiece, int toX, int toY){
        if (checkTile(toX, toY) != null) return false;

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

    private boolean checkBounds(int toX, int toY){
        if(toX < 0) return false;
        if(toX > tableWidth-1) return false;
        if(toY < 0) return false;
        if(toY > tableHeight-1) return false;

        return true;
    }


}