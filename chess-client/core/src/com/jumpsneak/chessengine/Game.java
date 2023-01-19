package com.jumpsneak.chessengine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;
import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.players.LocalPlayer;
import com.jumpsneak.chessengine.players.OnlinePlayer;

public class Game extends ApplicationAdapter {
	Stage stage;
	Board board;
	
	@Override
	public void create () {
		stage = new Stage(new ScreenViewport());
		board = new Board(new LocalPlayer("Michi"), new OnlinePlayer("Loser"));
		stage.addActor(board);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		stage.draw();
	}
	
	@Override
	public void dispose () {

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
}
