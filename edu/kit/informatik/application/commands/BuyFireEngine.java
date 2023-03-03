package edu.kit.informatik.application.commands;

import edu.kit.informatik.data.Data;

/**
 * This class contains the method of command 'buy engine' and a helping method
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class BuyFireEngine {

    // private constructor
    private BuyFireEngine() {
    }

    /**
     * This method checks the conditions (reputation points and target field validation) and if the conditions are valid
     * adds the new fire fighter to the (active) player
     *
     * @param targetFieldRow    row position
     * @param targetFieldColumn column position
     * @return the remaining reputation points
     */
    public static String comBuyFireEngine(int targetFieldRow, int targetFieldColumn) {
        if (Data.activePlayer == null) {
            String errorMessage;
            errorMessage = "Error, game is over.";
            return errorMessage;
        }
        if (Data.activePlayer.getReputation() < 5) {
            return "Error, player has not enough reputation points.";
        }

        if (!isTargetFieldValid(targetFieldRow, targetFieldColumn)) {
            return "Error, target field is either too far away from own fire station or burning.";
        }

        Data.activePlayer.addFireFighter(targetFieldRow, targetFieldColumn);
        Data.activePlayer.changeReputation(-5);
        return String.valueOf(Data.activePlayer.getReputation());
    }

    /**
     * This method checks if target field is close to own fire station and not burning
     *
     * @param targetFieldRow    row position
     * @param targetFieldColumn column position
     * @return true if target field is close to own fire station and not burning, otherwise false
     */
    private static boolean isTargetFieldValid(int targetFieldRow, int targetFieldColumn) {
        int fireStationRow = 0;
        int fireStationColumn = 0;
        String playerName = Data.activePlayer.getName();
        // is target field burning?
        if ((Data.game.getMatchField()[targetFieldRow][targetFieldColumn].equals("*"))
                || (Data.game.getMatchField()[targetFieldRow][targetFieldColumn].equals("+"))) {
            return false;
        }
        // is target field close to own fire station?
        for (int i = 0; i < Data.game.getQtyRows(); i++) {
            for (int j = 0; j < Data.game.getQtyColumns(); j++) {
                if (Data.game.getMatchField()[i][j].equals(playerName)) {
                    fireStationRow = i;
                    fireStationColumn = j;
                    break;
                }
            }
        }
        int distanceToTargetFieldRow = Math.abs(fireStationRow - targetFieldRow);
        int distanceToTargetFieldColumn = Math.abs(fireStationColumn - targetFieldColumn);
        if (!((distanceToTargetFieldColumn + distanceToTargetFieldRow == 1)
                || ((distanceToTargetFieldColumn == 1) && (distanceToTargetFieldRow == 1)))) {
            return false;
        }
        return true;
    }
}