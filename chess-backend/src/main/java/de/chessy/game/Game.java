package de.chessy.game;

import de.chessy.game.pieces.*;

import java.util.HashMap;
import java.util.Map;

public class Game {

    public int id;
    public Integer white;
    public Integer black;

    public int[][] board;

    public Map<Integer, PieceMetadata> metadata;

    public GameStatus status;

    public Game(int id, Integer white, Integer black) {
        this.id = id;
        this.white = white;
        this.black = black;
        this.board = new int[8][8];
        this.metadata = new HashMap<>();
        this.status = GameStatus.WAITING_FOR_PLAYER;
    }

    public Integer getOtherPlayer(int userId) {
        if (userId == white) {
            return black;
        } else if (userId == black) {
            return white;
        } else {
            throw new IllegalArgumentException("User is not part of this game");
        }
    }

    private int pieceId = 0;

    public void generateInitialField() {
        generatePawns();
        generateRooks();
        generateKnights();
        generateBishops();
        generateQueens();
        generateKings();
    }

    private void generateKings() {
        board[4][0] = pieceId;
        metadata.put(pieceId, new KingMetadata(pieceId, white));
        pieceId++;
        board[4][7] = pieceId;
        metadata.put(pieceId, new KingMetadata(pieceId, black));
        pieceId++;
    }

    private void generateQueens() {
        board[3][0] = pieceId;
        metadata.put(pieceId, new QueenMetadata(pieceId, white));
        pieceId++;
        board[3][7] = pieceId;
        metadata.put(pieceId, new QueenMetadata(pieceId, black));
        pieceId++;
    }

    private void generateBishops() {
        board[0][2] = pieceId;
        metadata.put(pieceId, new BishopMetadata(pieceId, white));
        pieceId++;
        board[0][5] = pieceId;
        metadata.put(pieceId, new BishopMetadata(pieceId, white));
        pieceId++;
        board[7][2] = pieceId;
        metadata.put(pieceId, new BishopMetadata(pieceId, black));
        pieceId++;
        board[7][5] = pieceId;
        metadata.put(pieceId, new BishopMetadata(pieceId, black));
        pieceId++;
    }

    private void generateKnights() {
        board[1][0] = pieceId;
        metadata.put(pieceId, new KnightMetadata(pieceId, white));
        pieceId++;
        board[6][0] = pieceId;
        metadata.put(pieceId, new KnightMetadata(pieceId, white));
        pieceId++;
        board[1][7] = pieceId;
        metadata.put(pieceId, new KnightMetadata(pieceId, black));
        pieceId++;
        board[6][7] = pieceId;
        metadata.put(pieceId, new KnightMetadata(pieceId, black));
        pieceId++;
    }

    private void generateRooks() {
        board[0][0] = pieceId;
        metadata.put(pieceId, new RookMetadata(pieceId, white));
        pieceId++;
        board[0][7] = pieceId;
        metadata.put(pieceId, new RookMetadata(pieceId, white));
        pieceId++;
        board[7][0] = pieceId;
        metadata.put(pieceId, new RookMetadata(pieceId, black));
        pieceId++;
        board[7][7] = pieceId;
        metadata.put(pieceId, new RookMetadata(pieceId, black));
        pieceId++;
    }

    private void generatePawns() {
        for (int i = 0; i < 8; i++) {
            board[1][i] = pieceId;
            metadata.put(pieceId, new PawnMetadata(pieceId, white));
            pieceId++;
        }
        for (int i = 0; i < 8; i++) {
            board[6][i] = pieceId;
            metadata.put(pieceId, new PawnMetadata(pieceId, black));
            pieceId++;
        }
    }

    public boolean isWhite(int id) {
        return white == id;
    }

    public boolean hasWhite() {
        return white != null;
    }

    public boolean hasBlack() {
        return black != null;
    }

    public boolean hasPlayer(int id) {
        if (hasBlack()) {
            var isBlack = black == id;
            if (isBlack) {
                return true;
            }
        }
        if (hasWhite()) {
            return white == id;
        }
        return false;
    }
}
