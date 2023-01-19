package de.chessy.server;

import de.chessy.server.events.PieceWasPlayedEvent;
import de.chessy.utils.Serializer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class ChessSocket extends WebSocketServer {
    private static final int port = 8000;

    public ChessSocket() {
        super(new InetSocketAddress(port));
    }

    private static final String GAME_ID_KEY = "gameid";

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        if (!handshake.hasFieldValue(GAME_ID_KEY)) {
            conn.close(-1, GAME_ID_KEY + " is missing");
            return;
        }
        int gameId = Integer.parseInt(handshake.getFieldValue(GAME_ID_KEY));
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        System.out.println("GameId: " + gameId);
        conn.send("Welcome to game " + gameId + "!");
        // do something after 5 seconds
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            conn.send(Serializer.serialize(new PieceWasPlayedEvent(0, 0, 1, 1)));
        }).start();
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
