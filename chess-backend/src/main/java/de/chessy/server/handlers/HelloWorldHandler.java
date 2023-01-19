package de.chessy.server.handlers;

import de.chessy.utils.HttpEndpoint;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class HelloWorldHandler extends HttpEndpoint {

    @Override
    public void onRequest(HttpRequest request, HttpResponse response) {
        response.send("Hello World!");
    }
}
