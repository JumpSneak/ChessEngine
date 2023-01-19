package de.chessy.game;

import de.chessy.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Game create(User creator) {
        Game game = new Game(currentId++, creator, null, new Board(generateInitialField()));
        games.add(game);
        return game;
    }

    public Optional<Game> get(int id) {
        return games.stream().filter(game -> game.id() == id).findFirst();
    }

    public Optional<String> getPieceName(Game game, int x, int y) {
        try {
            return Optional.of(game.board().fields()[x][y].pieceName());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Move> makeMove(Game game, int x, int y, int oldX, int oldY, User player) {
        try {
            var move = new Move(x, y, oldX, oldY);
            var board = game.board();
            var isValidMove = MoveValidator.isValidMove(move, board);
            if (!isValidMove) {
                return Optional.empty();
            }
            var pieceName = getPieceName(game, oldX, oldY);
            if (pieceName.isEmpty()) {
                return Optional.empty();
            }
            board.fields()[x][y] = new Piece(pieceName.get(), player.id() == game.white().id());
            board.fields()[oldX][oldY] = null;
            return Optional.of(move);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    private static Piece[][] generateInitialField() {
        Piece[][] field = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            field[1][i] = new Piece("pawn", true);
            field[6][i] = new Piece("pawn", false);
        }
        field[0][0] = new Piece("rook", true);
        field[0][7] = new Piece("rook", true);
        field[7][0] = new Piece("rook", false);
        field[7][7] = new Piece("rook", false);
        field[0][1] = new Piece("knight", true);
        field[0][6] = new Piece("knight", true);
        field[7][1] = new Piece("knight", false);
        field[7][6] = new Piece("knight", false);
        field[0][2] = new Piece("bishop", true);
        field[0][5] = new Piece("bishop", true);
        field[7][2] = new Piece("bishop", false);
        field[7][5] = new Piece("bishop", false);
        field[0][3] = new Piece("queen", true);
        field[0][4] = new Piece("king", true);
        field[7][3] = new Piece("queen", false);
        field[7][4] = new Piece("king", false);
        return field;
    }

}
