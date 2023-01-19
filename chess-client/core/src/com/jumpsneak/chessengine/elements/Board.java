package com.jumpsneak.chessengine.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jumpsneak.chessengine.players.LocalPlayer;
import com.jumpsneak.chessengine.players.OnlinePlayer;
import com.jumpsneak.chessengine.players.Player;
import com.jumpsneak.chessengine.transfer.Client;
import com.jumpsneak.chessengine.transfer.MoveInformation;
import space.earlygrey.shapedrawer.JoinType;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Board extends Group {
    TextureRegion muell = new TextureRegion(new Texture("white.png"));
    Player playerWhite;
    Player playerBlack;
    Player activePlayer;
    Piece activePiece = null;
    boolean whiteTurn = true;
    boolean onlineGame = false;
    MoveInformation lastMove = null;
    // Attributes
    float originx = 80;
    float originy = 80;
    float tileSize = 106;
    float colsx = 8;
    float rowsy = 8;
    boolean boardFlipped = false;
    List<Piece> pieceslist = new ArrayList<>();
    Piece[][] boardPieces = new Piece[(int)colsx][(int)rowsy];

    public Board(Player playerWhite, Player playerBlack, boolean joining) {
        // Board stuff
        originx = (Gdx.graphics.getWidth() >> 1) - colsx * tileSize / 2;
        originy = (Gdx.graphics.getHeight() >> 1) - rowsy * tileSize / 2;
        initPieces();
        // settings for players
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
        // choose local or online
        if (playerWhite instanceof OnlinePlayer || playerBlack instanceof OnlinePlayer) {
            onlineGame = true;
            Client.localPlaysAsWhite = true;
            boolean result;
            if(joining){
                result = Client.joinGame(this);
            }else{
                result = Client.createGame(this);
            }
            if(!result){
                System.out.println("No Connection");
                System.exit(0);
            }
        }else{
            setWhite(true);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // dragging
        inputUpdate();
        activePlayer.getMove();
        if (Gdx.input.isTouched() && activePiece != null) {
            activePiece.posx = Gdx.input.getX() - activePiece.textureRegion.getRegionWidth() / 2;
            activePiece.posy = Gdx.graphics.getHeight() - Gdx.input.getY() - activePiece.textureRegion.getRegionHeight() / 2;
        }
        // draw chessboard
        ShapeDrawer shaper = new ShapeDrawer(batch, muell);
        for (int y = 0; y < rowsy; y++) {
            for (int x = 0; x < colsx; x++) {
                if ((x + y) % 2 == 0) {
                    shaper.filledRectangle(x * tileSize + originx, y * tileSize + originy, tileSize, tileSize, new Color(0x04cc39ff));
                } else {
                    shaper.filledRectangle(x * tileSize + originx, y * tileSize + originy, tileSize, tileSize, new Color(0xbef7ceff));
                }
            }
        }
        // draw pieces
        for (int i = 0; i < pieceslist.size(); i++) {
            if(activePiece!= null && pieceslist.get(i) == activePiece){
                continue;
            }
            batch.draw(pieceslist.get(i).textureRegion, pieceslist.get(i).posx, pieceslist.get(i).posy, tileSize, tileSize);
        }
        // draw possible moves
        float radius = tileSize / 6;
        if (activePiece != null) {
            for (int y = 0; y < rowsy; y++) {
                for (int x = 0; x < colsx; x++) {
                    if (activePiece.isLegalMove(x, y)) {
                        if(getPieceOn(x, y) == null) {
                            shaper.filledCircle(invertTileAccordingly(x, true) * tileSize + originx + tileSize / 2,
                                    invertTileAccordingly(y, false) * tileSize + originy + tileSize / 2,
                                    radius, new Color(0x33333322));
                        }else{
                            shaper.setColor(new Color(0x33333322));
                            shaper.circle(invertTileAccordingly(x, true) * tileSize + originx + tileSize / 2,
                                    invertTileAccordingly(y, false) * tileSize + originy + tileSize / 2,
                                    tileSize/2-5,
                                    10,
                                    JoinType.SMOOTH);
                        }
                    }
                }
            }
            // draw active piece on top
            batch.draw(activePiece.textureRegion, activePiece.posx, activePiece.posy, tileSize, tileSize);
        }


    }

    public void inputUpdate() {
        // Drag and Drop
        if (Gdx.input.justTouched()) { // begin drag
            if(onlineGame && Client.illegalMove){
                System.out.println("ILLEGAL");
                return;
            }
            float height = tileSize * rowsy;
            int xtile = invertTileAccordingly(getMouseTileX(), true);
            int ytile = invertTileAccordingly(getMouseTileY(), false);
            activePiece = getPieceOn(xtile, ytile);
            // old method
//            for (Piece p : pieceslist) {
//                if (p.tilex == xtile && p.tiley == ytile) {
//                    activePiece = p;
//                    pieceslist.remove(p);
//                    pieceslist.add(p);
//                    return;
//                }
//            }
        } else if (activePiece != null && !Gdx.input.isTouched()) { // drop
            int xtile = invertTileAccordingly(getMouseTileX(), true);
            int ytile = invertTileAccordingly(getMouseTileY(), false);
            if (activePlayer.dropPiece(activePiece, xtile, ytile)) {
                // move done
            }
            positionPiece(activePiece, activePiece.tilex, activePiece.tiley);
            activePiece = null;
        }
        // testing
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            flipBoard();
        }
    }

    public boolean movePiece(Piece piece, int toTilex, int toTiley) {
        if (piece == null) {
            return false;
        }
        Piece otherPiece = getPieceOn(toTilex, toTiley);
        boolean successful = false;
        if (piece.isWhite == whiteTurn // players turn
                && (otherPiece == null || otherPiece.isWhite != piece.isWhite) // can be placed
                && piece.isLegalMove(toTilex, toTiley)
                && !isChecked(piece.tilex, piece.tiley, toTilex, toTiley)) { // legal piece move
            if (otherPiece != null && otherPiece.isWhite != piece.isWhite) {
                removePiece(otherPiece);
            }
            if (onlineGame) {
                Client.sendMove(piece, toTilex, toTiley);
            }
            lastMove = new MoveInformation(piece.tilex, piece.tiley, toTilex, toTiley);
            setPieceOn(null, piece.tilex, piece.tiley);
            piece.tilex = toTilex;
            piece.tiley = toTiley;
            setPieceOn(piece, piece.tilex, piece.tiley);
            if(piece.name.equals("Pawn")){
                // Pawn to Queen conversion
                if(piece.isWhite && piece.tiley == 7 || !piece.isWhite && piece.tiley == 0){
                    Piece q = new Queen(this, piece.tilex, piece.tiley, piece.isWhite);
                    positionPiece(q, q.tilex, q.tiley);
                    removePiece(piece);
                    setPieceOn(q, q.tilex, q.tiley);
                    pieceslist.add(q);
                }
            }
//            if(piece instanceof Pawn){
//                if(((Pawn) piece).isUntouched()) {
//                    ((Pawn) piece).setUntouchedFalse(); // untouched determined differently now
//                    ((Pawn) activePiece).setEnPassantPossible(true); // ENPASSANT TODO
//                }else if(((Pawn) activePiece).isEnPassantPossible()){
//                    ((Pawn) activePiece).setEnPassantPossible(false);
//                }
//            }
            System.out.println(activePlayer.toString()+cordsToString(piece, toTilex, toTiley));
            successful = true;
            whiteTurn = !whiteTurn;
            if (activePlayer == playerWhite) {
                activePlayer = playerBlack;
            } else {
                activePlayer = playerWhite;
            }
        }
        positionPiece(piece, piece.tilex, piece.tiley);
        return successful;
    }

    public Piece getPieceOn(int x, int y) {
        if(x < 0 || x >= colsx || y <0 || y>=rowsy){
            return null;
        }
        return boardPieces[x][y];
        // old method
//        for (Piece p : pieceslist) {
//            if (p.tilex == x && p.tiley == y) {
//                return p;
//            }
//        }
//        return null;
    }
    public boolean setPieceOn(Piece piece, int x, int y){
        if(x < 0 || x >= colsx || y <0 || y>=rowsy){
            return false;
        }
        boardPieces[x][y] = piece;
        return true;
    }
    public boolean isChecked(int oldx, int oldy, int newx, int newy){
                                                                                //TODO
        return false;
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

        // init boardPieces
        for(Piece p: pieceslist){
            if(p != null){
                boardPieces[p.tilex][p.tiley] = p;
            }
        }
    }
    public void removePiece(Piece piece){
        if(getPieceOn(piece.tilex, piece.tiley) == piece) {
            setPieceOn(null, piece.tilex, piece.tiley);
        }
        pieceslist.remove(piece);
    }
    public void setWhite(boolean toWhite) {
        if(!toWhite){
            Player zw = playerWhite;
            this.playerWhite = playerBlack;
            this.playerBlack = zw;
        }
        playerWhite.setBoard(this);
        playerBlack.setBoard(this);
        playerWhite.setIsWhite(true);
        playerBlack.setIsWhite(false);
        if (!toWhite) {
            flipBoard();
        }
        activePlayer = playerWhite;
    }

    public void flipBoard() {
        boardFlipped = !boardFlipped;
        for (Piece p : pieceslist) {
            positionPiece(p, p.tilex, p.tiley);
        }
    }

    public float invertCord(float input, boolean isX) {
        if (isX) {
            return colsx * this.tileSize - (input + 1) * tileSize + originx;
        } else {
            return rowsy * this.tileSize - (input + 1) * tileSize + originy;
        }
    }

    public int invertTileAccordingly(int input, boolean isX) {
        if (boardFlipped) {
            if (isX) {
                return (int) colsx - 1 - input;
            } else {
                return (int) rowsy - 1 - input;
            }
        } else {
            return input;
        }
    }

    public void positionPiece(Piece p, int newtileX, int newtileY) {
        p.posx = this.originx + newtileX * this.tileSize;
        p.posy = this.originy + newtileY * this.tileSize;
        if (boardFlipped) {
            p.posx = invertCord(newtileX, true);
            p.posy = invertCord(newtileY, false);
//            p.posx = colsx * this.tileSize - (newtileX+1) * tileSize + originx;
//            p.posy = rowsy * this.tileSize - (newtileY+1) * tileSize + originy;
        }
    }

    public String cordsToString(Piece p, int x, int y) {
        String result = "";
        if (p != null) {
            result += p.name;
            result += " ";
        }
        result += Character.toString(x + 65);
        result += y + 1;
        return result;
    }

    public int getMouseTileX() {
        return (int) ((Gdx.input.getX() - originx) / tileSize);

    }

    public int getMouseTileY() {
        return (int) ((Gdx.graphics.getHeight() - Gdx.input.getY() - originy) / tileSize);
    }

    public boolean isBoardFlipped() {
        return boardFlipped;
    }
}
