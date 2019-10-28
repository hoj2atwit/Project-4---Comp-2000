import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {

	private static ArrayList<Player> players;
	private static Deck deck;
	
	public static void main(String[] args){
		initialize();
		
		play();
	}
	
	//Sets starting players and deck.
	public static void initialize() {
		Scanner in = new Scanner(System.in);
		deck = new Deck();
		players = new ArrayList<Player>();
		
		//Creates a new deck.
		for(Suite s: Suite.values()) {
			for(Rank r: Rank.values()) {
				deck.add(new Card(s,r));
			}
		}
		
		//Sets the player amount.
		System.out.printf("How many players are there?(1-4)%n");
		int playerAmnt = 0;
		//Keeps asking for a correct input until given one.
		while(true) {
			try {
				playerAmnt = in.nextInt();
				while(playerAmnt < 1 || playerAmnt > 4) {
					System.out.printf("I said a number between 1 and 4.%n");
					playerAmnt = in.nextInt();
				}
				break;
			}catch(Exception e) {
				System.out.printf("Try Entering a number ya dumbo.(1-4)%n");
				in.nextLine();
			}
		}
		
		//Absorbs random new line.
		in.nextLine();
		
		//Gets name for each player.
		for(int i = 1; i <= playerAmnt; i++) {
			System.out.printf("Please enter a name for player #%d%n", i);
			players.add(new Player(in.nextLine()));
		}
		
		//Adds the "House", depending on the game.
		//players.add(new Player("House"));
		
	}
	
	//Starts the actual game.
	public static void play() {
		deck.Shuffle();
		System.out.printf("%d%n", deck.getNumCards());
		deck.deal(players, 26);
		System.out.printf("%d%n", deck.getLength());
		System.out.printf("%b%n", deck.isEmpty());
	}
	
	
}
