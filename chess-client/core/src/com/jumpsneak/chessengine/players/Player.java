package com.jumpsneak.chessengine.players;

import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.elements.Piece;
import com.jumpsneak.chessengine.transfer.Client;

public abstract class Player {
    Board board;
    String name;
    boolean isWhite = false;

    public Player(String name) {
        this.name = name;
    }

    // instantly move
    public abstract boolean dropPiece(Piece activePiece, int x, int y);

    // Check for and get move
    public abstract boolean getMove();

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setIsWhite(boolean b) {
        this.isWhite = b;
    }

    public String toString() {
        return (isWhite ? "WHITE" : "BLACK") +": "+ name + "\t";
    }
}
