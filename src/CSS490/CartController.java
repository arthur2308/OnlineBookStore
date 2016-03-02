package CSS490;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class userController
 */
@WebServlet("/cartController")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private final String p_username = "username";
	private final String p_password = "password";
	private final String p_email = "email";
	private Cart cart = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI();
		System.out.println(requestURI); 
		String url = "";
		HttpSession sess = request.getSession();
		cart = (Cart) sess.getAttribute("Cart");
		if(requestURI.endsWith("add")){
			url = addBook(request);
		}else if(requestURI.endsWith("modify")){
			url = modifyQuant(request);
		}else if(requestURI.endsWith("remove")){
			url = removeBook(request);
		}
		CartDB.set(cart);
		response.sendRedirect(url);
	}
	
	private String addBook(HttpServletRequest request){
		String url = "";
		String book = request.getParameter("book");
		int book_id = Integer.parseInt(book);
		boolean flag = false;
		Book b = BookDB.getBook(book_id);
		if (b != null) {
			if (b.getInventory() > 0) {
				flag = cart.add(b);
			}
		}
		if(flag){
			url = "/cart.jsp";
		}
		return url;
	}
	
	private String modifyQuant(HttpServletRequest request){
		String url = "";
		String book = request.getParameter("book1");
		String s_quant = request.getParameter("quantity");
		int book_id = Integer.parseInt(book);
		int quantity = Integer.parseInt(s_quant);
		boolean flag = false;
		Book b = BookDB.getBook(book_id);
		if (b != null) {
			if (b.getInventory() > 0) {
				flag = cart.modify(b, quantity);
			}
		}
		if(flag){
			url = "/cart.jsp";
		}
		return url;
	}
	
	private String removeBook(HttpServletRequest request){
		String url = "";
		String book = request.getParameter("book2");
		int book_id = Integer.parseInt(book);
		int user_id = cart.getUserId();
		boolean flag = false;
		if (CartDB.remove(user_id, book_id)) {
			flag = cart.remove(book_id);
		}
		if(flag){
			url = "/cart.jsp";
		}
		return url;
	}
	
}
