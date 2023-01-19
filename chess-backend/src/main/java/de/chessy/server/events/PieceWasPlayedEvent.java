package de.chessy.server.events;

public class PieceWasPlayedEvent extends Event {

    public final int oldX;
    public final int oldY;
    public final int x;
    public final int y;

    public PieceWasPlayedEvent(int oldX, int oldY, int x, int y) {
        super("PIECE_PLAYED");
        this.oldX = oldX;
        this.oldY = oldY;
        this.x = x;
        this.y = y;
    }
}
