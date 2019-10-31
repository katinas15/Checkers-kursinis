package sample;

import javafx.scene.Group;
import javafx.scene.shape.Circle;

public class Piece {
    private int posX, posY;
    private Circle sprite;

    public Piece(int posX, int posY, Circle sprite) {
        this.posX = posX;
        this.posY = posY;
        this.sprite = sprite;
    }

    public Circle getSprite() {
        return sprite;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
