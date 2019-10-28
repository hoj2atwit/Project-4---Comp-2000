
public class Player {
	private String name;
	private Hand hand;
	private int score;
	private boolean stand;
	private int wins;
	
	Player(String name){
		this.name = name;
		this.hand = new Hand();
		this.score = 0;
		this.wins = 0;
		this.stand = false;
	}
	
	public void draw(Deck deck) {
		if(!deck.isEmpty()) {
			hand.add(deck.removeFirst());
		}
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Hand getHand() {
		return hand;
	}
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public boolean isStand() {
		return stand;
	}

	public void setStand(boolean stand) {
		this.stand = stand;
	}
	
}
