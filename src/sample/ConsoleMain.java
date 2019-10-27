package sample;

import static sample.GameBoard.tableHeight;
import static sample.GameBoard.tableWidth;

public class ConsoleMain {
    static GameBoard gameBoard = new GameBoard();
    public static void main(String[] args){
        printGame();
    }

    private static void printGame(){
        for(int i = 0;i<tableHeight;i++){
            for(int j = 0;j<tableWidth;j++){
                if(gameBoard.getTile(i,j) == 2) System.out.print("X");
                else if(gameBoard.getTile(i,j) == 1) System.out.print("Y");
                else System.out.print("O");
            }
            System.out.println();
        }
    }
}
