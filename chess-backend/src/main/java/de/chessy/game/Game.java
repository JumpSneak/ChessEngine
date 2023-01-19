package de.chessy.game;

public class Game {

    public int id;
    public Integer white;
    public Integer black;
    public Board board;

    public Game(int id, Integer white, Integer black, Board board) {
        this.id = id;
        this.white = white;
        this.black = black;
        this.board = board;
    }

    public int getOtherPlayer(int userId) {
        if (userId == white) {
            return black;
        } else if (userId == black) {
            return white;
        } else {
            throw new IllegalArgumentException("User is not part of this game");
        }
    }

    public boolean isWhite(int id) {
        return white == id;
    }
}
