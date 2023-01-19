package de.chessy.server.middleware;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.chessy.server.Errors;
import de.chessy.utils.HttpEndpoint;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HttpEndpointWrapper implements HttpHandler {

    private final List<Middleware> middlewares;

    public HttpEndpointWrapper(HttpEndpoint endpoint, Middleware... middlewares) {
        this.middlewares = new ArrayList<>(middlewares.length + 1);
        Collections.addAll(this.middlewares, middlewares);
        this.middlewares.add(new HttpEndpointMiddlewareWrapper(endpoint));
    }

    @Override
    public void handle(HttpExchange exchange) {
        var next = new MiddlewareNextFunctionImpl();
        HttpRequest request = new HttpRequest(exchange);
        HttpResponse response = new HttpResponse(exchange);
        try (exchange) {
            next.next(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.send(Errors.INTERNAL_SERVER_ERROR);
            return;
        }
        if (response.isClosed()) {
            return;
        }
        response.send(Errors.NOT_FOUND);
    }

    private class MiddlewareNextFunctionImpl implements MiddlewareNextFunction {

        private int index = 0;

        @Override
        public void next(HttpRequest request, HttpResponse response) {
            if (index < middlewares.size()) {
                var middleware = middlewares.get(index);
                index++;
                try {
                    middleware.handle(request, response, this);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setStatusCode(500);
                    response.send(Errors.INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    private record HttpEndpointMiddlewareWrapper(HttpEndpoint endpoint) implements Middleware {


        @Override
        public void handle(HttpRequest request, HttpResponse response, MiddlewareNextFunction next) {
            endpoint.onRequest(request, response);
        }
    }
}
