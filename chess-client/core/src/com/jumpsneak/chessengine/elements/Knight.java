package com.jumpsneak.chessengine.elements;

public class Knight extends Piece{
    public Knight(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Knight", tilex, tiley, isWhite, 3, 3);
    }

    @Override
    public boolean isLegalPosition(int newTilePosX, int newTilePosY) {
        return Math.abs(newTilePosX - tilex)*Math.abs(newTilePosY-tiley) == 2;
    }

    @Override
    public boolean isLegalMotion(int newTilePosX, int newTilePosY) {
        return true;
    }
}
