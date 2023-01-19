package com.jumpsneak.chessengine.transfer;

public class Environment {

    public static boolean isLocal = true;
    public static final String REMOTE_API_URL = "https://api.chess.julianhartl.dev/";
    public static final String LOCAL_API_URL = "http://localhost:7999/";
    public static final String REMOTE_SOCKETS_URL = "wss://socket.chess.julianhartl.dev/";
    public static final String LOCAL_SOCKETS_URL = "ws://localhost:8000/";

    public static String getApiUrl() {
        return isLocal ? LOCAL_API_URL : REMOTE_API_URL;
    }

    public static String getSocketsUrl() {
        return isLocal ? LOCAL_SOCKETS_URL : REMOTE_SOCKETS_URL;
    }
}
