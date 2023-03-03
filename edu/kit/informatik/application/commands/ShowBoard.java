package edu.kit.informatik.application.commands;

import edu.kit.informatik.data.Data;


/**
 * This class contains the method for the command show board
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class ShowBoard {

    // private constructor
    private ShowBoard() {
    }

    /**
     * This method returns the current match field in form of String array
     *
     * @return the current match field in form
     */
    public static String[] comShowBoard() {
        int qtyRows = Data.game.getQtyRows();
        int qtyColumns = Data.game.getQtyColumns();
        String[] comShowBoard = new String[qtyRows];

        for (int row = 0; row < qtyRows; row++) {
            comShowBoard[row] = "";
            for (int column = 0; column < qtyColumns; column++) {
                if (Data.game.getMatchField()[row][column].equals("*")
                        || Data.game.getMatchField()[row][column].equals("+")) {
                    comShowBoard[row] = comShowBoard[row] + "," + Data.game.getMatchField()[row][column];
                } else {
                    comShowBoard[row] = comShowBoard[row] + "," + "x";
                }
            }
            comShowBoard[row] = comShowBoard[row].substring(1);
        }
        return comShowBoard;
    }
}
