package edu.kit.informatik.application.commands;

import edu.kit.informatik.data.Data;
import edu.kit.informatik.data.FireFighter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class contains the method for the refill command and an helping method
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class Refill {

    // private constructor
    private Refill() {
    }

    /**
     * This method refills the water for a given fire fighter, by first checking all conditions
     *
     * @param fireFighterName name of fire fighter
     * @return a specific error message if a condition was violated or the number of remaining action points of
     * player
     */
    public static String comRefill(String fireFighterName) {
        FireFighter fireFighter = Data.game.getFireFighter(fireFighterName);
        if (fireFighter == null) {
            return "Error, this fire fighter does not exist.";
        }
        if (fireFighter.getActionPoints() == 0) {
            return "Error, no actions points, refill not possible.";
        }
        if (fireFighter.getWaterPoints() == 3) {
            return "Error, this fire fighter cannot be refilled because is already fully loaded.";
        }
        int posRow = fireFighter.getPosRow();
        int posColumn = fireFighter.getPosColumn();

        if (isFireFighterCloseToABCDL(posRow, posColumn)) {
            fireFighter.setWaterPoints(3);
            fireFighter.setActionPoints(fireFighter.getActionPoints() - 1);
            fireFighter.setNoMove(true);
        } else {
            return "Error, fire fighter needs to be close to fire station or fire water pond for refilling.";
        }
        return String.valueOf(fireFighter.getActionPoints());
    }

    /**
     * This method checks if the fire fighter is in the right position
     * <p>
     * actual position of the fire fighter
     *
     * @param posRow    row position
     * @param posColumn column position
     * @return true if the fire fire fighter is near to a water source, false otherwiese
     */
    private static boolean isFireFighterCloseToABCDL(int posRow, int posColumn) {
        int qtyRows = Data.game.getQtyRows();
        int qtyColumns = Data.game.getQtyColumns();
        ArrayList<String> waterSource = new ArrayList<String>(Arrays.asList(new String[]{"A", "B", "C", "D", "L"}));
        int checkToNorth = -1;          //northern fields to be checked
        int checkToSouth = +1;          //southern fields to be checked
        int checkToWest = -1;           //western fields to be checked
        int checkToEast = +1;           //eastern fields to be checked
        boolean isWaterSource = false;

        // if fireFighter is located in the first row, no check in direction north necessary
        if (posRow == 0) {
            checkToNorth = 0;
        }
        // if fireFighter is located in the last row, no check in direction south necessary
        if (posRow == qtyRows - 1) {
            checkToSouth = 0;
        }
        // if fireFighter is located in the first column, no check in direction west necessary
        if (posColumn == 0) {
            checkToWest = 0;
        }
        // if fireFighter is located in the last column, no check in direction east necessary
        if (posColumn == qtyColumns - 1) {
            checkToEast = 0;
        }
        //To check if either fire station or fire water pond is on a field around the fire fighter
        for (int i = posRow + checkToNorth; i <= posRow + checkToSouth; i++) {
            for (int j = posColumn + checkToWest; j <= posColumn + checkToEast; j++) {
                {
                    isWaterSource = isWaterSource || waterSource.contains(Data.game.getMatchField()[i][j]);
                }
            }
        }
        return isWaterSource;
    }


}



