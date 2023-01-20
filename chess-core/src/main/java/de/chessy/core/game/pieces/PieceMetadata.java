package de.chessy.core.game.pieces;

// Note: cannot be abstract because it needs to be serializable
public class PieceMetadata  {

    public final int id;

    public final boolean isWhite;

    public final PieceKind kind;

    public PieceMetadata(int id, boolean isWhite, PieceKind kind) {
        this.id = id;
        this.isWhite = isWhite;
        this.kind = kind;
    }

}
