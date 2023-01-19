package de.chessy.server.handlers;

import de.chessy.game.Game;
import de.chessy.game.GameRepository;
import de.chessy.server.responses.CreateGameResponse;
import de.chessy.user.User;
import de.chessy.utils.HttpEndpoint;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;


public class CreateGameHandler extends HttpEndpoint {


    @Override
    public void onRequest(HttpRequest request, HttpResponse response) {
        User user = request.getAttribute("user");
        Game game = GameRepository.getInstance().create(user);
        CreateGameResponse createGameResponse = new CreateGameResponse(game.id(), false);
        response.send(createGameResponse);
    }
}
