package de.chessy.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.chessy.utils.HttpResponse;

public class HelloWorldHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        HttpResponse<String> response = new HttpResponse<>(exchange);
        response.send("Hello World!");
    }
}
