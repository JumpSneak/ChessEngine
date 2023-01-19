package de.chessy.server.handlers.game;

import de.chessy.game.Game;
import de.chessy.game.GameRepository;
import de.chessy.server.ChessSocket;
import de.chessy.server.Errors;
import de.chessy.server.dtos.JoinGameDto;
import de.chessy.server.events.UserJoinedGameEvent;
import de.chessy.user.User;
import de.chessy.utils.HttpEndpoint;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

import java.util.List;

public class JoinGameEndpoint extends HttpEndpoint {
    @Override
    public void onRequest(HttpRequest request, HttpResponse response) {
        GameRepository gameRepository = GameRepository.getInstance();
        JoinGameDto joinGameDto = request.getBody(JoinGameDto.class);
        if (joinGameDto == null) {
            response.setStatusCode(400);
            response.send(Errors.INVALID_BODY);
            return;
        }
        User user = request.getAttribute("user");
        Game joinedGame = gameRepository.join(joinGameDto.gameId(), user.id());
        if (joinedGame == null) {
            response.setStatusCode(400);
            response.send(Errors.GAME_NOT_JOINED);
            return;
        }
        List<Integer> receivers = List.of(joinedGame.getOtherPlayer(user.id()));
        ChessSocket.getInstance().emitEvent(new UserJoinedGameEvent(joinedGame.id, user.id()), receivers);
        response.send(joinedGame);
    }
}
