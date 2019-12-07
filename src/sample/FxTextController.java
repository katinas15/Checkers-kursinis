package sample;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import java.util.List;
import java.util.stream.Collectors;
import static sample.FxMain.*;
import static sample.GameBoard.tableWidth;

public class FxTextController {
    private Text text = new Text();
    private Text gameOverText = new Text();
    private Button endTurnButton = new Button();
    private Button resetButton = new Button();

    private void setCurrentPlayerText(){
        if(currentPlayer){
            text.setText("Player White");
        } else text.setText("Player Red");

        text.setX(tileSize*tableWidth + 10);
        text.setY(20);
    }

    private void setEndTurnButton(){
        endTurnButton.setText("End Turn");
        endTurnButton.setLayoutX(tileSize*tableWidth + 10);
        endTurnButton.setLayoutY(40);

        endTurnButton.setOnAction(actionEvent ->  {
            if(secondHit){
                changePlayer();
                paintAll();
            } else {
                System.out.println("You must move at least once!");
            }
        });
    }

    private void setResetButton(){
        resetButton.setText("Reset All");
        resetButton.setLayoutX(tileSize*tableWidth + 10);
        resetButton.setLayoutY(80);

        resetButton.setOnAction(actionEvent ->  {
            resetGame();
        });
    }

    private void gameOver(){
        gameOverText.setX(tileSize*tableWidth);
        gameOverText.setY(140);

        List<Piece> pieces = allPieces.stream()
                .filter(   piece -> piece.getColor() == true)
                .collect(Collectors.toList());

        if(pieces.size() < 1){
            gameOverText.setText("Player Red Wins!!!");
            return;
        }

        pieces = allPieces.stream()
                .filter(   piece -> piece.getColor() == false)
                .collect(Collectors.toList());

        if(pieces.size() < 1){
            gameOverText.setText("Player White Wins!!!");
            return;
        }
    }

    public Text getText() {
        return text;
    }

    public Text getGameOverText() {
        return gameOverText;
    }

    public Button getEndTurnButton() {
        return endTurnButton;
    }

    public Button getResetButton() {
        return resetButton;
    }
}
