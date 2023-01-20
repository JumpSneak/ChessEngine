package de.chessy.core.events;

public class ConnectToGameEvent extends Event {

    public int gameId;
    public ConnectToGameEvent(int gameId) {
        super("CONNECT_TO_GAME");
        this.gameId = gameId;
    }

    public ConnectToGameEvent() {
    }
}
