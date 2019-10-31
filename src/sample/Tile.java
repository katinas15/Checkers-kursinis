package sample;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public class Tile {
    private int posX, posY;
    private Rectangle sprite;

    public Tile(int posX, int posY, Rectangle sprite) {
        this.posX = posX;
        this.posY = posY;
        this.sprite = sprite;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public Rectangle getSprite() {
        return sprite;
    }
}
