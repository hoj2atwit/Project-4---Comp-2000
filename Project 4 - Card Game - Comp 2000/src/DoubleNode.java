
public class DoubleNode<T> {
	private Card card;
	private DoubleNode<T> next;
	private DoubleNode<T> prev;
	
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
	
	public DoubleNode<T> getNext() {
		return next;
	}
	
	public DoubleNode<T> getPrev() {
		return prev;
	}
	
	public void setNext(DoubleNode<T> nextNode) {
		next = nextNode;
	}
	
	public void setPrev(DoubleNode<T> prevNode) {
		prev = prevNode;
	}
	
	public DoubleNode<T> copyData() {
		return new DoubleNode<T>(card);
	}
}
