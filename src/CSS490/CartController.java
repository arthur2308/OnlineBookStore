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
		if(requestURI.endsWith("register")){
			url = registerUser(request);
		}else if(requestURI.endsWith("modify")){
			url = modifyBook(request);
		}else if(requestURI.endsWith("remove")){
			url = removeBook(request);
		}
		
		response.sendRedirect(url);
	}
	
	private String registerUser(HttpServletRequest request){
		String url = "";
		String username = request.getParameter(p_username);
		char[] pass = request.getParameter(p_password).toCharArray();
		String email = request.getParameter(p_email);
		
		int flag = 0;
		flag = UserDB.checkUserAvail(username, email);
		if(flag > 0){
			url = "/fail.jsp";
		}else{
			User user = new User();
			
			user.setUsername(username);
			user.setPass(pass);
			user.setEmail(email);
			if(UserDB.insertUser(user)){
				url = "/success.jsp";
			}
		}
		return url;
	}
	
	private String modifyBook(HttpServletRequest request){
		String url = "";
		String username = request.getParameter(p_username);
		User user = new User();
		user.setUsername(username);
		user.setPass(request.getParameter(p_password).toCharArray());
		user.setEmail(request.getParameter(p_email));
		int flag = 0;
		flag = UserDB.modifyUser(user);
		if(flag > 0){
			url = "/listUsers.jsp";
		}
		return url;
	}
	
	private String removeBook(HttpServletRequest request){
		String url = "";
		String book = request.getParameter("book");
		int book_id = Integer.parseInt(book);
		boolean flag = false;
		flag = cart.remove(book_id);
		if(flag){
			url = "/cart.jsp";
		}
		return url;
	}
}
