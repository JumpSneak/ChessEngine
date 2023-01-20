package de.chessy.core.responses;

import de.chessy.core.game.Game;

public record CreateGameResponse(Game game, boolean isWhitePlayer) {
}
