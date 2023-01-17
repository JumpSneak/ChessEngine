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

    private List<Game> games;

    public Game create() {
        Game game = new Game(currentId++);
        games.add(game);
        return game;
    }

}
