package de.chessy.server;

import de.chessy.server.responses.ErrorResponse;

public class Errors {
    public static final ErrorResponse GAME_NOT_FOUND = new ErrorResponse("Game not found", 1, "Check if the game id is correct");
    public static final ErrorResponse MISSING_IS_WHITE_PLAYER = new ErrorResponse("Missing isWhitePlayer", 2, "Check if the isWhitePlayer header is set");

    public static ErrorResponse invalidGameId(Object gameId) {
        return new ErrorResponse("Invalid game id", 2, "The game id " + gameId + " is invalid");
    }

    public static final ErrorResponse MISSING_GAME_ID = new ErrorResponse("Missing game id", 3, "The game id is missing");

    public static ErrorResponse invalidMove(int x, int y, boolean isWhite) {
        return new ErrorResponse("Invalid move", 2, "The move " + x + ", " + y + " is not valid for " + (isWhite ? "white" : "black"));
    }

    public static ErrorResponse invalidIsWhitePlayer(String isWhitePlayer) {
        return new ErrorResponse("Invalid isWhitePlayer", 2, "The isWhitePlayer " + isWhitePlayer + " is not valid");
    }
}
