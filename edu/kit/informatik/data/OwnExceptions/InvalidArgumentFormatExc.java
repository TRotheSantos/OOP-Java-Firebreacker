package edu.kit.informatik.data.OwnExceptions;

/**
 * Class for exceptions (only one)
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class InvalidArgumentFormatExc extends Exception {

    /**
     * efficient way to check the format
     *
     * @param errMessage the String error message
     */
    public InvalidArgumentFormatExc(String errMessage) {
        super(errMessage);
    }
}
