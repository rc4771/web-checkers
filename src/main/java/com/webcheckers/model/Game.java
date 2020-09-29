package com.webcheckers.model;

public class Game {
    private int gameID;
    private Board board;
    private Player redPlayer;
    private Player whitePlayer;

    public Game(int gameID, Player redPlayer, Player whitePlayer) {
        this.gameID = gameID;
        this.board = new Board();
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
    }

    public int getGameID() {
        return gameID;
    }

    public Board getBoard() {
        return board;
    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }
}
