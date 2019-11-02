public enum Suite{
	SPADES, CLUBS, DIAMONDS, HEARTS;
	public String toString() {
		switch(this) {
			case SPADES:
				return "Spades";
			case CLUBS:
				return "Clubs";
			case DIAMONDS:
				return "Diamonds";
			case HEARTS:
				return "Hearts";
			default:
				return "";
		}
	}
}
		
