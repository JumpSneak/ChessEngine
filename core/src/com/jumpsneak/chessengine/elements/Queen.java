package com.jumpsneak.chessengine.elements;

public class Queen extends Piece{
    public Queen(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Queen", tilex, tiley, isWhite, 9, 1);
    }
}
