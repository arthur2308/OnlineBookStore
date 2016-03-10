package CSS490;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BookRatingDB extends Database{

	public static void getRating(Book book) {
		BookRating br = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int book_id = book.getProductId();
		try{
			if (connect()) {
				String query = "select * from book_rating where book_id = ?";
				stmt = conn.prepareStatement(query);

				stmt.setInt(1, book_id);
				rs = stmt.executeQuery();

				double avgRating = 0;
				int count = 0;
				while(rs.next()){
					br = new BookRating();
					br.setBookId(book_id);
					br.setUser(rs.getInt("user_id"));
					br.setRating(rs.getInt("rating"));
					br.setDate(rs.getDate("rate_date"));
					br.setComment(rs.getString("rate_comment"));
					book.addRating(br);
					avgRating += rs.getInt("rating");
					count++;
				}
				if (count > 0) {
					avgRating /= count;
				}
				book.setAvgRating(avgRating);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
	}
	
	public static int setRating(BookRating br) {
		int flag = 0;
		PreparedStatement stmt = null;
		try{
			if (connect()) {
				String query = "insert into book_rating values (?, ?, ?, ?, ?)";
				stmt = conn.prepareStatement(query);
				
				SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String date = sdf.format(br.getDate());
				
				stmt.setInt(1, br.getBookId());
				stmt.setInt(2, br.getUser());
				stmt.setInt(3, br.getRating());
				stmt.setString(4, date);
				stmt.setString(5, br.getComment());
				
				flag = stmt.executeUpdate();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, null);
		}
		return flag;
	}
	
	public static ArrayList<Book> getTopRated() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Book> bookList = new ArrayList<Book>();
		try{
			if (connect()) {
				String query = "select product_id, title, author, AVG(rating) as rating from book_rating r, book b "
						+ "where b.product_id = r.book_id group by book_id order by rating desc limit 10;";
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
}
