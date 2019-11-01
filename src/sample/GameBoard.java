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

    public void movePiece(Piece selectedPiece, int toX, int toY){
        if(selectedPiece.isQueen()){

        } else {
            if(checkStep(selectedPiece, toX, toY)){
                selectedPiece.setPosition(toX, toY);
            }
        }
    }

//    private void changeToQueen(int color, int pieceX,int pieceY){
//        if(color == 2 && pieceY == tableHeight-1) board[pieceY][pieceX] = 4;
//        if(color == 1 && pieceY == 0) board[pieceY][pieceX] = 3;
//    }
//
//    private boolean allowSecondHit(int color,int pieceX, int pieceY){
//        if(color == 1 || color == 2){
//            if((checkHit(color,pieceX, pieceY, pieceX + 1, pieceY + 1))) return true;
//            if((checkHit(color,pieceX, pieceY, pieceX + 1, pieceY - 1))) return true;
//            if((checkHit(color,pieceX, pieceY, pieceX - 1, pieceY - 1))) return true;
//            if((checkHit(color,pieceX, pieceY, pieceX - 1, pieceY + 1))) return true;
//        }
//
//        return false;
//    }
//
//    private boolean checkHit(Piece selectedPiece, int toX, int toY){
//        if(color == 2){
//            if( (board[toY][toX] == 1 || board[toY][toX] == 3) && board[toY + (toY - pieceY)][toX + (toX - pieceX)] == 0) return true;
//            if( (board[toY][toX] == 1 || board[toY][toX] == 3) && board[toY - (toY - pieceY)][toX - (toX - pieceX)] == 0) return true;
//        } else if(color == 1){
//            if( (board[toY][toX] == 2 || board[toY][toX] == 4) && board[toY + (toY - pieceY)][toX + (toX - pieceX)] == 0) return true;
//            if( (board[toY][toX] == 2 || board[toY][toX] == 4) && board[toY - (toY - pieceY)][toX - (toX - pieceX)] == 0) return true;
//        } else if (color == 3){
//            int behindX = toSingle(toX - pieceX);
//            int behindY = toSingle(toY - pieceY);
//            if( (board[toY][toX] == 2 || board[toY][toX] == 4) && board[toY + behindY][toX + behindX] == 0) return true;
//            if( (board[toY][toX] == 2 || board[toY][toX] == 4) && board[toY - behindY][toX - behindX] == 0) return true;
//        } else if (color == 4){
//            int behindX = toSingle(toX - pieceX);
//            int behindY = toSingle(toY - pieceY);
//            if( (board[toY][toX] == 1 || board[toY][toX] == 3) && board[toY + behindY][toX + behindX] == 0) return true;
//            if( (board[toY][toX] == 1 || board[toY][toX] == 3) && board[toY - behindY][toX - behindX] == 0) return true;
//        }
//        return false;
//    }

    private boolean checkStep(Piece selectedPiece, int toX, int toY){
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

}