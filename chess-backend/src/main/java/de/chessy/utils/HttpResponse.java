package de.chessy.utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse<T> {
    private HttpExchange exchange;

    private int statusCode = 200;
    private String contentType = "application/json";

    public HttpResponse(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void send(T body) {
        try {
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(statusCode, 0);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(Serializer.serialize(body).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            exchange.close();
        }
    }
}
