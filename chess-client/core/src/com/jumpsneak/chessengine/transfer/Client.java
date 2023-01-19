package com.jumpsneak.chessengine.transfer;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.elements.Pawn;
import com.jumpsneak.chessengine.elements.Piece;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Client {
    static String ip_address = "https://api.chess.julianhartl.dev/";//"http://localhost:7999/";//
    static int port = 7999;
    static class Endpoints{
        static public String play = "game/playPiece";
        static public String create = "game/create";
    }
    static HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();
    static ClientSocket clientSocket;
    public static boolean localPlaysAsWhite = true;
    public static boolean illegalMove = false;
    static int playerid = 0;
    static int gameid = -1;
    static MoveInformation bufferedInput = null;
    public static void sendSpam(MoveInformation moveInformation) {
        System.out.println(makeRequest(new MoveInformation(1, 1, 0, 1), Endpoints.play).body());
    }
    public static void sendMove(Piece piece, int toTileX, int toTileY){
        HttpResponse<String> response = makeRequest(new MoveInformation(piece.getTilex(), piece.getTiley(), toTileX, toTileY), Endpoints.play);
        if(response==null || response.statusCode() != 200){
            illegalMove = true;
        }
    }

    public static boolean createGame(Board board) {
        try {
            // old
            var response = makeRequest("", Endpoints.create);
            System.out.println(response);
            if(response.statusCode() != 200) return false;
            JsonValue j = new JsonReader().parse(response.body());
            gameid = j.getInt("gameId");
            board.setWhite(j.getBoolean("isWhitePlayer"));
//            Gson g = new Gson();
//            CreateGameResponse createGameResponse = g.fromJson(response.body(), CreateGameResponse.class);
//            gameid = createGameResponse.id;
            //board.setWhite(createGameResponse.isWhite); falsch
//            System.out.println(createGameResponse.isWhite);
            // movesocket
            clientSocket = new ClientSocket(gameid, playerid);
            return response.statusCode() == 200 && clientSocket.connectBlocking(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static HttpResponse<String> makeRequest(Object o, String path) {
        try {
            return client.send(HttpRequest.newBuilder(URI.create(ip_address + path))
                    .header("Content-Type", "application/json")
                    .header("gameId", String.valueOf(gameid))
                    .header("userId", String.valueOf(playerid))
                    .header("isWhitePlayer", String.valueOf(localPlaysAsWhite))
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(o)))
                    .build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static MoveInformation getAndRemoveBufferedInput(){
        MoveInformation result = bufferedInput;
        bufferedInput = null;
        return result;
    }
    public static void setBufferedInput(MoveInformation input){
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