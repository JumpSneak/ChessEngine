package com.jumpsneak.chessengine.elements;

public class Rook extends Piece{
    public Rook(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Rook", tilex, tiley, isWhite, 5, 4);
    }
}
