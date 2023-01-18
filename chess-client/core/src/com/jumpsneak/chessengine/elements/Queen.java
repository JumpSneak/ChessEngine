package com.jumpsneak.chessengine.elements;

public class Queen extends Piece{
    public Queen(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Queen", tilex, tiley, isWhite, 9, 1);
    }
//TODO all
    @Override
    public boolean isLegalPosition(int newTilePosX, int newTilePosY) {
        return false;
    }

    @Override
    public boolean isLegalMotion(int newTilePosX, int newTilePosY) {
        return false;
    }
}
