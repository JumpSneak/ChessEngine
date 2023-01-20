package de.chessy.core.events;

import de.chessy.core.game.GameStatus;

public class GameStatusChangedEvent extends Event {

    public  GameStatus status;
    public  int gameId;

    public GameStatusChangedEvent(int gameId, GameStatus status) {
        super("GAME_STATUS_CHANGED");
        this.status = status;
        this.gameId = gameId;
    }

    public GameStatusChangedEvent() {
    }
}
