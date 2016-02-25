package CSS490;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
//	private static String dbURL = "jdbc:mysql://localhost:3306/CSS490";
//	private static String dbUser = "css490";
//	private static String dbPass = "css490pass";
	private static String dbURL = "jdbc:mysql://us-cdbr-azure-central-a.cloudapp.net/CSS490Web";
	private static String dbUser = "bf7b6ac1773bc4";
	private static String dbPass = "70e08111";
	
	protected static Connection conn = null;
	
	//insert a user
	protected static boolean connect(){
		boolean result = false;
		try{
//			Context initCtx = new InitialContext();
//			Context envCtx = (Context)initCtx.lookup("java:comp/env");
//			DataSource ds = (DataSource)envCtx.lookup("jdbc/css490");
//			conn = ds.getConnection();
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			result = true;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	protected static void closeAll(Statement stmt, Connection conn, ResultSet rs){
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
	
	protected static void closeAll(Statement stmt, Connection conn){
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
