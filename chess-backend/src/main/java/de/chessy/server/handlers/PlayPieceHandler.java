package de.chessy.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.chessy.game.Game;
import de.chessy.game.GameRepository;
import de.chessy.server.Errors;
import de.chessy.server.dtos.ChessMoveDto;
import de.chessy.server.responses.PlayPieceResponse;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class PlayPieceHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try (exchange) {
            HttpRequest<ChessMoveDto> request = new HttpRequest<>(exchange, ChessMoveDto.class);
            ChessMoveDto moveDto = request.getBody();
            boolean isWhitePlayer = request.getAttribute("isWhitePlayer");
            GameRepository gameRepository = GameRepository.getInstance();
            Game game = request.getAttribute("game");

            HttpResponse<Object> response = new HttpResponse<>(exchange);

            var move = gameRepository.makeMove(game, moveDto.x(), moveDto.y(), moveDto.oldX(), moveDto.oldY(), isWhitePlayer);
            if (move.isEmpty()) {
                response.setStatusCode(400);
                response.send(Errors.invalidMove(moveDto.x(), moveDto.y(), isWhitePlayer));
                return;
            }
            var pieceName = gameRepository.getPieceName(game, moveDto.x(), moveDto.y());
            PlayPieceResponse playPieceResponse = new PlayPieceResponse(moveDto.x(), moveDto.y(), pieceName.get());
            response.send(playPieceResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}