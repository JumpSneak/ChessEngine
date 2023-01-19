package de.chessy.game.pieces;

public abstract class PieceMetadata {

    public final int id;

    public final int belongsTo;

    public final PieceKind kind;

    public PieceMetadata(int id, int belongsTo, PieceKind kind) {
        this.id = id;
        this.belongsTo = belongsTo;
        this.kind = kind;
    }

}
