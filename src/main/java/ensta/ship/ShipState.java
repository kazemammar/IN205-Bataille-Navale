package ensta.ship;

public class ShipState {

    private AbstractShip ship;
    private boolean struck;

    public ShipState(){}

    public ShipState(AbstractShip aShip)
    {
        this.ship = aShip;
        this.struck = false;
    }


    public AbstractShip getShip()
    {
        return this.ship;
    }

    public void addStrike()
    {
        if (struck==false)
        {
            this.struck = true;
            if(this.ship != null) this.ship.addStrike();
        };
    }

    public boolean isStruck()
    {
        return struck;
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        if(this.ship != null)
        {
            if (struck) sb.append("\u001b[1;31m"+this.ship.getLabel()+"\u001b[0m");
            else sb.append(this.ship.getLabel());
        }
        else
        {
            sb.append('.');
        };
        return sb.toString();
    }




}
