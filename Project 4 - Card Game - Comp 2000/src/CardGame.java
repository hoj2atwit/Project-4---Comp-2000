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
		players.add(new Player("House"));
		
		/*
		 * Used to test if aces are being calculated properly.
		 * System.out.printf("%nAce Test?(Enter y or n)%n");
		String answer = "";
		do {
			answer = in.next();
			if(!answer.equals("y") && !answer.equals("n")) {
				System.out.printf("Please enter a valid answer.%n");
			}else if(answer.equals("y")){
				for(Player p: players) {
					for(Suite s: Suite.values()) {
						if(deck.remove(new Card(s, Rank.ACE))) {
							p.getHand().add(new Card(s, Rank.ACE));
							break;
						}
					}
				}
			}
		}while(!answer.equals("y") && !answer.equals("n"));
		 */
		
		
		deck.Shuffle();
		deck.Shuffle();
		
	}
	
	public static void hit(Player player) {
		player.draw(deck);
	}
	
	public static void calculateScore(Player player) {
		int score = 0;
		ArrayList<Card> aces = new ArrayList<Card>();
		for(Card c: player.getHand().toArray()) {
			if(c.getRank() == Rank.ACE) {
				aces.add(c);
			}else {
				score += c.getRank().getValue(false);
			}
		}
		
		for(Card c: aces) {
			score += c.getRank().getValue((score + 11 <= 21));
		}
		player.setScore(score);
	}
	
	//Starts the actual game.
	public static void play() {
		Scanner in = new Scanner(System.in);
		deck.deal(players, 2);
		int standCount = 0;
		while (standCount < players.size()) {
			for(Player p: players) {
				calculateScore(p);
				if(!p.isStand()) {
					if(p.getName().equals("House")) {
						if(!(p.getScore() >= 17)) {
							System.out.printf("The House hits.%n%n");
							hit(p);
						}else {
							System.out.printf("The House stands.%n%n");
							p.setStand(true);
						}
					}else {
						System.out.printf("%s, would you like to hit or stand?%n", p.getName());
						System.out.printf("Your current score is %d%n", p.getScore());
						String input = "";
						do {
							input = in.nextLine();
							if(!input.toLowerCase().equals("hit") && !input.toLowerCase().equals("stand")) {
								System.out.printf("Please enter a valid answer(Either \"hit\" or \"stand\")%n");
							}
						}while(!input.toLowerCase().equals("hit") && !input.toLowerCase().equals("stand"));
						
							if(input.toLowerCase().equals("hit")) {
								System.out.printf("%s hits%n%n", p.getName());
								hit(p);
							}else {
								System.out.printf("%s stands%n%n", p.getName());
								p.setStand(true);
							}
					}
					standCount = 0;
				}else {
					standCount++;
				}
			}
		}
		results();
	}
	
	public static void results() {
		Scanner in = new Scanner(System.in);
		System.out.printf("%nHere are the results!%n");
		Player winner = null;
		for(Player p: players) {
			calculateScore(p);
			if(p.getScore() < 21) {
				if(winner == null || winner.getScore() < p.getScore()) {
					winner = p;
				}
			}
		}
		System.out.printf("The winner(s) are...%n");
		if(winner == null) {
			System.out.printf("There were no winners :(%n");
		}else {
			for(Player p: players) {
				if(p.getScore() == winner.getScore()) {
					p.setWins(p.getWins() + 1);
					System.out.printf("%n%s%nWith a hand of:%n%sWith a total score of: %d%n", p.getName(), p.getHand(), p.getScore());
				}
			}
		}
		System.out.printf("%nWould you like to play again?(Enter y or n)%n");
		String answer = "";
		do {
			answer = in.next();
			if(!answer.equals("y") && !answer.equals("n")) {
				System.out.printf("Please enter a valid answer.%n");
			}else if(answer.equals("y")){
				initialize();
				play();
			}else {
				System.out.printf("Ok Goodbye!%n");
			}

		}while(!answer.equals("y") && !answer.equals("n"));
		in.close();
	}
	
	
}
