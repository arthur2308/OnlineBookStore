package CSS490;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class BookDB extends Database{

	public static Book getBook(int productId) {
		Book book = null;
		PreparedStatement stmtBk = null;
		PreparedStatement stmtInv = null;
		ResultSet rsBk = null;
		ResultSet rsInv = null;
		try{
			if (connect()) {
				String selBook = "select * from book where product_id = ?";
				String selInv = "select * from book_inventory where book_id = ?";
				stmtBk = conn.prepareStatement(selBook);
				stmtInv = conn.prepareStatement(selInv);
				
				stmtBk.setInt(1, productId);
				rsBk = stmtBk.executeQuery();

				while(rsBk.next()){
					book = new Book();
					book.setProductId(productId);
					book.setTitle(rsBk.getString("title"));
					book.setAuthor(rsBk.getString("author"));
					book.setPublisher(rsBk.getString("publisher"));
					book.setPublishYear(rsBk.getInt("publish_year"));
					book.setCategory(rsBk.getString("category"));
				}
				
				
				stmtInv.setInt(1, productId);
				rsInv = stmtInv.executeQuery();
				
				while(rsInv.next()) {
					book.setPrice(rsInv.getDouble("price"));
					book.setInventory(rsInv.getInt("inven_amount"));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmtBk,conn,rsBk);
			closeAll(stmtInv,null,rsInv);
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
	
	public static boolean modifyBook(Book book){
		boolean result = false;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
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
				
				stmt.executeUpdate();
		
				query = "update book_inventory set price = ?, inven_amount = ?, book_id = ? where book_id = ?";
				stmt2 = conn.prepareStatement(query);
				stmt2.setDouble(1, book.getPrice());
				stmt2.setInt(2, book.getInventory());
				stmt2.setInt(3, book.getProductId());
				stmt2.setInt(4, book.getProductId());
					
				stmt2.executeUpdate();
				result = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, null);
			closeAll(stmt2, null);
		}
		return result;
	}
	
	public static boolean deleteBook(Book book) {
		
		boolean result = false;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		
		try{
			if (connect()) {
				
				String query = "DELETE FROM book_inventory WHERE book_id= ?;";
				stmt2 = conn.prepareStatement(query);
				stmt2.setInt(1, book.getProductId());
				stmt2.executeUpdate();
				
				 query = "DELETE FROM book WHERE product_id= ?;";
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, book.getProductId());
				stmt.executeUpdate();
				
				

				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, null);
			closeAll(stmt2, null);
		}
		return result;
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


	public static boolean  insertBook(Book book){
		boolean result = false;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		PreparedStatement retriveBook = null;
		ResultSet rs = null;
		try{
			if (connect()) {
				String query = "insert into book values (0,?,?,?,?,?)";
				
					stmt = conn.prepareStatement(query);
					stmt.setString(1, book.getTitle());
					stmt.setString(2, book.getAuthor());
					stmt.setString(3, book.getPublisher());
					stmt.setInt(4, book.getPublishYear());
					stmt.setString(5,book.getCategory());
					
					stmt.executeUpdate();
					
					String select = "select * from book where title = ? AND author = ? AND publisher = ? AND category = ? AND publish_year = ?";
					retriveBook = conn.prepareStatement(select);
					
					retriveBook.setString(1, book.getTitle());
					retriveBook.setString(2, book.getAuthor());
					retriveBook.setString(3, book.getPublisher());
					retriveBook.setString(4, book.getCategory());
					retriveBook.setInt(5, book.getPublishYear());
					
					// retrieve the book id
					int book_id = -1;
					rs  = retriveBook.executeQuery();
					while (rs.next()) {
						book_id = rs.getInt("product_id");
					}
					
					//String query = "insert into book values (0,?,?,?,?,?)";
					query = "insert into book_inventory values (?,?,?)";
					stmt2 = conn.prepareStatement(query);
					stmt2.setInt(1, book_id);
					stmt2.setDouble(2, book.getPrice());
					stmt2.setInt(3, book.getInventory());
					
						
					stmt2.executeUpdate();
					result = true;
					
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn);
			closeAll(stmt2, null);
			closeAll(retriveBook, null);
		}

		return result;
	}

		public static int checkBookAvail(String author, String title){ 
		int flag = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			if (connect()) {
				stmt = conn.prepareStatement("select * from book where author = ? or title = ?");
				stmt.setString(1, author);
				stmt.setString(2, title);
				rs = stmt.executeQuery();
				if(rs.next()){
					flag = 1;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}

	public static ArrayList<Book> searchBookbyTitle(String title) {
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		try{
			if (connect()) {
				String query = "select * from book where title like ? order by author;";
				stmt = conn.prepareStatement(query);
				stmt.setString(1,"%"+title+"%");
				rs = stmt.executeQuery();

				while(rs.next()){
					Book b = new Book();
					b.setProductId(rs.getInt("product_id"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					b.setPublisher(rs.getString("publisher"));
					b.setPublishYear(rs.getInt("publish_year"));
					b.setCategory(rs.getString("category"));
					
					// gets the price and inventory
					query = "select * from book_inventory where book_id = ?";
					stmt2 = conn.prepareStatement(query);
					stmt2.setInt(1, b.getProductId());
					rs2 = stmt2.executeQuery();
					
					while(rs2.next()) {
						b.setPrice(rs2.getDouble("price"));
						b.setInventory(rs2.getInt("inven_amount"));
					}
					
					bookList.add(b);
					closeAll(stmt2, null, rs2);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
	
		return bookList;
		
	}
	
	public static ArrayList<Book> searchBookbyCategory(String title) {
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		try{
			if (connect()) {
				String query = "select * from book where category like ? order by author;";
				stmt = conn.prepareStatement(query);
				stmt.setString(1,"%"+title+"%");
				rs = stmt.executeQuery();

				while(rs.next()){
					Book b = new Book();
					b.setProductId(rs.getInt("product_id"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					b.setPublisher(rs.getString("publisher"));
					b.setPublishYear(rs.getInt("publish_year"));
					b.setCategory(rs.getString("category"));
					
					// gets the price and inventory
					query = "select * from book_inventory where book_id = ?";
					stmt2 = conn.prepareStatement(query);
					stmt2.setInt(1, b.getProductId());
					rs2 = stmt2.executeQuery();
					
					while(rs2.next()) {
						b.setPrice(rs2.getDouble("price"));
						b.setInventory(rs2.getInt("inven_amount"));
					}
					
					bookList.add(b);
					closeAll(stmt2, null, rs2);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
	
		return bookList;
		
	}
	
	public static ArrayList<Book> getThisTopTen() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		try{
			if (connect()) {
				String query = "select product_id, title, author, SUM(quantity) as total from book b, transaction_list t, transaction_log l "
						+ "where b.product_id = l.book_id AND l.transaction_id = t.transaction_id AND "
						+ "t._date >= DATE(NOW()) - INTERVAL 1 WEEK GROUP BY title ORDER BY total desc limit 10;";
				stmt = conn.prepareStatement(query);
				rs = stmt.executeQuery();

				while(rs.next()){
					Book b = new Book();
					b.setProductId(rs.getInt("product_id"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					
					bookList.add(b);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return bookList;
	}
	
	public static ArrayList<Book> getLastTopTen() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		try{
			if (connect()) {
				String query = "select product_id, title, author, SUM(quantity) as total from book b, transaction_list t, transaction_log l "
						+ "where b.product_id = l.book_id AND l.transaction_id = t.transaction_id AND "
						+ "t._date >= DATE(NOW()) - INTERVAL 2 WEEK AND t._date <= DATE(NOW()) - INTERVAL 1 WEEK GROUP BY title ORDER BY total desc limit 10;";
				stmt = conn.prepareStatement(query);
				rs = stmt.executeQuery();

				while(rs.next()){
					Book b = new Book();
					b.setProductId(rs.getInt("product_id"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					
					bookList.add(b);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return bookList;
	}
	
	public static ArrayList<Book> getThisTopGenre(String genre) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		try{
			if (connect()) {
				String query = "select product_id, title, author, SUM(quantity) as total from book b, transaction_list t, transaction_log l "
						+ "where b.product_id = l.book_id AND l.transaction_id = t.transaction_id AND b.category = ? AND "
						+ "t._date >= DATE(NOW()) - INTERVAL 1 WEEK GROUP BY title ORDER BY total desc limit 5;";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, genre);
				rs = stmt.executeQuery();

				while(rs.next()){
					Book b = new Book();
					b.setProductId(rs.getInt("product_id"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					
					bookList.add(b);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return bookList;
	}
	
	public static ArrayList<Book> getLastTopGenre(String genre) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		try{
			if (connect()) {
				String query = "select product_id, title, author, SUM(quantity) as total from book b, transaction_list t, transaction_log l "
						+ "where b.product_id = l.book_id AND l.transaction_id = t.transaction_id AND b.category = ? AND "
						+ "t._date >= DATE(NOW()) - INTERVAL 2 WEEK AND t._date <= DATE(NOW()) - INTERVAL 1 WEEK GROUP BY title ORDER BY total desc limit 5;";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, genre);
				rs = stmt.executeQuery();

				while(rs.next()){
					Book b = new Book();
					b.setProductId(rs.getInt("product_id"));
					b.setTitle(rs.getString("title"));
					b.setAuthor(rs.getString("author"));
					
					bookList.add(b);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return bookList;
	}
}
