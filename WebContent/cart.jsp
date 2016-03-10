<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, CSS490.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cart</title>
<style>
	table 
	{
		width: 1000px;
		margin: 5% auto auto;
		border-collapse: collapse;
	}
	
	th, td 
	{
		padding: 12px 10px;
	}
	
	th
	{
		text-transform: uppercase;
		letter-spacing: 0.1em;
		font-size: 90%;
		border-bottom: 2px solid #111111;
		border-top: 1px solid #999;
		text-align: left;
	}
	
	input[type=number]
	{
    	width: 50%;
	} 

	
	#authorization
	{
		float:right;
		color:white;
	}
	
	#welcome_user
	{
		float:right;
		color:white;
	}
	
	a:link.red_box_link, a:visited.red_box_link
	{
	    background-color: #f44336;
	    color: white;
	    padding: 17px 25px;
	    text-align: center;	
	    text-decoration: none;
	    display: inline-block;
	}
	
	a:hover.red_box_link, a:active.red_box_link
	{
	    background-color: red;
	}
	
	#topBar
	{
		position: fixed;
		background-color: #333;
	    left: 0;
	    top: 0;
	    width: 100%;
	}
	
	h1 
	{
		color:white;
		display: inline-block;
		margin: 10pt;
	}
	
	body
	{
		background: Gainsboro ; <!--#E0E0E0;--> 
	}
	
	#searchBox
	{
		display:inline-block;
		float:center;
		padding-left:25%;
	}
	
	#search_button
	{
	
		background-color: #f44336;
	    color: white;
	    padding: 10px 15px;
	    text-align: center;	
	    text-decoration: none;
	    display: inline-block;
	    
	}
	
	  ul {list-style: none;padding: 0px;margin: 0px;}
	  ul li {display: block;position: relative;float: left;border:1px solid #333}
	  li ul {display: none;}
	  ul li a {display: block;background: #333;padding: 5px 10px 5px 10px;text-decoration: none;
	           white-space: nowrap;color: #fff;}
	  ul li a:hover {background: #f00;}
	  li:hover ul {display: block; position: absolute;}
	  li:hover li {float: none;}
	  li:hover a {background: #f00;}
	  li:hover li a:hover {background: #000;}
	  #drop-nav li ul li {border-top: 0px;}
	
	
	#acct 
	{
		Color:white;
	}
	
		
	#QueryForm
	{
		display: inline-block;
	}	
	
	#pageTitle
	{
		margin: 7% auto auto;
		color:black;
		padding-left:45%;
	}
	

</style>
<% 
	HttpSession sess = request.getSession();
	User user = (User) sess.getAttribute("User");
	Cart cart = (Cart) sess.getAttribute("Cart");
	String username = "Guest";
	if (user != null) {
		username = user.getUsername();
	}
	if (cart == null) {
		System.out.println("cart was null");
		cart = new Cart();
		sess.setAttribute("Cart", cart);
	}
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
	
	function signOut()
	{
		document.logoutUser.submit();
	}
	
</script>


</head>
<body>


<div id = "topBar">
			<a href = "/">
			<h1 align = "center" >Nap Store</h1>
			</a>
			<!--  authorization was here put it back here, the whole div  -->
			
			<div id = "welcome_user"> 
		  		<p>
		  		
		  		<%
		  		
		  		//HTMLDocument doc = new HTMLDocument(); 
		  		//doc.getElement("authorization").
		  		try {
		  			out.print("Hello, " + user.getUsername() + " - ");
		  			%>
		  			<!-- <br><button id = "logout" onclick="signOut()">Sign out</button> --> 
		  			<a href = "/cart.jsp" id = "acct">Cart </a>
		  			<%
		  			out.println("(" + cart.getSize() + ") - ");
		  			%>
		  			<a href = "javascript:signOut()" id = "acct" >Sign Out </a>
		  			 <% 
		  		} catch (NullPointerException e)
		  		{ 
		  		
		  		%>
		  		<div id = "authorization"> 
		  			<a href = "loginForm.html" class = "red_box_link">Login</a> 
					<a href = "signupForm.html" class = "red_box_link">Sign Up</a> 
				</div> 
		  		
		  		<%
		  		} 

	  			//document.getElementById("authorization").style.visibility = "hidden";
	  			//out.print("Null pointer exception has happened!: " + e);
		  		%>
		  			
		  		
			</div> 
			
			
			
			<div id = "searchBox" >
			<a href = "/">
				<img src="/Images/3D-Box-1.png" height="42" width="42">
			</a>
			<form action="book/searchByTitle" method="post" id = "QueryForm">
				<input type="text" style="width: 300px; height: 20px;" name="title" id = "title">
		  		<input type="submit" id = "search_button" value="Search"> 
		     </form>
	  		</div>
	  		
	  	

	  		
	  		
	  		<ul id="drop-nav">
			 
			  <li> <a href = "#">Books</a>
			  <ul>
			 	 <li><a href = "book/list" >Browse</a></li>
			  	<li><a href = "#" >Top 10 Books!</a></li>
			  </ul>
			  </li>
			  
			  <li><a href="#">Account</a>
			    <ul>
			      <li><a href="/cart.jsp">Cart</a></li>
			    </ul>
			  </li>
			  
			  <li><a href="#">Nap Pages</a>
			    <ul>
			      <li><a href="/">Home Page</a></li>
			      <li><a href="#">Drupal</a></li>
			    </ul>
			  </li>
			
			  <li><a href="#">Contact</a>
			    <ul>
			      <li><a href="#">General Inquiries</a></li>
			      <li><a href="#">Ask me a Question</a></li>
			    </ul>
			  </li>
			
			</ul>
			
			
	  	</div>
	  	
	  	<h1 id = "pageTitle"> 
	  	<%
	  	out.print(username + "'s ");
	  	%>
	  	
	  	Cart</h1>
	  	

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
		<th>Subtotal</th>
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
	<td width="20%"><a href="bookDetails.jsp?<%=b.getProductId()%>"><%=b.getTitle()%></a></td>
	<td width="20%"><%=b.getAuthor()%></td>
	<td width="20%"><%=b.getPublisher()%></td>
	<td width="20%"><%=b.getPublishYear()%></td>
	<td width="20%"><%=b.getCategory()%></td>
	<td width="20%"><%=b.getPrice()%></td>
	<td width="20%"><%=item.getQuantity()%> - 
		<input type="number" name="number<%=b.getProductId()%>" id="number<%=b.getProductId()%>" 
				value="<%=item.getQuantity()%>"
				onblur="javascript:modifyCart('<%=b.getProductId()%>');"
				onkeydown="if (event.keyCode == 13) {modifyCart('<%=b.getProductId()%>');}"
				min="0" max="100" >
	</td>
	<td width="20%"><%=(b.getPrice() * item.getQuantity())%></td>
	<td width="20%">
		<a href="javascript:removeCart('<%=b.getProductId()%>');">[remove]</a>
	</td>
</tr>
<%
	}
%>
	<tr>
		<th colspan="8"><span style="float:right">Total</span></th>
		<th><%=cart.getTotal()%></th>
		<th>
			<form name="buyCart" method="post" action="cart/buy">
				<input type="submit" id="submit" value="Buy"/>
			</form>
		</th>
	</tr>
</table>
<form name="bookRemove" method="post" action="cart/remove">
<input type="hidden" name="book1" id="book2">
</form>
<form name="bookModify" method="post" action="cart/modify">
	<input type="hidden" name="book1" id="book1">
	<input type="hidden" name="quantity" id="quantity">
</form>
<form name="logoutUser" method="post" action="user/logout">
		</form>
</body>
</html>