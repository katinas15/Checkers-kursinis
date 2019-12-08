package sample;

import static sample.FxMain.changePlayer;

public class ControllerContext {
    PieceControllerStrategy controller;

    public ControllerContext(instanceof RegularPiece) {
        this.controller = new RegularPieceController();
    }

    public ControllerContext(QueenPiece piece) {
        this.controller = new QueenPieceController();
    }

    public void update(Piece selectedPiece, int toX, int toY){
        if(controller.checkHit(selectedPiece, toX, toY)){
            controller.processHit(selectedPiece,toX,toY);
            controller.checkSecondHit(selectedPiece);

        } else if(controller.checkStep(selectedPiece, toX, toY)) {
            selectedPiece.setPosition(toX, toY);
            changePlayer();

        }
    }
}
