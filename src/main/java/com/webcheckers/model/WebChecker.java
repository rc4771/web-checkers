package com.webcheckers.model;

import java.util.Scanner;
import java.util.logging.Logger;

public class WebChecker {
    private static final Logger LOG = Logger.getLogger(WebChecker.class.getName());

    //Results enum for the winning side.
    public enum GamePlay {RED_WON,WHITE_WON,INVALID}

    //Constants
    public static final int BOARD_SIZE = 8;
    public static final int RED_ANCHOR_X = 1;
    public static final int RED_ANCHOR_Y = 7; //Helps me remember where red starts
    public static final int WHITE_ANCHOR_X = 0;
    public static final int WHITE_ANCHOR_Y = 0; //Helps me remember where white starts

    //Attributes
    /**
     * Red turn is 0 and White turn is 1.
     */
    public int turn = 0;
    public int howManyPiecesLeft = 24;
    /**
     * Game pieces available for red and white.
     */
    public int RedPieces = 12;
    public int WhitePieces = 12;
    public static String[][] Checkerboard = new String[8][9];
    public static int row = 0;
    public static int col = 0;

    //Constructors

    /**
     * Simple add piece to the "matrix" 8x8 board.
     * R for red and W for white
     */
    public static void addPiece(int x, int y, String piece){
        Checkerboard[x][y] = piece;
    }

    /**
     * Remove a piece from its previous position.
     */
    public static void removePiece(int a, int b,int x, int y) {
        if (Checkerboard[a][b]==null || Checkerboard[x][y] !=null) {
            System.out.println("No pieces there/collision with other pieces.");
        } else {
            Checkerboard[a][b] = " ";
        }
    }

    public static void movePiece(String[] abxy) {
        int a = Integer.parseInt(abxy[0]);
        int b = Integer.parseInt(abxy[1]);
        int x = Integer.parseInt(abxy[2]);
        int y = Integer.parseInt(abxy[3]);
        String P = Checkerboard[a][b];
        removePiece(a,b,x,y);
        addPiece(x,y,P);
        showCheckerBoard();
    }

    /**
     * Show board class (only used for debugging and testing)
     * Nicely formatted with pods for each squares.
     */
    public static void showCheckerBoard() {
        for (row = 0; row < Checkerboard.length; row++) {
            System.out.println("");
            System.out.println("-----------------");

            for(col = 0; col < Checkerboard[row].length; col++) {
                if (Checkerboard[row][col] == null) {
                    System.out.print("| ");
                } else {
                    System.out.print("|" + Checkerboard[row][col]);
                }
            }
        }
        System.out.println("");
        System.out.println("-----------------");
    }

    /**
     * Queries whether the game is at the beginning; meaning no moves have yet
     * been made.
     *
     * @return true if no guesses have been made, otherwise, false
     */
    public synchronized boolean isGameBeginning() {
        //if there are less than the total pieces then a game is in progress.
        return RedPieces + WhitePieces == howManyPiecesLeft;
    }

    /**
     * Check for valid spacing of the movement. diagonal and jump.
     * @param abxy ab is prev coordinate and xy is new coordinate
     * @return true if the jump is legal.
     */
    public boolean Jumping(String[] abxy) {
        int a = Integer.parseInt(abxy[0]);
        int b = Integer.parseInt(abxy[1]);
        int x = Integer.parseInt(abxy[2]);
        int y = Integer.parseInt(abxy[3]);
        return x == a - 2 && y == b + 2 || x == a + 2 && y == b + 2 || x == a - 2 && y == b - 2 || x == a + 2 && y == b - 2;
    }
    /**
     * Check for valid move command
     * @param abxy ab is prev coordinate and xy is new coordinate
     * @return true if the move is within the 8x8 board
     */
    public static boolean isValidMove(String[] abxy) {
        int a = Integer.parseInt(abxy[0]);
        int b = Integer.parseInt(abxy[1]);
        int x = Integer.parseInt(abxy[2]);
        int y = Integer.parseInt(abxy[3]);
        return x <= BOARD_SIZE && y <= BOARD_SIZE && x >= 0 && y >= 0;
    }

    public void hasDestroyed(String[] abxy) {
        int x = Integer.parseInt(abxy[0]);
        int y = Integer.parseInt(abxy[1]);
        //TODO
        }

