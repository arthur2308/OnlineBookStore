package CSS490;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class UserDB {
//	private static String dbURL = "jdbc:mysql://localhost:3306/CSS490";
//	private static String dbUser = "css490";
//	private static String dbPass = "css490pass";
	private static String dbURL = "jdbc:mysql://us-cdbr-azure-central-a.cloudapp.net/CSS490Web";
	private static String dbUser = "bf7b6ac1773bc4";
	private static String dbPass = "70e08111";
	
	//insert a user
	public static boolean insertUser(User user){
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
//			Context initCtx = new InitialContext();
//			Context envCtx = (Context)initCtx.lookup("java:comp/env");
//			DataSource ds = (DataSource)envCtx.lookup("jdbc/css490");
//			conn = ds.getConnection();
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into CUSTOMER values (0,?,?,?)";

			if (user.createHash()) {
				stmt = conn.prepareStatement(query);
				stmt.setString(1, user.getUsername());
				stmt.setBytes(2, user.getHash());
				stmt.setString(3, user.getEmail());

				int i = stmt.executeUpdate();
				if(i > 0){
					result = true;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			user.clearPass();
			closeAll(stmt, conn);
		}
		
		return result;
	}
	
	public static boolean loginUser(User user) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
//			Context initCtx = new InitialContext();
//			Context envCtx = (Context)initCtx.lookup("java:comp/env");
//			DataSource ds = (DataSource)envCtx.lookup("jdbc/css490");
//			conn = ds.getConnection();
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select username, pass, email from customer where username = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, user.getUsername());
			
			ResultSet rs = stmt.executeQuery();
			
			byte[] hashPass = null;
			String email = null;
			while (rs.next())
			{
				hashPass = rs.getBytes("pass");
				email = rs.getString("email");
			}
			
			result = user.checkPass(hashPass);
			
			if (result) {
				user.setEmail(email);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			user.clearPass();
			closeAll(stmt, conn);
		}
		
		return result;
	}
	
	
	// show all users
			
	public static ArrayList<User> showUsers(){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<User> users = new ArrayList();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select username, name, email, DATE_FORMAT(signUpDate, '%Y-%m-%d')"+
					" from user order by username";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				User u = new User();
				u.setUsername(rs.getString(1));
				u.setName(rs.getString(2));
				u.setEmail(rs.getString(3));
				u.setSignUpDate(rs.getString(4));
				users.add(u);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
		return users;
	}
	
	// show a single user
	
	public static User showAUser(String username){
		String name = "";
		String email = "";
		String signUpDate = ""; 
	
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User u = new User();
		u.setUsername(username);
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select name, email, signUpDate from user where username = ?";
			stmt = conn.prepareStatement(query);
	
			stmt.setString(1, username);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				u.setName(rs.getString("name"));
				u.setEmail(rs.getString("email"));
				u.setSignUpDate(rs.getString("signUpDate"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return u;
	}
	
	// modify a user's information
	
	public static int modifyUser(User u){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "update user set passwd = ?, name= ?, email=? where username = ?";
			stmt = conn.prepareStatement(query);
			stmt.setBytes(1, u.getHash());
			stmt.setString(2, u.getName());
			stmt.setString(3, u.getEmail());
			stmt.setString(4, u.getUsername());

			flag = stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}
	
	//modify a user's information
	
	public static int deleteUser(String username){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("delete from user where username = ?");
			stmt.setString(1, username);
			flag = stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}
	
	// check whether a user has already signed up or not. 
	
	public static int checkUserAvail(String username, String email){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select * from user where username = ? or email = ?");
			stmt.setString(1, username);
			stmt.setString(2, email);
			rs = stmt.executeQuery();
			if(rs.next()){
				flag = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}

	
	private static void closeAll(Statement stmt, Connection conn, ResultSet rs){
		if(stmt != null){
			try{
				stmt.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		if(conn != null){
			try{
				conn.close();
			}catch(SQLException sqle){
			}
		}
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException sqle){
			}
		}
	}
	
	private static void closeAll(Statement stmt, Connection conn){
		if(stmt != null){
			try{
				stmt.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		if(conn != null){
			try{
				conn.close();
			}catch(SQLException sqle){
			}
		}
	}
}