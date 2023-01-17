package com.jumpsneak.chessengine.elements;

public class Rook extends Piece{
    public Rook(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Rook", tilex, tiley, isWhite, 5, 4);
    }
    @Override
    public boolean isLegalMove(int newTilePosX, int newTilePosY) {
        boolean legalMove = Math.abs(newTilePosX - tilex) == 0 || Math.abs(newTilePosY-tiley) == 0;
        boolean legalPosition = true;
        return legalMove && legalPosition;
    }
}
// GraphQL