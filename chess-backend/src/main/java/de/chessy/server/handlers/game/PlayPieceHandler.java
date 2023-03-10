package de.chessy.server.handlers.game;

import de.chessy.game.Game;
import de.chessy.game.GameRepository;
import de.chessy.server.ChessSocket;
import de.chessy.server.Errors;
import de.chessy.server.dtos.ChessMoveDto;
import de.chessy.server.events.PieceWasPlayedEvent;
import de.chessy.server.responses.PlayPieceResponse;
import de.chessy.user.User;
import de.chessy.utils.HttpEndpoint;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

import java.util.List;

public class PlayPieceHandler extends HttpEndpoint {

    @Override
    public void onRequest(HttpRequest request, HttpResponse response) {
        ChessMoveDto moveDto = request.getBody(ChessMoveDto.class);
        GameRepository gameRepository = GameRepository.getInstance();
        Game game = request.getAttribute("game");
        User user = request.getAttribute("user");

        var move = gameRepository.makeMove(game, moveDto.x(), moveDto.y(), moveDto.oldX(), moveDto.oldY(), user);
        if (false) {
            response.setStatusCode(400);
            response.send(Errors.invalidMove(moveDto.x(), moveDto.y()));
            return;
        }
        PieceWasPlayedEvent event = new PieceWasPlayedEvent(moveDto.oldX(), moveDto.oldY(), moveDto.x(), moveDto.y());
        Integer receiver = game.getOtherPlayer(user.id());
        ChessSocket.getInstance().emitEvent(event, receiver);
        PlayPieceResponse playPieceResponse = new PlayPieceResponse(moveDto.x(), moveDto.y());
        response.send(playPieceResponse);
    }
}
