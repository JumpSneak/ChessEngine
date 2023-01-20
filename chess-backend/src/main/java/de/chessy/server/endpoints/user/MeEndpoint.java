package de.chessy.server.endpoints.user;

import de.chessy.user.User;
import de.chessy.utils.HttpEndpoint;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

public class MeEndpoint extends HttpEndpoint {

    @Override
    public void onRequest(HttpRequest request, HttpResponse response) {
        User user = request.getAttribute("user");
        response.send(user);
    }
}
