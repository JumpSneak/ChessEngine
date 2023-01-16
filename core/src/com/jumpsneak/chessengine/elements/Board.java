package com.jumpsneak.chessengine.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.ScreenUtils;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Board extends Group {
    TextureRegion muell = new TextureRegion(new Texture("white.png"));
    // Attributes
    float originx = 80;
    float originy = 80;
    float tileSize = 106;
    float colsx = 8;
    float rowsy = 8;
    List<Piece> pieceslist = new ArrayList<>();

    public Board() {
        originx = Gdx.graphics.getWidth() / 2 - colsx * tileSize / 2;
        originy = Gdx.graphics.getHeight() / 2 - rowsy * tileSize / 2;
        initPieces();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        pieceslist.get(0).posx = Gdx.input.getX();
//        pieceslist.get(0).posy = Gdx.graphics.getHeight()- Gdx.input.getY(); //testing
        ShapeDrawer shaper = new ShapeDrawer(batch, muell);
        for (int y = 0; y < rowsy; y++) {
            for (int x = 0; x < colsx; x++) {
                if ((x + y) % 2 == 0) {
                    shaper.setColor(new Color(0x04cc39ff));
                    shaper.filledRectangle(x * tileSize + originx, y * tileSize + originy, tileSize, tileSize);
                } else {
                    shaper.setColor(new Color(0xbef7ceff));
                    shaper.filledRectangle(x * tileSize + originx, y * tileSize + originy, tileSize, tileSize);
                }

            }
        }
        for (int i = 0; i < pieceslist.size(); i++) {
            batch.draw(pieceslist.get(i).textureRegion, pieceslist.get(i).posx, pieceslist.get(i).posy, tileSize, tileSize);
        }
    }

    public boolean movePiece(Piece piece, int toTilex, int toTiley){
        boolean isfree = true;
        for(Piece p: pieceslist){
            if(p.tilex == toTilex && p.tiley == toTiley){
                isfree = false;
            }
        }
        return isfree;
    }
    public void initPieces() {
        for (int i = 0; i < 8; i++) {
            pieceslist.add(new Pawn(this, i, 1, true));
            pieceslist.add(new Pawn(this, i, 6, false));
        }

        // White
        pieceslist.add(new Rook(this, 0, 0, true));
        pieceslist.add(new Rook(this, 7, 0, true));
        pieceslist.add(new Knight(this, 1, 0, true));
        pieceslist.add(new Knight(this, 6, 0, true));
        pieceslist.add(new Bishop(this, 2, 0, true));
        pieceslist.add(new Bishop(this, 5, 0, true));
        pieceslist.add(new Queen(this, 3, 0, true));
        pieceslist.add(new King(this, 4, 0, true));
        // Black
        pieceslist.add(new Rook(this, 0, 7, false));
        pieceslist.add(new Rook(this, 7, 7, false));
        pieceslist.add(new Knight(this, 1, 7, false));
        pieceslist.add(new Knight(this, 6, 7, false));
        pieceslist.add(new Bishop(this, 2, 7, false));
        pieceslist.add(new Bishop(this, 5, 7, false));
        pieceslist.add(new Queen(this, 3, 7, false));
        pieceslist.add(new King(this, 4, 7, false));
    }
}
