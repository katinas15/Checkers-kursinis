package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import static sample.FxMain.tileSize;

abstract class Piece {
    protected int strokeSize = 3;
    protected String[] playerPieceColor = {"#ede6e4", "#fc3903"};
    protected boolean color;
    protected int posX, posY;
    protected Shape sprite;

    public Piece(int posX, int posY, boolean color) {
        this.posX = posX;
        this.posY = posY;
        this.color = color;
    }

    public Shape getSprite() {
        return sprite;
    }

    public int getPosX() { return posX; }

    public int getPosY() { return posY; }

    public boolean getColor() {
        return color;
    }

    public void setPosition(int x, int y){
        this.posX = x;
        this.posY = y;
    }
}
