package sample;

import java.io.IOException;
import java.util.Scanner;

import static sample.GameBoard.tableHeight;
import static sample.GameBoard.tableWidth;

public class ConsoleMain {

    public static void main(String[] args) throws IOException {
        GameBoard gameBoard = new GameBoard();
        Scanner keyboard = new Scanner(System.in);
        while(true){
            printGame(gameBoard);
            int pieceX = Integer.parseInt(keyboard.nextLine().trim());
            int pieceY = Integer.parseInt(keyboard.nextLine().trim());
            int toX = Integer.parseInt(keyboard.nextLine().trim());
            int toY = Integer.parseInt(keyboard.nextLine().trim());
            gameBoard.movePiece(pieceX,pieceY,toX, toY);
        }
    }

    private static void printGame(GameBoard gameBoard){
        System.out.print(" ");
        for(int j = 0;j<tableWidth;j++){
            System.out.print(j);

        }
        System.out.println();

        for(int i = 0;i<tableHeight;i++){
            System.out.print(i);
            for(int j = 0;j<tableWidth;j++){
                if(gameBoard.getTile(i,j) == 2) System.out.print("X");
                else if(gameBoard.getTile(i,j) == 1) System.out.print("Y");
                else System.out.print("O");
            }
            System.out.println();
        }
    }


}
