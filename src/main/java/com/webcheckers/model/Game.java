package com.webcheckers.model;

/**
 * An object to represent an active game of checkers, with a board, between two players. One player is the "red"
 * player and the other one is the "white" player.
 */
public class Game {
    private int gameID;
    private Board board;
    private Player redPlayer;
    private Player whitePlayer;

    private int pendingMoveStartRow;
    private int pendingMoveStartCell;
    private int pendingMoveEndRow;
    private int pendingMoveEndCell;

    public enum MoveResult {
        OK,                     // The move is valid and can be made
        PIECE_NULL_ERR,         // There is no piece to move at the start space, so invalid
        END_OCCUPIED_ERR,       // There is a piece at the end space, so invalid
        MOVE_DIRECTION_ERR,     // The direction of the move is backwards, so invalid
        TOO_FAR_ERR             // The piece moved too far without jumping, so invalid
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
        this.whitePlayer = whitePlayer;

        pendingMoveStartRow = -1;
        pendingMoveStartCell = -1;
        pendingMoveEndRow = -1;
        pendingMoveEndCell = -1;
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
     * Validates the move of a piece. This validates:
     *      1) There is a piece at this row & cell
     *      2) There isn't a piece at the ending row & cell
     *      3) The direction of the jump according to the piece's color
     *      4) TODO 1/2 The move only goes 1 square if there are no jumps made
     *      5) TODO If this move isn't a jump, and the player with the color of this piece CAN make a jump, then that
     *              jump must be made, so this is an invalid move
     *      6) TODO If this move is a jump, and there are more jumps to be made after this jump, then those jumps must
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
        if (!board.hasPieceAt(startRow, startCell)) {   // #1
            return MoveResult.PIECE_NULL_ERR;
        }

        if (board.hasPieceAt(endRow, endCell)) {        // #2
            return MoveResult.END_OCCUPIED_ERR;
        }

        Piece.PieceColor color = board.getPieceColorAt(startRow, startCell);

        if (color == Piece.PieceColor.RED && startRow > endRow) {   // #3
            return MoveResult.MOVE_DIRECTION_ERR;
        } else if (color == Piece.PieceColor.WHITE && startRow < endRow) {
            return MoveResult.MOVE_DIRECTION_ERR;
        }

        // TODO Implement jumps & multijumps validation
        if (Math.sqrt(Math.pow(endRow - startRow, 2.0) + Math.pow(endCell - startCell, 2.0)) > 1.5) {    // #4
            return MoveResult.TOO_FAR_ERR;
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
        pendingMoveStartRow = startRow;
        pendingMoveStartCell = startCell;
        pendingMoveEndRow = endRow;
        pendingMoveEndCell = endCell;
    }

    /**
     * Resets the pending move for the player who's turn it is, meaning submitMove(..) will return early and do nothing
     */
    public void resetPendingMove() {
        pendingMoveStartRow = -1;
        pendingMoveStartCell = -1;
        pendingMoveEndRow = -1;
        pendingMoveEndCell = -1;
    }

    /**
     * Submits the currently pending move and moves the pieces on the board. If the current pending move has not been
     * set or was reset, no move will happen
     */
    public void submitMove() {
        if (pendingMoveStartRow < 0 || pendingMoveStartCell < 0 || pendingMoveEndRow < 0 || pendingMoveEndCell < 0) {
            return;
        }

        board.movePiece(pendingMoveStartRow, pendingMoveStartCell, pendingMoveEndRow, pendingMoveEndCell);

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
