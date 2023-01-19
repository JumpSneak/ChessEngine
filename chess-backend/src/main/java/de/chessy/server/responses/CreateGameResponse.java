package de.chessy.server.responses;

import de.chessy.game.Game;

public record CreateGameResponse(Game game, boolean isWhitePlayer) {
}
