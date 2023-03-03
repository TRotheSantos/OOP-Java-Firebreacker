package edu.kit.informatik.application.commands;

import edu.kit.informatik.data.Data;
import edu.kit.informatik.data.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the method for the command fire to roll and various helping methods
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public final class FireToRoll {

    private static String[][] mField;           // auxiliary matrix of same size as match field, needed to avoid
    // double effect on a field (wind from two or more sides)

    // private constructor
    private FireToRoll() { }

    /**
     * This method manages the command 'fire to roll' by using two helping methods
     *
     * @param diceNumber the dice number
     * @return the status of the player
     */
    public static String comFireToRoll(String diceNumber) {
        mField = new String[Data.game.getQtyRows()][Data.game.getQtyColumns()];
        switch (diceNumber) {
            case "1":
                extendFire(-1, 0);     // North
                extendFire(0, 1);      // East
                extendFire(1, 0);      // South
                extendFire(0, -1);     // West
                break;
            case "2":
                extendFire(-1, 0);     // North
                break;
            case "3":
                extendFire(0, 1);      // East
                break;
            case "4":
                extendFire(1, 0);      // South
                break;
            case "5":
                extendFire(0, -1);     // West
                break;
            case "6":
                return "OK";
            default:
        }
        // write back new field status to game match field
        for (int i = 0; i < Data.game.getQtyRows(); i++) {
            for (int j = 0; j < Data.game.getQtyColumns(); j++) {
                if (mField[i][j] != null) {
                    Data.game.getMatchField()[i][j] = mField[i][j];
                }
            }
        }
        return playerStatus();
    }

    /**
     * Method which represents the fire spread by wind
     * <p>
     * depending on dice number:
     *
     * @param row    fire spreading on row
     * @param column fire spreading on column
     */
    private static void extendFire(int row, int column) {
        int startR = 0;
        int startC = 0;
        int endR = Data.game.getQtyRows() - 1;
        int endC = Data.game.getQtyColumns() - 1;
        if (row == -1) {
            for (int i = 0; i <= endC; i++) {
                if (Data.game.getMatchField()[startR][i].equals("+")) {
                    mField[startR][i] = "*";
                }
            }
            startR = 1;                                         //to skip first row in case wind blows to north
        }
        if (row == 1) {
            for (int i = 0; i <= endC; i++) {
                if (Data.game.getMatchField()[endR][i].equals("+")) {
                    mField[endR][i] = "*";
                }
            }
            endR = endR - 1;                                    //to skip last row in case wind blows to south
        }
        if (column == -1) {
            for (int i = 0; i <= endR; i++) {
                if (Data.game.getMatchField()[i][startC].equals("+")) {
                    mField[i][startC] = "*";
                }
            }
            startC = 1;                                         //to skip first column in case wind blows to west
        }
        if (column == 1) {
            for (int i = 0; i <= endR; i++) {
                if (Data.game.getMatchField()[i][endC].equals("+")) {
                    mField[i][endC] = "*";
                }
            }
            endC = endC - 1;                                    //to skip last column in case wind blows to east
        }


        for (int i = startR; i <= endR; i++) {
            for (int j = startC; j <= endC; j++) {
                String forest = Data.game.getMatchField()[i][j];
                switch (forest) {
                    case "+":
                        mField[i][j] = "*";
                        break;
                    case "*":
                        mField[i][j] = "*";
                        String currentTargetSituation = Data.game.getMatchField()[i + row][j + column];
                        if (mField[i + row][j + column] == (null)) {
                            mField[i + row][j + column] = futureTargetStatus(currentTargetSituation);
                        }
                        break;
                    default:
                }
            }
        }
    }

    /**
     * Method which gets how the target field changes (destination of wind)
     *
     * @param target the target field
     * @return the new status
     */
    private static String futureTargetStatus(String target) {
        final ArrayList<String> forestSituation = new ArrayList<>(List.of("w", "d", "+", "*"));
        switch (target) {
            case "A":
            case "B":
            case "C":
            case "D":
            case "L":
                return target;
            case "w":
                return "d";
            case "d":
                return "+";
            case "*":
                return "*";
            default:
        }
        return "";
    }

    /**
     * Method which manages the players status and specific message
     *
     * @return "OK", "lose" or name of next player in order depending on current match situation
     */
    private static String playerStatus() {

        // remove burned fire fighters
        for (int i = 0; i < Data.game.getQtyRows(); i++) {
            for (int j = 0; j < Data.game.getQtyColumns(); j++) {
                if (Data.game.getMatchField()[i][j].equals("*")) {
                    Data.game.removeFireFighters(i, j);
                }
            }
        }

        // any player with no fire fighters?
        ArrayList<Player> playersToRemove = new ArrayList<>();
        for (Player player : Data.game.getPlayerList()) {
            if (player.getFireFighterList().size() == 0) {
                playersToRemove.add(player);
            }
        }
        Data.game.getPlayerList().removeAll(playersToRemove);

        // all Firefighters burned?
        if (Data.game.getFireFighterList().size() == 0) {
            Data.activePlayer = null;
            return "lose";
        }

        String nextPlayer = Data.activePlayer.getName();

        while (Data.activePlayer.getFireFighterList().size() == 0) {
            Data.activePlayer = Data.game.getNextPlayer(Data.activePlayer);
        }
        Data.game.getPlayersOrder().removeAll(playersToRemove);

        if (nextPlayer.equals(Data.activePlayer.getName())) {
            return "OK";
        } else {
            return Data.activePlayer.getName();
        }
    }

}



