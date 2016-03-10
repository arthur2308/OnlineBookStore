<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "java.util.*, CSS490.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Book</title>
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
      <h1>Are you sure you want to delete this Book?</h1>
      <form method="post" name="form1" id="form1" action="book/delete">
	     <input type="hidden" id="book_id" name="book_id" value="<%=book_id%>"  >
	     
        <label for="author"><%=book.getAuthor()%></label>
        <div id="feedback1" class="feedback"></div>

        <label for="title"><%=book.getTitle()%></label>
        <div id="feedback2" class="feedback"></div>
        
       <label for="category"><%=book.getCategory()%> </label>
        <div id="feedback2" class="feedback"></div>
        
        <label for="inventory"><%=book.getInventory()%></label>
        <div id="feedback3" class="feedback"></div>

        <label for="price"><%=book.getPrice()%></label>
        <div id="feedback4" class="feedback"></div>
        
        <label for="publisher">"<%=book.getPublisher()%>"</label>
        <div id="feedback5" class="feedback"></div>
        
        <label for="publisherYear"><%=book.getPublishYear()%></label>
        <div id="feedback5" class="feedback"></div>
        
       <input type="submit" id="submit" value="Delete Book"/>
     
       
       </form> 
       
       			<form action="book/searchByTitleAdmin" method="post" id = "QueryForm">
				<input type="hidden" style="width: 300px; height: 20px;" name="title" id = "title" value= "">
		  		<input type="submit" id = "Cancel" value="Cancel"> 
		  		</form>
</body>
</html>