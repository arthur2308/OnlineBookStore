<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "CSS490.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%
HttpSession sess = request.getSession();
User user = (User) sess.getAttribute("User");
if (user == null || !UserDB.isAdmin(user.getUserId())) {
	response.sendRedirect("/index.jsp");
	return;
}
%>
</head>
<body>

</body>
</html>