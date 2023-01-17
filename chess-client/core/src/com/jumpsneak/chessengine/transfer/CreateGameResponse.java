package com.jumpsneak.chessengine.transfer;

public class CreateGameResponse {
    public int id;
    public boolean isWhite;

    public CreateGameResponse(int id, boolean isWhite) {
        this.id = id;
        this.isWhite = isWhite;
    }

    @Override
    public String toString() {
        return "CreateGameResponse{" +
                "id=" + id +
                ", isWhite=" + isWhite +
                '}';
    }
}
