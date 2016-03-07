package CSS490;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CartDB extends Database{
	
	public static Cart get(int userId) {
		PreparedStatement stmt = null;
		PreparedStatement stmtbook = null;
		PreparedStatement stmtprice = null;
		ResultSet rs = null;
		ResultSet rsbook = null;
		ResultSet rsprice = null;
		Cart cart = new Cart(userId);;
		try{
			if (connect()) {
				String query = "select * from cart where customer_id = ?;";
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, userId);
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
						cart.add(b, rs.getInt("amount"));
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
		return cart;
	}
	
	public static boolean set(Cart cart) {
		PreparedStatement stmtIns = null;
		PreparedStatement stmtUpd = null;
		PreparedStatement stmtSel = null;
		boolean result = false;
		ResultSet rs = null;
		if (cart.getUserId() < 0) {
			return false;
		}
		try{
			if (connect()) {
				ArrayList<CartItem> items = cart.getCart();
				String insert = "insert into cart values (?,?,?)";
				String update = "update cart set amount = ? where customer_id = ? AND book_id = ?";
				String select = "select * from cart where customer_id = ? AND book_id = ?;";
				stmtIns = conn.prepareStatement(insert);
				stmtUpd = conn.prepareStatement(update);
				stmtSel = conn.prepareStatement(select);
				
				for(CartItem i:items){
					int user_id = cart.getUserId();
					int book_id = i.getBook().getProductId();
					int amount = i.getQuantity();
					
					stmtSel.setInt(1, user_id);
					stmtSel.setInt(2, book_id);
					rs = stmtSel.executeQuery();
					
					boolean found = false;
					while (rs.next()) {
						found = true;
					}
					if (found) {
						stmtUpd.setInt(1, amount);
						stmtUpd.setInt(2, user_id);
						stmtUpd.setInt(3, book_id);
						
						stmtUpd.executeUpdate();
					}
					else {
						stmtIns.setInt(1, user_id);
						stmtIns.setInt(2, book_id);
						stmtIns.setInt(3, i.getQuantity());

						stmtIns.executeUpdate();
					}
				}
				result = true;
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmtIns, conn, rs);
			closeAll(stmtUpd, null);
			closeAll(stmtSel, null);
		}
		return result;
	}
	
	public static boolean remove(int user_id, int book_id) {
		boolean result = false;
		PreparedStatement stmt = null;
		try{
			if (connect()) {
				String query = "delete from cart where customer_id = ? AND book_id = ?";
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, user_id);
				stmt.setInt(2, book_id);
				
				int i = stmt.executeUpdate();
				if (i > 0) {
					result = true;
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, null);
		}
		return result;
	}
	
	public static boolean removeAll(int user_id) {
		boolean result = false;
		PreparedStatement stmt = null;
		try{
			if (connect()) {
				String query = "delete from cart where customer_id = ?";
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, user_id);
				
				int i = stmt.executeUpdate();
				if (i > 0) {
					result = true;
				}
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, null);
		}
		return result;
	}
}
