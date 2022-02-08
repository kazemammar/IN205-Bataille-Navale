package ensta.ship;

import ensta.util.Orientation;

public class Submarine extends AbstractShip {
    public Submarine()
    {
        super('D',"Submarine",3, Orientation.EAST);
    }

    public Submarine(Orientation anOrientation)
    {
        super('D',"Submarine",3,anOrientation);
    }
}
