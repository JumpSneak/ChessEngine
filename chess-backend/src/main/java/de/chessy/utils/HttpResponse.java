package de.chessy.utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;

public class HttpResponse {
    private HttpExchange exchange;

    private int statusCode = 200;
    private String contentType = "application/json";

    public HttpResponse(HttpExchange exchange) {
        this.exchange = exchange;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void send(Object body) {
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

    public boolean isClosed() {
        int responseCode = exchange.getResponseCode();
        return responseCode != -1;
    }
}
