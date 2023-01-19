package de.chessy.server;

import de.chessy.game.GameRepository;
import de.chessy.server.events.Event;
import de.chessy.user.User;
import de.chessy.user.UserRepository;
import de.chessy.utils.Serializer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChessSocket extends WebSocketServer {
    private static final int port = 8000;

    private ChessSocket() {
        super(new InetSocketAddress(port));
    }

    private static ChessSocket instance;

    public static ChessSocket getInstance() {
        if (instance == null) {
            instance = new ChessSocket();
        }
        return instance;
    }


    private static final String GAME_ID_KEY = "gameid";
    private static final String USER_ID_KEY = "userid";

    private final Map<Integer, WebSocket> connections = new HashMap<>();

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        if (!handshake.hasFieldValue(GAME_ID_KEY)) {
            conn.close(-1, GAME_ID_KEY + " is missing");
            return;
        }
        if (!handshake.hasFieldValue(USER_ID_KEY)) {
            conn.close(-1, USER_ID_KEY + " is missing");
            return;
        }
        int userId = Integer.parseInt(handshake.getFieldValue(USER_ID_KEY));
        int gameId = Integer.parseInt(handshake.getFieldValue(GAME_ID_KEY));
        var game = GameRepository.getInstance().get(gameId);
        if (game.isEmpty()) {
            conn.close(-1, "Game not found");
            return;
        }
        User user = UserRepository.getInstance().findUserById(userId);
        if (user == null) {
            conn.close(-1, "User not found");
            return;
        }
        conn.send("Welcome to game " + gameId + ", user " + userId);
        connections.put(userId, conn);
    }

    public void emitEvent(Event event, List<Integer> userIds) {
        String serialized = Serializer.serialize(event);
        if (userIds.isEmpty()) {
            broadcast(serialized);
        } else {
            List<WebSocket> userConnections = userIds.stream().map(connections::get).collect(Collectors.toList());
            broadcast(serialized, userConnections);
        }
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
