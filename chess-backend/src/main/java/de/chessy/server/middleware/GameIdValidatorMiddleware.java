package de.chessy.server.middleware;

import com.sun.net.httpserver.HttpExchange;
import de.chessy.game.GameRepository;
import de.chessy.server.Errors;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class GameIdValidatorMiddleware implements Middleware {


    @Override
    public void handle(HttpExchange exchange, MiddlewareNextFunction next)  {
        var request = new HttpRequest(exchange);
        String gameId = request.getHeaders().getFirst("gameId");
        var httpResponse = new HttpResponse<>(exchange);
        if (gameId == null) {
            httpResponse.setStatusCode(400);
            httpResponse.send(Errors.MISSING_GAME_ID);
            return;
        }
        int parsedGameId;
        try {
            parsedGameId = Integer.parseInt(gameId);
        } catch (NumberFormatException e) {
            httpResponse.setStatusCode(400);
            httpResponse.send(Errors.invalidGameId(gameId));
            return;
        }
        GameRepository gameRepository = GameRepository.getInstance();
        var game = gameRepository.get(parsedGameId);
        if (game.isEmpty()) {
            httpResponse.setStatusCode(400);
            httpResponse.send(Errors.GAME_NOT_FOUND);
            return;
        }
        exchange.setAttribute("game", game.get());
        next.next(exchange);
    }
}
