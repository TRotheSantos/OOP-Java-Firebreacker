package edu.kit.informatik.data;

import java.util.ArrayList;


/**
 * This class is the game.
 *
 * @author Tilmann Rothe Santos
 * @version 1.0
 */
public class Game {
    private String[] initArguments;
    private int qtyRows;
    private int qtyColumns;
    private String[][] matchField;
    private ArrayList<Player> playerList;
    private ArrayList<Player> playersOrder;
    private ArrayList<FireFighter> fireFighterList;
    private boolean noTurn;       // after 4 turns, no further turn is possible
    private boolean noSort;       // no sorting, if player is eliminated and round not finished

    /**
     * constructor to create a game
     */
    public Game() {
        fireFighterList = new ArrayList<FireFighter>();
        createPlayerList();
    }

    /**
     * Method to create the player list and another list for the turn oder
     */
    private void createPlayerList() {
        playerList = new ArrayList<Player>();
        playersOrder = new ArrayList<Player>();

        Player player = new Player("A");
        playerList.add(player);
        playersOrder.add(player);

        player = new Player("B");
        playerList.add(player);
        playersOrder.add(player);

        player = new Player("C");
        playerList.add(player);
        playersOrder.add(player);

        player = new Player("D");
        playerList.add(player);
        playersOrder.add(player);
    }

    /**
     * set-method so set the arguments
     *
     * @param initArguments the initializing arguments
     */
    public void setInitArguments(String[] initArguments) {
        this.initArguments = initArguments;
    }

    /**
     * get-method for the arguments
     *
     * @return the initializing arguments
     */
    public String[] getInitArguments() {
        return initArguments;
    }

    /**
     * get-method for the columns
     *
     * @return the quantity of columns
     */
    public int getQtyColumns() {
        return qtyColumns;
    }


    /**
     * get-method for the rows
     *
     * @return the quantity of rows
     */
    public int getQtyRows() {
        return qtyRows;
    }

    /**
     * set-method for the match field
     *
     * @param matchField 2D array
     */
    public void setMatchField(String[][] matchField) {
        this.matchField = matchField;
        qtyRows = matchField.length;
        qtyColumns = matchField[0].length;
    }

    /**
     * get-method for the match field
     *
     * @return match field
     */
    public String[][] getMatchField() {
        return matchField;
    }

    /**
     * get-method for player
     *
     * @param playerName the name of player f.e. 'A'
     * @return player or null if player name is not in the list
     */
    public Player getPlayer(String playerName) {
        for (Player player : playerList) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * get-method for fire fighters of all players
     *
     * @param fireFighterName f.e. 'A0'
     * @return the firefighter or null if the name does not exist
     */
    public FireFighter getFireFighter(String fireFighterName) {
        for (FireFighter fireFighter : fireFighterList) {
            if (fireFighter.getName().equals(fireFighterName)) {
                return fireFighter;
            }
        }
        return null;
    }

    /**
     * Method to add a new fire fighter to the list
     *
     * @param fireFighter the new fire fighter
     */
    public void addFireFighter(FireFighter fireFighter) {
        fireFighterList.add(fireFighter);
    }

    /**
     * Method which removes fire fighters, in case they are burned
     * <p>
     * position:
     *
     * @param row    row position
     * @param column column position
     */
    public void removeFireFighters(int row, int column) {
        ArrayList<FireFighter> fireFightersToRemove = new ArrayList<>();
        for (FireFighter fireFighter : fireFighterList) {
            if ((fireFighter.getPosRow() == row) && (fireFighter.getPosColumn() == column)) {
                fireFightersToRemove.add(fireFighter);
                Player player = getPlayer(String.valueOf(fireFighter.getName().charAt(0)));
                player.getFireFighterList().remove(fireFighter);
            }
        }
        fireFighterList.removeAll(fireFightersToRemove);
    }

    /**
     * get-method of list of fire fighters
     *
     * @return the list of fire fighters
     */
    public ArrayList<FireFighter> getFireFighterList() {
        return fireFighterList;
    }

    /**
     * get-method of list of players
     *
     * @return the list of players
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Method which gets and sets the next player in order
     *
     * @param activePlayer the active player
     * @return the 'new' active player
     */
    public Player getNextPlayer(Player activePlayer) {
        int playerIndex = playersOrder.indexOf(activePlayer);
        if (activePlayer.getFireFighterList().size() == 0) {
            noSort = true;
        }

        if (playerIndex == playersOrder.size() - 1) {
            Player firstPlayerFromLastTurn = playersOrder.get(0);
            if (!noSort) {
                playersOrder.remove(firstPlayerFromLastTurn);               // remove Player from first position
                playersOrder.add(firstPlayerFromLastTurn);                  // and add him at the end
            }
            noTurn = true;
            noSort = false;
            return playersOrder.get(0);
        } else {
            return playersOrder.get(playerIndex + 1);
        }
    }

    /**
     * get-method for the players order list
     *
     * @return the players order list
     */
    public ArrayList<Player> getPlayersOrder() {
        return playersOrder;
    }

    /**
     * get-method for 'is turn possible'
     *
     * @return true if no turn is possible, false otherwise
     */
    public boolean isNoTurn() {
        return noTurn;
    }

    /**
     * set-method for no turn
     *
     * @param noTurn is true if dice needs to be rolled first
     */
    public void setNoTurn(boolean noTurn) {
        this.noTurn = noTurn;
    }
}
