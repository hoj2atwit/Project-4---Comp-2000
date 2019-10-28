
public class Hand extends Pile{
	public String toString() {
		String s = "";
		DoubleNode<Card> card = getFirstNode();
		while(card != null) {
			s += card.getData();
			card = card.getNext();
		}
		return s;
	}
}
