<!DOCTYPE html>
<html>
<head>

    <link rel="stylesheet" type="text/css" href="css/default.css" />
    <link rel="stylesheet" type="text/css" href="css/search.css" />
    <title>RaySys</title>
</head>

<body>
<%@ include file="header.jsp" %>
<%
if(session.getAttribute("invalidNewUser") != null && (Boolean)session.getAttribute("invalidNewUser") != true)
{
	session.setAttribute("invalidNewUser", false);
}

if(!(userClass.equals("a"))){
	response.sendRedirect("/radiologydb/");
}
if(request.getAttribute("newUserError") != null)
{%>
	<p>Invalid data please try again</p>
	<%
	request.setAttribute("newUserError", null);
}
%>
<div style="text-align:center;">
	<div id="contentTitle">RaySys</div>
    <div id="contentSub">New User</div>
	<form action="newUser" method="post" accept-charset="utf-8">
		<table border="0" style="margin:auto">
			<tr valign=top align=left>
				<td>User Name</td>
				<td><input type="text" name="username" maxlength="24"></td>	
			</tr>	
			<tr valign=top align=left>
				<td>Password</td>
				<td><input type="text" name="password" maxlength="24"></td>	
			</tr>	
			<tr valign=top align=left>
				<td>class</td>
				<td><input type="text" name="class" maxlength="1"></td>	
			</tr>
			<tr valign=top align=left>
				<td>First Name</td>
				<td><input type="text" name="fname" maxlength="24"></td>	
			</tr>
			<tr valign=top align=left>
				<td>Last Name</td>
				<td><input type="text" name="lname" maxlength="24"></td>	
			</tr>
			<tr valign=top align=left>
				<td>Address</td>
				<td><input type="text" name="address" maxlength="128"></td>	
			</tr>
			<tr valign=top align=left>
				<td>Email</td>
				<td><input type="text" name="email" maxlength="128"></td>	
			</tr>
			<tr valign=top align=left>
				<td>Phone</td>
				<td><input type="text" name="phone" maxlength="10"></td>
			</tr>
			<trt>
				<td></td><td align =right><input type="submit" value="Add User"></td>
			</tr>
		</table>
	
	</form>
</div>
<%@ include file="../footer.jsp" %>
</body>
</html>
