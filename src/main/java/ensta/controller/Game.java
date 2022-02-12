package ensta.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ensta.ai.PlayerAI;
import ensta.model.Board;
import ensta.model.Hit;
import ensta.model.Player;
import ensta.ship.AbstractShip;
import ensta.ship.BattleShip;
import ensta.ship.Carrier;
import ensta.ship.Destroyer;
import ensta.ship.Submarine;
//import ensta.ColorUtil;

public class Game {

	/* ***
	 * Constante
	 */
	public static final File SAVE_FILE = new File("savegame.dat");
	public boolean multijoueur;

	/* ***
	 * Attributs
	 */
	private Player player1;
	private Player player2;
	private Scanner sin;

	/* ***
	 * Constructeurs
	 */
	public Game() {}

	public Game init() {
		if (!loadSave()) {
			// init attributes
			Scanner sin;
			System.out.println("Voulez vous jouer en mode multijoueur ? Repondre par un booléen:");
			try
			{
				sin = new Scanner(System.in);
				multijoueur = sin.nextBoolean();
			}
			catch (Exception e)
			{}

			System.out.println("entre ton nom:");

			// TODO use a scanner to read player name
			String name1 = "Player 1";
			try
			{
				sin = new Scanner(System.in);
				name1 = sin.nextLine();
			}
			catch (Exception e)
			{}

			String name2 = "AI";
			if (multijoueur)
			{
				System.out.println("entre le nom de ton adversaire");

				// TODO use a scanner to read player name
				name2 = "Player 2";
				try
				{
					sin = new Scanner(System.in);
					name2 = sin.nextLine();
				}
				catch (Exception e)
				{}
			}


			// TODO init boards
			Board b1, b2;
			b1 = new Board(name1,10);
			b2 = new Board(name2,10);

			// TODO init this.player1 & this.player2
			ArrayList<AbstractShip> ships1 = new ArrayList();
			ships1.add(new Destroyer());
			ships1.add(new Submarine());
			ships1.add(new Submarine());
			ships1.add(new BattleShip());
			ships1.add(new Carrier());
			player1 = new Player(b1,b2,ships1);

			ArrayList<AbstractShip> ships2 = new ArrayList();
			ships2.add(new Destroyer());
			ships2.add(new Submarine());
			ships2.add(new Submarine());
			ships2.add(new BattleShip());
			ships2.add(new Carrier());
			if (multijoueur) player2 = new Player(b2,b1,ships2);
			else player2 = new PlayerAI(b2,b1,ships2);

			System.out.println(b1.getName());
			b1.print();
			// place player ships
			player1.putShips();

			if (multijoueur)
			{
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println(b2.getName());
				sleep(1000);
				sin = new Scanner(System.in);
				b2.print();
			}
			player2.putShips();
		}
		return this;
	}

	/* ***
	 * Méthodes
	 */
	private static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		int[] coords = new int[2];
		Board b1 = player1.board;
		Board b2 = player2.board;
		Hit hit;

		// main loop
		if (multijoueur)
		{
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			System.out.println(b1.getName());
			sleep(3000); //changement de joueur
			sin = new Scanner(System.in);
		};
		b1.print();
		boolean done;
		do {
			hit = Hit.MISS;
			try
			{ hit = player1.sendHit(coords);}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			boolean strike = hit != Hit.MISS;

			/*
            boolean strike = hit != Hit.MISS; // TODO set this hit on his board (b1)
            b1.setHit(strike, coords[0], coords[1]);
            // setHit est effectué dans sendHit
            */

			done = updateScore();
			b1.print();
			System.out.println(makeHitMessage(false /* outgoing hit */, coords, hit));

			save();

			if (!done && !strike)
			{
				if (multijoueur)
				{
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
					System.out.println(b2.getName());
					sleep(3000); //changement de joueur
					sin = new Scanner(System.in);
					b2.print();
				};
				do
				{
					hit = Hit.MISS; // TODO player2 send a hit.
					try
					{ hit = player2.sendHit(coords);}
					catch (Exception e)
					{ e.printStackTrace();}
					strike = hit != Hit.MISS;

					if (strike)
					{
						b1.print();
					}
					System.out.println(makeHitMessage(true /* incoming hit */, coords, hit));
					done = updateScore();

					if (!done)
					{
						save();
					}
				} while(strike && !done);
			}
			if (multijoueur )
			{
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
				System.out.println(b1.getName());
				sleep(3000); //changement de joueur
				sin = new Scanner(System.in);
				b1.print();
			};

		} while (!done);

		SAVE_FILE.delete();
		System.out.println(String.format("joueur %d gagne", player1.lose ? 2 : 1));
	}


	private void save() {
    	/*
        try {
            // TODO bonus 2 : uncomment
            //  if (!SAVE_FILE.exists()) {
            //      SAVE_FILE.getAbsoluteFile().getParentFile().mkdirs();
            //  }

            // TODO bonus 2 : serialize players

        } catch (IOException e) {
            e.printStackTrace();
        }
        */
	}

	private boolean loadSave() {
		if (SAVE_FILE.exists()) {
        	/*
            try {
                // TODO bonus 2 : deserialize players

                return true;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            */
			return true;
		};
		return false;
	}

	private boolean updateScore() {
		for (Player player : new Player[]{player1, player2}) {
			int destroyed = 0;
			for (AbstractShip ship : player.getShips()) {
				if (ship.isSunk()) {
					destroyed++;
				}
			}

			player.destroyedCount = destroyed;
			player.lose = destroyed == player.getShips().length;
			if (player.lose) {
				return true;
			}
		}
		return false;
	}

	private String makeHitMessage(boolean incoming, int[] coords, Hit hit) {
		String msg = new String();
		switch (hit)
		{
			case MISS:
				msg = hit.toString();
				break;
			case STRIKE:
				msg = "\u001b[1;31m"+hit.toString()+"\u001b[0m";
				break;
			default:
				msg = "\u001b[1;31m"+hit.toString()+"\u001b[0m coulé";
		}
		msg = String.format("%s Frappe en %c%d : %s", incoming ? "<=" : "=>",
				((char) ('A' + coords[0])),
				(coords[1] + 1), msg);
		return msg;
	}

	private static List<AbstractShip> createDefaultShips() {
		return Arrays.asList(new AbstractShip[]{new Destroyer(), new Submarine(), new Submarine(), new BattleShip(), new Carrier()});
	}

	public static void main(String args[]) {
		new Game().init().run();
	}
}
