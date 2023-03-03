package edu.kit.informatik.application.commands;

import edu.kit.informatik.data.Data;
import edu.kit.informatik.data.FireFighter;

/**
 * This class contains the method for the command 'extinguish'
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class Extinguish {

    // private constructor
    private Extinguish() {
    }

    /**
     * This method checks the conditions first and after changes the status of the target field
     *
     * @param fireFighterName   the name of the fire fighter performing the action
     * @param targetFieldRow    row position
     * @param targetFieldColumn column position
     * @return - an specific error message if condition was violated or if extinguished correctly
     * - the new status of the target field and the remaining number of action points
     * - or 'win' if the players won the game
     */
    public static String comExtinguish(String fireFighterName, int targetFieldRow, int targetFieldColumn) {
        if (Data.activePlayer == null) {
            String errorMessage;
            errorMessage = "Error, game is over.";
            return errorMessage;
        }
        FireFighter fireFighter = Data.game.getFireFighter(fireFighterName);
        if (fireFighter == null) {
            return "Error, this fire fighter does not exist.";
        }
        if (fireFighter.getActionPoints() == 0) {
            return "Error, no actions points, extinguishing not possible.";
        }
        if (fireFighter.getWaterPoints() == 0) {
            return "Error, this fire fighter cannot extinguish any fire, because it has no water.";
        }
        if (fireFighter.hasBeenFieldAlreadyExtinguished(targetFieldRow, targetFieldColumn)) {
            return "Error, during this turn you already extinguished "
                    + "field <" + targetFieldRow + "," + targetFieldColumn + ">.";
        }

        int posRow = fireFighter.getPosRow();
        int posColumn = fireFighter.getPosColumn();

        int distanceToTargetFieldRow = Math.abs(posRow - targetFieldRow);
        int distanceToTargetFieldColumn = Math.abs(posColumn - targetFieldColumn);
        if (distanceToTargetFieldColumn + distanceToTargetFieldRow != 1) {
            return "Error, fire fighter too far away from fire.";
        }

        switch (Data.game.getMatchField()[targetFieldRow][targetFieldColumn]) {
            case "A":
            case "B":
            case "C":
            case "D":
            case "L":
            case "w":
                return "Error, fire stations, fire water ponds and wet fields cannot be extinguished.";
            case "d":
                Data.game.getMatchField()[targetFieldRow][targetFieldColumn] = "w";
                break;
            case "+":
                Data.game.getMatchField()[targetFieldRow][targetFieldColumn] = "w";
                Data.activePlayer.changeReputation(1);
                break;
            case "*":
                Data.game.getMatchField()[targetFieldRow][targetFieldColumn] = "+";
                Data.activePlayer.changeReputation(1);
                break;
            default:
        }
        // checks, if no field is burning
        int burningForest = 0;
        for (int i = 0; i < Data.game.getQtyRows(); i++) {
            for (int j = 0; j < Data.game.getQtyColumns(); j++) {
                if ((Data.game.getMatchField()[i][j].equals("*")) || (Data.game.getMatchField()[i][j].equals("+"))) {
                    burningForest = burningForest + 1;
                }
            }
        }
        if (burningForest == 0) {                   // players won the game
            Data.activePlayer = null;
            return "win";
        }
        fireFighter.addExtinguishedField(targetFieldRow, targetFieldColumn);
        fireFighter.setActionPoints(fireFighter.getActionPoints() - 1);
        fireFighter.setWaterPoints(fireFighter.getWaterPoints() - 1);
        fireFighter.setNoMove(true);
        return Data.game.getMatchField()[targetFieldRow][targetFieldColumn] + "," + fireFighter.getActionPoints();
    }
}
