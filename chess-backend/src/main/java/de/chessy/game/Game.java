package de.chessy.game;

import de.chessy.user.User;

public record Game(int id, User white, User black, Board board) {

}
