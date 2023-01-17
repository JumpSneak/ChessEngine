package de.chessy;

import de.chessy.database.DatabaseDriver;
import de.chessy.server.ChessServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DatabaseDriver.connect();
        ChessServer server = ChessServer.create(7999);
        server.start();
        DatabaseDriver.closeConnection();
    }
}
