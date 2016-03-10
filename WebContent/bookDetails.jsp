<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, CSS490.*" %>
<jsp:useBean id="cart" class="CSS490.Cart"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Details</title>
<style>
table {
	border-collapse: collapse;
	width: 1000;
	margin: 10% auto auto;
}

table, td, th {
	border: 1px solid black;
}

td.pic {
	width: 35%;
	rowspan: 2;
}

th.label {
	width: 15%;
	color: red;
}

td.label {
	width: 15%;
	color: red;
}

td.info {
	color: purple;
}

td.price {
	width: 15%;
	color: green;
}

table.ratings {
	
}

.acidjs-rating-stars, .acidjs-rating-stars label::before {
	display: inline-block;
}

.acidjs-rating-stars label:hover, .acidjs-rating-stars label:hover ~
	label {
	color: #189800;
}

.acidjs-rating-stars * {
	margin: 0;
	padding: 0;
}

.acidjs-rating-stars input {
	display: none;
}

.acidjs-rating-stars {
	unicode-bidi: bidi-override;
	direction: rtl;
}

.acidjs-rating-stars label {
	color: #ccc;
}

.acidjs-rating-stars label::before {
	content: "\2605";
	width: 18px;
	line-height: 18px;
	text-align: center;
	font-size: 18px;
	cursor: pointer;
}

.acidjs-rating-stars input:checked ~ label {
	color: #f5b301;
}

.acidjs-rating-disabled {
	
	-webkit-pointer-events: none;
	-moz-pointer-events: none;
	pointer-events: none;
}
span#submit-button {
	float: right;
	text-align: center;
}
textarea#comment {
	width: 92%;
	height: 100%;
	color: gray;
}
div#addcartlink {
	text-align: center;
}
span#comment-text {
	color: gray;
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
	

</style>
</head>
<body>


	  	
<%
	HttpSession sess = request.getSession();
	User user = (User) sess.getAttribute("User");
	String s_bid = request.getQueryString();
	int book_id = -1;
	try {
		book_id = Integer.parseInt(s_bid);
	}
	catch (Exception e) {
		response.sendRedirect("/index.jsp");
		return;
	}
	Book book = BookDB.getBook(book_id);
	if (book == null) {
		response.sendRedirect("/index.jsp");
		return;
	}
	BookRatingDB.getRating(book);
	
	int user_id = -1;
	if (user != null) {
		user_id = user.getUserId();
	}
	int avgRating = 0;
%>

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
			
			  <li><a href="#" class="toplink">Category</a>
			    <ul>
			      <li><a href="javascript:category('Adventure')" class="toplink">Adventure</a></li>
			      <li><a href="javascript:category('Fantasy')" class="toplink">Fantasy</a></li>
			      <li><a href="javascript:category('Comedy')" class="toplink">Comedy</a></li>
			      <li><a href="javascript:category('Literature')" class="toplink">Literature</a></li>
			      <li><a href="javascript:category('Science')" class="toplink">Science</a></li>
			    </ul>
			  </li>
			
			</ul>
			
			
	  	</div>
	  	
<table width="1000" height="300">
	<tr>
		<td class="pic" rowspan="7">X</td>
		<th class="label">Title</th>
		<td class="info" colspan="2"><%=book.getTitle()%></td>
	</tr>
	<tr>
		<th class="label">Author</th>
		<td class="info" colspan="2"><%=book.getAuthor()%></td>
	</tr>
	<tr>
		<th class="label">Genre</th>
		<td class="info" colspan="2"><%=book.getCategory()%></td>
	</tr>
	<tr>
		<th class="label">Publisher</th>
		<td class="info" colspan="2"><%=book.getPublisher()%></td>
	</tr>
	<tr>
		<th class="label">Publish year</th>
		<td class="info" colspan="2"><%=book.getPublishYear()%></td>
	</tr>
	<tr>
		<th class="label">Average rating</th>
		<td><%=book.getAvgRating()%></td>
		<td class="price" rowspan="2"><div id="addcartlink">
			<a href="javascript:addCart('<%=book_id%>');">[add cart]</a></div></td>
	</tr>
	<tr>
		<th class="label">Price</th>
		<td class="price"><%=book.getPrice()%></td>
	</tr>
</table>

<table class="ratings" width="1000">
	<tr>
		<td class="label">Leave a rating!</td>
		<td rowspan="2"><textarea id="comment" name="comment" form="setRating" 
				onfocus="clearContents(this);">Please leave a comment.</textarea>
			<span id="submit-button"><input type="submit" id="submit" form="setRating" 
			style="height: 40px; top: 50%;"></span>
		</td>
	</tr>
	<tr>
			<td>
				<div class="acidjs-rating-stars test">
					<form name="setRating" id="setRating" method="post" action="bookrating/set"
					 onsubmit="return ratingCheck();">
						<input type="radio" name="rating" id="group-1-0" value="5" /><label for="group-1-0"></label>
						<input type="radio" name="rating" id="group-1-1" value="4" /><label for="group-1-1"></label>
						<input type="radio" name="rating" id="group-1-2" value="3" /><label for="group-1-2"></label>
						<input type="radio" name="rating" id="group-1-3" value="2" /><label for="group-1-3"></label>
						<input type="radio" name="rating" id="group-1-4" value="1" /><label for="group-1-4"></label>
						<input type="hidden" name="book1" id="book1" value="<%=book_id%>">
						<input type="hidden" name="user1" id="user1" value="<%=user_id%>">
					</form>
				</div>
			</td>
		</tr>
	<%
		ArrayList<BookRating> ratingList = book.getRating();
		for(BookRating br:ratingList){
			avgRating++;
	%>
			<tr>
				<td>User rating:</td>
				<td rowspan="2"><%=br.getComment()%></td>
			</tr>
			<tr>
				<td>
					<div class="acidjs-rating-stars acidjs-rating-disabled">
    					<form>
    						<%
    						for (int i = 5; i > 0; --i) {
    							if (i == br.getRating()) {
    								%>
    								<input disabled="disabled" type="radio" checked="checked" name="group-3" 
    								id="group-3-<%=i%>" value="<%=i%>" /><label for="group-3-<%=i%>"></label>
    								<%
    							} else {
    								%>
    								<input disabled="disabled" type="radio" name="group-3" 
    								id="group-3-<%=i%>" value="<%=i%>" /><label for="group-3-<%=i%>"></label>
    								<%
    							}
    						}
    						%>
   						</form>
					</div>
				</td>
			</tr>
	<%
		}
	%>
</table>
<script>	
	function ratingCheck() {
		var rate = document.getElementsByName('rating');
		var rating;
		for (var i = 0; i < rate.length; i++) {
			if (rate[i].checked) {
				rating = rate[i].value;
			}
		}
		if (rating == null) {
			alert("Please select a rating!");
			return false;
		}
		if (<%=user_id%> < 1) {
			alert("Please log in.");
			return false;
		}
		else {
			return true;
		}
	}
	
	function addCart(book) {
		document.getElementById("book").value = book;
		document.addBook.submit();
	}
	
	function clearContents(element) {
		element.value = '';
		element.style = "color: black;";
	}

function category(genre) {
	document.getElementById("category").value = genre;
	document.category.submit();
}
</script>
<form name="category" method = "post" action = "book/category">
<input type = "hidden" name = "category" id="category">
</form>
<form name="addBook" method="post" action="cart/add">
<input type="hidden" name="book" id="book">
</form>
</body>
</html>