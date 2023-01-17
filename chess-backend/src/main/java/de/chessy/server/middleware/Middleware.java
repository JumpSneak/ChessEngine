package de.chessy.server.middleware;

import com.sun.net.httpserver.HttpExchange;

public interface Middleware {

    void handle(HttpExchange exchange, MiddlewareNextFunction next) ;

}
