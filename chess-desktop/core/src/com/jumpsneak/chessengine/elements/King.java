package com.jumpsneak.chessengine.elements;

public class King extends Piece{

    public King(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "King", tilex, tiley, isWhite, -1, 0);
    }
    @Override
    public boolean isLegalMove(int newTilePosX, int newTilePosY) {
        return Math.abs(this.tilex-newTilePosX) <= 1 && Math.abs(this.tiley-newTilePosY) <= 1;
    }
}
