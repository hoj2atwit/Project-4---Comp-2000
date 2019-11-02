import java.util.ArrayList;
import java.util.Scanner;

public class CardGame {

	private static ArrayList<Player> players;
	private static Deck deck;
	private static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args){
		initialize(true);
		play();
		
	}
	
	//Sets starting players and deck.
	public static void initialize(boolean newPlayers) {
		deck = new Deck();
		
		//Creates a new deck.
		for(Suite s: Suite.values()) {
			for(Rank r: Rank.values()) {
				deck.add(new Card(s,r));
			}
		}
		
		//Asks for new players if starting a brand new game
		if (!newPlayers) {
			//Resets all current players hands and stand position
			for(Player p: players) {
				p.setHand(new Hand());
				p.setStand(false);
			}
		} else {
			players = new ArrayList<Player>();
			
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
					System.out.printf("Try Entering a number please.(1-4)%n");
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
			System.out.printf("How many AI's would you like to add?(0-4)%n");
			int houseAmnt = 0;
			//Keeps asking for a correct input until given one.
			while(true) {
				try {
					houseAmnt = in.nextInt();
					while(houseAmnt < 0 || houseAmnt > 4) {
						System.out.printf("I said a number between 0 and 4.%n");
						houseAmnt = in.nextInt();
					}
					break;
				}catch(Exception e) {
					System.out.printf("Try Entering a number please.(0-4)%n");
					in.nextLine();
				}
			}
			while(houseAmnt > 0) {
				players.add(new Player(String.format("House%d", houseAmnt)));
				houseAmnt--;
			}
		}
		
		
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
		
		//Shuffles twice to better ansure a good shuffle.
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
	
	//Starts the Black Jack game.
	public static void play() {
		deck.deal(players, 2);
		int standCount = 0;
		while (standCount < players.size()) {
			for(Player p: players) {
				calculateScore(p);
				if(!p.isStand()) {
					if(p.getName().contains("House")) {
						if(!(p.getScore() >= 17)) {
							System.out.printf("%s hits.%n%n", p.getName());
							hit(p);
						}else {
							System.out.printf("%s stands.%n%n", p.getName());
							p.setStand(true);
						}
					}else {
						System.out.printf("%s's current score is %d.%n",p.getName(), p.getScore());
						if(p.getScore() > 21) {
							p.setStand(true);
							System.out.printf("%s has lost.%n%n",p.getName(), p.getScore());
						} else {
							
						
							System.out.printf("%s, would you like to hit or stand?%n", p.getName());
							in.nextLine();
							String input = "";
							do {
								input = in.next();
								if(!input.toLowerCase().equals("hit") && !input.toLowerCase().equals("stand")) {
									System.out.printf("Please enter a valid answer(Either \"hit\" or \"stand\")%n");
								}
							}while(!input.toLowerCase().equals("hit") && !input.toLowerCase().equals("stand"));
						
							if(input.toLowerCase().equals("hit")) {
								System.out.printf("%s hits%n%n", p.getName());
								hit(p);
							} else {
								System.out.printf("%s stands%n%n", p.getName());
								p.setStand(true);
							}
						}
					}
					standCount = 0;
				}else {
					standCount++;
				}
			}
		}
		results();
		in.close();
	}
	
	//Prints the final results of the current game ans asks for retry.
	public static void results() {
		System.out.printf("%nHere are the results!%n");
		Player winner = null;
		for(Player p: players) {
			calculateScore(p);
			if(p.getScore() <= 21) {
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
		retry();
	}
	
	private static void retry() {
		System.out.printf("%nWould you like to play again?(Enter y or n)%n");
		String answer = "";
		
		//Keeps asking if the players would like to play again until they give a correct input.
		do {
			answer = in.next();
			if(!answer.equals("y") && !answer.equals("n")) {
				System.out.printf("Please enter a valid answer.%n");
			}else if(answer.equals("y")){
				System.out.printf("Would you like to play with the same players? (y or n)%n");
				
				//Keeps asking for if the players want to make a new party or not
				do {
					answer = in.next();
					if(!answer.equals("y") && !answer.equals("n")) {
						System.out.printf("Please enter a valid answer.%n");
					} else if(answer.equals("y")){
						initialize(false);
					} else {
						findRoundWinners();
						initialize(true);
					}
				}while(!answer.equals("y") && !answer.equals("n"));
				play();
			}else {
				
				findRoundWinners();
				//End goodbye message to the players.
				System.out.printf("I hope you all had fun!%nSee ya later!%n");
				in.close();
			}

		}while(!answer.equals("y") && !answer.equals("n"));
	}
	
	//Finds and prints whomever has the most wins of all the players.
	private static void findRoundWinners() {
		int winsAmnt = 0;
		for(Player p: players) {
			if (winsAmnt < p.getWins()) {
				winsAmnt = p.getWins();
			}
		}
		
		//Prints winners if anyone won the round.
		if(winsAmnt == 0) {
			System.out.printf("There were no winners :(%n");
		}else {
			String winners = "";
			int winnerAmnt = 0;
			String lastWinner = "";
			//Sets the player's names who won into a single string.
			for(Player p: players) {
				if(p.getWins() == winsAmnt) {
					if(winners.equals(""))
						winners += p.getName();
					else {
						if(!lastWinner.equals("")) {
							winners += ", " + lastWinner;
						}
						
						lastWinner = p.getName();
						
					}
					winnerAmnt++;
				}
			}
			
			//Prints different text depending on if there are multiple winners and if there was only 1 total win.
			if(winnerAmnt > 1) {
				winners += " and " + lastWinner; 
				if(winnerAmnt > 2) {
					if (winsAmnt != 1) {
						System.out.printf("%s all won the rounds, each with a total of %d wins!%n", winners, winsAmnt);
				 	} else {
				 		System.out.printf("%s all won the rounds, each with a total of %d win!%n", winners, winsAmnt);
				 	}
				} else {
					if (winsAmnt != 1) {
						System.out.printf("%s both won the rounds, each with a total of %d wins!%n", winners, winsAmnt);
				 	} else {
				 		System.out.printf("%s both won the rounds, each with a total of %d win!%n", winners, winsAmnt);
				 	}
				}
				
			} else {
				if (winsAmnt != 1) {
					System.out.printf("%s won the rounds with a total of %d wins!%n", winners, winsAmnt);
			 	} else {
			 		System.out.printf("%s won the round with a total of %d win!%n", winners, winsAmnt);
			 	}
			}
		}
	}
	
	
}
