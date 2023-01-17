package de.chessy;

import de.chessy.server.ChessServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ChessServer server = ChessServer.create(7999);
        server.start();
    }
}
