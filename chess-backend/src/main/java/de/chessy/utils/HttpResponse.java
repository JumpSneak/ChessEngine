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

    private boolean isClosed = false;

    private boolean hasSentHeaders = false;

    public void send(Object body) {
        try {
            if (!hasSentHeaders) {
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(statusCode, 0);
            }
            hasSentHeaders = true;
            OutputStream responseBody = exchange.getResponseBody();
            if (!isClosed) {
                responseBody.write(Serializer.serialize(body).getBytes());
                responseBody.close();
                isClosed = true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            exchange.close();
        }
    }

    public boolean isClosed() {
        return isClosed;
    }
}
