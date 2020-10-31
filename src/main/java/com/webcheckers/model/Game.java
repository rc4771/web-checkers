package com.webcheckers.model;

import com.webcheckers.util.MoveValidator;

import java.util.LinkedList;

/**
 * An object to represent an active game of checkers, with a board, between two players. One player is the "red"
 * player and the other one is the "white" player.
 *
 * @author David Allen
 */
public class Game {

    /** The game ID */
    private int gameID;

    /** Whether or not the game is valid */
    private boolean active;

    /** The board being played on */
    private Board board;

    /** The red player */
    private Player redPlayer;

    /** The white player */
    private Player whitePlayer;

    /** The move currently being made */
    private Move pendingMove;

    public enum WinType {
        RED_WIN,
        WHITE_WIN,
        NO_WIN
    }

    /**
     * Creates a new Game object, with a new board and two players to participate
     * @param gameID
     *      The integer ID this game should have, should be >= 0
     * @param redPlayer
     *      The player that will have red checkers
     * @param whitePlayer
     *      The player that will have white checkers
     */
    public Game(int gameID, Player redPlayer, Player whitePlayer) {
        this.gameID = gameID;
        this.board = new Board();
        this.redPlayer = redPlayer;
        this.redPlayer.setIsTurn(true);
        this.whitePlayer = whitePlayer;
        this.whitePlayer.setIsTurn(false);
        this.active = true;

        this.pendingMove = null;
    }

    /**
     * Gets the integer game ID for this game
     */
    public int getGameID() {
        return gameID;
    }

    /**
     * Gets the board object for this game's board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the Player object that is associated with red checkers
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Gets the PLayer object that is associated with white checkers
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Returns the name of a game as "redplayer vs whiteplayer
     * @return The name of the game
     */
    public String getName() {
        return redPlayer.getName() + " vs " + whitePlayer.getName();
    }

    public boolean getActive(){
        return active;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public Piece.PieceColor getCurrentTurn() {
        if (redPlayer.getIsTurn()) {
            return Piece.PieceColor.RED;
        } else {
            return Piece.PieceColor.WHITE;
        }
    }

    /**
     * Sets the current pending move for the player who's turn it is
     * @param startRow
     *      The starting row to move from, should be within board bounds
     * @param startCell
     *      The starting cell to move from, should be within board bounds
     * @param endRow
     *      The ending row to move to, should be within board bounds
     * @param endCell
     *      The ending cell to move to, should be within board bounds
     */
    public void setPendingMove(int startRow, int startCell, int endRow, int endCell) {
        if (MoveValidator.calculateValidJumps(board, getCurrentTurn()).isEmpty()) {
            pendingMove = new Move(new Position(startRow, startCell), new Position(endRow, endCell));
        } else {
            pendingMove = MoveValidator.isValidJump(board, getCurrentTurn(), new Position(startRow, startCell), new Position(endRow, endCell));
        }
    }

    /**
     * Resets the pending move for the player who's turn it is, meaning submitMove(..) will return early and do nothing
     */
    public void resetPendingMove() {
        this.pendingMove = null;
    }

    /**
     * Submits the currently pending move and moves the pieces on the board. If the current pending move has not been
     * set or was reset, no move will happen
     */
    public void submitMove() {
        if (pendingMove == null) {
            return;
        }

        board.movePiece(pendingMove);

        // toggle turns
        this.redPlayer.setIsTurn(!this.redPlayer.getIsTurn());
        this.whitePlayer.setIsTurn(!this.whitePlayer.getIsTurn());

        resetPendingMove();

        if (whitePlayer.getIsTurn() && whitePlayer instanceof AIPlayer) {
            ((AIPlayer) whitePlayer).performTurn(this);
        }
    }

    /**
     * Gets the piece color of a player in this game.
     * @param player
     *      The player to get the color for
     * @return
     *      A Piece.PieceColor enum for that player's color. This will return null if the player provided
     *      is not in this game
     */
    public Piece.PieceColor getPlayerColor(Player player) {
        if (player.getName().equals(redPlayer.getName())) {
            return Piece.PieceColor.RED;
        } else {
            return Piece.PieceColor.WHITE;
        }
    }

    /**
     * Checks the pieces on the board to see if all of one color are gone
     * @return
     *  If game is over, it will return a WinType based on what color pieces are gone
     */
    public WinType checkWin() {

        int whitePieces = 0;
        int redPieces = 0;

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.board.hasPieceAt(i, j)){
                    if(this.board.getPieceAt(i, j).getColor() == Piece.PieceColor.RED){
                        redPieces++;
                    }
                    else{
                        whitePieces++;
                    }
                }
            }
        }

        if(redPieces == 0){
            return WinType.WHITE_WIN;
        }
        else if (whitePieces == 0){
            return WinType.RED_WIN;
        }

        return WinType.NO_WIN; //game is not over
    }
}
