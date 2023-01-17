package de.chessy.utils;

import com.sun.net.httpserver.HttpExchange;
import de.chessy.server.responses.ErrorResponse;

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

    public int setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return statusCode;
    }

    public void send(T body) {
        try {
            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(statusCode, 0);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(Serializer.serialize(body).getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            exchange.close();
        }
    }
}
