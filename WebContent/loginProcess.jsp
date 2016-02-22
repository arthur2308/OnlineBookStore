<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="java.util.*" %>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="CSS490.User" %>
<jsp:useBean id="userDB" class="CSS490.UserDB"/>
<jsp:useBean id="user" class="CSS490.User"/>
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
    <div id="page">
      <h1>Login account</h1>
      <%
      		String username = request.getParameter("username");
			char[] pass = request.getParameter("password").toCharArray();
			user.setUsername(username);
			user.setPass(pass);
			
			boolean result = userDB.loginUser(user);
      %>
      <h2>Welcome <%=request.getParameter("username") %></h2>
      <div id="response">
      	Your login <span id = "emph">
      	<%
      		if (result)
      		{
      			out.println("suceeded! <br />");
      			out.println("Your email address is: " + user.getEmail());
      		}
      		else
      		{
      			out.println("failed.");
      		}
      	%>
      	</span>
      </div>
      
      
    </div>
  </body>
</html>