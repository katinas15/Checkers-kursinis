package sample;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    @Test
    public void checkRegularHit(){
        int toX = 1, toY = 2;
        int[][] board = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 2, 0, 0},
                {1, 0, 0, 0}
        };
        GameBoard gameBoard = new GameBoard(board);
        StandardPieceMethods SPM = new StandardPieceMethods();

        Piece actualPiece = SPM.checkTile(0,3);
        gameBoard.update(actualPiece, toX, toY);

        assertEquals(2, actualPiece.getPosX());
        assertEquals(1, actualPiece.getPosY());
    }

    @Test
    public void checkRegularStep(){
        int toX = 1, toY = 2;
        int[][] board = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {1, 0, 0, 0}
        };
        GameBoard gameBoard = new GameBoard(board);
        StandardPieceMethods SPM = new StandardPieceMethods();

        Piece actualPiece = SPM.checkTile(0,3);
        gameBoard.update(actualPiece, toX, toY);

        assertEquals(toX, actualPiece.getPosX());
        assertEquals(toY, actualPiece.getPosY());
    }

    @Test
    public void checkChangeToQueenAfterHit(){
        int toX = 1, toY = 1;
        int[][] board = {
                {0, 0, 0},
                {0, 2, 0},
                {1, 0, 0}
        };
        GameBoard gameBoard = new GameBoard(board);
        StandardPieceMethods SPM = new StandardPieceMethods();

        Piece actualPiece = SPM.checkTile(0,2);
        gameBoard.update(actualPiece, toX, toY);

        assertTrue(SPM.checkTile(2,0) instanceof QueenPiece);
    }

    @Test
    public void checkChangeToQueenAfterStep(){
        int toX = 2, toY = 0;
        int[][] board = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        GameBoard gameBoard = new GameBoard(board);
        StandardPieceMethods SPM = new StandardPieceMethods();

        Piece actualPiece = SPM.checkTile(1,1);
        gameBoard.update(actualPiece, toX, toY);

        assertTrue(SPM.checkTile(2,0) instanceof QueenPiece);
    }

    @Test
    public void checkQueenHit(){
        int toX = 2, toY = 1;
        int[][] board = {
                {0, 0, 0, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 0},
                {3, 0, 0, 0}
        };
        GameBoard gameBoard = new GameBoard(board);
        StandardPieceMethods SPM = new StandardPieceMethods();

        Piece actualPiece = SPM.checkTile(0,3);
        gameBoard.update(actualPiece, toX, toY);

        assertEquals(3, actualPiece.getPosX());
        assertEquals(0, actualPiece.getPosY());
    }

    @Test
    public void checkQueenStep(){
        int toX = 2, toY = 1;
        int[][] board = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {3, 0, 0, 0}
        };
        GameBoard gameBoard = new GameBoard(board);
        StandardPieceMethods SPM = new StandardPieceMethods();

        Piece actualPiece = SPM.checkTile(0,3);
        gameBoard.update(actualPiece, toX, toY);

        assertEquals(toX, actualPiece.getPosX());
        assertEquals(toY, actualPiece.getPosY());
    }
}
