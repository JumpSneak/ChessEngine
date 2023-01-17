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

    private T body;

    public T getBody() {
        if (body == null) {
            body = Serializer.parse(exchange.getRequestBody(), type);
        }
        return body;
    }

    public Headers getHeaders() {
        return exchange.getRequestHeaders();
    }
}
