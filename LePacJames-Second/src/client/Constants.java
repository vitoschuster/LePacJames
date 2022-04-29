/**
 * Constants - Class that contains all constants
 * 
 * @author V.Schuster
 * @author L.Krpan
 * @version 1604
 */
package client;

public enum Constants {
    /** Window width in pixels */
    W(1120),

    /** Window height in pixels */
    H(700),

    /** Port number of the socket to connect to */
    PORT(5357),

    /** Refresh rate in milliseconds */
    FPS(33),

    /** Number of ghosts in the game */
    GHOST_NUM(4),

    /** Width of the eatables grid */
    GRID_WIDTH(5),

    /** Height of the eatables grid */
    GRID_HEIGHT(5);

    private int numVal;

    /**
     * Constants constructor
     */
    Constants(int numVal) {
        this.numVal = numVal;
    }

    /**
     * Sends int value of constant
     * 
     * @return int value of constant
     */
    public int toInt() {
        return this.numVal;
    }
}
