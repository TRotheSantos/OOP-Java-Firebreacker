package edu.kit.informatik.userinterface;

import edu.kit.informatik.data.Data;

import java.util.regex.Pattern;


/**
 * This class contains a method to check and split the input line.
 * For splitting the Pattern class is imported.
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class CheckInputLine {

    // private constructor
    private CheckInputLine() {
    }


    /**
     * Method to first check the input line with the given command patterns and then splits the arguments into an array
     *
     * @param inputLine the input line (read line)
     * @return an checked array of Strings
     */
    public static String[] checkInputLine(String inputLine) {
        String activePlayerName;
        if (Data.activePlayer == null) {
            activePlayerName = "A";                         // in case that game is lost and there is no active player,
        } else {                                            // "A" is used as a dummy
            activePlayerName = Data.activePlayer.getName(); // else use name of active player
        }
        boolean isInputCorrect = false;
        String[] splitInputLine = new String[1];

        for (String lookFor : DefineConstants.SYNTAX_COMMANDS) {
            isInputCorrect = isInputCorrect
                    || Pattern.matches(lookFor.replace("activePlayer", activePlayerName), inputLine);
        }
        if (isInputCorrect) {
            splitInputLine = inputLine.split("[, ]");
        } else {
            splitInputLine[0] = "Error, incorrect command/syntax or fire fighter does not belong to active player.";
        }
        return splitInputLine;
    }
}
