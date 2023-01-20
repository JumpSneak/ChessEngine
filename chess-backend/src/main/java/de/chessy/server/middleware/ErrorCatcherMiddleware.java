package de.chessy.server.middleware;

import de.chessy.server.Errors;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class ErrorCatcherMiddleware implements Middleware {
    @Override
    public void handle(HttpRequest request, HttpResponse response, MiddlewareNextFunction next) {
        try {
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
}
