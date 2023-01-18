package com.jumpsneak.chessengine.elements;

public class Rook extends Piece {
    public Rook(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Rook", tilex, tiley, isWhite, 5, 4);
    }

    @Override
    public boolean isLegalPosition(int newTilePosX, int newTilePosY) {
        return (Math.abs(newTilePosX - tilex) == 0 || Math.abs(newTilePosY - tiley) == 0);
    }

    @Override
    public boolean isLegalMotion(int newTilePosX, int newTilePosY) {
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
}
// GraphQL