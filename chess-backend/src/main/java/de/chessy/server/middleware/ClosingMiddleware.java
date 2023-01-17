package de.chessy.server.middleware;

import com.sun.net.httpserver.HttpExchange;

public class ClosingMiddleware implements Middleware {


    @Override
    public void handle(HttpExchange exchange, MiddlewareNextFunction next) {
        try (exchange) {
            next.next(exchange);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
