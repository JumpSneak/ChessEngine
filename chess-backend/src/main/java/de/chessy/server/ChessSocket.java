package de.chessy.server;

import de.chessy.game.GameRepository;
import de.chessy.server.events.ConnectToGameEvent;
import de.chessy.server.events.Event;
import de.chessy.user.User;
import de.chessy.user.UserRepository;
import de.chessy.utils.Serializer;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.List;
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

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        try {
            System.out.println("New connection from " + conn.getRemoteSocketAddress().getHostName());
            if (!handshake.hasFieldValue(GAME_ID_KEY)) {
                conn.close(CloseFrame.REFUSE, GAME_ID_KEY + " is missing");
                return;
            }
            if (!handshake.hasFieldValue(USER_ID_KEY)) {
                conn.close(CloseFrame.REFUSE, USER_ID_KEY + " is missing");
                return;
            }
            int userId = Integer.parseInt(handshake.getFieldValue(USER_ID_KEY));
            int gameId = Integer.parseInt(handshake.getFieldValue(GAME_ID_KEY));
            var game = GameRepository.getInstance().get(gameId);
            if (game.isEmpty()) {
                conn.close(CloseFrame.REFUSE, "Game not found");
                return;
            }
            User user = UserRepository.getInstance().findUserById(userId);
            if (user == null) {
                conn.close(CloseFrame.REFUSE, "User not found");
                return;
            }
            if (!game.get().hasPlayer(userId)) {
                conn.close(CloseFrame.REFUSE, "User is not part of this game");
                return;
            }
            conn.setAttachment(user.id());
            System.out.println("User " + user.id() + " connected to game " + gameId);
            emitEvent(new ConnectToGameEvent(gameId), user.id());
        } catch (Exception e) {
            e.printStackTrace();
            conn.close(CloseFrame.ABNORMAL_CLOSE, "Internal server error");
        }
    }

    public void emitEvent(Event event, Integer receiver) {
        emitEvent(event, List.of(receiver));
    }

    public void emitEvent(Event event, List<Integer> userIds) {
        String serialized = Serializer.serialize(event);
        if (userIds.isEmpty()) {
            broadcast(serialized);
        } else {
            List<WebSocket> userConnections = getUserConnections(userIds);
            broadcast(serialized, userConnections);
        }
    }

    private List<WebSocket> getUserConnections(List<Integer> userIds) {
        return getConnections().stream()
                .filter(conn -> userIds.contains((Integer) conn.getAttachment()))
                .collect(Collectors.toList());
    }

    @Override
    public void run() {
        System.out.println("Socket started on port " + port);
        super.run();
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        Integer userId = conn.getAttachment();
        if (userId != null) {
            System.out.println("User " + userId + " disconnected");
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received message: " + message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {

    }

}
