package CSS490;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class BookDB extends Database{

	public static Book getBook(int productId) {
		Book book = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			if (connect()) {
				String query = "select * from book where product_id = ?";
				stmt = conn.prepareStatement(query);

				stmt.setInt(1, productId);
				rs = stmt.executeQuery();

				while(rs.next()){
					book = new Book();
					book.setProductId(productId);
					book.setTitle(rs.getString("title"));
					book.setAuthor(rs.getString("author"));
					book.setPublisher(rs.getString("publisher"));
					book.setPublishYear(rs.getInt("publish_year"));
					book.setCategory(rs.getString("category"));
				}
				
				closeAll(stmt, null, rs);
				
				query = "select * from book_inventory where book_id = ?";
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, productId);
				rs = stmt.executeQuery();
				
				while(rs.next()) {
					book.setPrice(rs.getDouble("price"));
					book.setInventory(rs.getInt("inven_amount"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return book;
	}
	
	public static int modifyUser(Book book){
		int flag = 0;
		PreparedStatement stmt = null;
		try{
			if (connect()) {
				String query = "update book set title = ?, author = ?, publisher = ?, publish_year = ?, category = ? where product_id = ?";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, book.getTitle());
				stmt.setString(2, book.getAuthor());
				stmt.setString(3, book.getPublisher());
				stmt.setInt(4, book.getPublishYear());
				stmt.setString(5, book.getCategory());
				stmt.setInt(6, book.getProductId());
				
				flag = stmt.executeUpdate();

				if (flag > 0) {
					closeAll(stmt, null, null);

					query = "update book_inventory set price = ?, inven_amount = ? where book_id = ?";
					stmt = conn.prepareStatement(query);
					stmt.setDouble(1, book.getPrice());
					stmt.setInt(2, book.getInventory());
					
					flag = stmt.executeUpdate();
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, null);
		}
		return flag;
	}
	
	public static ArrayList<Book> allBooks(){
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		try{
			if (connect()) {
				String query = "select * from book order by author;";
				stmt = conn.prepareStatement(query);
				rs = stmt.executeQuery();
				
				query = "select * from book_inventory where book_id = ?";
				stmt2 = conn.prepareStatement(query);
				
				while(rs.next()){
					Book b = new Book();
					b.setProductId(rs.getInt("product_id"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					b.setPublisher(rs.getString("publisher"));
					b.setPublishYear(rs.getInt("publish_year"));
					b.setCategory(rs.getString("category"));
					
					// gets the price and inventory
					
					stmt2.setInt(1, b.getProductId());
					rs2 = stmt2.executeQuery();
					
					while(rs2.next()) {
						b.setPrice(rs2.getDouble("price"));
						b.setInventory(rs2.getInt("inven_amount"));
					}
					
					bookList.add(b);
				}
				closeAll(stmt2, null, rs2);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
		return bookList;
	}
}
