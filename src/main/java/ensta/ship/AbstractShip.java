package ensta.ship;


import ensta.util.Orientation;

public abstract class AbstractShip {

    protected char label;
    protected String name;
    protected int size;
    protected Orientation orientation;

    public AbstractShip() {
        this.label = '.';
        this.name = "";
        this.size = 0;
        this.orientation = Orientation.EAST;
    }

    public AbstractShip(char aLabel, String aName, int aSize, Orientation anOrientation) {
        this.label = aLabel;
        this.name = aName;
        this.size = aSize;
        this.orientation = anOrientation;
    }


    public char getLabel()
    {
        return label;
    }
    public String getName()
    {
        return name;
    }
    public int getLength()
    {
        return size;
    }
    public Orientation getOrientation()
    {
        return orientation;
    }

    public void setOrientation(Orientation anOrientation)
    {
        this.orientation = anOrientation;
    }


}
