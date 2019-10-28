import java.util.ArrayList;

public class Deck extends Pile{
	
	public void deal(ArrayList<Player> players, int cardAmnt) {
		if(!isEmpty() && getNumCards() >= (cardAmnt * players.size())) {
			for(int i = 1; i <= cardAmnt; i++) {
				for(Player player: players) {
					player.draw(this);
				}
			}
			
		}else {
			System.out.printf("Not Enough Cards to deal to everyone.%n");
		}
	}
	
	public String toString() {
		String s = "";
		for(Card c: toArray()) {
			s += c;
		}
		return s;

	}
	
	
}
