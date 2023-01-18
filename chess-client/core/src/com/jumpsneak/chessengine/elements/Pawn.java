package com.jumpsneak.chessengine.elements;

public class Pawn extends Piece{
    public Pawn(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Pawn", tilex, tiley, isWhite, 1, 5);
    }

    // TODO all
    @Override
    public boolean isLegalPosition(int newTilePosX, int newTilePosY) {
        return true;
    }

    @Override
    public boolean isLegalMotion(int newTilePosX, int newTilePosY) {
        return true;
    }
}
