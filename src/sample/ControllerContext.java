package sample;

public class ControllerContext {
    PieceControllerStrategy controller;

    public ControllerContext(Piece piece) {
        if(piece instanceof RegularPiece){
            this.controller = new RegularPieceController();
        } else if (piece instanceof QueenPiece){
            this.controller = new QueenPieceController();
        }

    }

    public void update(Piece selectedPiece, int toX, int toY){
        if(controller.checkHit(selectedPiece, toX, toY)){
            controller.processHit(selectedPiece,toX,toY);
            controller.checkSecondHit(selectedPiece);
        } else if(controller.checkStep(selectedPiece, toX, toY)) {
            controller.processStep(selectedPiece, toX, toY);
        }
    }
}
