package de.chessy.utils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class HttpRequest<T> {
    private final HttpExchange exchange;
    private final Class<T> type;

    public HttpRequest(HttpExchange exchange, Class<T> type) {
        this.exchange = exchange;
        this.type = type;
    }

    public HttpRequest(HttpExchange exchange) {
        this.exchange = exchange;
        this.type = null;
    }

    private T body;

    public T getBody() {
        if (body == null) {
            body = Serializer.parse(exchange.getRequestBody(), type);
        }
        return body;
    }

    public <A> A getAttribute(String name) {
        return (A) exchange.getAttribute(name);
    }

    public Headers getHeaders() {
        return exchange.getRequestHeaders();
    }
}
