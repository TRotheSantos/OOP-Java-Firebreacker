package edu.kit.informatik.userinterface;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.OwnExceptions.InvalidArgumentFormatExc;
import edu.kit.informatik.data.Player;

import java.util.regex.Pattern;

/**
 * This class contains four methods to check the command line, match field and position conditions (of firefighters
 * and players)
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class CheckCommandLine {

    // private constructor
    private CheckCommandLine() {
    }

    /**
     * Method to first check the input line with the given command patterns and then splits the arguments into an array
     *
     * @param commandLine the input line (read line)
     * @return an errorMessage or "" if commandLine is valid
     */
    public static String[][] checkCommandLineStructure(String[] commandLine)
            throws InvalidArgumentFormatExc, NumberFormatException {

        if (commandLine.length > 1) {
            String errMessage = "invalid game initialization.";
            throw new InvalidArgumentFormatExc(errMessage);
        }
        String[] splitCommandLine = commandLine[0].split(",");
        int qtyRows;
        int qtyColumns;

        if (!Pattern.matches("^\\d*[13579]$", splitCommandLine[0])) {
            String errMessage = "quantity of rows needs to be an odd number.";
            throw new InvalidArgumentFormatExc(errMessage);
        } else {
            qtyRows = Integer.parseInt(splitCommandLine[0]);
        }

        if (!Pattern.matches("^\\d*[13579]$", splitCommandLine[1])) {
            String errMessage = "quantity of columns needs to be an odd number.";
            throw new InvalidArgumentFormatExc(errMessage);
        } else {
            qtyColumns = Integer.parseInt(splitCommandLine[1]);
        }

        int qtyArguments = qtyRows * qtyColumns + 2;
        if (splitCommandLine.length != qtyArguments) {
            String errMessage = "invalid quantity of arguments.";
            throw new InvalidArgumentFormatExc(errMessage);
        }

        String[][] matchfield = new String[qtyRows][qtyColumns];
        int argNumber = 2;
        for (int row = 0; row < qtyRows; row++) {
            for (int column = 0; column < qtyColumns; column++) {
                matchfield[row][column] = splitCommandLine[argNumber];
                argNumber = argNumber + 1;
            }
        }
        return matchfield;
    }

    /**
     * This method checks the match field conditions,
     *
     * @param game the entered game, to check
     * @return true if all conditions comply
     * @throws InvalidArgumentFormatExc a specified error message, if one conditions is violated
     */
    public static boolean checkMatchField(Game game) throws InvalidArgumentFormatExc {
        int qtyRows = game.getQtyRows();
        int qtyColumns = game.getQtyColumns();
        String[][] matchField = game.getMatchField();
        int countPlayers = 0;
        int countFireFighters = 0;
        int countFireWaterPond = 0;
        int burnForest = 0;
        int strongBurnForest = 0;
        String errMessage;

        for (int i = 0; i < qtyRows; i++) {
            for (int j = 0; j < qtyColumns; j++) {
                switch (matchField[i][j]) {
                    case "A":
                    case "B":
                    case "C":
                    case "D":
                        if (checkPlayerPosition(i, j, qtyRows, qtyColumns, matchField[i][j])) {
                            countPlayers = countPlayers + 1;
                            break;
                        }
                        errMessage = "player " + matchField[i][j] + " on wrong position.";
                        throw new InvalidArgumentFormatExc(errMessage);
                    case "A0":
                    case "B0":
                    case "C0":
                    case "D0":
                        if (checkFireFighterPosition(i, j, qtyRows, qtyColumns, matchField[i][j])) {
                            countFireFighters = countFireFighters + 1;
                            for (Player player : game.getPlayerList()) {
                                if (player.getName().equals(matchField[i][j].substring(0, 1))) {
                                    player.addFireFighter(i, j);
                                }
                            }
                            matchField[i][j] = "d";
                            break;
                        }
                        errMessage = "fire fighter " + matchField[i][j] + " on wrong position.";
                        throw new InvalidArgumentFormatExc(errMessage);
                    case "L":
                        if (((i == 0) && (j == qtyColumns / 2)) || ((i == qtyRows - 1) && (j == qtyColumns / 2))
                                || ((i == qtyRows / 2) && (j == qtyColumns - 1)) || ((i == qtyRows / 2) && (j == 0))) {
                            countFireWaterPond = countFireWaterPond + 1;
                        } else {
                            errMessage = "fire water pond on wrong position.";
                            throw new InvalidArgumentFormatExc(errMessage);
                        }
                        break;
                    case "*":
                        strongBurnForest = strongBurnForest + 1;
                        break;
                    case "+":
                        burnForest = burnForest + 1;
                        break;
                    case "d":
                    case "w":
                        break;
                    default:
                        errMessage = "invalid argument <" + matchField[i][j] + ">.";
                        throw new InvalidArgumentFormatExc(errMessage);
                }
            }
        }
        if ((countPlayers == 4) && (countFireFighters == 4)
                && (countFireWaterPond == 4) && (burnForest >= 1) && (strongBurnForest >= 1)) {
            return true;
        }
        errMessage = "not all mandatory elements placed.";
        throw new InvalidArgumentFormatExc(errMessage);
    }

    /**
     * Method to check if each players position is on the correct corner
     * <p>
     * of player:
     *
     * @param row        row position
     * @param column     column position
     *                   <p>
     *                   match field:
     * @param qtyRows    quantity of rows
     * @param qtyColumns quantity of columns
     *                   <p>
     *                   player:
     * @param player     player to check
     * @return true if condition for player is complied, false otherwise
     */
    private static boolean checkPlayerPosition(int row, int column, int qtyRows, int qtyColumns, String player) {
        switch (player) {
            case "A":
                if ((row == 0) && (column == 0)) {
                    return true;
                }
                break;
            case "B":
                if ((row == qtyRows - 1) && (column == qtyColumns - 1)) {
                    return true;
                }
                break;
            case "C":
                if ((row == qtyRows - 1) && (column == 0)) {
                    return true;
                }
                break;
            case "D":
                if ((row == 0) && (column == qtyColumns - 1)) {
                    return true;
                }
                break;
            default:
        }
        return false;
    }

    /**
     * Method to check the right position of fire fighter
     * <p>
     * position of fire fighter:
     *
     * @param row         row position
     * @param column      column position
     *                    <p>
     *                    match field:
     * @param qtyRows     quantity of rows
     * @param qtyColumns  quantity of columns
     *                    <p>
     *                    fire fighter
     * @param fireFighter of player AO, BO, CO, DO
     * @return true if the position matches the condition, false otherwise
     */
    private static boolean checkFireFighterPosition(
            int row, int column, int qtyRows, int qtyColumns, String fireFighter) {
        switch (fireFighter) {
            case "A0":
                if ((row == 1) && (column == 1)) {
                    return true;
                }
                break;
            case "B0":
                if ((row == qtyRows - 2) && (column == qtyColumns - 2)) {
                    return true;
                }
                break;
            case "C0":
                if ((row == qtyRows - 2) && (column == 1)) {
                    return true;
                }
                break;
            case "D0":
                if ((row == 1) && (column == qtyColumns - 2)) {
                    return true;
                }
                break;
            default:
        }
        return false;
    }
}
