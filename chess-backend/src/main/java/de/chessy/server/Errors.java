package de.chessy.server;

import de.chessy.server.responses.ErrorResponse;

public class Errors {
    public static final ErrorResponse GAME_NOT_FOUND = new ErrorResponse("Game not found", 1, "Check if the game id is correct");

    public static ErrorResponse invalidMove(int x, int y, boolean isWhite) {
        return new ErrorResponse("Invalid move", 2, "The move " + x + ", " + y + " is not valid for " + (isWhite ? "white" : "black"));
    }
}
