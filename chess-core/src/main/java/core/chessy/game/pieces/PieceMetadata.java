package core.chessy.game.pieces;

public abstract class PieceMetadata {

    public final int id;

    public final boolean isWhite;

    public final PieceKind kind;

    public PieceMetadata(int id, boolean isWhite, PieceKind kind) {
        this.id = id;
        this.isWhite = isWhite;
        this.kind = kind;
    }

}
