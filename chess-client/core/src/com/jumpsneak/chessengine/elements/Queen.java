package com.jumpsneak.chessengine.elements;

public class Queen extends Piece{
    public Queen(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Queen", tilex, tiley, isWhite, 9, 1);
    }
//TODO all
    @Override
    public boolean isLegalPosition(int newTilePosX, int newTilePosY) {
        return (Math.abs(newTilePosX - tilex) == 0 || Math.abs(newTilePosY - tiley) == 0)
                || Math.abs(newTilePosX - tilex) == Math.abs(newTilePosY - tiley);
    }

    @Override
    public boolean isLegalMotion(int newTilePosX, int newTilePosY) {
        return islegalStraightMotion(newTilePosX, newTilePosY) || islegalDiagonalMotion(newTilePosX, newTilePosY);
    }

    public boolean islegalStraightMotion(int newTilePosX, int newTilePosY){
        int xmovement = newTilePosX - tilex;
        int ymovement = newTilePosY - tiley;
        boolean directionX = ymovement == 0;
        int tile = directionX ? tilex : tiley;
        int newTile = directionX ? newTilePosX : newTilePosY;
        int movement = directionX ? xmovement : ymovement;
        int incr = 1;
        if (movement < 0) {
            incr = -1;
        }
        for (int i = tile + incr; incr > 0 ? i < newTile : i > newTile; i += incr) {
            if (directionX) {
                if (getPieceOn(i, tiley) != null) {
                    return false;
                }
            } else {
                if (getPieceOn(tilex, i) != null) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean islegalDiagonalMotion(int newTilePosX, int newTilePosY) {
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
