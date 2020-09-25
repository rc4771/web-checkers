package com.webcheckers.appl;

import com.webcheckers.model.WebChecker;
//import com.webcheckers.model.WebChecker.GamePlay;

public class PlayerLobby {

    //Constants

    //Attributes

    //This is game.
    private WebChecker game;
    //I don't know if I need GameCenter.
    private final GameCenter gameCenter;

    //Construct new lobby but wait until the player starts the game.
    PlayerLobby(GameCenter gameCenter) {
        game = null;
        this.gameCenter = gameCenter;
    }

    //Get current if playing, else get new game.
    public synchronized WebChecker currentGame() {
        if (game == null) {
            game = gameCenter.getGame();
        }
        return game;
    }

    //Game end. When the player(s) finished playing.
    public void finishedGame() {
        game = null;
    }

    //Player makes a move.
    //two number represent x and y
    //TODO
//    public synchronized GamePlay makeMove(String xy) {
//        GamePlay xy = game.makeMove(xy);
//        if (game.isFinished()) {
//            gameCenter.gameFinished();
//        }
//        return result;
//    }

    //Cleanup
    public void endSession() {
        game = null;
    }

    //Check if player is starting a new game.
    public boolean isStartingGame() {
        return game.isGameBeginning();
    }

    //Does red player still have pieces on the table?
    public boolean redHasMorePieces() {
        return game.redHasMorePieces();
    }

    //Does white player still have pieces on the table?
    public boolean whiteHasMorePieces() {
        return game.whiteHasMorePieces();
    }

    //Counter for red pieces
    public int piecesLeftRed() {
        return game.redPiecesLeft();
    }

    //Counter for white pieces
    public int piecesLeftWhite() {
        return game.whitePiecesLeft();
    }

}
