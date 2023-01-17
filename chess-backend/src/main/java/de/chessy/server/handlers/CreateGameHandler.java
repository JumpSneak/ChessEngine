package de.chessy.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.chessy.server.responses.CreateGameResponse;
import de.chessy.utils.HttpResponse;

public class CreateGameHandler implements HttpHandler {

    private static int currentId = 0;

    @Override
    public void handle(HttpExchange exchange) {
        HttpResponse<CreateGameResponse> response = new HttpResponse<>(exchange);
        response.send(new CreateGameResponse(currentId++));
    }
}
