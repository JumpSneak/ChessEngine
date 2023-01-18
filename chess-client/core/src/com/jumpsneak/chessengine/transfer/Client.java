package com.jumpsneak.chessengine.transfer;

import com.badlogic.gdx.Net;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.elements.Pawn;
import com.jumpsneak.chessengine.elements.Piece;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Client {
    static String ip_address = "http://localhost:7999/";
    // "https://chess.julianhartl.dev/";
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
    static boolean flipped;

    static int id = -1;

    public static void sendSpam(MoveInformation moveInformation) {
        System.out.println(makeRequest(new MoveInformation(1, 1, 0, 1), Endpoints.play).body());
    }
    public static void sendMove(Piece piece, int toTileX, int toTileY){
        makeRequest(new MoveInformation(piece.getTilex(), piece.getTiley(), toTileX, toTileY), Endpoints.play);
    }

    public static boolean createGame(Board board) {
        flipped = board.isBoardFlipped();
        var response = makeRequest("", Endpoints.create);
        Gson g = new Gson();
        CreateGameResponse createGameResponse = g.fromJson(response.body(), CreateGameResponse.class);
        id = createGameResponse.id;
        //board.setWhite(createGameResponse.isWhite); falsch
        System.out.println(response.body());
        return response.statusCode() == 200;
    }

    public static HttpResponse<String> makeRequest(Object o, String path) {
        try {
            return client.send(HttpRequest.newBuilder(URI.create(ip_address + path))
                    .header("Content-Type", "application/json")
                    .header("gameId", "1")
                    .header("playerId", String.valueOf(id))
                            .header("isWhitePlayer", String.valueOf(flipped))
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(o)))
                    .build(), HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

//    public static HttpRequest getRequest(MoveInformation moveInformation){
//        return HttpRequest.newBuilder(URI.create(ip_address + "game/playPiece"))
//                .header("Content-Type", "application/json")
//                .header("gameId", "1")
//                .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(moveInformation)))
//                .build();
//    }