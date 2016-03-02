package CSS490;

public class CartItem {

	private Book book;
	private int quantity;
	
	public CartItem() {
		
	}
	
	public void setBook(Book b) {
		book = b;
	}
	
	public Book getBook() {
		return book;
	}
	
	public void setQuantity(int i) {
		quantity = i;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void increment() {
		quantity++;
	}
	
	public void decrement() {
		quantity--;
	}
	
	public boolean equals(Book b) {
		return book.equals(b);
	}
}
