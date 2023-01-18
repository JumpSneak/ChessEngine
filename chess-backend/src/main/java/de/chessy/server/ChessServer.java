package de.chessy.server;

import com.sun.net.httpserver.HttpServer;
import de.chessy.server.handlers.CreateGameHandler;
import de.chessy.server.handlers.HelloWorldHandler;
import de.chessy.server.handlers.PlayPieceHandler;
import de.chessy.server.middleware.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ChessServer {

    private final HttpServer server;

    private ChessServer(int port) throws IOException {
        server = HttpServer.create(
                new InetSocketAddress(port), 0);
        server.createContext("/game/playPiece", new MiddlewareApplier(
                new PlayPieceHandler(),
                new ClosingMiddleware(),
                new LoggingMiddleware(),
                new IsWhitePlayerValidatorMiddleware(),
                new GameIdValidatorMiddleware()
        ));
        server.createContext("/", new MiddlewareApplier(
                new HelloWorldHandler(),
                new ClosingMiddleware(),
                new LoggingMiddleware()
        ));
        server.createContext("/game/create", new MiddlewareApplier(
                new CreateGameHandler(),
                new ClosingMiddleware(),
                new LoggingMiddleware()
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