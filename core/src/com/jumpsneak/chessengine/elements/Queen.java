package com.jumpsneak.chessengine.elements;

public class Queen extends Piece{
    public Queen(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Queen", tilex, tiley, isWhite, 9, 1);
    }
    @Override
    public boolean isLegalMove(int newTilePosX, int newTilePosY) {
        return true;
    }
}
