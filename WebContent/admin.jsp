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
}
table, td, th {
	border: 1px solid black;
}
</style>
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

<%
double salesThisWeek = TransactionDB.getSalesThisWeek();
double salesLastWeek = TransactionDB.getSalesLastWeek();
double salesThisMonth = TransactionDB.getSalesThisMonth();
double salesLastMonth = TransactionDB.getSalesLastMonth();

%>
<p>Weekly and monthly profit</p>
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


<p>List of users who bought from the same category more than twice a month</p>
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