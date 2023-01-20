package core.chessy.events;

public class UserJoinedGameEvent extends Event {

    public final int gameId;

    public final int userId;
    private final boolean isWhitePlayer;

    public UserJoinedGameEvent(int gameId, int userId, boolean isWhitePlayer) {
        super("USER_JOINED_GAME");
        this.gameId = gameId;
        this.userId = userId;
        this.isWhitePlayer = isWhitePlayer;
    }
}
