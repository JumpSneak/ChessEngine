package de.chessy.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.chessy.game.Game;
import de.chessy.game.GameRepository;
import de.chessy.server.responses.CreateGameResponse;
import de.chessy.user.User;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class CreateGameHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        HttpRequest<?> request = new HttpRequest<>(exchange);
        HttpResponse<CreateGameResponse> response = new HttpResponse<>(exchange);
        User user = request.getAttribute("user");
        Game game = GameRepository.getInstance().create(user);
        CreateGameResponse createGameResponse = new CreateGameResponse(game.id(), true);
        response.send(createGameResponse);
    }
}
