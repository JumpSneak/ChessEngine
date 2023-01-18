package com.jumpsneak.chessengine.elements;

public class Bishop extends Piece {
    public Bishop(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Bishop", tilex, tiley, isWhite, 3, 2);
    }

    @Override
    public boolean isLegalPosition(int newTilePosX, int newTilePosY) {
        return Math.abs(newTilePosX - tilex) == Math.abs(newTilePosY - tiley);
    }

    @Override
    public boolean isLegalMotion(int newTilePosX, int newTilePosY) {
        int xmovement = newTilePosX - tilex;
        int ymovement = newTilePosY - tiley;
        boolean xup = xmovement > 0;
        boolean yup = ymovement > 0;
        int incrX, incrY;

        if (xup && yup) { // up right /
            incrX = incrY = 1;
        } else if (!xup && !yup) { // down left /
            incrX = incrY = -1;
        } else if (!xup && yup) { // up left \
            incrX = -1;
            incrY = 1;
        } else { // down right \
            incrX = 1;
            incrY = -1;
        }
        for (int i = 1; i < Math.abs(xmovement); i++) {
            if(getPieceOn(tilex + incrX*i, tiley + incrY*i)!= null){
                return false;
            }
        }
        return true;
    }
}
