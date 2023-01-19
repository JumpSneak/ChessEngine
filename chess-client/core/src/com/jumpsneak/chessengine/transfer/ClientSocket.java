package com.jumpsneak.chessengine.transfer;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Map;

public class ClientSocket extends WebSocketClient {
    public ClientSocket(int gameid, int userid) throws URISyntaxException {
        super(new URI(Environment.getSocketsUrl()), Map.of("gameid", String.valueOf(gameid), "userid", String.valueOf(userid)));
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("Hello, it is me. Mario :)");
        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("received message: " + message);
        JsonValue json = new JsonReader().parse(message);
        if (json.getString("eventKey").equals("PIECE_PLAYED")) {
            Client.setBufferedInput(new MoveInformation(
                    json.getInt("oldX"), json.getInt("oldY"),
                    json.getInt("x"), json.getInt("y")));
        }
    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

}