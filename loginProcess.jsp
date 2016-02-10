<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="java.util.*" %>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.security.spec.*" %>
<%@ page import="javax.crypto.*" %>
<%@ page import="javax.crypto.spec.PBEKeySpec" %>
<%@ page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Login Process</title>
    <link rel="stylesheet" href="css/c06.css" />
    <style>
    	div#en{
    		color: white;
    	}
    </style>
  </head>
  <body>
  	<div id="connect">
  		<%
		Connection conn = null;
		try{
			String dbURL = "jdbc:mysql://localhost:3306/CSS490";
			String dbUser = "css490";
			String dbPass = "css490pass";
		
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			out.println("Successfully connected");
		}catch(Exception e){
			e.printStackTrace();
		}
		%>
	</div>
    <div id="page">
      <h1>Create account</h1>
      <%
      		PreparedStatement stmt = conn.prepareStatement("insert into CUSTOMER values (0,?,?,?,?)");
      		String username = request.getParameter("username");
			char[] passwrd = request.getParameter("password").toCharArray();
			String email = request.getParameter("email");
			
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			KeySpec spec = new PBEKeySpec(passwrd, salt, 65536, 256);
			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			byte[] hash = f.generateSecret(spec).getEncoded();
			Base64.Encoder enc = Base64.getEncoder();
			System.out.printf("salt: %s%n", enc.encodeToString(salt));
			System.out.printf("hash: %s%n", enc.encodeToString(hash));
			
			stmt.setString(1, username);
			stmt.setBytes(2, hash);
			stmt.setString(3, email);
			stmt.setBytes(4, salt);
			stmt.executeUpdate();
      %>
      <h2>Welcome <%=request.getParameter("username") %></h2>
      <div id="response">
      	Your password is <span id = "emph">
      	<%
      		out.println(passwrd);
      		out.println("<br /> salt: %s%n" + enc.encodeToString(salt));
      		out.println("<br /> hash: %s%n" + enc.encodeToString(hash));
      	%>
      	</span>
      </div>
      
      
    </div>
  </body>
</html>