package com.jumpsneak.chessengine.elements;

public class Bishop extends Piece {
    public Bishop(Board board, int tilex, int tiley, boolean isWhite) {
        super(board, "Bishop", tilex, tiley, isWhite, 3, 2);
    }

    @Override
    public boolean isLegalPosition(int newTilePosX, int newTilePosY) {
        return Math.abs(newTilePosX - tilex) == Math.abs(newTilePosY - tiley);
    }

    @Override
    public boolean isLegalMotion(int newTilePosX, int newTilePosY) {
//        int xmovement = newTilePosX - tilex;
//        int ymovement = newTilePosY - tiley;
//        if(xmovement > 0 && ymovement > 0){
//
//        }
//
//        for (int right = 0; right <; right++) {
//            for (int left = 0; left <; left++) {
//
//            }
//        }
        return true;
    }
}
