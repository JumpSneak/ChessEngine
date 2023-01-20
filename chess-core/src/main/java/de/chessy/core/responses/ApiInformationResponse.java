package de.chessy.core.responses;

import java.util.List;

public record ApiInformationResponse(int version, List<String> authors) {
}
