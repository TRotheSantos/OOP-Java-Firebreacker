package edu.kit.informatik.data;

import java.util.ArrayList;

/**
 * This class is to create objects from the type 'FireFighter', it contains
 * - all needed attributes
 * - the position
 * - methods
 * - a list of the already extinguished fields
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public class FireFighter {
    private int posRow;
    private int posColumn;
    private String name;
    private int waterPoints;
    private int actionPoints;
    private boolean noMove;         // noMove is true, when extinguishing or refilling action has been performed
    private ArrayList<int[]> fieldsAlreadyExtinguishedList;

    /**
     * the constructor to invoke an object of type fire fighter
     *
     * @param name      the name f.e. 'A0'
     * @param posRow    the row position
     * @param posColumn the column position
     */
    public FireFighter(String name, int posRow, int posColumn) {
        this.name = name;
        this.posRow = posRow;
        this.posColumn = posColumn;
        waterPoints = 3;
        actionPoints = 3;
        noMove = false;
        fieldsAlreadyExtinguishedList = new ArrayList<>();
    }

    /**
     * get-method of the name
     *
     * @return the name of fire fighter
     */
    public String getName() {
        return name;
    }

    /**
     * set-method to set the action points
     *
     * @param actionPoints the action points of fire fighter
     */
    public void setActionPoints(int actionPoints) {
        this.actionPoints = actionPoints;
    }

    /**
     * get-method of the name
     *
     * @return the name of fire fighter
     */
    public int getActionPoints() {
        return actionPoints;
    }

    /**
     * get-method of the water points
     *
     * @return the water points of fire fighter
     */
    public int getWaterPoints() {
        return waterPoints;
    }

    /**
     * get-method of the row position
     *
     * @return the row position of fire fighter
     */
    public int getPosRow() {
        return posRow;
    }

    /**
     * set-method to set the row position
     *
     * @param posRow the row positions of fire fighter
     */
    public void setPosRow(int posRow) {
        this.posRow = posRow;
    }

    /**
     * get-method of the column position
     *
     * @return the column position of fire fighter
     */
    public int getPosColumn() {
        return posColumn;
    }

    /**
     * set-method to set the column position
     *
     * @param posColumn the column position of fire fighter
     */
    public void setPosColumn(int posColumn) {
        this.posColumn = posColumn;
    }

    /**
     * set-method to set the water points
     *
     * @param waterPoints the water points of fire fighter
     */
    public void setWaterPoints(int waterPoints) {
        this.waterPoints = waterPoints;
    }

    /**
     * get-method for the move status
     *
     * @return true if no move is allowed
     */
    public boolean isNoMove() {
        return noMove;
    }

    /**
     * set-method for move status
     *
     * @param noMove is true, when extinguishing or refilling action has been performed
     *               false at beginning of each turn
     */
    public void setNoMove(boolean noMove) {
        this.noMove = noMove;
    }

    /**
     * Method to add a new field to the extinguished list
     *
     * @param row    row position
     * @param column column position
     */
    public void addExtinguishedField(int row, int column) {
        int[] extinguishedField = new int[2];
        extinguishedField[0] = row;
        extinguishedField[1] = column;
        fieldsAlreadyExtinguishedList.add(extinguishedField);
    }

    /**
     * Method to clear data of the already extinguished field list
     */
    public void clearFieldsAlreadyExtinguishedList() {
        fieldsAlreadyExtinguishedList.clear();
    }

    /**
     * Method to check if a field was already extinguished by looking through the list
     *
     * @param row    row position
     * @param column column position
     * @return true if this position is in the list, false otherwise
     */
    public boolean hasBeenFieldAlreadyExtinguished(int row, int column) {
        for (int[] field : fieldsAlreadyExtinguishedList) {
            if ((field[0] == row) && (field[1] == column)) {
                return true;
            }
        }
        return false;
    }

}
