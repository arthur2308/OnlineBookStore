package CSS490;

import java.util.Date;

public class BookRating {

	private int user_id;
	private int book_id;
	private int rating;
	private Date date;
	private String comment;
	
	public BookRating() {
		user_id = -1;
		book_id = -1;
		rating = -1;
		date = null;
		comment = "";
	}
	
	public BookRating(int user_id, int book_id) {
		this.user_id = user_id;
		this.book_id = book_id;
		rating = -1;
		date = null;
		comment = "";
	}
	
	public void setUser(int user_id) {
		this.user_id = user_id;
	}
	
	public int getUser() {
		return user_id;
	}
	
	public void setBookId(int book_id) {
		this.book_id = book_id;
	}
	
	public int getBookId() {
		return book_id;
	}
	
	public void setRating(int rate) {
		this.rating = rate;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getComment() {
		return comment;
	}
}
