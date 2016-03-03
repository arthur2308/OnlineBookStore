package CSS490;

import java.util.Date;

public class Transaction {

	private int trans_id;
	private int user_id;
	private Date date;
	private double total;
	
	private Cart cart;
	
	public Transaction() {
		trans_id = -1;
		user_id = -1;
		date = null;
		total = -1;
		cart = null;
	}
	
	public Transaction(int user_id, Date date, double total, Cart cart) {
		this.trans_id = -1;
		this.user_id = user_id;
		this.date = date;
		this.total = total;
		this.cart = cart;
	}
	
	public void setTransId(int id) {
		trans_id = id;
	}
	
	public int getTransId() {
		return trans_id; 
	}
	
	public void setUserId(int id) {
		user_id = id;
	}
	
	public int getUserId() {
		return user_id; 
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public Cart getCart() {
		return cart;
	}
}
