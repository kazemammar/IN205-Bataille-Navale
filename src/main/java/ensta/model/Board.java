package ensta.model;

import ensta.ship.AbstractShip;
import ensta.ship.ShipState;
import ensta.util.Orientation;

public class Board implements IBoard{


	private String name;
	private ShipState[][] navires;
	private Boolean[][] frappes;

	private static final int DEFAULT_SIZE = 10;


	public String getName() {
		return name;
	}

	public void setName(String aName)
	{
		this.name = aName;
	}

	public ShipState[][] getNavires()
	{
		return navires;
	}


	public void setNavires(int aSize)
	{
		this.navires = new ShipState[aSize][aSize];
	}

	public Boolean[][] getFrappes()
	{
		return frappes;
	}

	public void setFrappes(int aSize)
	{
		this.frappes = new Boolean[aSize][aSize];
	}

	public Board(String aName, int aSize)
	{
		this.name = aName;
		this.navires = new ShipState[aSize][aSize];
		for( int i = 0 ; i < aSize ; i++)
		{
			for ( int j = 0 ; j < aSize ; j++)
			{
				this.navires[j][i] = new ShipState();
			}
		}
		this.frappes = new Boolean[aSize][aSize];
	};

	public Board(String aName)
	{
		this(aName,10);
	};

	public Board() {
	}

	public void print() {
		String letters = "   A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
		String titles =  "                                                      ";
		int size = navires[0].length;
		StringBuilder sb = new StringBuilder();
		sb.append("\nNavires :");
		sb.append(titles.substring(0,2+2*size));
		sb.append("Frappes :\n");
		sb.append(letters.substring(0,2+2*size));
		sb.append("         ");
		sb.append(letters.substring(0,2+2*size));
		sb.append("\n");

		int i,j;

		for (j = 0; j<size; j++)
		{
			sb.append(j+1);
			if (j<9) sb.append(" ");
			for (i=0;i<size;i++)
			{
				sb.append(" ");
				ShipState ship = this.navires[j][i];
				if (ship == null) sb.append(".");
				else sb.append(ship);
			};
			sb.append("         ");
			sb.append(j+1);
			if (j<9) sb.append(" ");
			for (i=0;i<size;i++)
			{
				Boolean bool = this.frappes[j][i];
				if (bool == null) sb.append(" .");
				else if (bool == false) sb.append(" X");
				else if (bool == true) sb.append("\u001b[1;31m X\u001b[0m");
			};
			sb.append("\n");
		};
		System.out.println(sb.toString());
	}

//	public boolean PutShip(AbstractShip ship, int x, int y) {
//		Orientation o = ship.getOrientation();
//		int dx = 0, dy = 0;
//		if (o == Orientation.EAST) {
//			if (x + ship.getLength() >= this.size) {
//				return false;
//			}
//			dx = 1;
//		} else if (o == Orientation.SOUTH) {
//			if (coords.getY() + ship.getLength() >= this.size) {
//				return false;
//			}
//			dy = 1;
//		} else if (o == Orientation.NORTH) {
//			if (coords.getY() + 1 - ship.getLength() < 0) {
//				return false;
//			}
//			dy = -1;
//		} else if (o == Orientation.WEST) {
//			if (coords.getX() + 1 - ship.getLength() < 0) {
//				return false;
//			}
//			dx = -1;
//		}
//
//		Coords iCoords = new Coords(coords);
//
//		for (int i = 0; i < ship.getLength(); ++i) {
//			if (this.hasShip(iCoords)) {
//				return false;
//			}
//			iCoords.setX(iCoords.getX() + dx);
//			iCoords.setY(iCoords.getY() + dy);
//		}
//
//		return true;
//	}

	@Override
	public int getSize()
	{
		return navires.length;
	}

	@Override
	public boolean putShip(AbstractShip ship, int x, int y) {
		return false;
	}

	@Override
	public boolean hasShip(int x, int y)
	{
		return false;
	}

	@Override
	public void setHit(boolean hit, int x, int y) {
		if (hit) frappes[y][x]= true;
		else frappes[y][x] = false;
	}

	@Override
	public Boolean getHit(int x, int y) {
		return frappes[y][x];
	}

	@Override
	public Hit sendHit(int x, int y) {
		Hit res = null;
		if (this.navires[y][x] != null)
		{
			this.navires[y][x].addStrike();
			if (this.navires[y][x].isStruck())
			{
				res = Hit.MISS;
				if (this.navires[y][x].getShip() != null)
				{
					res = Hit.STRIKE;
					if (this.navires[y][x].getShip().isSunk())
					{
						int length = this.navires[y][x].getShip().getLength();
						res = Hit.fromInt(length);
					}
				}
			}
		}
		return res;
	}

	@Override
	public boolean canPutShip(AbstractShip ship, int x, int y) {
		return false;
	}
}
