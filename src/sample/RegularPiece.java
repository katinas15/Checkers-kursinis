package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import static sample.FxMain.*;

public class RegularPiece extends Piece {
    private boolean color;
    private int posX, posY;
    private Circle sprite;

    public RegularPiece(int posX, int posY, boolean color) {
        super(posX, posY, color);

        sprite = new Circle();
        sprite.setRadius(tileSize/2f - strokeSize/2f);
        sprite.setStrokeWidth(strokeSize);
        sprite.setStroke(Color.BLACK);

        int val = color? 0 : 1;
        sprite.setFill(Color.web(playerPieceColor[val]));
    }

    @Override
    public Shape getSprite() {
        return sprite;
    }
}
