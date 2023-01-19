package de.chessy.server.middleware;

import de.chessy.server.Errors;
import de.chessy.user.UserRepository;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class UserAuthenticationMiddleware implements Middleware {


    @Override
    public void handle(HttpRequest request, HttpResponse response, MiddlewareNextFunction next) {
        try {
            String userId = request.getHeaders().getFirst("userId");
            if (userId == null) {
                response.setStatusCode(400);
                response.send(Errors.missingParameter("userId"));
                return;
            }

            UserRepository userRepository = UserRepository.getInstance();
            var user = userRepository.findUserById(Integer.parseInt(userId));
            if (user == null) {
                response.setStatusCode(403);
                response.send(Errors.UNAUTHORIZED);
                return;
            }
            request.setAttribute("user", user);
            next.next(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.setStatusCode(400);
            response.send(Errors.invalidParameter("userId"));
        }
    }
}
