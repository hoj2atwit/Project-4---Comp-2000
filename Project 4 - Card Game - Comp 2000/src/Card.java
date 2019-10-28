
public class Card {
	
	private Suite suite;
	private Rank rank;
	
	public Card(String suite, String rank) {
		this.suite = Suite.valueOf(suite.toUpperCase());
		this.rank = Rank.valueOf(rank.toUpperCase());
	}
	
	public Card(Suite suite, Rank rank) {
		this.suite = suite;
		this.rank = rank;
	}
	
	public Suite getSuite() {
		return suite;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public boolean equals(Card card) {
		if (suite.equals(card.getSuite()) && rank.equals(card.getRank())) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return String.format("%s, %s%n", rank, suite);
	}

}
