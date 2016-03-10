<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, CSS490.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Modify Book</title>
</head>
<body>

<%

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
 
%>
      
      <form method="post" name="form1" id="form1" action="book/modify">
	     
	     <input type="hidden" id="book_id" name="book_id" value="<%=book_id%>"  >
	     
        <label for="author">Author </label>
        <input type="text" id="author" name="author" value="<%=book.getAuthor()%>" />
        <div id="feedback1" class="feedback"></div>

        <label for="title">Title  </label>
        <input type="text" id="title" name="title" value="<%=book.getTitle()%>" />
        <div id="feedback2" class="feedback"></div>
        
       <label for="category">Category  </label>
        <input type="text" id="category" name="category" value="<%=book.getCategory()%>" />
        <div id="feedback2" class="feedback"></div>
        
        <label for="inventory">Inventory </label>
        <input type="number" id="inventory" name="inventory" value="<%=book.getInventory()%>" />
        <div id="feedback3" class="feedback"></div>

        <label for="price">Price </label>
        <input type="number" step="any" id="price" name="price" value="<%=book.getPrice()%>" />
        <div id="feedback4" class="feedback"></div>
        
        <label for="publisher">Publisher </label>
        <input type="text" id="publisher" name="publisher" value="<%=book.getPublisher()%>" />
        <div id="feedback5" class="feedback"></div>
        
        <label for="publisherYear">PublisherYear </label>
        <input type="number" id="publishYear" name="publishYear" value="<%=book.getPublishYear()%>"  />
        <div id="feedback5" class="feedback"></div>
        
       <input type="submit" id="submit" value="Modify Book"/>
       
       </form> 
       
         			<form action="book/searchByTitleAdmin" method="post" id = "QueryForm">
				<input type="hidden" style="width: 300px; height: 20px;" name="title" id = "title" value= "">
		  		<input type="submit" id = "Cancel" value="Cancel"> 
		  		</form>
</body>
</html>