<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert Book</title>
</head>
<body>
      
      <form method="post" name="form1" id="form1" action="book/create">
	
        <label for="author">Author </label>
        <input type="text" id="author" name="author" />
        <div id="feedback1" class="feedback"></div>

        <label for="title">Title  </label>
        <input type="text" id="title" name="title"  />
        <div id="feedback2" class="feedback"></div>
        
       <label for="category">Category  </label>
        <input type="text" id="category" name="category"  />
        <div id="feedback2" class="feedback"></div>
        
        <label for="inventory">Inventory </label>
        <input type="number" id="inventory" name="inventory"  />
        <div id="feedback3" class="feedback"></div>

        <label for="price">Price </label>
        <input type="number" step="any" id="price" name="price"  />
        <div id="feedback4" class="feedback"></div>
        
        <label for="publisher">Publisher </label>
        <input type="text" id="publisher" name="publisher"  />
        <div id="feedback5" class="feedback"></div>
        
        <label for="publisherYear">PublisherYear </label>
        <input type="number" id="publishYear" name="publishYear"  />
        <div id="feedback5" class="feedback"></div>
        
       <input type="submit" id="submit" value="Create Book"/>
       </form> 
       
        <form action="book/searchByTitleAdmin" method="post" id = "QueryForm">
	    <input type="hidden" style="width: 300px; height: 20px;" name="title" id = "title" value= "">
		 <input type="submit" id = "Cancel" value="Cancel"> 
		  		</form>
</body>
</html>