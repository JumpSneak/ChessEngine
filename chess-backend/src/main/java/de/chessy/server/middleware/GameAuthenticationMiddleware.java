package de.chessy.server.middleware;

import de.chessy.game.Game;
import de.chessy.server.Errors;
import de.chessy.user.User;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class GameAuthenticationMiddleware implements Middleware{
    @Override
    public void handle(HttpRequest request, HttpResponse response, MiddlewareNextFunction next) {
        Game game = request.getAttribute("game");
        User user = request.getAttribute("user");
        if(game.hasPlayer(user.id())) {
            next.next(request, response);
        } else {
            response.setStatusCode(401);
            response.send(Errors.UNAUTHORIZED);
        }
    }
}
