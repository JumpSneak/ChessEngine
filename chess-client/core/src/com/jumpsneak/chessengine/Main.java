package com.jumpsneak.chessengine;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.players.LocalPlayer;
import com.jumpsneak.chessengine.players.OnlinePlayer;
import com.jumpsneak.chessengine.screens.GameScreen;
import com.jumpsneak.chessengine.screens.MainMenu;

public class Main extends ApplicationAdapter {
	Stage stage;
	Board board;

	public void host(){
		board = new Board(new LocalPlayer("Michi"), new OnlinePlayer("Loser"), false);
		stage.addActor(board);
	}
	public void join(){
		board = new Board(new LocalPlayer("Michi"), new OnlinePlayer("Loser"), true);
		stage.addActor(board);
	}
	public void local(){
		board = new Board(new LocalPlayer("Michi"), new LocalPlayer("Loser"), true);
		stage.addActor(board);
	}
	@Override
	public void create () {
//		board = new Board(new LocalPlayer("Michi"), new OnlinePlayer("Loser"));
//		stage.addActor(board);
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
	}
	@Override
	public void render () {
		if(Gdx.input.isKeyJustPressed(Input.Keys.H)) host();
		else if(Gdx.input.isKeyJustPressed(Input.Keys.J)) join();
		else if(Gdx.input.isKeyJustPressed(Input.Keys.L)) local();
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
