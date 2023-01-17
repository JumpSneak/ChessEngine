package de.chessy.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.chessy.server.dtos.ChessMoveDto;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class PlayPieceHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        HttpRequest<ChessMoveDto> request = new HttpRequest<>(exchange, ChessMoveDto.class);
        int gameId = Integer.parseInt(request.getHeaders().getFirst("gameId"));
        System.out.println(request.getBody());
        System.out.println("GameId: " + gameId);
        HttpResponse<String> response = new HttpResponse<>(exchange);
        response.send("OK");
    }
}
