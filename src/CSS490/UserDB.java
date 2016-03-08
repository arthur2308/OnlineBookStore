package CSS490;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class UserDB extends Database{

	//insert a user
	public static boolean insertUser(User user){
		boolean result = false;
		PreparedStatement stmt = null;
		try{
			//			Context initCtx = new InitialContext();
			//			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			//			DataSource ds = (DataSource)envCtx.lookup("jdbc/css490");
			//			conn = ds.getConnection();

			if (connect()) {
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
		PreparedStatement stmt = null;
		try{
			//			Context initCtx = new InitialContext();
			//			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			//			DataSource ds = (DataSource)envCtx.lookup("jdbc/css490");
			//			conn = ds.getConnection();

			if (connect()) {
				String query = "select * from customer where username = ?";
				stmt = conn.prepareStatement(query);
				stmt.setString(1, user.getUsername());

				ResultSet rs = stmt.executeQuery();

				byte[] hashPass = null;
				String email = null;
				int id = -1;
				while (rs.next())
				{
					hashPass = rs.getBytes("pass");
					email = rs.getString("email");
					id = rs.getInt("cust_id");
				}

				result = user.checkPass(hashPass);

				if (result) {
					user.setUserId(id);
					user.setEmail(email);
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


	// show all users

	public static ArrayList<User> showUsers(){
		int flag = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<User> users = new ArrayList();
		try{
			if (connect()) {
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

		PreparedStatement stmt = null;
		ResultSet rs = null;
		User u = new User();
		u.setUsername(username);
		try{
			if (connect()) {
				String query = "select name, email, signUpDate from user where username = ?";
				stmt = conn.prepareStatement(query);

				stmt.setString(1, username);
				rs = stmt.executeQuery();

				while(rs.next()){
					u.setName(rs.getString("name"));
					u.setEmail(rs.getString("email"));
					u.setSignUpDate(rs.getString("signUpDate"));
				}
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			if (connect()) {
				String query = "update user set passwd = ?, name= ?, email=? where username = ?";
				stmt = conn.prepareStatement(query);
				stmt.setBytes(1, u.getHash());
				stmt.setString(2, u.getName());
				stmt.setString(3, u.getEmail());
				stmt.setString(4, u.getUsername());

				flag = stmt.executeUpdate();
			}
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			if (connect()) {
				stmt = conn.prepareStatement("delete from user where username = ?");
				stmt.setString(1, username);
				flag = stmt.executeUpdate();
			}
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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			if (connect()) {
				stmt = conn.prepareStatement("select * from customer where username = ? or email = ?");
				stmt.setString(1, username);
				stmt.setString(2, email);
				rs = stmt.executeQuery();
				if(rs.next()){
					flag = 1;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}

	public static boolean isAdmin(int userid) {
		boolean result = false;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			if (connect()) {
				stmt = conn.prepareStatement("select * from customer where cust_id = ?");
				stmt.setInt(1, userid);
				rs = stmt.executeQuery();
				if(rs.next()){
					result = rs.getBoolean("admin");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return result;
	}
}
