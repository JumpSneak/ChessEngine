package com.jumpsneak.chessengine;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.jumpsneak.chessengine.Game;
import com.jumpsneak.chessengine.elements.Board;
import com.jumpsneak.chessengine.elements.Pawn;
import com.jumpsneak.chessengine.transfer.Client;
import com.jumpsneak.chessengine.transfer.MoveInformation;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Client.sendSpam(new MoveInformation(4, 2, 3, 2));
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("ChessEngine");
		config.setWindowedMode(900, 900);
		new Lwjgl3Application(new Game(), config);
	}
}
