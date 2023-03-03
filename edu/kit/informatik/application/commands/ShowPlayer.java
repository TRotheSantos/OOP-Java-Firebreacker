package edu.kit.informatik.application.commands;

import edu.kit.informatik.data.Data;
import edu.kit.informatik.data.FireFighter;

import java.util.ArrayList;

/**
 * This class contains the method for the command 'show player'
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class ShowPlayer {

    // private constructor
    private ShowPlayer() {
    }

    /**
     * This method manages the command 'show player', it gets the attributes of demanding player
     *
     * @return an array with attributes
     */
    public static String[] comShowPlayer() {
        ArrayList<String> comShowPlayer = new ArrayList<>();
        String row = "";
        if (Data.activePlayer == null) {                   // check if player already lost
            String[] errorMessage = new String[1];
            errorMessage[0] = "Error, game is over, no active player.";
            return errorMessage;
        }

        row = Data.activePlayer.getName() + "," + Data.activePlayer.getReputation();
        comShowPlayer.add(row);
        for (FireFighter fireFighter : Data.activePlayer.getFireFighterList()) {
            row = fireFighter.getName() + "," + fireFighter.getWaterPoints() + ","
                    + fireFighter.getActionPoints() + "," + fireFighter.getPosRow() + "," + fireFighter.getPosColumn();
            comShowPlayer.add(row);
        }
        return comShowPlayer.toArray(new String[0]);
    }
}
