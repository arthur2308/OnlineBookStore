package CSS490;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class userController
 */
@WebServlet("/userController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private final String p_username = "username";
	private final String p_password = "password";
	private final String p_email = "email";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
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
		if(requestURI.endsWith("register")){
			url = registerUser(request);
		}else if(requestURI.endsWith("modify")){
			url = modifyUser(request);
		}else if(requestURI.endsWith("delete")){
			url = deleteUser(request);
		}else if(requestURI.endsWith("login")){
			url = loginUser(request);
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
	
	private String modifyUser(HttpServletRequest request){
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
	
	private String deleteUser(HttpServletRequest request){
		String url = "";
		String username = request.getParameter(p_username);
		int flag = 0;
		flag = UserDB.deleteUser(username);
		if(flag > 0){
			url = "/listUsers.jsp";
		}
		return url;
	}
	
	private String loginUser(HttpServletRequest request){
		String url = "";
		User user = new User();
		user.setUsername(request.getParameter(p_username));
		user.setPass(request.getParameter(p_password).toCharArray());
		
		if(UserDB.loginUser(user)){
			url = "/success.jsp";
		}
		else {
			url = "/fail.jsp";
		}
		return url;
	}
}
