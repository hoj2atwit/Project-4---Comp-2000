
public class Player {
	private String name;
	private Hand hand;
	private int score;
	
	Player(String name){
		this.name = name;
		this.hand = new Hand();
		this.score = 0;
	}
	
	public void draw(Deck deck) {
		hand.add(deck.removeFirst());
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
	
}
