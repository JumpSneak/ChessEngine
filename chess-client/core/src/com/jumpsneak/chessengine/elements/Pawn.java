package com.jumpsneak.chessengine.elements;

public class Pawn extends Piece {
    boolean untouched = true;
    boolean enPassantPossible = false;

    public Pawn(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Pawn", tilex, tiley, isWhite, 1, 5);
    }

    @Override
    public boolean isLegalPosition(int newTilePosX, int newTilePosY) {
        int move = newTilePosY - tiley;
        if (!isWhite) {
            move *= -1;
        }
        Piece p = getPieceOn(newTilePosX, newTilePosY);
        return newTilePosX == tilex && p == null // move forward
                || Math.abs(newTilePosX - tilex) == 1 && move == 1 && (p != null); // beat diagonally
    }

    @Override
    public boolean isLegalMotion(int newTilePosX, int newTilePosY) {
        int move = newTilePosY - tiley;
        if (!isWhite) {
            move *= -1;
        }
        if (untouched) {
//            untouched = tiley == 1 && isWhite || tiley == 6 && !isWhite;
            return move == 2 && getPieceOn(tilex, isWhite ? tiley + 1 : tiley - 1) == null || move == 1;
        }
        return move == 1;
    }
    public void setUntouchedFalse(){
        untouched = false;
    }
    public void setEnPassantPossible(boolean b){
        enPassantPossible = b;
    }

    public boolean isUntouched() {
        return untouched;
    }
    public boolean isEnPassantPossible() {
        return enPassantPossible;
    }
}
