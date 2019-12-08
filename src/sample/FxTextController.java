package sample;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.List;
import java.util.stream.Collectors;

import static sample.FxMain.*;
import static sample.GameBoard.*;

public class FxTextController {
    private Text text = new Text();
    private Text gameOverText = new Text();
    private Button endTurnButton = new Button();
    private Button resetButton = new Button();

    private int textOffsetX = 10;
    private int textOffsetY = 20;

    private GameBoard board;

    public void update(){
        setCurrentPlayerText();
        setEndTurnButton();
        setResetButton();
        gameOver();
    }

    public void setCurrentPlayerText(){
        if(currentPlayer){
            text.setText("Player White");
        } else text.setText("Player Red");

        text.setX(tileSize*tableWidth + textOffsetX);
        text.setY(textOffsetY);
    }

    public void setEndTurnButton(){
        endTurnButton.setText("End Turn");
        endTurnButton.setLayoutX(tileSize*tableWidth + textOffsetX);
        endTurnButton.setLayoutY(textOffsetY*2);

        endTurnButton.setOnAction(actionEvent ->  {
            if(secondHit){
                changePlayer();
                board.paintAll();
            } else {
                System.out.println("You must move at least once!");
            }
        });
    }

    public void setResetButton(){
        resetButton.setText("Reset All");
        resetButton.setLayoutX(tileSize*tableWidth + textOffsetX);
        resetButton.setLayoutY(textOffsetY*4);

        resetButton.setOnAction(actionEvent ->  {
            board.resetGame();
        });
    }

    public void gameOver(){
        gameOverText.setX(tileSize*tableWidth);
        gameOverText.setY(textOffsetY*7);

        List<Piece> pieces = allPieces.stream()
                .filter(   piece -> piece.getColor() == true)
                .collect(Collectors.toList());

        if(pieces.size() <= 0){
            gameOverText.setText("Player Red Wins!!!");
            return;
        }

        pieces = allPieces.stream()
                .filter(   piece -> piece.getColor() == false)
                .collect(Collectors.toList());

        if(pieces.size() <= 0){
            gameOverText.setText("Player White Wins!!!");
            return;
        }

        gameOverText.setText("");
    }

    public void resetGameOver(){
        gameOverText.setText("");
    }

    public void setBoard(GameBoard board){
        this.board = board;
    }

    public Group getFields(){
        return new Group(text, endTurnButton, gameOverText, resetButton);
    }
}
