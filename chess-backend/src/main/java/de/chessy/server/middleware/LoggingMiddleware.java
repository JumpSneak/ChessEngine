package de.chessy.server.middleware;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class LoggingMiddleware implements HttpHandler {
    private final HttpHandler next;

    public LoggingMiddleware(HttpHandler next) {
        this.next = next;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Incoming request: " + exchange.getRequestURI());
        next.handle(exchange);
        System.out.println("Outgoing response: " + exchange.getResponseCode());
    }
}
