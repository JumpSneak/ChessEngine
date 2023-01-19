package de.chessy.server;

import de.chessy.game.GameStatus;
import de.chessy.server.responses.ErrorResponse;

public class Errors {
    public static final ErrorResponse GAME_NOT_FOUND = new ErrorResponse("Game not found", 1, "Check if the game id is correct");
    public static final ErrorResponse UNAUTHORIZED = new ErrorResponse("Unauthorized", 2, "Check if the userId is correct");
    public static final ErrorResponse NOT_FOUND = new ErrorResponse("Not found", 3, "Check if the url is correct");

    public static final ErrorResponse INTERNAL_SERVER_ERROR = new ErrorResponse("Internal server error", 4, "Check the server logs");
    public static final ErrorResponse GAME_NOT_CREATED = new ErrorResponse("Game not created", 5, "Check the server logs");
    public static final ErrorResponse INVALID_BODY = new ErrorResponse("Invalid body", 6, "Check the body of the request");
    public static final ErrorResponse GAME_NOT_JOINED = new ErrorResponse("Game not joined", 7, "Check the server logs");

    public static ErrorResponse missingParameter(String parameterName) {
        return new ErrorResponse("Missing parameter", 3, "Check if the parameter " + parameterName + " is present");
    }

    public static ErrorResponse invalidGameId(Object gameId) {
        return new ErrorResponse("Invalid game id", 2, "The game id " + gameId + " is invalid");
    }

    public static final ErrorResponse MISSING_GAME_ID = new ErrorResponse("Missing game id", 3, "The game id is missing");

    public static ErrorResponse invalidMove(int x, int y) {
        return new ErrorResponse("Invalid move", 2, "The move " + x + ", " + y + " is not valid.");
    }

    public static ErrorResponse invalidIsWhitePlayer(String isWhitePlayer) {
        return new ErrorResponse("Invalid isWhitePlayer", 2, "The isWhitePlayer " + isWhitePlayer + " is not valid");
    }

    public static ErrorResponse invalidParameter(String userId) {
        return new ErrorResponse("Invalid parameter", 2, "The parameter " + userId + " is invalid");
    }

    public static ErrorResponse gameStatusNotMatching(GameStatus requiredStatus, GameStatus status) {
        return new ErrorResponse("Game status not matching", 2, "The game status " + status + " is not matching the required status " + requiredStatus);
    }
}
