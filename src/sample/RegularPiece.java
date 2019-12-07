package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static sample.FxMain.playerPieceColor;
import static sample.FxMain.tileSize;

public class RegularPiece extends Piece {
    private boolean color;
    private int posX, posY;
    private Circle sprite;

    public RegularPiece(int posX, int posY, boolean color) {
        super(posX, posY, color);

        sprite = new Circle();
        sprite.setRadius(tileSize/2f - 1.5f);
        sprite.setStrokeWidth(3);
        sprite.setStroke(Color.BLACK);

        int val = color? 0 : 1;
        sprite.setFill(Color.web(playerPieceColor[val]));
    }
}
