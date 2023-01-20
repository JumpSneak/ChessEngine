package de.chessy.core.events;

public class UserJoinedGameEvent extends Event {

    public int gameId;

    public int userId;
    private boolean isWhitePlayer;

    public UserJoinedGameEvent(int gameId, int userId, boolean isWhitePlayer) {
        super("USER_JOINED_GAME");
        this.gameId = gameId;
        this.userId = userId;
        this.isWhitePlayer = isWhitePlayer;
    }


    public UserJoinedGameEvent() {
    }
}
