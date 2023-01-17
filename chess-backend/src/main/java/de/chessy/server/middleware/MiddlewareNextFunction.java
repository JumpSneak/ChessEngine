package de.chessy.server.middleware;

import com.sun.net.httpserver.HttpExchange;

public interface MiddlewareNextFunction {
    void next(HttpExchange exchange);

}
