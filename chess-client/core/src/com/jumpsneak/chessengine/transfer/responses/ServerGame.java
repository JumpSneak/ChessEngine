package com.jumpsneak.chessengine.transfer.responses;

public record ServerGame(int id, int white, int black, int[][] board, Map<Integer, ServerPiece> metadata) {
}
