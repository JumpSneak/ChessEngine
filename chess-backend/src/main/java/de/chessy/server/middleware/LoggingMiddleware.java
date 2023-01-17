package de.chessy.server.middleware;

import com.sun.net.httpserver.HttpExchange;

public class LoggingMiddleware implements Middleware {

    @Override
    public void handle(HttpExchange exchange, MiddlewareNextFunction next)  {
        System.out.println("Incoming request: " + exchange.getRequestURI());
        next.next(exchange);
        System.out.println("Outgoing response: " + exchange.getResponseCode());
    }
}