    //TODO
    //GAME HERE
    //ab is previous coordinate.
    //xy is new coordinate.
    //turn == 0 is red and turn == 1 is white.
    public synchronized void makeMove(String[] abxy) {
        final GamePlay thisPlay;
        if (!isValidMove(abxy)) {
            thisPlay = GamePlay.INVALID;
        } else {
            //assert that the game isn't over.
            if (RedPieces == 0 || WhitePieces == 0) {
                throw new IllegalStateException("One player has been defeated.");
            }
            if (turn == 0) {
                //check if the move destroy any pieces
                //TODO
                //"Move" the piece chosen.
                //TODO
            }
        }
    }

    /**
     * Check if the game is finished.
     * @return true if the game was won by one side.
     */
    public synchronized boolean isFinished() {
        return RedPieces == 0 || WhitePieces == 0;
    }

    /**
     * Check if red still has pieces on the board.
     * @return true if red still has pieces.
     */
    public synchronized boolean redHasMorePieces() {
        return RedPieces > 0;
    }

    /**
     * Check if white still has pieces on the board.
     * @return true if white still has pieces.
     */
    public synchronized boolean whiteHasMorePieces() {
        return WhitePieces > 0;
    }

    public synchronized int redPiecesLeft() {
        return RedPieces;
    }

    public synchronized int whitePiecesLeft() {
        return WhitePieces;
    }

    /**
     * Pieces adder for the debugger board.
     * @param color color of the piece.
     */
    public static void piecesAdder(String color) {
        if (color.equals("R")) {
            addPiece(RED_ANCHOR_Y,RED_ANCHOR_X,"R");
            addPiece(RED_ANCHOR_Y,RED_ANCHOR_X+2,"R");
            addPiece(RED_ANCHOR_Y,RED_ANCHOR_X+4,"R");
            addPiece(RED_ANCHOR_Y,RED_ANCHOR_X+6,"R");
            addPiece(RED_ANCHOR_Y-1,RED_ANCHOR_X-1,"R");
            addPiece(RED_ANCHOR_Y-1,RED_ANCHOR_X+1,"R");
            addPiece(RED_ANCHOR_Y-1,RED_ANCHOR_X+3,"R");
            addPiece(RED_ANCHOR_Y-1,RED_ANCHOR_X+5,"R");
            addPiece(RED_ANCHOR_Y-2,RED_ANCHOR_X,"R");
            addPiece(RED_ANCHOR_Y-2,RED_ANCHOR_X+2,"R");
            addPiece(RED_ANCHOR_Y-2,RED_ANCHOR_X+4,"R");
            addPiece(RED_ANCHOR_Y-2,RED_ANCHOR_X+6,"R");
        }
        if (color.equals("W")) {
            addPiece(WHITE_ANCHOR_Y,WHITE_ANCHOR_X,"W");
            addPiece(WHITE_ANCHOR_Y,WHITE_ANCHOR_X+2,"W");
            addPiece(WHITE_ANCHOR_Y,WHITE_ANCHOR_X+4,"W");
            addPiece(WHITE_ANCHOR_Y,WHITE_ANCHOR_X+6,"W");
            addPiece(WHITE_ANCHOR_Y+1,WHITE_ANCHOR_X+1,"W");
            addPiece(WHITE_ANCHOR_Y+1,WHITE_ANCHOR_X+3,"W");
            addPiece(WHITE_ANCHOR_Y+1,WHITE_ANCHOR_X+5,"W");
            addPiece(WHITE_ANCHOR_Y+1,WHITE_ANCHOR_X+7,"W");
            addPiece(WHITE_ANCHOR_Y+2,WHITE_ANCHOR_X,"W");
            addPiece(WHITE_ANCHOR_Y+2,WHITE_ANCHOR_X+2,"W");
            addPiece(WHITE_ANCHOR_Y+2,WHITE_ANCHOR_X+4,"W");
            addPiece(WHITE_ANCHOR_Y+2,WHITE_ANCHOR_X+6,"W");
        }
    }
    public static void Scanner(){
        Scanner userInput = new Scanner(System.in);
        while(true) {
            System.out.println("Format: abxy");
            System.out.println("Where ab is previous row,col and xy is new row, col.");
            System.out.println("Type-in 'q' to quit.");
            System.out.print(">>");
            String input = userInput.nextLine();
            String[] Coordinates = input.split("");
            if (Coordinates[0].equals("q")) {
                System.exit(0);
            } else if (Coordinates.length == 4 && isValidMove(Coordinates)) {
                movePiece(Coordinates);
            } else {
                Scanner();
            }
        }
    }


    public static void main(String[] args) {
        //add red first.
        piecesAdder("R");
        piecesAdder("W");
        showCheckerBoard();
        //Scanner to get user input.
        Scanner();
    }
}
