package com.jumpsneak.chessengine.players;

import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.elements.Piece;

public class LocalPlayer extends Player{

    public LocalPlayer(String name) {
        super(name);
    }

    @Override
    public boolean dropPiece(Piece activePiece, int x, int y) {
        return board.movePiece(activePiece, x, y);
    }

    @Override
    public boolean getMove() {
        return false;
    }
}
