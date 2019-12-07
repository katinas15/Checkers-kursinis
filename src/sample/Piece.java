package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static sample.FxMain.playerPieceColor;
import static sample.FxMain.tileSize;

abstract class Piece {

    protected boolean color;
    protected int posX, posY;
    protected Circle sprite;

    public Piece(int posX, int posY, boolean color) {
        this.posX = posX;
        this.posY = posY;
        this.color = color;
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

    public boolean getColor() {
        return color;
    }

    public void setPosition(int x, int y){
        this.posX = x;
        this.posY = y;
    }
}
