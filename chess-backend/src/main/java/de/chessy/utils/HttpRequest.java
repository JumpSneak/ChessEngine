package de.chessy.utils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class HttpRequest {
    private final HttpExchange exchange;

    public HttpRequest(HttpExchange exchange) {
        this.exchange = exchange;
    }

    private Object body;

    public <T> T getBody(Class<T> type) {
        try {
            if (body == null) {
                body = Serializer.parse(exchange.getRequestBody(), type);
            }
            return (T) body;
        } catch (SerializationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <A> A getAttribute(String name) {
        return (A) exchange.getAttribute(name);
    }

    public <A> void setAttribute(String name, A value) {
        exchange.setAttribute(name, value);
    }

    public Headers getHeaders() {
        return exchange.getRequestHeaders();
    }

    public String getMethod() {
        return exchange.getRequestMethod();
    }

    public String getPath() {
        return exchange.getRequestURI().getPath();
    }


}
