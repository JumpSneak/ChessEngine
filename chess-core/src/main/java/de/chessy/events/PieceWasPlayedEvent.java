package de.chessy.events;

public class PieceWasPlayedEvent extends Event {

    public final int oldX;
    public final int oldY;
    public final int x;
    public final int y;

    public final int playerId;

    public PieceWasPlayedEvent(int oldX, int oldY, int x, int y, int playerId) {
        super("PIECE_PLAYED");
        this.oldX = oldX;
        this.oldY = oldY;
        this.x = x;
        this.y = y;
        this.playerId = playerId;
    }
}
