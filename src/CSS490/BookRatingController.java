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
@WebServlet("/BookRatingController")
public class BookRatingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookRatingController() {
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
		if(requestURI.endsWith("set")){
			url = setRating(request);
		} 	else {
			url = "/index.jsp";
		}
		response.sendRedirect(url);
	}
	
	private String setRating(HttpServletRequest request){
		String url = "/listBooks.jsp";
		String book = request.getParameter("book1");
		String s_rate = request.getParameter("rating");
		int book_id = Integer.parseInt(book);
		int rating = Integer.parseInt(s_rate);
		int user_id = Integer.parseInt(request.getParameter("user1"));
		String comment = request.getParameter("comment");
		BookRating br = new BookRating(user_id, book_id);
		br.setRating(rating);
		br.setComment(comment);
		br.setDate(new Date());
		int flag = BookRatingDB.setRating(br);
		
		if(flag > 0){
			url = "/listBooks.jsp";
		}
		return url;
	}
}
