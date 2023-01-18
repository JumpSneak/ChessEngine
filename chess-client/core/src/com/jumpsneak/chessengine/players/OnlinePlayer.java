package com.jumpsneak.chessengine.players;

import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.elements.Piece;
import com.jumpsneak.chessengine.transfer.Client;
import com.jumpsneak.chessengine.transfer.MoveInformation;

public class OnlinePlayer extends Player{
    public OnlinePlayer(String name) {
        super(name);
    }

    @Override
    public boolean dropPiece(Piece activePiece, int x, int y) {
        return false;
    }

    @Override
    public boolean getMove() {
        // check for new move from server, if so then movePiece
        MoveInformation bufferedInput = Client.getAndRemoveBufferedInput();
        if(bufferedInput != null){
            Piece p = board.getPieceOn(bufferedInput.oldX(), bufferedInput.oldY());
            boolean status = board.movePiece(p, bufferedInput.x(), bufferedInput.y());
            bufferedInput = null;
            return status;
        }else{
            return false;
        }
    }
}
