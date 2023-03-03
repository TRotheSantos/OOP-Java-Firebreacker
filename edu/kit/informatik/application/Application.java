package edu.kit.informatik.application;

import edu.kit.informatik.application.commands.*;
import edu.kit.informatik.data.Data;
import edu.kit.informatik.userinterface.CheckInputLine;
import edu.kit.informatik.userinterface.Output;

/**
 * This class contains the application-method.
 * A method that connects user interface with the program structure
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class Application {

    // private constructor
    private Application() {
    }

    /**
     * Splits the input line into an array, also checks the needed syntax
     * and gives it to the specific method (buyFireEngine, extinguish, fireToRoll, Move, Refill, Reset, showBoard,
     * showField, showPlayer, Turn)
     * The methods then return an array or string which is given to the handle output method.
     *
     * @param inputLine input line from the main-method
     * @return true: if program should stop (quit)
     * false: for all other commands
     */
    public static boolean application(String inputLine) {
        String[] splitInputLine = CheckInputLine.checkInputLine(inputLine); //splits and checks (regex)
        String[] output = new String[1];
        String fireFighterName;
        int targetFieldRow;
        int targetFieldColumn;
        String command = splitInputLine[0];

        // to check, which commands can not be performed when dice needs to be rolled
        if (Data.game.isNoTurn()) {
            switch (command) {
                case "move":
                case "extinguish":
                case "refill":
                case "buy-fire-engine":
                case "turn":
                    splitInputLine[0] = "Error, you need to roll the dice now (fire-to-roll).";
                    command = "Default";
                    break;
                default:
            }
        }
        switch (command) {
            case "quit":
                return true;
            case "move":
                fireFighterName = splitInputLine[1];
                targetFieldRow = Integer.parseInt(splitInputLine[2]);
                targetFieldColumn = Integer.parseInt(splitInputLine[3]);
                output[0] = Move.comMove(fireFighterName, targetFieldRow, targetFieldColumn);
                break;
            case "extinguish":
                fireFighterName = splitInputLine[1];
                targetFieldRow = Integer.parseInt(splitInputLine[2]);
                targetFieldColumn = Integer.parseInt(splitInputLine[3]);
                output[0] = Extinguish.comExtinguish(fireFighterName, targetFieldRow, targetFieldColumn);
                break;
            case "refill":
                fireFighterName = splitInputLine[1];
                output[0] = Refill.comRefill(fireFighterName);
                break;
            case "buy-fire-engine":
                targetFieldRow = Integer.parseInt(splitInputLine[1]);
                targetFieldColumn = Integer.parseInt(splitInputLine[2]);
                output[0] = BuyFireEngine.comBuyFireEngine(targetFieldRow, targetFieldColumn);
                break;
            case "fire-to-roll":
                if (!Data.game.isNoTurn()) {
                    output[0] = "Error, only when all four players made their turn, you can roll the dice.";
                    break;
                }
                String diceNumber = splitInputLine[1];
                output[0] = FireToRoll.comFireToRoll(diceNumber);
                Data.game.setNoTurn(false);
                break;
            case "turn":
                output[0] = Turn.comTurn();
                break;
            case "reset":
                output[0] = Reset.comReset();
                break;
            case "show-board":
                output = ShowBoard.comShowBoard();
                break;
            case "show-field":
                int row = Integer.parseInt(splitInputLine[1]);
                int column = Integer.parseInt(splitInputLine[2]);
                output[0] = ShowField.comShowField(row, column);
                break;
            case "show-player":
                output = ShowPlayer.comShowPlayer();
                break;
            default:
                output[0] = splitInputLine[0];
        }
        Output.handleOutput(output);
        return false;
    }
}
