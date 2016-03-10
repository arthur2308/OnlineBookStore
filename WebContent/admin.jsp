<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, CSS490.*" %>
<jsp:useBean id="cart" class="CSS490.Cart"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
table {
	border-collapse: collapse;
}
table, td, th {
	border: 1px solid black;
	margin: 2% auto auto;
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
  li:hover a.toplink {background: #f00;}
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
cart = (Cart) sess.getAttribute("Cart");
if (user == null || !UserDB.isAdmin(user.getUserId())) {
	response.sendRedirect("/index.jsp");
	return;
}
%>
</head>
<body>
<div id = "topBar">
			<a href "/">
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
			 
			  <li> <a href = "#" class="toplink">Books</a>
			  <ul>
			 	 <li><a href = "book/list"  class="toplink">Browse</a></li>
			  	<li><a href = "#"  class="toplink">Top 10 Books!</a></li>
			  </ul>
			  </li>
			  
			  <li><a href="#" class="toplink">Account</a>
			    <ul>
			      <li><a href="/cart.jsp" class="toplink">Cart</a></li>
			    </ul>
			  </li>
			  
			  <li><a href="#" class="toplink">Nap Pages</a>
			    <ul>
			      <li><a href="/" class="toplink">Home Page</a></li>
			      <li><a href="#" class="toplink">Drupal</a></li>
			    </ul>
			  </li>
			
			  <li><a href="#" class="toplink">Contact</a>
			    <ul>
			      <li><a href="#" class="toplink">General Inquiries</a></li>
			      <li><a href="#" class="toplink">Ask me a Question</a></li>
			    </ul>
			  </li>
			  
			  <%
		  			if (user != null && UserDB.isAdmin(user.getUserId()))
		  			{
		  				%>
		  					<li><a href="#" class="toplink">Web site Administration</a>
		  						<ul>
			 					     <li><a href="/admin.jsp" class="toplink">Stats</a></li>
			 					     
			 					     <li>
			 					     <form action="book/searchByTitleAdmin" method="post" id = "QueryForm" name="adminbook">
			 					     <input type="hidden" style="width: 300px; height: 20px;" name="title" id = "title">
			 					     <a href="javascript:adminbook()" class="toplink">Edit books</a>
			 					     </form></li>
			 					     
								    
							    </ul>
		  					</li>
		  				<%	
		  			}
		  		
		  		%>
			
			</ul>
			
			
	  	</div>


<%
double salesThisWeek = TransactionDB.getSalesThisWeek();
double salesLastWeek = TransactionDB.getSalesLastWeek();
double salesThisMonth = TransactionDB.getSalesThisMonth();
double salesLastMonth = TransactionDB.getSalesLastMonth();

%>
<h1 id = "pageTitle"> Book Selection</h1>
	<p align = "center"> Weekly and monthly profit</p>
<table width="800">
	<tr>
		<th></th>
		<th>Current</th>
		<th>Last</th>
		<th>Change %</th>
		<th>Profit</th>
	</tr>
	<tr>
		<th>Weekly</th>
		<td><%=salesThisWeek %></td>
		<td><%=salesLastWeek %></td>
		<td><%=((salesThisWeek / salesLastWeek -1) * 100)%></td>
		<td><%=(salesThisWeek - salesLastWeek) %></td>
	</tr>
	<tr>
		<th>Monthly</th>
		<td><%=salesThisMonth %></td>
		<td><%=salesLastMonth %></td>
		<td><%=((salesThisMonth / salesLastMonth -1) * 100)%></td>
		<td><%=(salesThisMonth - salesLastMonth) %></td>
	</tr>
</table>


<p align = "center">Users who bought books twice a month from the same category</p>
<%
	ArrayList<String[]> stringlist = TransactionDB.getCustomers2Genre();
%>
<table width="500">
	<tr>
		<th>User</th>
		<th>Genre</th>
	</tr>
	<%
	for (String[] s:stringlist) {
	%>
	<tr>
		<td><%=s[0] %></td>
		<td><%=s[1] %></td>
	</tr>
	<%
	}
	%>
</table>
</body>
</html>