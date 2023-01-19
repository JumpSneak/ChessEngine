package com.jumpsneak.chessengine.transfer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Map;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

public class ClientSocket extends WebSocketClient {
    public ClientSocket(int gameid, int playerid) throws URISyntaxException {
        super(new URI("wss://socket.chess.julianhartl.dev"), Map.of("gameid", String.valueOf(gameid), "playerid", String.valueOf(playerid)));
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
        if(json.getString("eventKey").equals("move")) {
            Client.setBufferedInput(new MoveInformation(
                    json.getInt("oldX"), json.getInt("oldY"),
                    json.getInt("newX"), json.getInt("newY")));
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