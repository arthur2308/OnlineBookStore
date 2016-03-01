package CSS490;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Cart extends Database{
	
	private ArrayList<CartItem> items = new ArrayList<CartItem>();
	
	public Cart() {
		items = new ArrayList<CartItem>();
	}
	
	public void create(int UserId) {
		PreparedStatement stmt = null;
		PreparedStatement stmtbook = null;
		PreparedStatement stmtprice = null;
		ResultSet rs = null;
		ResultSet rsbook = null;
		ResultSet rsprice = null;
		try{
			if (connect()) {
				String query = "select * from cart where customer_id = ?;";
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, UserId);
				rs = stmt.executeQuery();

				String bookquery = "select * from book where product_id = ?";
				String pricequery = "select * from book_inventory where book_id = ?";
				stmtbook = conn.prepareStatement(bookquery);
				stmtprice = conn.prepareStatement(pricequery);
				while(rs.next()){
					stmtbook.setInt(1, rs.getInt("book_id"));
					rsbook = stmtbook.executeQuery();
					
					while (rsbook.next()) {
						Book b = new Book();
						b.setProductId(rsbook.getInt("product_id"));
						b.setTitle(rsbook.getString("title"));
						b.setAuthor(rsbook.getString("author"));
						b.setPublisher(rsbook.getString("publisher"));
						b.setPublishYear(rsbook.getInt("publish_year"));
						b.setCategory(rsbook.getString("category"));

						// gets the price and inventory
						stmtprice.setInt(1, b.getProductId());
						rsprice = stmtprice.executeQuery();

						while(rsprice.next()) {
							b.setPrice(rsprice.getDouble("price"));
							b.setInventory(rsprice.getInt("inven_amount"));
						}
						CartItem temp = new CartItem();
						temp.setBook(b);
						temp.setQuantity(rs.getInt("amount"));
						items.add(temp);
					}
				}
				closeAll(stmtbook, null, rsbook);
				closeAll(stmtprice, null, rsprice);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
	}
	
	public boolean add(Book b) {
		CartItem i = get(b);
		if (i != null) {
			i.increment();
			return true;
		}
		else {
			i = new CartItem();
			i.setBook(b);
			i.setQuantity(1);
			return items.add(i);
		}
	}
	
	public boolean remove(int book_id) {
		CartItem i = get(book_id);
		if (i != null) {
			if (i.getQuantity() > 1) {
				i.decrement();
				return true;
			}
			else {
				return items.remove(i);
			}
		}
		else {
			return false;
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
	
}
