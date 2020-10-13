package com.webcheckers.model;

import com.webcheckers.ui.PostSubmitTurnRoute;

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

    /** The board being played on */
    private Board board;

    /** The red player */
    private Player redPlayer;

    /** The white player */
    private Player whitePlayer;

    /** The move currently being made */
    private Move pendingMove;

    /** The list of valid jumps */
    private LinkedList<Move> validJumps;

    /**
     * The result of a move attemp2
     */
    public enum MoveResult {
        OK,                     // The move is valid and can be made
        PIECE_NULL_ERR,         // There is no piece to move at the start space, so invalid
        END_OCCUPIED_ERR,       // There is a piece at the end space, so invalid
        MOVE_DIRECTION_ERR,     // The direction of the move is backwards, so invalid
        TOO_FAR_ERR,            // The piece moved too far without jumping, so invalid
        NOT_TURN_ERR,           // It is not the player's turn to move
        INVALID_JUMP,           // An invalid jump has been made
        MUST_MAKE_JUMP,         // Player made a single move when a jump is required
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

        this.pendingMove = null;
        this.validJumps = new LinkedList<>();
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
     * Calculates the possible jumps the current player could make
     */
    public void calculateValidJumps() {
        validJumps.clear();
        if (redPlayer.getIsTurn()) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board.getPieceColorAt(i, j) == Piece.PieceColor.RED) {
                        validJumps.addAll(board.getPieceAt(i, j).getJumps(board, new Position(i, j)));
                    }
                }
            }
        } else if (whitePlayer.getIsTurn()) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board.getPieceColorAt(i, j) == Piece.PieceColor.WHITE) {
                        validJumps.addAll(board.getPieceAt(i, j).getJumps(board, new Position(i, j)));
                    }
                }
            }
        }
    }

    /**
     * Checks to see if a jump is valid
     * If no jumps can be made, then the move is valid
     * @param start The starting position of the jump
     * @param end The end position of the jump
     * @return The move that was made, null if invalid
     */
    public Move isValidJump(Position start, Position end) {
        calculateValidJumps();

        for (Move jump: validJumps) {
            if (jump.getStart().equals(start) && jump.getEnd().equals(end)) {
                return jump;
            }
        }

        return null;
    }

    /**
     * Validates the move of a piece. This validates:
     *      1) There is a piece at this row & cell
     *      2) There isn't a piece at the ending row & cell
     *      3) The direction of the jump according to the piece's color
     *      4) The move only goes 1 square if there are no jumps made
     *      5) If this move isn't a jump, and the player with the color of this piece CAN make a jump, then that
     *              jump must be made, so this is an invalid move
     *      6) If this move is a jump, and there are more jumps to be made after this jump, then those jumps must
     *              be made as well, so this is an invalid move
     * @param startRow
     *      The starting row to move from, should be within board bounds
     * @param startCell
     *      The starting cell to move from, should be within board bounds
     * @param endRow
     *      The ending row to move to, should be within board bounds
     * @param endCell
     *      The ending cell to move to, should be within board bounds
     * @return
     *      An enum MoveResult representing how the validation of the move checked out, see MoveResult for details
     */
    public MoveResult validateMove(int startRow, int startCell, int endRow, int endCell) {

        calculateValidJumps();

        if (!board.hasPieceAt(startRow, startCell)) {   // #1
            return MoveResult.PIECE_NULL_ERR;
        }

        if (board.hasPieceAt(endRow, endCell)) {        // #2
            return MoveResult.END_OCCUPIED_ERR;
        }

        Piece.PieceColor color = board.getPieceColorAt(startRow, startCell);

        if (color == Piece.PieceColor.RED && startRow > endRow                                 // #3 (also checks if
                && this.board.getPieceTypeAt(startRow,startCell) == Piece.PieceType.SINGLE) {  //a single piece is used,
                                                                                               // it is invalid if so)
            return MoveResult.MOVE_DIRECTION_ERR;
        }
        else if (color == Piece.PieceColor.RED && !redPlayer.getIsTurn()){     //checking for turn
            return MoveResult.NOT_TURN_ERR;
        }
        else if (color == Piece.PieceColor.WHITE && startRow < endRow
                && this.board.getPieceTypeAt(startRow,startCell) == Piece.PieceType.SINGLE) {
            return MoveResult.MOVE_DIRECTION_ERR;
        }
        else if (color == Piece.PieceColor.WHITE && !whitePlayer.getIsTurn()){    //checking for turn
            return MoveResult.NOT_TURN_ERR;
        }
        else if (Math.sqrt(Math.pow(endRow - startRow, 2.0) + Math.pow(endCell - startCell, 2.0)) > 1.5) {    // #4
            if (!validJumps.isEmpty()) { // #5 & #6
                if (isValidJump(new Position(startRow, startCell), new Position(endRow, endCell)) == null) {
                    return MoveResult.INVALID_JUMP;
                } else {
                    return MoveResult.OK;
                }
            }
            return MoveResult.TOO_FAR_ERR;
        } else if (!validJumps.isEmpty()) {
            return MoveResult.MUST_MAKE_JUMP;
        }

        return MoveResult.OK;
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
        if (validJumps.isEmpty()) {
            pendingMove = new Move(new Position(startRow, startCell), new Position(endRow, endCell));
        } else {
            pendingMove = isValidJump(new Position(startRow, startCell), new Position(endRow, endCell));
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
            System.out.println("move is null");
            return;
        }

        board.movePiece(pendingMove);

        // toggle turns
        this.redPlayer.setIsTurn(!this.redPlayer.getIsTurn());
        this.whitePlayer.setIsTurn(!this.whitePlayer.getIsTurn());

        resetPendingMove();
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
        } else if (player.getName().equals(whitePlayer.getName())) {
            return Piece.PieceColor.WHITE;
        } else {
            return null;
        }
    }
}
