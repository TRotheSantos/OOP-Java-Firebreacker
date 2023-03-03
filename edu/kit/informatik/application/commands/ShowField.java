package edu.kit.informatik.application.commands;

import edu.kit.informatik.data.Data;
import edu.kit.informatik.data.Player;

/**
 * Class containing the method for the command 'show field'
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class ShowField {

    // private constructor
    private ShowField() {
    }

    /**
     * Method for 'show field', gets the status of specific field
     * <p>
     * position of field that is demanded
     *
     * @param row    row position
     * @param column column position
     * @return the status of specific field
     */
    public static String comShowField(int row, int column) {
        String comShowField = "";
        comShowField = Data.game.getMatchField()[row][column];
        for (Player player : Data.game.getPlayerList()) {
            comShowField = comShowField + player.fireFightersOnField(row, column);
        }
        return comShowField;
    }
}