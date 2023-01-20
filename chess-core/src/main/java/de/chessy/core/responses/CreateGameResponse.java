package de.chessy.core.responses;

import de.chessy.game.ServerGame;

public record CreateGameResponse(ServerGame game, boolean isWhitePlayer) {
}
