package com.jumpsneak.chessengine.elements;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public abstract class Piece{
    Board board;
    String name;
    int tilex, tiley;
    float posx, posy;
    boolean isWhite;
    int value;
    TextureRegion textureRegion;


    public Piece(Board board, final String name, int tilex, int tiley, final boolean isWhite, int value, int textureId) {
        this.board = board;
        this.name = name;
        this.tilex = tilex;
        this.tiley = tiley;
        this.posx = board.originx + tilex * board.tileSize;
        this.posy = board.originy + tiley * board.tileSize;
        this.isWhite = isWhite;
        this.value = value;

        Texture mainTexture = new Texture("pieces.png");

        textureRegion = new TextureRegion(mainTexture);
        textureRegion.setRegion(textureId * mainTexture.getWidth() / 6, isWhite ? 0 : mainTexture.getHeight() / 2 + 1,
                mainTexture.getWidth() / 6, mainTexture.getHeight() / 2);
    }
    public abstract boolean isLegalMove(int newTilePosX, int newTilePosY);


    public int getTilex() {
        return tilex;
    }

    public int getTiley() {
        return tiley;
    }
}
