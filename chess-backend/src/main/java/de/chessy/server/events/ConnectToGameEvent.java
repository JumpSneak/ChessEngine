package de.chessy.server.events;

public class ConnectToGameEvent extends Event {

    public final int gameId;
    public ConnectToGameEvent(int gameId) {
        super("CONNECT_TO_GAME");
        this.gameId = gameId;
    }
}
