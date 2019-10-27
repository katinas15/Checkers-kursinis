package sample;

public class GameBoard {

    public static int tableWidth = 8;
    public static int tableHeight = 8;

    int[][] board = {
            {0, 2, 0, 2, 0, 2, 0, 2},
            {2, 0, 2, 0, 2, 0, 2, 0},
            {0, 2, 0, 2, 0, 2, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 0, 1, 0}
    };

    public int getTile(int x,int y){
        return board[x][y];
    }

    public void movePiece(int pieceX, int pieceY, int toX, int toY){
        int piece = board[pieceY][pieceX];
        if(!checkPiece(piece)) return;

        if(checkSingleStep(piece%2==0, pieceX, pieceY, toX, toY)){
            board[pieceY][pieceX] = 0;
            board[toY][toX] = piece;
        }
    }

    private boolean checkPiece(int piece){
        if(piece == 0) {
            System.out.println("incorrect piece");
            return false;
        }
        return true;
    }

    private boolean checkSingleStep(boolean color, int pieceX, int pieceY, int toX, int toY){
        if(color){
            if( (pieceX + 1) == toX && (pieceY + 1) == toY) return true;
            if( (pieceX - 1) == toX && (pieceY + 1) == toY) return true;
        } else {
            if( (pieceX + 1) == toX && (pieceY - 1) == toY) return true;
            if( (pieceX - 1) == toX && (pieceY - 1) == toY) return true;
        }

        System.out.println("incorrect destination");
        return false;
    }

}
