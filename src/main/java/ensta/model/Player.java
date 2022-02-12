package ensta.model;

import java.io.Serializable;
import java.util.List;

import ensta.ship.AbstractShip;
import ensta.util.Orientation;
import ensta.view.InputHelper;

public class Player {
	/*
	 * ** Attributs
	 */
	private Board board;
	protected Board opponentBoard;
	private int destroyedCount;
	protected AbstractShip[] ships;
	private boolean lose;
    private String stringOrientation;

    /*
	 * ** Constructeur
	 */
	public Player(Board board, Board opponentBoard, List<AbstractShip> ships) {
		this.setBoard(board);
		this.ships = ships.toArray(new AbstractShip[0]);
		this.opponentBoard = opponentBoard;
	}

	/*
	 * ** Méthodes
	 */

	/**
	 * Read keyboard input to get ships coordinates. Place ships on given
	 * coodrinates.
	 */
	public void putShips() {
		boolean done = false;
		int i = 0;

		do {
			AbstractShip ship = ships[i];
			String msg = String.format("placer %d : %s(%d)", i + 1, ship.getName(), ship.getLength());
			System.out.println(msg);
			InputHelper.ShipInput res = InputHelper.readShipInput();
			// TODO set ship orientation
            String stringOrientation = new String();
            if (res != null) stringOrientation = res.orientation;
            switch(stringOrientation)
            {
                case "n":
                    ship.setOrientation(Orientation.NORTH);
                    break;
                case "s":
                    ship.setOrientation(Orientation.SOUTH);
                    break;
                case "e":
                    ship.setOrientation(Orientation.EAST);
                    break;
                case "w":
                    ship.setOrientation(Orientation.WEST);
                    break;
            }
			// TODO put ship at given position
            try
            { board.putShip(ship, res.x, res.y); }
            catch (Exception e)
            {
                System.out.println("Réessayez avec des valeurs correctes !");
            }
			// TODO when ship placement successful
            boolean success = false;
            success = board.hasShip(res.x, res.y);
            if (success) board.print();
            else i--;

			++i;
			done = i == 5;

			board.print();
		} while (!done);
	}

	public Hit sendHit(int[] coords) {
		boolean done;
		Hit hit = null;
		do {
			System.out.println("où frapper?");
			InputHelper.CoordInput hitInput = InputHelper.readCoordInput();
			done = false;
			if (opponentBoard != null)
			{
				int x = hitInput.x;
				int y = hitInput.y;
				boolean in = 0 <= x && x < opponentBoard.getSize() && 0 <= y && y < opponentBoard.getSize();
				done = board.getHit(x,y) == null && in ;
				if (done)
				{
					// TODO call sendHit on this.opponentBoard
					hit = opponentBoard.sendHit(x,y);
					board.setHit(hit != Hit.MISS, x, y);
					if (hit != Hit.MISS && hit != Hit.STRIKE)
					{
						System.out.println(hit.toString() + " coulé");
					}

					// TODO : Game expects sendHit to return BOTH hit result & hit coords.
					// return hit is obvious. But how to return coords at the same time ?
					coords[0] = x;
					coords[1] = y;
				}
				else
				{
					System.out.println("Réessayez avec des valeurs correctes !");
				};
			};
		} while (!done);

		return hit;
	}

	public AbstractShip[] getShips() {
		return ships;
	}

	public void setShips(AbstractShip[] ships) {
		this.ships = ships;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getDestroyedCount() {
		return destroyedCount;
	}

	public void setDestroyedCount(int destroyedCount) {
		this.destroyedCount = destroyedCount;
	}

	public boolean isLose() {
		return lose;
	}

	public void setLose(boolean lose) {
		this.lose = lose;
	}
}
