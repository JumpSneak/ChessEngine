package de.chessy.server.events;

public class UserJoinedGameEvent extends Event {

    public final int gameId;

    public final int userId;

    public UserJoinedGameEvent(int gameId, int userId) {
        super("USER_JOINED_GAME");
        this.gameId = gameId;
        this.userId = userId;
    }
}
