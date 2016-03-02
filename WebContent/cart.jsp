<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, CSS490.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
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
</style>
<% 
	HttpSession sess = request.getSession();
	User user = (User) sess.getAttribute("User");
	Cart cart = (Cart) sess.getAttribute("Cart");
	%>
<script>
	function removeCart(book) {
		document.getElementById("book2").value = book;
		document.bookRemove.submit();
	}
	function modifyCart(book, quant) {
		document.getElementById("book1").value = book;
		document.getElementById("quantity").value = document.getElementById("number" + book).value;
		document.bookModify.submit();
	}
	
</script>
</head>
<body>
<h1>Cart for user:  <% out.print(user.getUsername()); %></h1>
<table id="list">
	<tr>
		<th>Product Id</th>
		<th>Title</th>
		<th>Author</th>
		<th>Publisher</th>
		<th>Publish year</th>
		<th>Category</th>
		<th>Price</th>
		<th>Quantity</th>
		<th></th>
	</tr>
<%
	ArrayList<CartItem> bookList = cart.getCart();
	for(CartItem item:bookList){
		Book b = item.getBook();
		int book_id = b.getProductId();
%>
<tr>
	<td width="20%"><%=b.getProductId()%></td>
	<td width="20%"><%=b.getTitle()%></td>
	<td width="20%"><%=b.getAuthor()%></td>
	<td width="20%"><%=b.getPublisher()%></td>
	<td width="20%"><%=b.getPublishYear()%></td>
	<td width="20%"><%=b.getCategory()%></td>
	<td width="20%"><%=b.getPrice()%></td>
	<td width="20%"><%=item.getQuantity()%>
		<input type="number" name="number<%=b.getProductId()%>" id="number<%=b.getProductId()%>" 
				value="<%=item.getQuantity()%>"
				onblur="javascript:modifyCart('<%=b.getProductId()%>');"
				onkeydown="if (event.keyCode == 13) {modifyCart('<%=b.getProductId()%>');}"
				min="0" max="100" >
	</td>
	<td width="20%">
		<a href="javascript:removeCart('<%=b.getProductId()%>');">[remove]</a>
	</td>
</tr>
<%
	}
%>
</table>
<form name="bookRemove" method="post" action="cart/remove">
<input type="hidden" name="book2" id="book2">
</form>
<form name="bookModify" method="post" action="cart/modify">
			<input type="hidden" name="book1" id="book1">
			<input type="hidden" name="quantity" id="quantity">
		</form>
</body>
</html>