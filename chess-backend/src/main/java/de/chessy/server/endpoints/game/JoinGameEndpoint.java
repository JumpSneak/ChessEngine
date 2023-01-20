package de.chessy.server.endpoints.game;

import de.chessy.game.ServerGame;
import de.chessy.game.GameRepository;
import de.chessy.server.ChessSocket;
import de.chessy.server.Errors;
import de.chessy.server.dtos.JoinGameDto;
import de.chessy.server.events.UserJoinedGameEvent;
import de.chessy.server.responses.JoinGameResponse;
import de.chessy.user.User;
import de.chessy.utils.HttpEndpoint;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class JoinGameEndpoint extends HttpEndpoint {
    @Override
    public void onRequest(HttpRequest request, HttpResponse response) {
        GameRepository gameRepository = GameRepository.getInstance();
        JoinGameDto joinGameDto = request.getBody(JoinGameDto.class);
        if (joinGameDto == null) {
            System.out.println("Invalid body: " + request.getRawBody());
            response.setStatusCode(400);
            response.send(Errors.INVALID_BODY);
            return;
        }
        User user = request.getAttribute("user");
        ServerGame joinedGame = gameRepository.join(joinGameDto.gameId(), user.id());
        if (joinedGame == null) {
            response.setStatusCode(400);
            response.send(Errors.GAME_NOT_JOINED);
            return;
        }
        var receiver = joinedGame.getOtherPlayer(user.id());
        boolean isWhitePlayer = joinedGame.isWhite(user.id());
        ChessSocket.getInstance().emitEvent(new UserJoinedGameEvent(joinedGame.id, user.id(), isWhitePlayer), receiver);
        response.send(new JoinGameResponse(joinedGame.id, isWhitePlayer));
    }
}
