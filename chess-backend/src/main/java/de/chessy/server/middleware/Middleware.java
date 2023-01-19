package de.chessy.server.middleware;

import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public interface Middleware {

    void handle(HttpRequest request, HttpResponse response, MiddlewareNextFunction next) ;


}
