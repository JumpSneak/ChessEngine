package de.chessy.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Iterator;

public class ChessSocket extends WebSocketServer {
    private static final int port = 8000;

    public ChessSocket() {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        if(!handshake.hasFieldValue("gameId")) {
            conn.close(-1, "gameId is missing");
            return;
        }
        int gameId = Integer.parseInt(handshake.getFieldValue("gameid"));
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        System.out.println("GameId: " + gameId);
        conn.send("Welcome to game " + gameId + "!");
    }

    @Override
    public void run() {
        System.out.println("Socket started on port " + port);
        super.run();
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {

    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onStart() {

    }

}
