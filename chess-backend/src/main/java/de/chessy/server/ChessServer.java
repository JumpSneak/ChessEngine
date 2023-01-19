package de.chessy.server;

import com.sun.net.httpserver.HttpServer;
import de.chessy.server.handlers.CreateGameHandler;
import de.chessy.server.handlers.HelloWorldHandler;
import de.chessy.server.handlers.PlayPieceHandler;
import de.chessy.server.handlers.user.MeEndpoint;
import de.chessy.server.middleware.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ChessServer {

    private final HttpServer server;

    private ChessServer(int port) throws IOException {
        server = HttpServer.create(
                new InetSocketAddress(port), 0);
        server.createContext("/game/playPiece", new HttpEndpointWrapper(
                new PlayPieceHandler(),
                new LoggingMiddleware(),
                new UserAuthenticationMiddleware(),
                new GameIdValidatorMiddleware()
        ));
        server.createContext("/", new HttpEndpointWrapper(
                new HelloWorldHandler(),
                new LoggingMiddleware()
        ));
        server.createContext("/game/create", new HttpEndpointWrapper(
                new CreateGameHandler(),
                new LoggingMiddleware(),
                new UserAuthenticationMiddleware()
        ));
        server.createContext("/user/me", new HttpEndpointWrapper(
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
