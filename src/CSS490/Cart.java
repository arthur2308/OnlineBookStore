package CSS490;

import java.util.ArrayList;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

public class Cart {
	
	private int userId;
	private ArrayList<CartItem> items = new ArrayList<CartItem>();
	private int size;
	
	public Cart() {
		userId = -1;
		items = new ArrayList<CartItem>();
	}
	
	public Cart(int id) {
		userId = id;
		items = new ArrayList<CartItem>();
	}
	
	public void setUserId(int id) {
		userId = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean add(Book b) {
		CartItem i = get(b);
		if (i != null) {
			i.increment();
			size++;
			return true;
		}
		else {
			i = new CartItem();
			i.setBook(b);
			i.setQuantity(1);
			if (items.add(i)) {
				size++;
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean add(Book b, int quant) {
		CartItem i = get(b);
		if (i != null) {
			i.increment();
			size++;
			return true;
		}
		else {
			i = new CartItem();
			i.setBook(b);
			i.setQuantity(quant);
			if (items.add(i)) {
				size+= quant;
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean remove(int book_id) {
		CartItem i = get(book_id);
		if (i != null) {
			if (items.remove(i)) {
				size -= i.getQuantity();
				return true;
			} else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	public boolean modify(Book b, int quantity) {
		CartItem i = get(b);
		if (quantity == 0) {
			return remove(b.getProductId());
		}
		else {
			if (i != null) {
				int diff = quantity - i.getQuantity();
				i.setQuantity(quantity);
				size += diff;
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	private CartItem get(Book b) {
		CartItem result = null;
		for (CartItem i:items) {
			if (i.equals(b)) {
				result = i;
			}
		}
		return result;
	}
	
	private CartItem get(int book_id) {
		CartItem result = null;
		for (CartItem i:items) {
			if (i.getBook().getProductId() == book_id) {
				result = i;
			}
		}
		return result;
	}
	
	public ArrayList<CartItem> getCart() {
		return items;
	}
	
	public double getTotal() {
		double total = 0;
		for (CartItem i:items) {
			total += i.getBook().getPrice() * i.getQuantity();
		}
		return total;
	}
	
	public void clear() {
		items.clear();
		size = 0;
	}
}
