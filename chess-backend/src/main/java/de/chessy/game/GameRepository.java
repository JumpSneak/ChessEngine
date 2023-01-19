package de.chessy.game;

import de.chessy.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameRepository {

    private static final int EMPTY_FIELD = -1;

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

    public Game create(int creator) {
        Game game = new Game(currentId++, creator, null);
        game.generateInitialField();
        games.add(game);
        return game;
    }

    public Optional<Game> get(int id) {
        return games.stream().filter(game -> game.id == id).findFirst();
    }
    public Optional<Move> makeMove(Game game, int x, int y, int oldX, int oldY, User player) {
        try {
            var move = new Move(x, y, oldX, oldY);
            var board = game.board;
            var isValidMove = MoveValidator.isValidMove(move, game, player);
            if (!isValidMove) {
                return Optional.empty();
            }
            board[x][y] = board[oldX][oldY];
            board[oldX][oldY] = EMPTY_FIELD;
            return Optional.of(move);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    public Game join(int gameId, int userId) {
        Optional<Game> gameOptional = get(gameId);
        if (gameOptional.isEmpty()) {
            System.out.println("Game could not be joined: Not found.");
            return null;
        }
        Game game = gameOptional.get();
        if (game.hasPlayer(userId)) {
            return game;
        }
        if (game.black == null) {
            game.black = userId;
        } else if (game.white == null) {
            game.white = userId;
        } else {
            System.out.println("Game could not be joined: Game is full.");
            return null;
        }
        return game;
    }
}
