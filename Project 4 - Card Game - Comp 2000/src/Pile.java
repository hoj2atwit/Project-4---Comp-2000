import java.rmi.NoSuchObjectException;
import java.util.Random;

public class Pile implements CardListInterface{
	
	private DoubleNode firstNode;
	private DoubleNode lastNode;
	private int numCards;
	private Random random = new Random();
	
	public Pile() {
		firstNode = null;
		lastNode = null;
		numCards = 0;
	}
	
	public void Shuffle() {
		for (int i = 0; i < numCards * 2; i++) {
			add(remove(random.nextInt(numCards)));
		}
	}
	
	/**
	 * Returns card at index location
	 * @param position
	 * @return
	 */
	public Card search(int position) {
		if (position < 0 || position >= numCards) {
			throw new IndexOutOfBoundsException();
		}
		DoubleNode temp = firstNode;
		for (int i = 0; i < position; i++) {
			temp = temp.getNext();
		}
		return temp.getData();
	}
	
	/**
	 * Returns index place of specific card; if -1 returned, card not found
	 * @param card
	 * @return
	 */
	public int search(Card card) {
		int place = -1;
		DoubleNode temp = firstNode;
		for (int i = 0; i < numCards; i++) {
			if (temp.getData().equals(card)) {
				place = i;
				break;
			}
			temp = temp.getNext();
		}
		return place;
	}
	
	/**
	 * Returns another pile that contains the (secondPileSize) amount of cards from the front of the pile
	 * Throws outofboundsexception if secondPileSize > numCards
	 * @param secondPileSize
	 * @return
	 */
	public Pile split(int secondPileSize) {
		if (secondPileSize > numCards) {
			throw new ArrayIndexOutOfBoundsException();
		}// else if (secondPileSize == 1) {
		//	Pile newPile = new Pile();
		//	DoubleNode temp = firstNode;
		//	firstNode = temp.getNext();
		//	firstNode.setPrev(null);
		//	temp.setNext(null);
		//	newPile.add(temp.getData());
		//	return newPile;
		//} else {
			DoubleNode temp = firstNode;
			Pile newPile = new Pile();
			for (int i = 0; i < secondPileSize; i++) {
				newPile.addLast(temp.getData());
				temp = temp.getNext();
			}
			firstNode = temp;
			firstNode.setPrev(null);
			numCards = numCards - secondPileSize;
			return newPile;
		//}
	}
	
	/**
	 * Splits pile in half
	 * @return
	 */
	public Pile split() {
		return split(numCards/2);
	}
	
	public void group(Group group) {
		switch(group) {
			case RANK:
				
			case SUIT:
		}
	}

	/**
	 * Adds card to the front of the pile
	 * Does not allow adding if duplicate card exists in pile
	 */
	@Override
	public void add(Card card) {
		DoubleNode toPlace = new DoubleNode(card);
		if (!contains(card)) {
			if (isEmpty()) {
				firstNode = toPlace;
				lastNode = firstNode;
				firstNode.setPrev(null);
				lastNode.setNext(null);
			} else {
				firstNode.setPrev(toPlace);
				toPlace.setNext(firstNode);
				firstNode = toPlace;
			}
			numCards++;
		} else {
			System.out.println("Card already exists in pile! ADD(Card card) error");
			System.exit(0);
		}
		
	}
	
	/**
	 * Adds card to the end of the pile
	 * Does not allow adding if duplicate card exists in pile
	 */
	@Override
	public void addLast(Card card) {
		DoubleNode toPlace = new DoubleNode(card);
		if (!contains(card)) {
			if (isEmpty()) {
				firstNode = toPlace;
				lastNode = firstNode;
				firstNode.setPrev(null);
				lastNode.setNext(null);
			} else {
				lastNode.setNext(toPlace);
				toPlace.setPrev(lastNode);
				lastNode = toPlace;
			}
			numCards++;
		} else {
			System.out.println("Card already exists in pile! ADDLAST(Card card) error");
			System.exit(0);
		}
	}

	/**
	 * Finds target card, removes it from pile. Returns true if removed, false if not detected at all
	 */
	@Override
	public boolean remove(Card card) {
		for (DoubleNode temp = firstNode; temp != null; temp = temp.getNext()) {
			if (temp.getData().equals(card)) {
				temp.getPrev().setNext(temp.getNext());
				temp.getNext().setPrev(temp.getPrev());
				numCards--;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes card at specific position
	 * @param position
	 * @return
	 */
	public Card remove(int position) {
		if (position < 0 || position >= numCards) {
			throw new ArrayIndexOutOfBoundsException();
		}
		DoubleNode toRemove = firstNode;
		for (int i = 0; i < position; i++) {
			toRemove = toRemove.getNext();
		}
		if (toRemove.getNext() == null || toRemove.getPrev() == null) {
			if (toRemove.getNext() == null) {
				lastNode = toRemove.getPrev();
				lastNode.setNext(null);
			}
			if (toRemove.getPrev() == null) {
				firstNode = toRemove.getNext();
				firstNode.setPrev(null);
			}
		} else {
			toRemove.getPrev().setNext(toRemove.getNext());
			toRemove.getNext().setPrev(toRemove.getPrev());
		}
		numCards--;
		return toRemove.getData();
	}

	/**
	 * Removes card from front of the pile
	 * @param card
	 * @return
	 */
	public Card removeFirst() {
		if (!isEmpty()) {
			DoubleNode temp = firstNode;
			firstNode = firstNode.getNext();
			firstNode.setPrev(null);
			numCards--;
			return temp.getData();
		} else {
			System.out.println("Can't remove-- Pile is empty!");
			return null;
		}
	}
	
	/**
	 * Removes card from end of the pile
	 */
	@Override
	public Card removeLast() {
		if (!isEmpty()) {
			DoubleNode temp = lastNode;
			lastNode = lastNode.getPrev();
			lastNode.setNext(null);
			numCards--;
			return temp.getData();
		} else {
			System.out.println("Can't remove-- Pile is empty!");
			return null;
		}
	}

	/**
	 * Returns true if pile contains cards; false otherwise
	 */
	@Override
	public boolean contains(Card card) {
		for (DoubleNode temp = firstNode; temp != null; temp = temp.getNext()) {
			if (temp.getData().equals(card)) {
				return true;
			}
		}
		return false;
	}
	

	@Override
	public int getLength() {
		return numCards;
	}
	
	public boolean isEmpty() {
		return numCards == 0;
	}

	public Card[] toArray() {
		Card[] temp = new Card[numCards];
		DoubleNode tempNode = firstNode;
		for (int i = 0; i < numCards; i++) {
			temp[i] = tempNode.getData();
			tempNode = tempNode.getNext();
		}
		return temp;
	}
	
	public void clear() {
		firstNode = null;
		lastNode = null;
		numCards = 0;
	}
	
	/**
	 * Adds a pile to the end of this pile
	 * Ex: Pile 1 = {1,2,3} Pile 2 = {1,4,5}
	 * Union pile = {1,2,3,4,5}, duplicate 1 discarded
	 * @param pile
	 */
	public void union(Pile pile) {
		Card[] deck = pile.toArray();
		for (int i = 0; i < deck.length; i++) {
			if (!contains(deck[i])) {
				addLast(deck[i]);
			}
		}
	}
	
	/**
	 * Currently unimplemented
	 */
	public void fillDeck() {
		
	}
}
