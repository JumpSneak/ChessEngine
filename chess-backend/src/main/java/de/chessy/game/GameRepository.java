package de.chessy.game;

import java.util.ArrayList;
import java.util.List;

public class GameRepository {


    private static int currentId = 0;

    private static GameRepository instance;

    public static GameRepository getInstance() {
        if (instance == null) {
            instance = new GameRepository();
        }
        return instance;
    }

    private GameRepository() {
        this.games = new ArrayList<>();
    }

    private final List<Game> games;

    public Game create() {
        Game game = new Game(currentId++, 1, new Board(generateInitialField()));
        games.add(game);
        return game;
    }

    private static String[][] generateInitialField() {
        String[][] field = new String[8][8];
        for (int i = 0; i < 8; i++) {
            field[1][i] = "P";
            field[6][i] = "p";
        }
        field[0][0] = "R";
        field[0][1] = "N";
        field[0][2] = "B";
        field[0][3] = "Q";
        field[0][4] = "K";
        field[0][5] = "B";
        field[0][6] = "N";
        field[0][7] = "R";
        field[7][0] = "r";
        field[7][1] = "n";
        field[7][2] = "b";
        field[7][3] = "q";
        field[7][4] = "k";
        field[7][5] = "b";
        field[7][6] = "n";
        field[7][7] = "r";
        return field;
    }

}
