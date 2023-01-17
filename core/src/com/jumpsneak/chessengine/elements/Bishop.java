package com.jumpsneak.chessengine.elements;

public class Bishop extends Piece{
    public Bishop(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Bishop", tilex, tiley, isWhite, 3, 2);
    }
    @Override
    public boolean isLegalMove(int newTilePosX, int newTilePosY) {
        return true;
    }
}
