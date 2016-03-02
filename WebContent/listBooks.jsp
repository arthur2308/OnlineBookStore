<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import = "java.util.*, CSS490.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>List of Books</title>
<style>
	body {
				font-family: Arial, Verdana, sans-serif;
				color: #111111;}
	table {
				width: 800px;
				margin: auto;
				border-collapse: collapse;
				}
	th, td {
				padding: 12px 10px;}
	th{
		
		text-transform: uppercase;
		letter-spacing: 0.1em;
		font-size: 90%;
		border-bottom: 2px solid #111111;
		border-top: 1px solid #999;
		text-align: left;
	}
	
	tr:nth-child(even){
		background-color: #efefef;
	}
	
	tr:hover {
		background-color: #c3e6e5;
	}
	
	tr:last-child{
		border-bottom: 2px solid #111111;
	}
</style>
<script>
	function addCart(book) {
		document.getElementById("book").value = book;
		document.addBook.submit();
	}
</script>
</head>
<body>
<table id="list">
	<tr>
		<th>Product Id</th>
		<th>Title</th>
		<th>Author</th>
		<th>Publisher</th>
		<th>Publish year</th>
		<th>Category</th>
		<th>Price</th>
		<th>Inventory</th>
		<th></th>
	</tr>
<%
	ArrayList<Book> bookList = BookDB.allBooks();
	for(Book b:bookList){
%>
<tr>
	<td width="20%"><%=b.getProductId()%></td>
	<td width="20%"><%=b.getTitle()%></td>
	<td width="20%"><%=b.getAuthor()%></td>
	<td width="20%"><%=b.getPublisher()%></td>
	<td width="20%"><%=b.getPublishYear()%></td>
	<td width="20%"><%=b.getCategory()%></td>
	<td width="20%"><%=b.getPrice()%></td>
	<td width="20%"><%=b.getInventory()%></td>
	<td width="20%">
		<a href="javascript:addCart('<%=b.getProductId()%>');">[add cart]</a>
	</td>
</tr>
<%
	}
%>
</table>
<form name="addBook" method="post" action="cart/add">
<input type="hidden" name="book" id="book">
</form>
</body>
</html>