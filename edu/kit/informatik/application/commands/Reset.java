package edu.kit.informatik.application.commands;

import edu.kit.informatik.data.Data;
import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.OwnExceptions.InvalidArgumentFormatExc;

/**
 * This class contains the method for the command reset
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class Reset {

    // private constructor
    private Reset() {
    }

    /**
     * This method resets the match, by initializing a new game with the initializing arguments
     *
     * @return "OK" always
     */
    public static String comReset() {
        String[] initArguments = Data.game.getInitArguments();

        Data.game = new Game();

        // reuse of complete game initialization procedure
        try {
            Data.initGame(initArguments);
        } catch (InvalidArgumentFormatExc ignored) { // arguments already checked during game initialization,
        }                                            // therefore no exception will be thrown

        return "OK";
    }
}
