package de.chessy.core.events;

public class PieceWasPlayedEvent extends Event {

    public  int oldX;
    public  int oldY;
    public  int x;
    public  int y;

    public  int playerId;

    public PieceWasPlayedEvent(int oldX, int oldY, int x, int y, int playerId) {
        super("PIECE_PLAYED");
        this.oldX = oldX;
        this.oldY = oldY;
        this.x = x;
        this.y = y;
        this.playerId = playerId;
    }

    public PieceWasPlayedEvent() {
    }
}
