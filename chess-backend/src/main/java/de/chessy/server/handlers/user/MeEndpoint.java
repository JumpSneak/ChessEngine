package de.chessy.server.handlers.user;

import de.chessy.user.User;
import de.chessy.utils.HttpEndpoint;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class MeEndpoint extends HttpEndpoint<Void, User> {


    @Override
    public void onRequest(HttpRequest<Void> request, HttpResponse<User> response) {
        User user = request.getAttribute("user");
        response.send(user);
    }
}
