package com.webcheckers.model;

import com.webcheckers.util.CheckersMinimaxAlgorithm;
import com.webcheckers.util.Message;

import java.util.Random;

public class AIPlayer extends Player {

    public static final String ROBOT_EMOJI = "\uD83E\uDD16";
    
    /** The list of AI player names (w/ the emoji) */
    public static final String[] AI_NAMES = {"Mrs. Hudson", "Inspector Lestrade",
            "Doctor Watson", "Enola Holmes", "Mycroft Holmes",
            "Professor Moriarty", "Sherlock Holmes"};

    private int aiPlayerIndex;

    /**
     * Creates a new player with a username
     *
     * @param aiPlayerIndex The integer that describes which AI player this is, corresponding to one of the players
     *                          in the AI_NAMES constant. Should be >= 0 and < AI_NAMES.length
     */
    public AIPlayer(int aiPlayerIndex) {
        super(String.format("%s%s", ROBOT_EMOJI, AI_NAMES[aiPlayerIndex]));

        this.aiPlayerIndex = aiPlayerIndex;
    }

    /**
     * Gets the AI player index for this AI player. Can be accessed through the AI_NAMES constant.
     * @return
     *      An integer from >=0 stating the AI player this is
     */
    public int getAIPlayerIndex() {
        return aiPlayerIndex;
    }

    /**
     * Performs the AI Player's turn
     * @param game the game being played
     */
    public void performTurn(Game game) {
        CheckersMinimaxAlgorithm.MovePossibility movePossibility = CheckersMinimaxAlgorithm.bestMove(game.getBoard(),
                game.getPlayerColor(this), aiPlayerIndex + 1);

        if (movePossibility != null) {
            Move move = movePossibility.getMove();
            game.setPendingMove(move.getStart().getRow(), move.getStart().getCell(),
                    move.getEnd().getRow(), move.getEnd().getCell());

            game.submitMove();
        }

        Game.WinType winType = game.checkWin();

        //set game active to false if a player wins
        if (!winType.equals(Game.WinType.NO_WIN)) {
            game.setActive(false);
        }
    }
}
