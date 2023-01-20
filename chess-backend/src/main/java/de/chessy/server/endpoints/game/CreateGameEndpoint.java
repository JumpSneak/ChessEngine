package de.chessy.server.endpoints.game;

import de.chessy.game.ServerGame;
import de.chessy.game.GameRepository;
import de.chessy.server.Errors;
import de.chessy.server.responses.CreateGameResponse;
import de.chessy.user.User;
import de.chessy.utils.HttpEndpoint;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;


public class CreateGameEndpoint extends HttpEndpoint {


    @Override
    public void onRequest(HttpRequest request, HttpResponse response) {
        User user = request.getAttribute("user");
        ServerGame game = GameRepository.getInstance().create(user.id());
        if (game == null) {
            response.setStatusCode(400);
            response.send(Errors.GAME_NOT_CREATED);
            return;
        }
        CreateGameResponse createGameResponse = new CreateGameResponse(game, game.isWhite(user.id()));
        response.send(createGameResponse);
    }
}
