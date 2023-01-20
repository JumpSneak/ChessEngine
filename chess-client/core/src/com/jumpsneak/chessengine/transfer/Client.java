package com.jumpsneak.chessengine.transfer;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.google.gson.Gson;
import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.elements.Piece;
import de.chessy.core.dtos.JoinGameDto;
import de.chessy.core.responses.CreateGameResponse;
import de.chessy.core.utils.Serializer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Client {

    static HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();
    static ClientSocket clientSocket;
    public static boolean localPlaysAsWhite = true;
    public static boolean illegalMove = false;
    static int playerid = 0;
    static int gameid = 0;
    static MoveInformation bufferedInput = null;

    public static void sendSpam(MoveInformation moveInformation) {
        System.out.println(makeRequest(new MoveInformation(1, 1, 0, 1), Endpoints.play).body());
    }

    public static void sendMove(Piece piece, int toTileX, int toTileY) {
        HttpResponse<String> response = makeRequest(new MoveInformation(piece.getTilex(), piece.getTiley(), toTileX, toTileY), Endpoints.play);
        if (response == null || response.statusCode() != 200) {
            illegalMove = true;
        }
    }

    public static boolean createGame(Board board) {
        try {
            playerid = 0;
            var response = makeRequest("", Endpoints.create);
            if (response == null || response.statusCode() != 200) {
                return false;
            }
            CreateGameResponse createGameResponse = Serializer.fromJson(response.body(), CreateGameResponse.class);
            gameid = createGameResponse.game().id;
            board.setWhite(createGameResponse.isWhitePlayer());
            clientSocket = new ClientSocket(gameid, playerid);
            return response.statusCode() == 200 && clientSocket.connectBlocking(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean joinGame(Board board) {
        try {
            playerid = 1;
            var response = makeRequest(new JoinGameDto(gameid), Endpoints.join);
            System.out.println(response);
            System.out.println(response.body());
            if (response.statusCode() != 200) return false;
            JsonValue j = new JsonReader().parse(response.body());
            gameid = j.getInt("gameId");
            board.setWhite(j.getBoolean("isWhitePlayer"));
            clientSocket = new ClientSocket(gameid, playerid);
            return response.statusCode() == 200 && clientSocket.connectBlocking(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static HttpResponse<String> makeRequest(Object o, String path) {
        try {
            return client.send(HttpRequest.newBuilder(URI.create(Environment.getApiUrl() + path))
                    .header("Content-Type", "application/json")
                    .header("gameId", String.valueOf(gameid))
                    .header("userId", String.valueOf(playerid))
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(o)))
                    .build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MoveInformation getAndRemoveBufferedInput() {
        MoveInformation result = bufferedInput;
        bufferedInput = null;
        return result;
    }

    public static void setBufferedInput(MoveInformation input) {
        bufferedInput = input;
    }
}

//    public static HttpRequest getRequest(MoveInformation moveInformation){
//        return HttpRequest.newBuilder(URI.create(ip_address + "game/playPiece"))
//                .header("Content-Type", "application/json")
//                .header("gameId", "1")
//                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(moveInformation)))
//                .build();
//    }