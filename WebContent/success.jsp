<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="CSS490.User" %>
<jsp:useBean id="user" class="CSS490.User"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Success</title>
	<% 
	HttpSession sess = request.getSession();
	user = (User) sess.getAttribute("User");
	%>
</head>
<body>
	<h1>
		<marquee>Success!</marquee>
	</h1>
	Username: <% out.print(user.getUsername()); %> <br />
	Email: <% out.print(user.getEmail()); %>
</body>
</html>