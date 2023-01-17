package de.chessy.server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.chessy.server.responses.CreateGameResponse;

import java.io.IOException;
import java.io.OutputStream;

public class CreateGameHandler implements HttpHandler {

    private static int currentId = 0;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson gson = new Gson();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, 0);
        OutputStream responseBody = exchange.getResponseBody();
        CreateGameResponse createGameResponse = new CreateGameResponse(currentId++);
        responseBody.write(gson.toJson(createGameResponse).getBytes());
        responseBody.close();
    }
}
