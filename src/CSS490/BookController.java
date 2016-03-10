package CSS490;


import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;


@WebServlet("/BookController")
public class BookController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	/**
     * @see HttpServlet#HttpServlet()
     */
    public BookController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI();
		if(requestURI.endsWith("list")){
			showBookList(request, response);
		}

	
	
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI();
		String url = "";
		boolean  t = false;
		
		if(requestURI.endsWith("searchByTitle")) {
			searchByTitle(request, response, "notAdmin");
			t = true;
		}
		
		if(requestURI.endsWith("searchByTitleAdmin")) {
			searchByTitle(request, response, "admin");
			t = true;
		}
		
		if(requestURI.endsWith("create")) {
			url = createBook(request);
		}
		
		if(requestURI.endsWith("modify")) {
			url = modifyBook(request);
		}
		
		if(requestURI.endsWith("delete")) {
			url = deleteBook(request);
		}
		
		if(requestURI.endsWith("category")) {
			searchByCategory(request, response, "notadmin");
		}
		
		if(url == "/AdminListBooks.jsp" ) {
			searchByTitle(request, response, "admin");
		}
		
	}
	
	
	private String deleteBook(HttpServletRequest request) {
		
		String url = "/AdminListBooks.jsp";
		String tempbookId = request.getParameter("book_id");
		int bookId = Integer.parseInt(tempbookId);
		Book book = new Book();
		book.setProductId(bookId);
	    BookDB.deleteBook(book);

		return url;
	}
	
	private String modifyBook(HttpServletRequest request) {
		
		
		String url = "";
		
		String tempbookId = request.getParameter("book_id");
		int bookId = Integer.parseInt(tempbookId);
		
		//Book temp = BookDB.getBook(bookId);
		
		String author = request.getParameter("author");
		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String publisher = request.getParameter("publisher");
		String tempYear = request.getParameter("publishYear");
		int publishYear = Integer.parseInt(tempYear);
		String tempPrice = request.getParameter("price");
		double price = Double.parseDouble(tempPrice);
		String tempInv = request.getParameter("inventory");
		int inventory = Integer.parseInt(tempInv);
		
		
			Book book = new Book();
			book.setProductId(bookId);
			book.setAuthor(author);
			book.setTitle(title);
			book.setCategory(category);
			book.setPublisher(publisher);
			book.setPublishYear(publishYear);
			book.setInventory(inventory);
			book.setPrice(price);

			if(BookDB.modifyBook(book)){
				url = "/AdminListBooks.jsp";
			} else {
				url = "/fail.jsp";
			}
		
		return url;
	}
	
	private String createBook(HttpServletRequest request) {
		
		String url = "";
		
		String author = request.getParameter("author");
		String category = request.getParameter("category");

		String publisher = request.getParameter("publisher");
		
		String tempYear = request.getParameter("publishYear");
		int publishYear = Integer.parseInt(tempYear);
		
		String title = request.getParameter("title");
		String tempPrice = request.getParameter("price");
		double price = Double.parseDouble(tempPrice);
		
		String tempInv = request.getParameter("inventory");
		int inventory = Integer.parseInt(tempInv);

	
		Book book = new Book();
			
		book.setAuthor(author);
		book.setCategory(category);
		book.setTitle(title);
		book.setInventory(inventory);
		book.setPublisher(publisher);
		book.setPublishYear(publishYear);
		book.setPrice(price);

		if(BookDB.insertBook(book)){
			url = "/AdminListBooks.jsp";
		} else {
				url = "/fail.jsp";
		}
		
		
		return url;
	}
	
	private void searchByTitle(HttpServletRequest request, HttpServletResponse response, String admin) throws ServletException{
		response.setContentType("text/html");
		System.out.println("return filtered books");
		String title = request.getParameter("title");
		try{
			
			ArrayList<Book> books = BookDB.searchBookbyTitle(title);
			request.setAttribute("books", books);
			System.out.println(books.size());
			RequestDispatcher rd = null;
			
			if(admin  == "admin") {
				rd = request.getRequestDispatcher("/AdminListBooks.jsp");
			} else {
				rd = request.getRequestDispatcher("/listBooks.jsp");
			}
			//RequestDispatcher rd = request.getRequestDispatcher("../listUsers_JSTL.jsp");
			rd.forward(request, response);
		}catch(Exception e){
			throw new ServletException(e);
		}
	}
	
	
	// get all books	
	private void showBookList(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		response.setContentType("text/html");
		System.out.println("return all books");
		
		
		try{
			ArrayList<Book> books = BookDB.allBooks();
			request.setAttribute("books", books);
			System.out.println(books.size());
			RequestDispatcher rd = request.getRequestDispatcher("/listBooks.jsp");
			//RequestDispatcher rd = request.getRequestDispatcher("../listUsers_JSTL.jsp");
			rd.forward(request, response);
		}catch(Exception e){
			throw new ServletException(e);
		}
	}
	
	
	private void searchByCategory(HttpServletRequest request, HttpServletResponse response, String admin) throws ServletException{
		response.setContentType("text/html");
		System.out.println("return filtered books" + " category");
		String title = request.getParameter("category");
		try{
			
			ArrayList<Book> books = BookDB.searchBookbyCategory(title);
			request.setAttribute("books", books);
			System.out.println(books.size());
			RequestDispatcher rd = null;
			
			if(admin  == "admin") {
				rd = request.getRequestDispatcher("/AdminListBooks.jsp");
			} else {
				rd = request.getRequestDispatcher("/listBooks.jsp");
			}
			//RequestDispatcher rd = request.getRequestDispatcher("../listUsers_JSTL.jsp");
			rd.forward(request, response);
		}catch(Exception e){
			throw new ServletException(e);
		}
	}


}
