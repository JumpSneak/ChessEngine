package com.jumpsneak.chessengine.elements;

public class Bishop extends Piece{
    public Bishop(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Bishop", tilex, tiley, isWhite, 3, 2);
    }
}
