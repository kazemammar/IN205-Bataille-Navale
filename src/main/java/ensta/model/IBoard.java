package ensta.model;

import ensta.ship.AbstractShip;
import ensta.util.Coordinates;

public interface IBoard {

    /**
     * Get the size of the grids contained in the Board
     * @return the size of the grids contained in the Board
     */
    public int getSize();

    /**
    * Put the given ship at the given position
    * @param aShip The ship to place on the board
    * @param x
     * @param y
     * @return true if the ship is put on the board
    */
    void putShip(ensta.ship.AbstractShip aShip, int x, int y) throws Exception;

    /**
     * Get if a ship is placed at the given position
     * @param x
     * @param y
     * @return true if a ship is located at the given position
     */
    public boolean hasShip(int x, int y);

    /**
     * Set the state of the hit at a given position
     * @param hit true if the hit must be set to successful
     * @param coords
     */
    public void setHit(boolean hit, Coordinates coords);

    /**
     * Get the state of a hit at the given position
     * @param coords
     * @return true if the hit is successful
     */
    public Boolean getHit(Coordinates coords);

    /**
     * Sends a hit at the given position
     * @param coords
     * @return status for the hit (eg : strike or miss)
     */

    public Hit sendHit(Coordinates coords);
}
