package de.chessy.server.middleware;

import com.sun.net.httpserver.HttpExchange;
import de.chessy.server.Errors;
import de.chessy.server.responses.ErrorResponse;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class IsWhitePlayerValidatorMiddleware implements Middleware {


    @Override
    public void handle(HttpExchange exchange, MiddlewareNextFunction next) {
        HttpRequest request = new HttpRequest(exchange);
        String isWhitePlayer = request.getHeaders().getFirst("isWhitePlayer");
        HttpResponse<ErrorResponse> httpResponse = new HttpResponse<>(exchange);
        if (isWhitePlayer == null) {
            httpResponse.setStatusCode(400);
            httpResponse.send(Errors.MISSING_IS_WHITE_PLAYER);
            return;
        }

        boolean parsedIsWhitePlayer;
        if (isWhitePlayer.equals("true")) {
            parsedIsWhitePlayer = true;
        } else if (isWhitePlayer.equals("false")) {
            parsedIsWhitePlayer = false;
        } else {
            httpResponse.setStatusCode(400);
            httpResponse.send(Errors.invalidIsWhitePlayer(isWhitePlayer));
            return;
        }
        exchange.setAttribute("isWhitePlayer", parsedIsWhitePlayer);
        next.next(exchange);
    }
}
