package de.chessy.core.game;

import de.chessy.core.game.pieces.PieceMetadata;

import java.util.Map;

public class Game {
    public int id;
    public Integer white;
    public Integer black;
    public int[][] board;
    public Map<Integer, PieceMetadata> metadata;
    public GameStatus status;
}
