package ensta;

import java.util.ArrayList;

import ensta.ai.BattleShipsAI;
import ensta.model.Board;
import ensta.model.Hit;
import ensta.ship.AbstractShip;
import ensta.ship.Carrier;
import ensta.ship.BattleShip;
import ensta.ship.Destroyer;
import ensta.ship.Submarine;
import ensta.util.Coordinates;

public class TestGame
{
    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args )
    {
        Board board = new Board("AI");
        board.print();
        AbstractShip ships[] = {new Destroyer(), new Submarine(), new Submarine(), new BattleShip(), new Carrier()};
        BattleShipsAI ai = new BattleShipsAI(board,board);
        ai.putShips(ships);
        board.print();

        int compteur = 0;
        Hit hit = null;
        Coordinates coords = new Coordinates();
        char x ;
        int y ;
        do
        {
            try
            { hit = ai.sendHit(coords);}
            catch (Exception e)
            { e.printStackTrace();}
            if (hit != null)
            {
                x = ((char) ('0' +  coords.getX() + 17));
                y = coords.getY() + 1;
                System.out.println("Hit : "+x+y+" "+hit.toString());
                if (hit != Hit.MISS && hit != Hit.STRIKE) compteur ++;
            };
            board.print();
            sleep(100);
        }
        while (compteur < 5);
    }
}
