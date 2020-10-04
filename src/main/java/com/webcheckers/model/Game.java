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

    public enum MoveResult {
        OK,
        PIECE_NULL_ERR,
        END_OCCUPIED_ERR,
        MOVE_DIRECTION_ERR,
        MUST_JUMP_ERR,
        MUST_MAKE_ALL_JUMPS_ERR
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
     *      4) If this move isn't a jump, and the player with the color of this piece CAN make a jump, then that
     *              jump must be made, so this is an invalid move
     *      5) If this move is a jump, and there are more jumps to be made after this jump, then those jumps must
     *              be made as well, so this is an invalid move
     * @param startRow
     * @param startCell
     * @param endRow
     * @param endCell
     * @return
     */
    public MoveResult validateMove(int startRow, int startCell, int endRow, int endCell) {
        if (!board.hasPieceAt(startRow, startCell)) {
            return MoveResult.PIECE_NULL_ERR;
        }

        if (board.hasPieceAt(startRow, endCell)) {
            return MoveResult.END_OCCUPIED_ERR;
        }

        Piece.PieceColor color = board.getPieceColorAt(startRow, startCell);

        if (color == Piece.PieceColor.RED && startRow > endRow) {
            return MoveResult.MOVE_DIRECTION_ERR;
        } else if (color == Piece.PieceColor.WHITE && startRow < endRow) {
            return MoveResult.MOVE_DIRECTION_ERR;
        }

        return MoveResult.OK;
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
