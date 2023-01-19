package com.jumpsneak.chessengine.transfer.responses;

import java.util.Map;

public record ServerGame(int id, int white, int black, int[][] board, Map<Integer, ServerPiece> metadata) {
}
