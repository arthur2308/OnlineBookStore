<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="CSS490.User" %>
<jsp:useBean id="user" class="CSS490.User"/>
<jsp:useBean id="book" class="CSS490.Book"/>
<%@ page import="CSS490.BookDB" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Success</title>
	<% 
	HttpSession sess = request.getSession();
	user = (User) sess.getAttribute("User");			//check if the user is null to get if they're logged in 
	book = BookDB.getBook(1);
	%>
</head>
<body>
	<h1>
		<marquee>Success!</marquee>
	</h1>
	Username: <% out.print(user.getUsername()); %> <br />
	Email: <% out.print(user.getEmail()); %> <br />
	
	Book id: <% out.print(book.getProductId()); %> <br />
	Title: <% out.print(book.getTitle()); %> <br />
	Author: <% out.print(book.getAuthor()); %> <br />
	Publisher: <% out.print(book.getPublisher()); %> <br />
	Publish year: <% out.print(book.getPublishYear()); %> <br />
	Genre: <% out.print(book.getCategory()); %> <br />
	Price: <% out.print(book.getPrice()); %> <br />
	Inventory: <% out.print(book.getInventory()); %> <br />
	
	<br /><a href=listBooks.jsp>List of books</a>
</body>
</html>