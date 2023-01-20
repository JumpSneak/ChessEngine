package de.chessy.game;

import de.chessy.server.ChessSocket;
import de.chessy.server.events.GameStatusChangedEvent;
import de.chessy.server.events.PieceWasPlayedEvent;
import de.chessy.server.events.UserJoinedGameEvent;
import de.chessy.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameRepository {

    private static final int EMPTY_FIELD = -1;

    private static int currentId = 0;

    private static GameRepository instance;

    private final ChessSocket chessSocket = ChessSocket.getInstance();

    public static GameRepository getInstance() {
        if (instance == null) {
            instance = new GameRepository();
        }
        return instance;
    }

    private GameRepository() {
        this.games = new ArrayList<>();
    }

    private final List<ServerGame> games;

    public ServerGame create(int creator) {
        ServerGame game = new ServerGame(currentId++, creator, null);
        game.generateInitialField();
        games.add(game);
        return game;
    }

    public Optional<ServerGame> get(int id) {
        return games.stream().filter(game -> game.id == id).findFirst();
    }

    public Optional<Move> makeMove(ServerGame game, int x, int y, int oldX, int oldY, User player) {
        try {
            var move = new Move(x, y, oldX, oldY);
            var board = game.board;
            var isValidMove = MoveValidator.isValidMove(move, game, player);
            if (!isValidMove) {
                return Optional.empty();
            }
            board[x][y] = board[oldX][oldY];
            board[oldX][oldY] = EMPTY_FIELD;
            PieceWasPlayedEvent event = new PieceWasPlayedEvent(oldX, oldY, x, y, player.id());
            Integer receiver = game.getOtherPlayer(player.id());
            chessSocket.emitEvent(event, receiver);
            return Optional.of(move);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    public ServerGame join(int gameId, int userId) {
        Optional<ServerGame> gameOptional = get(gameId);
        if (gameOptional.isEmpty()) {
            System.out.println("Game could not be joined: Not found.");
            return null;
        }
        ServerGame game = gameOptional.get();
        if (game.hasPlayer(userId)) {
            System.out.println("Game could not be joined: User already in game.");
            return null;
        }
        if (game.black == null) {
            game.black = userId;
        } else if (game.white == null) {
            game.white = userId;
        } else {
            System.out.println("Game could not be joined: Game is full.");
            return null;
        }
        UserJoinedGameEvent event = new UserJoinedGameEvent(gameId, userId, game.isWhite(userId));
        chessSocket.emitEvent(event, userId);
        return game;
    }

    public boolean setStatus(int gameId, GameStatus status) {
        Optional<ServerGame> gameOptional = get(gameId);
        if (gameOptional.isEmpty()) {
            return false;
        }
        ServerGame game = gameOptional.get();
        game.status = status;
        GameStatusChangedEvent event = new GameStatusChangedEvent(gameId, status);
        chessSocket.emitEvent(event, game.getPlayers());
        return true;
    }
}
