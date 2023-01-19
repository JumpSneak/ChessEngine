package de.chessy.server.middleware;

import com.sun.net.httpserver.HttpExchange;
import de.chessy.server.Errors;
import de.chessy.server.responses.ErrorResponse;
import de.chessy.user.UserRepository;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class UserAuthenticationMiddleware implements Middleware {


    @Override
    public void handle(HttpExchange exchange, MiddlewareNextFunction next) {
        HttpResponse<ErrorResponse> httpResponse = new HttpResponse<>(exchange);
        try {
            HttpRequest<?> request = new HttpRequest<>(exchange);
            String userId = request.getHeaders().getFirst("userId");
            if (userId == null) {
                httpResponse.setStatusCode(400);
                httpResponse.send(Errors.missingParameter("userId"));
                return;
            }

            UserRepository userRepository = UserRepository.getInstance();
            var user = userRepository.findUserById(Integer.parseInt(userId));
            if (user == null) {
                httpResponse.setStatusCode(403);
                httpResponse.send(Errors.UNAUTHORIZED);
                return;
            }
            exchange.setAttribute("user", user);
            next.next(exchange);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            httpResponse.setStatusCode(400);
            httpResponse.send(Errors.invalidParameter("userId"));
        }
    }
}
