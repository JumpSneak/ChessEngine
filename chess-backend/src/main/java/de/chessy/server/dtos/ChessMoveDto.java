package de.chessy.server.dtos;

public record ChessMoveDto(String piece, int y, int x, int oldX, int oldY) {
}
