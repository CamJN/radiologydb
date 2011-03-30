<html>

<head>

    <link rel="stylesheet" type="text/css" href="default.css" />
    <link rel="stylesheet" type="text/css" href="search.css" />
    <script language="JavaScript" type="text/javascript" src="contentflow.js" load="DEFAULT"></script>
    <title>RaySys</title>
</head>

<body>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="util.ConnectionManager"%>
<%@ page import="java.sql.*"%>

<%@ include file="header.jsp" %>

<%
Connection conn = ConnectionManager.getConnection();
Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
ResultSet rset = stmt.executeQuery("select u.user_name, u.password, u.class, p.first_name, p.last_name, p.address, p.email, p.phone from users u, persons p where u.user_name ='"+ request.getParameter("uname") +"' and u.user_name =  p.user_name");
rset.next();
%>
	<form action="userUpdate" method="post" accept-charset="utf-8">
		<table border="0">
			<tr><th>Update User</th></tr>
			<tr><th>Please update where needed</th></tr>
			<tr valign=top align=left>
				<td>User Name</td>
				<td><%= rset.getString(1) %></td>
				<td><input type="text" name="username" value = <%= rset.getString(1) %> style="display:none"></td>
			</tr>	
			<tr valign=top align=left>
				<td>Password</td>
				<td><input type="text" name="password" value = <%= rset.getString(2) %>></td>	
			</tr>	
			<tr valign=top align=left>
				<td>class</td>
				<td><input type="text" name="class" value = <%= rset.getString(3) %>></td>	
			</tr>
			<tr valign=top align=left>
				<td>First Name</td>
				<td><input type="text" name="fname" value = <%= rset.getString(4) %>></td>	
			</tr>
			<tr valign=top align=left>
				<td>Last Name</td>
				<td><input type="text" name="lname" value = <%= rset.getString(5) %>></td>	
			</tr>
			<tr valign=top align=left>
				<td>Address</td>
				<td><input type="text" name="address" value = <%= rset.getString(6) %>></td>	
			</tr>
			<tr valign=top align=left>
				<td>Email</td>
				<td><input type="text" name="email" value = <%= rset.getString(7) %>></td>	
			</tr>
			<tr valign=top align=left>
				<td>Phone</td>
				<td><input type="text" name="phone" value = <%= rset.getString(8) %>></td>	
			</tr>
		</table>
	<p><input type="submit" value="Update User"></p>
	</form>
<%conn.close();%>
</body>
</html>
