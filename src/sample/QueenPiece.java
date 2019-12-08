package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import static sample.FxMain.*;

public class QueenPiece extends Piece {

    public QueenPiece(int posX, int posY, boolean color) {
        super(posX, posY, color);
        int val = color? 0 : 1;
        sprite = new Rectangle(0, 0, tileSize/1.25f, tileSize/1.25f);
        sprite.setFill(Color.web(playerPieceColor[val]));
        sprite.setStrokeWidth(strokeSize);
        sprite.setStroke(Color.BLACK);
    }

    @Override
    public Shape getSprite() {
        return sprite;
    }
}
