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
		
		if(requestURI.endsWith("searchByTitle")) {
			searchByTitle(request, response);
		}
		
		if(requestURI.endsWith("create")) {
			
		}
		
		if(requestURI.endsWith("modify")) {
			
		}
	}
	
	private String modifyBook(HttpServletRequest request) {
		
		
		String url = "";
		
		String author = request.getParameter("author");
		String category = request.getParameter("category");
		
		String tempInv = request.getParameter("inventory");
		int inventory = Integer.parseInt(tempInv);
		
		String publisher = request.getParameter("publisher");
		
		String tempYear = request.getParameter("publishYear");
		int publishYear = Integer.parseInt(tempYear);
		
		String title = request.getParameter("title");
		
		int flag = 0;
		flag = UserDB.checkUserAvail(author, title);
		if(flag > 0){
			url = "/fail.jsp";
		}else{
			User user = new User();
			
//			user.setUsername(username);
//			user.setPass(pass);
//			user.setEmail(email);
//			if(BookDB.(user)){
//				url = "/success.jsp";
//			}
		}
		
		return null;
	}
	
	private void searchByTitle(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		response.setContentType("text/html");
		System.out.println("return filtered books");
		String title = request.getParameter("title");
		try{
			
			ArrayList<Book> books = BookDB.searchBookbyTitle(title);
			request.setAttribute("books", books);
			System.out.println(books.size());
			RequestDispatcher rd = request.getRequestDispatcher("/listBooks.jsp");
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
	


}
