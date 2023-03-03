package edu.kit.informatik.application.commands;

import edu.kit.informatik.data.Data;
import edu.kit.informatik.data.FireFighter;


/**
 * Class contains the method for the command 'turn'
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class Turn {

    // private constructor
    private Turn() {
    }

    /**
     * Method which ends the turn of player
     *
     * @return the name of next player in order
     */
    public static String comTurn() {
        if (Data.activePlayer == null) {        // check if player lost
            String errorMessage;
            errorMessage = "Error, game is over.";
            return errorMessage;
        }
        Data.activePlayer = Data.game.getNextPlayer(Data.activePlayer);
        for (FireFighter fireFighter : Data.activePlayer.getFireFighterList()) {
            fireFighter.setActionPoints(3);
            fireFighter.setNoMove(false);
            fireFighter.clearFieldsAlreadyExtinguishedList();
        }
        return Data.activePlayer.getName();     // next player
    }
}
