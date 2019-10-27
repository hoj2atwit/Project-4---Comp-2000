
public class DoubleNode<T> {
	private Card card;
	private DoubleNode next;
	private DoubleNode prev;
	
	public DoubleNode (Card card) {
		this.card = card;
		next = null;
		prev = null;
	}
	
	public Card getData() {
		return card;
	}
	
	public void setData (Card card) {
		this.card = card;
	}
	
	public DoubleNode getNext() {
		return next;
	}
	
	public DoubleNode getPrev() {
		return prev;
	}
	
	public void setNext(DoubleNode nextNode) {
		next = nextNode;
	}
	
	public void setPrev(DoubleNode prevNode) {
		prev = prevNode;
	}
	
	public DoubleNode copyData() {
		return new DoubleNode(card);
	}
}
