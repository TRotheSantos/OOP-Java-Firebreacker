package edu.kit.informatik.userinterface;

import edu.kit.informatik.Terminal;

/**
 * This class contains the handle output method, to abstract from the Terminal class
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class Output {

    // private constructor
    private Output() {
    }

    /**
     * This method prints the output, every element of the array
     *
     * @param output String array of output which is given in the application method from one of the command methods
     */
    public static void handleOutput(String[] output) {
        for (String outputLine : output) {
            Terminal.printLine(outputLine);
        }
    }
}
