package de.chessy.server.middleware;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MiddlewareApplier implements HttpHandler {

    private final List<Middleware> middlewares;

    public MiddlewareApplier(HttpHandler handler, Middleware... middlewares) {
        this.middlewares = new ArrayList<>(middlewares.length + 1);
        Collections.addAll(this.middlewares, middlewares);
        this.middlewares.add(new HttpHandlerMiddlewareWrapper(handler));
    }

    @Override
    public void handle(HttpExchange exchange) {
        int index = 0;
        var next = new MiddlewareNextFunctionImpl(index);
        next.next(exchange);
    }

    private class MiddlewareNextFunctionImpl implements MiddlewareNextFunction {
        private int index;

        public MiddlewareNextFunctionImpl(int index) {
            this.index = index;
        }

        @Override
        public void next(HttpExchange exchange) {
            Middleware middleware = middlewares.get(index++);
            middleware.handle(exchange, this);
        }
    }

    private record HttpHandlerMiddlewareWrapper(HttpHandler handler) implements Middleware {

        @Override
        public void handle(HttpExchange exchange, MiddlewareNextFunction next) {
            try {
                handler.handle(exchange);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
