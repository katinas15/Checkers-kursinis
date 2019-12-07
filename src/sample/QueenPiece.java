package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static sample.FxMain.tileSize;

public class QueenPiece extends Piece {

    public QueenPiece(int posX, int posY, boolean color) {
        super(posX, posY, color);
        sprite = new Circle();
        sprite.setRadius(tileSize / 3f);
        sprite.setFill(Color.BLACK);
    }
}
