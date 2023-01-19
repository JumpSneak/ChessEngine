package de.chessy.server.middleware;

import de.chessy.game.GameRepository;
import de.chessy.server.Errors;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class GameIdValidatorMiddleware implements Middleware {


    @Override
    public void handle(HttpRequest request, HttpResponse response, MiddlewareNextFunction next) {
        String gameId = request.getHeaders().getFirst("gameId");
        if (gameId == null) {
            response.setStatusCode(400);
            response.send(Errors.MISSING_GAME_ID);
            return;
        }
        int parsedGameId;
        try {
            parsedGameId = Integer.parseInt(gameId);
        } catch (NumberFormatException e) {
            response.setStatusCode(400);
            response.send(Errors.invalidGameId(gameId));
            return;
        }
        GameRepository gameRepository = GameRepository.getInstance();
        var game = gameRepository.get(parsedGameId);
        if (game.isEmpty()) {
            response.setStatusCode(400);
            response.send(Errors.GAME_NOT_FOUND);
            return;
        }
        request.setAttribute("game", game.get());
        next.next(request, response);
    }
}
