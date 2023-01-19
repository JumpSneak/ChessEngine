package de.chessy.server.middleware;

import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class LoggingMiddleware implements Middleware {

    @Override
    public void handle(HttpRequest request, HttpResponse response, MiddlewareNextFunction next) {
        System.out.println("LoggingMiddleware: " + request.getMethod() + " " + request.getPath());
        next.next(request, response);
        System.out.println("LoggingMiddleware: " + response.getStatusCode());
    }
}
