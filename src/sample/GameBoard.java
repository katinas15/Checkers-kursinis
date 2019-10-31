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

    public int movePiece(int pieceX, int pieceY, int toX, int toY){
        if(pieceX < 0 || pieceY < 0 || pieceX > 7 || pieceY > 7) return 2;
        int piece = board[pieceY][pieceX];

        if(checkStep(piece, pieceX, pieceY, toX, toY)){
            if(board[toY][toX] == 1 || board[toY][toX] == 2 || board[toY][toX] == 3 || board[toY][toX] == 4){
                if(checkHit(piece,pieceX, pieceY, toX, toY)){
                    if(piece == 3 || piece == 4){
                        int behindX = toSingle(toX - pieceX);
                        int behindY = toSingle(toY - pieceY);
                        board[toY + behindY][toX + behindX] = piece;
                        board[pieceY][pieceX] = 0;
                        board[toY][toX] = 0;
                        changeToQueen(piece, toX + behindX, toY + behindY) ;
                        if (allowSecondHit(piece, toX + behindX, toY + behindY)) return 1;
                    } else {
                        board[toY + (toY - pieceY)][toX + (toX - pieceX)] = piece;
                        board[pieceY][pieceX] = 0;
                        board[toY][toX] = 0;
                        changeToQueen(piece,toX + (toX - pieceX), toY + (toY - pieceY));
                        if (allowSecondHit(piece, toX + (toX - pieceX), toY + (toY - pieceY))) return 1;
                    }
                } else return 2;
            } else {
                board[pieceY][pieceX] = 0;
                board[toY][toX] = piece;
            }
        } else if(checkHit(piece,pieceX, pieceY, toX, toY)){
            if(piece == 3 || piece == 4){
                int behindX = toSingle(toX - pieceX);
                int behindY = toSingle(toY - pieceY);
                board[toY + behindY][toX + behindX] = piece;
                board[pieceY][pieceX] = 0;
                board[toY][toX] = 0;
                changeToQueen(piece, toX + behindX, toY + behindY) ;
                if (allowSecondHit(piece, toX + behindX, toY + behindY)) return 1;
            } else {
                board[toY + (toY - pieceY)][toX + (toX - pieceX)] = piece;
                board[pieceY][pieceX] = 0;
                board[toY][toX] = 0;
                changeToQueen(piece,toX + (toX - pieceX), toY + (toY - pieceY));
                if (allowSecondHit(piece, toX + (toX - pieceX), toY + (toY - pieceY))) return 1;
            }
        } else {

            return 2;
        }

        return 0;
    }

    private void changeToQueen(int color, int pieceX,int pieceY){
        if(color == 2 && pieceY == tableHeight-1) board[pieceY][pieceX] = 4;
        if(color == 1 && pieceY == 0) board[pieceY][pieceX] = 3;
    }

    private boolean allowSecondHit(int color,int pieceX, int pieceY){
        if(color == 1 || color == 2){
            if((checkHit(color,pieceX, pieceY, pieceX + 1, pieceY + 1))) return true;
            if((checkHit(color,pieceX, pieceY, pieceX + 1, pieceY - 1))) return true;
            if((checkHit(color,pieceX, pieceY, pieceX - 1, pieceY - 1))) return true;
            if((checkHit(color,pieceX, pieceY, pieceX - 1, pieceY + 1))) return true;
        }

        return false;
    }

    private boolean checkHit(int color,int pieceX, int pieceY, int toX, int toY){
        if(toX + (toX - pieceX) < 0 || toY + (toY - pieceY) < 0 || toX + (toX - pieceX) > tableWidth-1 || toY + (toY - pieceY) > tableHeight-1) return false;
        if(color == 2){
            if( (board[toY][toX] == 1 || board[toY][toX] == 3) && board[toY + (toY - pieceY)][toX + (toX - pieceX)] == 0) return true;
            if( (board[toY][toX] == 1 || board[toY][toX] == 3) && board[toY - (toY - pieceY)][toX - (toX - pieceX)] == 0) return true;
        } else if(color == 1){
            if( (board[toY][toX] == 2 || board[toY][toX] == 4) && board[toY + (toY - pieceY)][toX + (toX - pieceX)] == 0) return true;
            if( (board[toY][toX] == 2 || board[toY][toX] == 4) && board[toY - (toY - pieceY)][toX - (toX - pieceX)] == 0) return true;
        } else if (color == 3){
            int behindX = toSingle(toX - pieceX);
            int behindY = toSingle(toY - pieceY);
            if( (board[toY][toX] == 2 || board[toY][toX] == 4) && board[toY + behindY][toX + behindX] == 0) return true;
            if( (board[toY][toX] == 2 || board[toY][toX] == 4) && board[toY - behindY][toX - behindX] == 0) return true;
        } else if (color == 4){
            int behindX = toSingle(toX - pieceX);
            int behindY = toSingle(toY - pieceY);
            if( (board[toY][toX] == 1 || board[toY][toX] == 3) && board[toY + behindY][toX + behindX] == 0) return true;
            if( (board[toY][toX] == 1 || board[toY][toX] == 3) && board[toY - behindY][toX - behindX] == 0) return true;
        }
        return false;
    }

    private int toSingle(int number){
        if(number > 0) return 1;
        else if (number < 0) return -1;
        return 0;
    }

    private boolean checkStep(int color, int pieceX, int pieceY, int toX, int toY){
        if(color == 2){
            if( (pieceX + 1) == toX && (pieceY + 1) == toY) return true;
            if( (pieceX - 1) == toX && (pieceY + 1) == toY) return true;
        } else if (color == 1){
            if( (pieceX + 1) == toX && (pieceY - 1) == toY) return true;
            if( (pieceX - 1) == toX && (pieceY - 1) == toY) return true;
        } else if (color == 3 || color == 4){
            int i = 1;
            while(pieceX + i < tableWidth && pieceY + i < tableHeight){
                if( (pieceX + i) == toX && (pieceY + i) == toY) return true;
                i++;
            }

            i = 1;
            while(pieceX - i >= 0 && pieceY + i < tableHeight){
                if( (pieceX - i) == toX && (pieceY + i) == toY) return true;
                i++;
            }

            i = 1;
            while(pieceX - i >= 0 && pieceY - i >= 0){
                if( (pieceX - i) == toX && (pieceY - i) == toY) return true;
                i++;
            }

            i = 1;
            while(pieceX + i < tableWidth && pieceY - i >= 0){
                if( (pieceX + i) == toX && (pieceY - i) == toY) return true;
                i++;
            }
        }

        System.out.println("incorrect destination");
        return false;
    }

}