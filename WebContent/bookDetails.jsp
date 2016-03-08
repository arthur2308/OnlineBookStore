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
	border-collapse: collapse;
	width: 1000;
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
</style>
</head>
<body>
<%
	HttpSession sess = request.getSession();
	User user = (User) sess.getAttribute("User");
	String s_bid = request.getQueryString();
	System.out.println(s_bid);
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
</script>
<form name="addBook" method="post" action="cart/add">
<input type="hidden" name="book" id="book">
</form>
</body>
</html>