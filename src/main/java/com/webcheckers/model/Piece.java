package com.webcheckers.model;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A class to represent a checkers piece on the board.
 *
 * @author Mike White
 */
public class Piece {
    /**
     * A enum representing the type of piece
     */
    public enum PieceType {
        SINGLE("SINGLE"),
        KING("KING");

        private String type;

        private PieceType(String type) {
            this.type = type;
        }

        public String toString() {
            return this.type;
        }
    }

    /**
     * An enum representing the piece color
     */
    public enum PieceColor {
        RED("RED"), WHITE("WHITE");

        private String color;

        private PieceColor(String color) {
            this.color = color;
        }

        public String toString() {
            return this.color;
        }

        public PieceColor opposite() {
            if (this == RED) {
                return WHITE;
            } else {
                return RED;
            }
        }
    }

    private PieceType type;
    private PieceColor color;

    /**
     * Creates a new piece from a color and type
     * @param color
     * @param type
     */
    public Piece(PieceColor color, PieceType type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Gets the piece type
     */
    public PieceType getType() {
        return this.type;
    }

    /**
     * Gets the piece color
     */
    public PieceColor getColor() {
        return this.color;
    }

    /**
     * sets the type of the Piece
     * @param type the type to set the piece to
     */
    public void setType(PieceType type){this.type = type;}

    /**
     * Gets the possible moves for the piece at a given position
     * @param position The starting position of the piece
     * @return The list of possible moves, given the spaces are empty
     */
    public ArrayList<Move> getNonJumpMoves(Position position) {
        ArrayList<Move> moves = new ArrayList<>();
        int row = position.getRow();
        int cell = position.getCell();

        if (this.type == PieceType.KING) { // kings move in four directions
            moves.add(new Move(position, new Position(row + 1, cell + 1)));
            moves.add(new Move(position, new Position(row + 1, cell - 1)));
            moves.add(new Move(position, new Position(row - 1, cell - 1)));
            moves.add(new Move(position, new Position(row - 1, cell + 1)));
        } else if (this.color == PieceColor.RED) { // red must advance
            moves.add(new Move(position, new Position(row + 1, cell + 1)));
            moves.add(new Move(position, new Position(row + 1, cell - 1)));
        } else { // white pieces move towards back
            moves.add(new Move(position, new Position(row - 1, cell - 1)));
            moves.add(new Move(position, new Position(row - 1, cell + 1)));
        }

        return moves;
    }

    /**
     * Gets the possible jumps that could be made by this piece
     * @param board The board being played on
     * @param position The position of the piece
     * @return The list of jumps that can be made
     */
    public LinkedList<Move> getJumps(Board board, Position position) {
        LinkedList<Move> jumps = new LinkedList<>();
        int row = position.getRow();
        int cell = position.getCell();

        if (this.type == PieceType.KING) {
            if (board.getPieceColorAt(row + 1, cell + 1) == this.color.opposite() &&
                    !board.hasPieceAt(row + 2, cell + 2) &&
                    board.inBounds(row + 2, cell + 2)) {
                Move newMove = new Move(position,
                        new Position(row + 2, cell + 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row + 1, cell + 1),
                                new Position(row + 1, cell + 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row + 2, cell + 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            } if (board.getPieceColorAt(row + 1, cell - 1) == this.color.opposite() &&
                    !board.hasPieceAt(row + 2, cell - 2) &&
                    board.inBounds(row + 2, cell - 2)) {
                Move newMove = new Move(position,
                        new Position(row + 2, cell - 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row + 1, cell - 1),
                                new Position(row + 1, cell - 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row + 2, cell - 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            } if (board.getPieceColorAt(row - 1, cell + 1) == this.color.opposite() &&
                    !board.hasPieceAt(row - 2, cell + 2) &&
                    board.inBounds(row - 2, cell + 2)) {
                Move newMove = new Move(position,
                        new Position(row - 2, cell + 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row - 1, cell + 1),
                                new Position(row - 1, cell + 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell + 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            } if (board.getPieceColorAt(row - 1, cell - 1) == this.color.opposite() &&
                    !board.hasPieceAt(row - 2, cell - 2) &&
                    board.inBounds(row - 2, cell - 2)) {
                Move newMove = new Move(position,
                        new Position(row - 2, cell - 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row - 1, cell - 1),
                                new Position(row - 1, cell - 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell - 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            }
        } else if (this.getColor() == PieceColor.RED) {
            if (board.getPieceColorAt(row + 1, cell + 1) == this.color.opposite() &&
                    !board.hasPieceAt(row + 2, cell + 2) &&
                    board.inBounds(row + 2, cell + 2)) {
                Move newMove = new Move(position,
                        new Position(row + 2, cell + 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row + 1, cell + 1),
                                new Position(row + 1, cell + 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row + 2, cell + 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            } if (board.getPieceColorAt(row + 1, cell - 1) == this.color.opposite() &&
                    !board.hasPieceAt(row + 2, cell - 2) &&
                    board.inBounds(row + 2, cell - 2)) {
                Move newMove = new Move(position,
                        new Position(row + 2, cell - 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row + 1, cell - 1),
                                new Position(row + 1, cell - 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row + 2, cell - 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            }
        } else if (this.color == PieceColor.WHITE) {
            if (board.getPieceColorAt(row - 1, cell + 1) == this.color.opposite() &&
                    !board.hasPieceAt(row - 2, cell + 2) &&
                    board.inBounds(row - 2, cell + 2)) {
                Move newMove = new Move(position,
                        new Position(row - 2, cell + 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row - 1, cell + 1),
                                new Position(row - 1, cell + 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell + 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            } if (board.getPieceColorAt(row - 1, cell - 1) == this.color.opposite() &&
                    !board.hasPieceAt(row - 2, cell - 2) &&
                    board.inBounds(row - 2, cell - 2)) {
                Move newMove = new Move(position,
                        new Position(row - 2, cell - 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row - 1, cell - 1),
                                new Position(row - 1, cell - 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell - 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            }
        }

        return jumps;
    }

    /**
     * Gets the possible jumps that could be made by this piece
     * @param board The board being played on
     * @param position The position of the piece
     * @param move The previous move
     * @return The list of jumps that can be made
     */
    public LinkedList<Move> getJumps(Board board, Position position, Move move) {
        LinkedList<Move> jumps = new LinkedList<>();
        int row = position.getRow();
        int cell = position.getCell();

        if (this.type == PieceType.KING) {
            if (board.getPieceColorAt(row + 1, cell + 1) == this.color.opposite() &&
                    !board.hasPieceAt(row + 2, cell + 2) &&
                    !move.pieceCaptured(new Position(row + 1, cell + 1)) &&
                    board.inBounds(row + 2, cell + 2)) {
                Move newMove = move.addMove(
                        new Position(row + 2, cell + 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row + 1, cell + 1),
                                new Position(row + 1, cell + 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row + 2, cell + 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            } if (board.getPieceColorAt(row + 1, cell - 1) == this.color.opposite() &&
                    !board.hasPieceAt(row + 2, cell - 2) &&
                    !move.pieceCaptured(new Position(row + 1, cell - 1)) &&
                    board.inBounds(row + 2, cell - 2)) {
                Move newMove = move.addMove(
                        new Position(row + 2, cell - 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row + 1, cell - 1),
                                new Position(row + 1, cell - 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row + 2, cell - 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            } if (board.getPieceColorAt(row - 1, cell + 1) == this.color.opposite() &&
                    !board.hasPieceAt(row - 2, cell + 2) &&
                    !move.pieceCaptured(new Position(row - 1, cell + 1)) &&
                    board.inBounds(row - 2, cell + 2)) {
                Move newMove = move.addMove(
                        new Position(row - 2, cell + 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row - 1, cell + 1),
                                new Position(row - 1, cell + 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell + 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            } if (board.getPieceColorAt(row - 1, cell - 1) == this.color.opposite() &&
                    !board.hasPieceAt(row - 2, cell - 2) &&
                    !move.pieceCaptured(new Position(row - 1, cell - 1)) &&
                    board.inBounds(row - 2, cell - 2)) {
                Move newMove = move.addMove(
                        new Position(row - 2, cell - 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row - 1, cell - 1),
                                new Position(row - 1, cell - 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell - 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            }
        } else if (this.getColor() == PieceColor.RED) {
            if (board.getPieceColorAt(row + 1, cell + 1) == this.color.opposite() &&
                    !board.hasPieceAt(row + 2, cell + 2) &&
                    !move.pieceCaptured(new Position(row + 1, cell + 1)) &&
                    board.inBounds(row + 2, cell + 2)) {
                Move newMove = move.addMove(
                        new Position(row + 2, cell + 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row + 1, cell + 1),
                                new Position(row + 1, cell + 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row + 2, cell + 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            } if (board.getPieceColorAt(row + 1, cell - 1) == this.color.opposite() &&
                    !board.hasPieceAt(row + 2, cell - 2) &&
                    !move.pieceCaptured(new Position(row + 1, cell - 1)) &&
                    board.inBounds(row + 2, cell - 2)) {
                Move newMove = move.addMove(
                        new Position(row + 2, cell - 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row + 1, cell - 1),
                                new Position(row + 1, cell - 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row + 2, cell - 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            }
        } else if (this.color == PieceColor.WHITE) {
            if (board.getPieceColorAt(row - 1, cell + 1) == this.color.opposite() &&
                    !board.hasPieceAt(row - 2, cell + 2) &&
                    !move.pieceCaptured(new Position(row - 1, cell + 1)) &&
                    board.inBounds(row - 2, cell + 2)) {
                Move newMove = move.addMove(
                        new Position(row - 2, cell + 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row - 1, cell + 1),
                                new Position(row - 1, cell + 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell + 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            } if (board.getPieceColorAt(row - 1, cell - 1) == this.color.opposite() &&
                    !board.hasPieceAt(row - 2, cell - 2) &&
                    !move.pieceCaptured(new Position(row - 1, cell - 1)) &&
                    board.inBounds(row - 2, cell - 2)) {
                Move newMove = move.addMove(
                        new Position(row - 2, cell - 2),
                        new Move.PieceCapture(
                                board.getPieceAt(row - 1, cell - 1),
                                new Position(row - 1, cell - 1)
                        )
                );
                LinkedList<Move> newJumps = getJumps(board, new Position(row - 2, cell - 2), newMove);
                if (newJumps.isEmpty()) {
                    jumps.add(newMove);
                } else {
                    jumps.addAll(newJumps);
                }
            }
        }

        return jumps;
    }
}
