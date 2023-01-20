package de.chessy.core.responses;

public record ErrorResponse(String message, int errorCode, String hint) {
}
