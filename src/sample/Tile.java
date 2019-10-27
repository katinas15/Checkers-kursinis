package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Tile {
    public static int tileSize = 50;
    private Rectangle rect;
    public Tile(boolean color){
        rect = new Rectangle(0,0,tileSize,tileSize);
        if(color){
            rect.setFill(Color.web("#a88132"));
        } else {
            rect.setFill(Color.web("#32a881"));
        }

    }

    public Rectangle getRect(){
        return rect;
    }


}
