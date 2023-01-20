package com.jumpsneak.chessengine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.players.LocalPlayer;
import com.jumpsneak.chessengine.players.OnlinePlayer;

public class GameScreen implements Screen {
    private Stage stage;
    private final Board board;

    public GameScreen(Board board) {
        this.board = board;
        create();
    }

    public void create() {
        stage = new Stage(new ScreenViewport());
        stage.addActor(board);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.draw();
    }

    @Override
    public void show() {

    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
