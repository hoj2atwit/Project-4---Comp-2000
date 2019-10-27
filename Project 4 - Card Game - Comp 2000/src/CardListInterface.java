
public interface CardListInterface {
	
	public void add(Card card);
	
	public void addLast(Card card);
	
	public boolean remove(Card card);
	
	public Card removeLast();
	
	public boolean contains(Card card);
	
	public int getLength();
	
}
