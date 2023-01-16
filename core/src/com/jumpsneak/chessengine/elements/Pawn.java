package com.jumpsneak.chessengine.elements;

public class Pawn extends Piece{
    public Pawn(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Pawn", tilex, tiley, isWhite, 1, 5);
    }
}
