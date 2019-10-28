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
        return board[y][x];
    }

    public boolean movePiece(int pieceX, int pieceY, int toX, int toY){
        int piece = board[pieceY][pieceX];
        if(!checkPiece(piece)) return false;

        if(checkSingleStep(piece%2==0, pieceX, pieceY, toX, toY)){
            if(board[toY][toX] == 1 || board[toY][toX] == 2){
                if(checkHit(piece%2==0,pieceX, pieceY, toX, toY)){
                    board[toY + (toY - pieceY)][toX + (toX - pieceX)] = piece;
                    board[pieceY][pieceX] = 0;
                    board[toY][toX] = 0;
                    return true;
                }
            } else {
                board[pieceY][pieceX] = 0;
                board[toY][toX] = piece;
            }
        }

        return false;
    }

    private boolean checkPiece(int piece){
        if(piece == 0) {
            System.out.println("incorrect piece");
            return false;
        }
        return true;
    }

    private boolean checkHit(boolean color,int pieceX, int pieceY, int toX, int toY){
        if(color){
            if(board[toY][toX] == 1 && board[toY + (toY - pieceY)][toX + (toX - pieceX)] == 0) return true;
        } else {
            if(board[toY][toX] == 2 && board[toY + (toY - pieceY)][toX + (toX - pieceX)] == 0) return true;
        }
        return false;
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
