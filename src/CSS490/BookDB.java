package CSS490;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class BookDB extends Database{

	public static Book getBook(int productId) {
		Book book = new Book();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		book.setProductId(productId);
		try{
			if (connect()) {
				String query = "select * from book where product_id = ?";
				stmt = conn.prepareStatement(query);

				stmt.setInt(1, productId);
				rs = stmt.executeQuery();

				while(rs.next()){
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
}
