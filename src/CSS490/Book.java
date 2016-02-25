package CSS490;

public class Book {

	private int productId;
	private String title;
	private String author;
	private String publisher;
	private int publishYear;
	private String category;
	private double price;
	private int inventory;
	// create book_ratings class

	public Book() {
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}
	
	public int getPublishYear() {
		return publishYear;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	
	public int getInventory() {
		return inventory;
	}
}
