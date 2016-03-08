<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, CSS490.*" %>
<%@ page import="CSS490.User" %>
<%@ page import="CSS490.Cart" %>
<%@ page import="javax.swing.text.html.HTMLDocument" %>
<jsp:useBean id="user" class="CSS490.User"/>
<jsp:useBean id="book" class="CSS490.Book"/>
<jsp:useBean id="cart" class="CSS490.Cart"/>
<%@ page import="CSS490.BookDB" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
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

</style>
<meta charset="UTF-8">
<title>Home Page
<% 
	HttpSession sess = request.getSession();
	user = (User) sess.getAttribute("User");			//check if the user is null to get if they're logged in 
	book = BookDB.getBook(1);
	cart = (Cart) sess.getAttribute("Cart");
	%>
</title>
</head>
	<body>
		<div id = "topBar">
			
			<h1 align = "center" >Nap Store</h1>
			
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
			
			
			<div id = searchBox>
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
	  	<br><br><br><br><br>
	  	<h2 align = "center"> Welcome to Amazon book store.</h2>
	  	<br><br>
	  	<h3> Featured: </h3>
	  	<% out.print("old man take a look at my life"); %>
		<br><% out.print(UserDB.isAdmin(1)); %><br><br><br><br>LOREM IPSUM<br><br><br><br><br><br>
		<br><br><br><br><br><br>LOREM IPSUM<br><br><br>
	    <br><br>LOREM IPSUM<br><br><br><br><br>LOREM IPSUM<br><br><br><br>
		<br><br><br>LOREM IPSUM<br><br><br><br><br>Hello, Welcome to the book store. You're on a home page.
		
		
		<script>
		
		// sigs a user out 
		function signOut()
		{
			document.logoutUser.submit();
		}
		<!-- 
		window.onload = function() {
			if (user != null) 
			{
				document.getElementById("authorization").style.visibility = "hidden";			
			}	
		}
		-->
		</script>
		<form name="logoutUser" method="post" action="user/logout">
		</form>
	</body>
</html>