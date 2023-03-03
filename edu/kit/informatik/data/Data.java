package edu.kit.informatik.data;

import edu.kit.informatik.data.OwnExceptions.InvalidArgumentFormatExc;
import edu.kit.informatik.userinterface.CheckCommandLine;
import edu.kit.informatik.userinterface.DefineConstants;

/**
 * The data class
 * Contains the game and active player and initGame method to initialize the game
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class Data {

    /**
     * creates new game
     */
    public static Game game = new Game();

    /**
     * declares active player
     */
    public static Player activePlayer;

    // private constructor
    private Data() {
    }

    /**
     * This method initializes the game by setting the arguments and the match field also throws exception (for better
     * handling) if format of arguments does not comply with the conditions
     *
     * @param args the arguments
     * @throws InvalidArgumentFormatExc if conditions are violated
     */
    public static void initGame(String[] args) throws InvalidArgumentFormatExc {

        game.setInitArguments(args);                    // stores all arguments of the commandline for a later reset
        game.setMatchField(CheckCommandLine.checkCommandLineStructure(args));

        if (CheckCommandLine.checkMatchField(game)) {
            int lastRow = game.getQtyRows() - 1;
            int lastColumn = game.getQtyColumns() - 1;
            DefineConstants.defineConstants(lastRow, lastColumn);
            activePlayer = game.getPlayer("A");
        }
    }
}


