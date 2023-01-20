package com.jumpsneak.chessengine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jumpsneak.chessengine.ChessGame;
import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.players.LocalPlayer;
import com.jumpsneak.chessengine.players.OnlinePlayer;

public class MainMenuScreen implements Screen {

    private final ChessGame game;

    private final OrthographicCamera camera;

    public Board host() {
        return new Board(new LocalPlayer("Michi"), new OnlinePlayer("Loser"), false);
    }

    public Board join() {
        return new Board(new LocalPlayer("Michi"), new OnlinePlayer("Loser"), true);
    }

    public Board local() {
        return new Board(new LocalPlayer("Michi"), new LocalPlayer("Loser"), true);
    }

    public MainMenuScreen(ChessGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to JumpSneak Chess Engine!", 100, 150);
        game.font.draw(game.batch, "Press [h] to host a game or [j] to join an existing game!", 100, 100);
        game.batch.end();


        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            game.setScreen(new GameScreen(host()));
            dispose();
        } else if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            game.setScreen(new GameScreen(join()));
            dispose();
        }
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