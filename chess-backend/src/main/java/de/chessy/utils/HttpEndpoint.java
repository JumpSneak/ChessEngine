package de.chessy.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class HttpEndpoint implements HttpHandler {
    @Override
    public final void handle(HttpExchange exchange) {
        onRequest(new HttpRequest(exchange), new HttpResponse(exchange));
    }

    public abstract void onRequest(HttpRequest request, HttpResponse response);
}
