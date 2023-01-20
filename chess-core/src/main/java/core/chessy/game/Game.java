package core.chessy.game;

import core.chessy.game.pieces.PieceMetadata;

import java.util.Map;

public class Game {
    public int id;
    public Integer white;
    public Integer black;
    public int[][] board;
    public Map<Integer, PieceMetadata> metadata;
    public GameStatus status;
}
