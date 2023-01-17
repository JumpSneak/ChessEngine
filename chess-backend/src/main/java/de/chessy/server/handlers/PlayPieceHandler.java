package de.chessy.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.chessy.game.GameRepository;
import de.chessy.server.Errors;
import de.chessy.server.dtos.ChessMoveDto;
import de.chessy.server.responses.ErrorResponse;
import de.chessy.server.responses.PlayPieceResponse;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class PlayPieceHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try (exchange) {
            HttpRequest<ChessMoveDto> request = new HttpRequest<>(exchange, ChessMoveDto.class);
            ChessMoveDto moveDto = request.getBody();
            int gameId = Integer.parseInt(request.getHeaders().getFirst("gameId"));
            boolean isWhitePlayer = Boolean.parseBoolean(request.getHeaders().getFirst("isWhitePlayer"));
            GameRepository gameRepository = GameRepository.getInstance();
            var game = gameRepository.get(gameId);
            System.out.println("GameId: " + gameId);
            if (game.isEmpty()) {
                HttpResponse<ErrorResponse> response = new HttpResponse<>(exchange);
                response.setStatusCode(400);
                response.send(Errors.GAME_NOT_FOUND);
                return;
            }
            HttpResponse<Object> response = new HttpResponse<>(exchange);
            var move = gameRepository.makeMove(game.get(), moveDto.x(), moveDto.y(), moveDto.oldX(), moveDto.oldY(), isWhitePlayer);
            if (move.isEmpty()) {
                response.setStatusCode(400);
                response.send(Errors.invalidMove(moveDto.x(), moveDto.y(), isWhitePlayer));
                return;
            }
            var pieceName = gameRepository.getPieceName(game.get(), moveDto.x(), moveDto.y());
            PlayPieceResponse playPieceResponse = new PlayPieceResponse(moveDto.x(), moveDto.y(), pieceName.get());
            response.send(playPieceResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
