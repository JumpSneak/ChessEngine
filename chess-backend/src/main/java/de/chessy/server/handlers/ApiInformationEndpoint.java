package de.chessy.server.handlers;

import de.chessy.server.responses.ApiInformation;
import de.chessy.utils.HttpEndpoint;
import de.chessy.utils.HttpRequest;
import de.chessy.utils.HttpResponse;

import java.util.List;

public class ApiInformationEndpoint extends HttpEndpoint {

    @Override
    public void onRequest(HttpRequest request, HttpResponse response) {
        response.send(new ApiInformation(
                1,
                List.of("Julian Hartl", "Mihal Gora")
        ));
    }
}
