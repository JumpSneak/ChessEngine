package de.chessy.server.middleware;

import de.chessy.game.Game;
import de.chessy.game.GameStatus;
import de.chessy.server.Errors;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class GameStatusRequirementMiddleware implements Middleware {

    private final GameStatus requiredStatus;

    public GameStatusRequirementMiddleware(GameStatus requiredStatus) {
        this.requiredStatus = requiredStatus;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response, MiddlewareNextFunction next) {

        Game game = request.getAttribute("game");
        if (game.getStatus() != requiredStatus) {
            response.setStatusCode(400);
            response.send(Errors.gameStatusNotMatching(requiredStatus, game.getStatus()));
            return;
        }
        next.next(request, response);

    }
}
