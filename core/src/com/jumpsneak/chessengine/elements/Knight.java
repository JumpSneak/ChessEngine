package com.jumpsneak.chessengine.elements;

public class Knight extends Piece{
    public Knight(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Knight", tilex, tiley, isWhite, 3, 3);
    }
}
