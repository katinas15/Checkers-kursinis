package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static sample.FxMain.playerPieceColor;
import static sample.FxMain.tileSize;

public class Piece {

    private boolean color;
    private int posX, posY;
    private Circle sprite;
    private boolean queen = false;

    public Piece(int posX, int posY, boolean color) {
        this.posX = posX;
        this.posY = posY;
        this.color = color;

        sprite = new Circle();

        sprite.setRadius(tileSize/2f - 1.5f);
        sprite.setStrokeWidth(3);
        sprite.setStroke(Color.BLACK);

        int val = color? 0 : 1;
        sprite.setFill(Color.web(playerPieceColor[val]));
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

    public boolean isQueen() {
        return queen;
    }

    public void setPosition(int x, int y){
        this.posX = x;
        this.posY = y;
    }
}
