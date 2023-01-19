package com.jumpsneak.chessengine.elements;

public class Pawn extends Piece {
    boolean untouched = true;

    public Pawn(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Pawn", tilex, tiley, isWhite, 1, 5);
    }

    // TODO all
    @Override
    public boolean isLegalPosition(int newTilePosX, int newTilePosY) {
        int move = newTilePosY - tiley;
        if (!isWhite) {
            move *= -1;
        }
        return newTilePosX == tilex && getPieceOn(newTilePosX, newTilePosY) == null // move forward
                || Math.abs(newTilePosX - tilex) == 1 && move == 1 && getPieceOn(newTilePosX, newTilePosY) != null; // beat diagonally
    }

    @Override
    public boolean isLegalMotion(int newTilePosX, int newTilePosY) {
        int move = newTilePosY - tiley;
        if (!isWhite) {
            move *= -1;
        }
        if (untouched) {
            untouched = tiley == 1 && isWhite || tiley == 6 && !isWhite;
            return move == 2 && getPieceOn(tilex, isWhite ? tiley + 1 : tiley - 1) == null || move == 1;
        }
        return move == 1;
    }
}
