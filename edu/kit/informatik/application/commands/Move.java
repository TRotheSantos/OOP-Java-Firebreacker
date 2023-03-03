package edu.kit.informatik.application.commands;

import edu.kit.informatik.data.Data;
import edu.kit.informatik.data.FireFighter;

/**
 * Class containing the method for command 'move' and an helping method
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class Move {

    // private constructor
    private Move() {
    }

    /**
     * This method manages the movement of a firefighter and checks all conditions
     *
     * @param fireFighterName   name of fire fighter which moves
     *                          target position:
     * @param targetFieldRow    row target position
     * @param targetFieldColumn column target position
     * @return String with specific error message or just "OK", if such movement is possible
     */
    public static String comMove(String fireFighterName, int targetFieldRow, int targetFieldColumn) {
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
            return "Error, no actions points, moving not possible.";
        }
        if (fireFighter.isNoMove()) {
            return "Error, this fire fighter cannot move, because it has performed an action already.";
        }

        int posRow = fireFighter.getPosRow();
        int posColumn = fireFighter.getPosColumn();
        int distanceToMoveRow = Math.abs(posRow - targetFieldRow);
        int distanceToMoveColumn = Math.abs(posColumn - targetFieldColumn);

        if (distanceToMoveRow + distanceToMoveColumn == 0) {
            return "Error, you are already on field <" + posRow + "," + posColumn + ">.";
        }

        if (distanceToMoveRow + distanceToMoveColumn > 2) {
            return "Error, such big steps are not allowed.";
        }

        // checks, if target field is accessible
        switch (Data.game.getMatchField()[targetFieldRow][targetFieldColumn]) {
            case "*":
            case "+":
                return "Error, a burning field may not be entered.";
            case "A":
            case "B":
            case "C":
            case "D":
            case "L":
                return "Error, neither fire stations nor fire water ponds can be entered.";
            default:
        }

        // checks, if the field, which should be passed is passable
        String forest;
        if (distanceToMoveRow == 2) {
            forest = Data.game.getMatchField()[(targetFieldRow + posRow) / 2][posColumn];
            if (!isFieldPassable(forest)) {
                return "Error, no passable way to target field.";
            }
        }
        if (distanceToMoveColumn == 2) {
            forest = Data.game.getMatchField()[posRow][(targetFieldColumn + posColumn) / 2];
            if (!isFieldPassable(forest)) {
                return "Error, no passable way to target field.";
            }
        }
        if (distanceToMoveRow == distanceToMoveColumn) {
            String forestSameRow = Data.game.getMatchField()[(posRow)][targetFieldColumn];
            String forestSameColumn = Data.game.getMatchField()[(targetFieldRow)][posColumn];
            if (!isFieldPassable(forestSameRow) && !isFieldPassable(forestSameColumn)) {
                return "Error, no passable way to target field.";
            }
        }

        fireFighter.setActionPoints(fireFighter.getActionPoints() - 1);
        fireFighter.setPosRow(targetFieldRow);
        fireFighter.setPosColumn(targetFieldColumn);

        return "OK";
    }

    /**
     * Simple helping method, for checking if a field is passable
     *
     * @param forest the field to check
     * @return true if given field is passable, false otherwise
     */
    private static boolean isFieldPassable(String forest) {
        switch (forest) {
            case "*":
            case "A":
            case "B":
            case "C":
            case "D":
            case "L":
                return false;
            default:
                return true;
        }
    }
}