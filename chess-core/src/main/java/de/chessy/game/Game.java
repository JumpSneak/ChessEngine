package de.chessy.game;

import de.chessy.game.pieces.PieceMetadata;

import java.util.Map;

public class Game {
    public int id;
    public Integer white;
    public Integer black;
    public int[][] board;
    public Map<Integer, PieceMetadata> metadata;
    public GameStatus status;
}
