package edu.kit.informatik.userinterface;

/**
 * This class contains the command patterns for every command
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class DefineConstants {

    /**
     * This ist the final number of different commands of the program
     */
    private static final int NUMBER_OF_COMMANDS = 11;
    
    /**
     * This is the syntax of the commands
     */
    public static final String[] SYNTAX_COMMANDS = new String[NUMBER_OF_COMMANDS];

    // private constructor
    private DefineConstants() {
    }

    /**
     * This method define the command pattern and adapts the changeable regex
     *
     * @param lastRowO    the last row
     * @param lastColumnO the last column
     */
    public static void defineConstants(int lastRowO, int lastColumnO) {

        defineCommandPattern();

        String lastRow = createRegEx(lastRowO);
        String lastColumn = createRegEx(lastColumnO);
        for (int i = 0; i < NUMBER_OF_COMMANDS; i++) {
            SYNTAX_COMMANDS[i] = SYNTAX_COMMANDS[i].replace("row", lastRow);
            SYNTAX_COMMANDS[i] = SYNTAX_COMMANDS[i].replace("column", lastColumn);
        }
    }

    /**
     * This sets all pattern in an array
     */
    private static void defineCommandPattern() {

        SYNTAX_COMMANDS[0] = "^move\\x20[activePlayer]\\d+[,]{1}[0]*row[,]{1}[0]*column$";
        SYNTAX_COMMANDS[1] = "^extinguish\\x20[activePlayer]\\d+[,]{1}[0]*row[,]{1}[0]*column$";
        SYNTAX_COMMANDS[2] = "^refill\\x20[activePlayer]\\d+$";
        SYNTAX_COMMANDS[3] = "^buy-fire-engine\\x20[0]*row[,]{1}[0]*column$";
        SYNTAX_COMMANDS[4] = "^fire-to-roll\\x20[1-6]$";
        SYNTAX_COMMANDS[5] = "^turn$";
        SYNTAX_COMMANDS[6] = "^reset$";
        SYNTAX_COMMANDS[7] = "^show-board$";
        SYNTAX_COMMANDS[8] = "^show-field\\x20[0]*row[,]{1}[0]*column$";
        SYNTAX_COMMANDS[9] = "^show-player$";
        SYNTAX_COMMANDS[10] = "^quit$";

    }

    /**
     * This method manipulates regex expressions with highest number for rows and columns, as a result the regex
     * checks all commands directly of coordinates in range or not
     *
     * @param numberToConvert last column or last row
     * @return a return String with the new pattern
     */
    private static String createRegEx(int numberToConvert) {
        String retStr;
        String toConvert = String.valueOf(numberToConvert);

        if (toConvert.length() == 1) {
            retStr = "[0-" + toConvert + "]{1}";
            return retStr;
        }

        retStr = "(([" + toConvert.charAt(0) + "]{1}";
        for (int i = 1; i < toConvert.length(); i++) {
            retStr = retStr + "[0-" + toConvert.charAt(i) + "]{1}";
        }

        retStr = retStr + ")|(";

        retStr = retStr + "[" + (Integer.parseInt(String.valueOf(toConvert.charAt(0))) - 1) + "]{1}";
        for (int i = 1; i < toConvert.length(); i++) {
            retStr = retStr + "[0-9]{1}";
        }

        retStr = retStr + ")";

        for (int i = toConvert.length() - 1; i > 0; i--) {
            retStr = retStr + "|([0-9]{" + i + "})){1}";
        }

        return retStr;
    }
}
