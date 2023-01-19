package de.chessy.server.middleware;

import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public interface MiddlewareNextFunction {
    void next(HttpRequest request, HttpResponse response);

}
