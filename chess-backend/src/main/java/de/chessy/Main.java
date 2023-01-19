package de.chessy;

import de.chessy.server.ChessServer;
import de.chessy.server.ChessSocket;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ChessSocket socket = new ChessSocket();
            ChessServer server = ChessServer.create(7999);
            socket.start();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
