package de.chessy.server.responses;

import de.chessy.game.ServerGame;

public record CreateGameResponse(ServerGame game, boolean isWhitePlayer) {
}
