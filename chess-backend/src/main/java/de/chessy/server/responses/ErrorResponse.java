package de.chessy.server.responses;

public record ErrorResponse(String message, int errorCode, String hint) {
}
