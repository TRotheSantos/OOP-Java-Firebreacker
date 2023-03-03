package edu.kit.informatik.data;

import java.util.ArrayList;

/**
 * This class is to create objects from the type 'FireFighter', it contains
 * - all needed attributes
 * - methods
 * - a list of the owned fire fighters
 * - index of added fire fighters, for better handling
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public class Player {
    private final String name;
    private int reputation;
    private ArrayList<FireFighter> fireFighterList;
    private int indexNewFireFighter;

    /**
     * the constructor to invoke an object of type player
     *
     * @param name f.e. 'A'
     */
    public Player(String name) {
        this.name = name;
        reputation = 0;
        fireFighterList = new ArrayList<FireFighter>();
        indexNewFireFighter = 0;
    }

    /**
     * get-method of the name
     *
     * @return the name of player
     */
    public String getName() {
        return name;
    }

    /**
     * This method adds a fire fighter to the game
     * <p>
     * position:
     *
     * @param posRow    row position
     * @param posColumn column position
     */
    public void addFireFighter(int posRow, int posColumn) {
        FireFighter fireFighter = new FireFighter(name + indexNewFireFighter, posRow, posColumn);
        fireFighterList.add(fireFighter);
        indexNewFireFighter = indexNewFireFighter + 1;
        Data.game.addFireFighter(fireFighter);
    }

    /**
     * get-method of the fire fighter list
     *
     * @return the fire fighter list
     */
    public ArrayList<FireFighter> getFireFighterList() {
        return fireFighterList;
    }

    /**
     * get-method of the reputation
     *
     * @return the reputation points of the player
     */
    public int getReputation() {
        return reputation;
    }

    /**
     * set-method of the reputation points
     *
     * @param modifier to modify the existing reputation points
     */
    public void changeReputation(int modifier) {
        reputation = reputation + modifier;
    }

    /**
     * Method to check which fire fighters are on specific position
     *
     * @param row    row position
     * @param column column position
     * @return the fire fighters on position
     */
    public String fireFightersOnField(int row, int column) {
        String fireFightersOnField = "";
        for (FireFighter fireFighter : fireFighterList) {
            if ((fireFighter.getPosRow() == row) && (fireFighter.getPosColumn() == column)) {
                fireFightersOnField = fireFightersOnField + "," + fireFighter.getName();
            }
        }
        return fireFightersOnField;
    }
}
