package de.chessy.server.responses;

import java.util.List;

public record ApiInformation(int version, List<String> authors) {
}
