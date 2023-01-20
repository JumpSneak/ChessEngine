package de.chessy.server;

import com.sun.net.httpserver.HttpServer;
import de.chessy.core.Endpoints;
import de.chessy.core.game.GameStatus;
import de.chessy.server.endpoints.ApiInformationEndpoint;
import de.chessy.server.endpoints.game.CreateGameEndpoint;
import de.chessy.server.endpoints.game.JoinGameEndpoint;
import de.chessy.server.endpoints.game.PlayPieceHandler;
import de.chessy.server.endpoints.user.MeEndpoint;
import de.chessy.server.middleware.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ChessServer {

    private final HttpServer server;

    private ChessServer(int port) throws IOException {
        server = HttpServer.create(
                new InetSocketAddress(port), 0);
        server.createContext(Endpoints.apiInfo, new HttpEndpointWrapper(
                new ApiInformationEndpoint(),
                new LoggingMiddleware()
        ));
        server.createContext(Endpoints.play, new HttpEndpointWrapper(
                new PlayPieceHandler(),
                new LoggingMiddleware(),
                new UserAuthenticationMiddleware(),
                new GameIdValidatorMiddleware(),
                new GameAuthenticationMiddleware(),
                new GameStatusRequirementMiddleware(GameStatus.IN_PROGRESS)
        ));
        server.createContext(Endpoints.create, new HttpEndpointWrapper(
                new CreateGameEndpoint(),
                new LoggingMiddleware(),
                new UserAuthenticationMiddleware()
        ));
        server.createContext(Endpoints.join, new HttpEndpointWrapper(
                new JoinGameEndpoint(),
                new LoggingMiddleware(),
                new UserAuthenticationMiddleware()
        ));
        server.createContext(Endpoints.currentUser, new HttpEndpointWrapper(
                new MeEndpoint(),
                new LoggingMiddleware(),
                new UserAuthenticationMiddleware()
        ));
        server.setExecutor(null);
    }

    public static ChessServer create(int port) throws IOException {
        return new ChessServer(port);
    }

    public void start() {
        System.out.println("Server started on port " + getPort());
        server.start();
    }

    public int getPort() {
        return server.getAddress().getPort();
    }

}
