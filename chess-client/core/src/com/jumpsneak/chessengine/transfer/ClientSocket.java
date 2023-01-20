package com.jumpsneak.chessengine.transfer;

import de.chessy.core.events.Event;
import de.chessy.core.events.PieceWasPlayedEvent;
import de.chessy.core.utils.Serializer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class ClientSocket extends WebSocketClient {
    public ClientSocket(int gameid, int userid) throws URISyntaxException {
        super(new URI(Environment.getSocketsUrl()), Map.of("gameid", String.valueOf(gameid), "userid", String.valueOf(userid)));
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to server");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received message: " + message);
        Event event = Serializer.fromJson(message, Event.class);
        if (event.eventKey.equals("PIECE_PLAYED")) {
            PieceWasPlayedEvent pieceWasPlayedEvent = Serializer.fromJson(message, PieceWasPlayedEvent.class);
            Client.setBufferedInput(new MoveInformation(
                    pieceWasPlayedEvent.oldX, pieceWasPlayedEvent.oldY,
                    pieceWasPlayedEvent.x, pieceWasPlayedEvent.y));
        }
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

}