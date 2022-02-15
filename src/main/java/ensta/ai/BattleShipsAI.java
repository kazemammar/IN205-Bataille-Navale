package ensta.ai;
import java.io.Serializable;
import java.util.Random;

import ensta.model.Hit;
import ensta.model.IBoard;
import ensta.ship.AbstractShip;
import ensta.util.Orientation;
import ensta.util.Coordinates;

public class BattleShipsAI implements Serializable {

    /* **
     * Attributs
     */

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * grid size.
     */
    private final int size;

    /**
     * My board. My ships have to be put on this one.
     */
    private final IBoard board;

    /**
     * My opponent's board. My hits go on this one to strike his ships.
     */
    private final IBoard opponent;

    /**
     * Coords of last known strike. Would be a good idea to target next hits around this point.
     */
    private Coordinates lastStrike;

    /**
     * If last known strike lead me to think the underlying ship has vertical placement.
     */
    private Boolean lastVertical;

    /* **
     * Constructeur
     */

    /**
     *
     * @param myBoard board where ships will be put.
     * @param opponentBoard Opponent's board, where hits will be sent.
     */
    public BattleShipsAI(IBoard myBoard, IBoard opponentBoard) {
        this.board = myBoard;
        this.opponent = opponentBoard;
        size = board.getSize();
    }

    /* **
     * Méthodes publiques
     */

    /**
     * Put the ships on owned board.
     * @param ships the ships to put
     */
    public void putShips(AbstractShip ships[]) {
        int x, y;
        Orientation o;
        Random rnd = new Random();
        Orientation[] orientations = Orientation.values();

        for (AbstractShip s : ships) {
            do {
                // TODO use Random to pick a random x, y & orientation
                x = rnd.nextInt(10);
                y = rnd.nextInt(10);
                o = orientations[rnd.nextInt(4)];
                s.setOrientation(o);
            } while(!canPutShip(s, x, y));
            try
            { board.putShip(s, x, y); }
            catch (Exception e)
            { e.printStackTrace(); };
        }
    }

    /**
     *
     * @param coords array must be of size 2. Will hold the coord of the send hit.
     * @return the status of the hit.
     */
    public Hit sendHit(Coordinates coords) {
        Coordinates res = null;
        if (coords == null) {
            throw new IllegalArgumentException("must provide an initialized array of size 2");
        }

        // already found strike & orientation?
        if (lastVertical != null) {
            if (lastVertical) {
                res = pickVCoord();
            } else {
                res = pickHCoord();
            }

            if (res == null) {
                // no suitable coords found... forget last strike.
                lastStrike = null;
                lastVertical = null;
            }
        } else if (lastStrike != null) {
            // if already found a strike, without orientation
            // try to guess orientation
            res = pickVCoord();
            if (res == null) {
                res = pickHCoord();
            }
            if (res == null) {
                // no suitable coords found... forget last strike.
                lastStrike = null;
            }
        }

        if (lastStrike == null) {
            res = pickRandomCoord();
        }

        Hit hit = opponent.sendHit(res);
        board.setHit(hit != Hit.MISS, res);

        if (hit != Hit.MISS) {
            if (lastStrike != null) {
                lastVertical = guessOrientation(lastStrike.getX(), res.getX());
            }
            lastStrike = res;
        }

        coords.setX(res.getX());
        coords.setY(res.getY());

        return hit;
    }

    /* ***
     * Méthodes privées
     */

    private boolean canPutShip(AbstractShip ship, int x, int y) {
        Orientation o = ship.getOrientation();
        int dx = 0, dy = 0;
        if (o == Orientation.EAST) {
            if (x + ship.getLength() >= this.size) {
                return false;
            }
            dx = 1;
        } else if (o == Orientation.SOUTH) {
            if (y + ship.getLength() >= this.size) {
                return false;
            }
            dy = 1;
        } else if (o == Orientation.NORTH) {
            if (y + 1 - ship.getLength() < 0) {
                return false;
            }
            dy = -1;
        } else if (o == Orientation.WEST) {
            if (x + 1 - ship.getLength() < 0) {
                return false;
            }
            dx = -1;
        }

        int ix = x;
        int iy = y;

        for (int i = 0; i < ship.getLength(); ++i) {
            if (board.hasShip(ix, iy)) {
                return false;
            }
            ix += dx;
            iy += dy;
        }

        return true;
    }

    private boolean guessOrientation(int c1, int c2) {
        return c1 == c2;
    }

    private boolean isUndiscovered(Coordinates coords) {
        int x = coords.getX();
        int y= coords.getY();
        return x >= 0 && x < size && y >= 0 && y < size && board.getHit(coords) == null;
    }

    private Coordinates pickRandomCoord() {
        Random rnd = new Random();
        int x=0;
        int y = 0;

        Coordinates coords = new Coordinates(x, y);
        do {
            x = rnd.nextInt(size);
            y = rnd.nextInt(size);
            coords.setX(x);
            coords.setY(y);
        } while (!isUndiscovered(coords));

        return coords;
    }

    /**
     * pick a coord verically around last known strike
     * @return suitable coord, or null if none is suitable
     */
    private Coordinates pickVCoord() {
        int x = lastStrike.getX();
        int y = lastStrike.getY();

        for (int iy : new int[] { y - 1, y + 1 }) {
            Coordinates coords = new Coordinates(x, iy);
            if (isUndiscovered(coords)) {
                return coords;
            }
        }
        return null;
    }

    /**
     * pick a coord horizontally around last known strike
     * @return suitable coord, or null if none is suitable
     */
    private Coordinates pickHCoord() {
        int x = lastStrike.getX();
        int y = lastStrike.getY();

        for (int ix : new int[] { x - 1, x + 1 }) {
            Coordinates coords = new Coordinates(ix, y);
            if (isUndiscovered(coords)) {
                return coords;
            }
        }
        return null;
    }
}
