package de.chessy;

import de.chessy.server.ChessServer;
import de.chessy.server.ChessSocket;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ChessSocket socket = new ChessSocket();
        socket.run();
        ChessServer server = ChessServer.create(7999);
        server.start();
    }
}
