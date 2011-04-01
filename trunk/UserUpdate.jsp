<html>
<%
Connection conn = ConnectionManager.getConnection();
Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
ResultSet rset = stmt.executeQuery("select u.user_name, u.password, u.class, p.first_name, p.last_name, p.address, p.email, p.phone from users u, persons p where u.user_name ='"+ request.getParameter("uname") +"' and u.user_name =  p.user_name");
rset.next();
String updateUserClass = rset.getString(3);
String updateUserName = request.getParameter("uname");
%>

<head>

    <link rel="stylesheet" type="text/css" href="default.css" />
    <link rel="stylesheet" type="text/css" href="search.css" />
    <script language="JavaScript" type="text/javascript" src="contentflow.js" load="DEFAULT"></script>
<SCRIPT LANGUAGE="JavaScript">

    <!-- Begin
    function moveOver()  
    {
        var boxLength = document.choiceForm.CurrentDoctors.length;
        var selectedItem = document.choiceForm.Doctors.selectedIndex;
        var selectedText = document.choiceForm.Doctors.options[selectedItem].text;
        var selectedValue = document.choiceForm.Doctors.options[selectedItem].value;
        var i;
        var isNew = true;

        if (boxLength != 0) {
            for (i = 0; i < boxLength; i++) {
                thisitem = document.choiceForm.CurrentDoctors.options[i].text;
                if (thisitem == selectedText) {
                    isNew = false;
                    break;
                }
            }
        } 

        if (isNew) {
            newoption = new Option(selectedText, selectedValue, false, false);
            document.choiceForm.CurrentDoctors.options[boxLength] = newoption;
        }

        <!-- document.choiceForm.Doctors.selectedIndex=-1; 
    }

    function removeMe() {
        var boxLength = document.choiceForm.CurrentDoctors.length;
        arrSelected = new Array();
        var count = 0;
        for (i = 0; i < boxLength; i++) {
            if (document.choiceForm.CurrentDoctors.options[i].selected) {
                arrSelected[count] = document.choiceForm.CurrentDoctors.options[i].value;
            }
            count++;
        }
        var x;
        for (i = 0; i < boxLength; i++) {
            for (x = 0; x < arrSelected.length; x++) {
                if (document.choiceForm.CurrentDoctors.options[i].value == arrSelected[x]) {
                    document.choiceForm.CurrentDoctors.options[i] = null;
                }
            }
            boxLength = document.choiceForm.CurrentDoctors.length;
        }
    }

    function saveMe() {
    	var form = document.forms["userUpdate"];
    	
    	<%
    	if(updateUserClass.equals("p") || updateUserClass.equals("d")){
    	%>
    	var strValues = "";
        var boxLength = document.choiceForm.CurrentDoctors.length;

		
        
        if (boxLength != 0) {
            for (i = 0; i < boxLength; i++) {
	            var hiddenField = document.createElement("input");
		        hiddenField.setAttribute("type", "hidden");
		        hiddenField.setAttribute("name", "uname"+i);
		        hiddenField.setAttribute("value", document.choiceForm.CurrentDoctors.options[i].value);
		
		        form.appendChild(hiddenField);
            }
        }
       
	    document.body.appendChild(form);    // Not entirely sure if this is necessary
		<%}%>
	    form.submit();
    }

    //  End -->
    </script>
        <title>RaySys</title>
</head>

<body>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="util.ConnectionManager"%>
<%@ page import="java.sql.*"%>

<%@ include file="header.jsp" %>


	<form name = "userUpdate" action="userUpdate" method="post" accept-charset="utf-8">
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
	</form>

	<%
	if(updateUserClass.equals("p")){
	
	
		String doctors ="select UNIQUE u.user_name, first_name, last_name from users u, persons p where u.user_name = p.user_name and class = 'd' ORDER BY last_name";
		rset = stmt.executeQuery(doctors);
    	%>
	<center>
    <form name="choiceForm">
        <table border=0>
        <tr>
        <td valign="top" width=175>
        Doctors:
        <br>
        <select name="Doctors" size=10 onchange="moveOver();">
        <%
        while (rset != null && rset.next())
		{
		%>
			<option value= <%=rset.getString(1)%> > <%= rset.getString(3) + ", " + rset.getString(2)%> 
		<%}
		%>
        </select>
        </td>
        <td valign="top">
        User's Doctors:
        <br>
       	<%
       	String patientDoctors = "select UNIQUE doctor_name, last_name, first_name from persons p, family_doctor f where user_name = doctor_name and patient_name='" + updateUserName + "' ORDER BY last_name";
		rset = stmt.executeQuery(patientDoctors);
       	%>
        <select multiple name="CurrentDoctors" style="width:150;" size="10">
            <%
	        while (rset != null && rset.next())
			{
			%>
				<option value= <%=rset.getString(1)%> > <%= rset.getString(2) + ", " + rset.getString(3)%> 
			<%}
			%>
        </select>
        </td>
        </tr>
        <tr>
        <td></td>
        <td colspan=2 height=10>
        <input type="button" value="Remove" onclick="removeMe();">
        </td>
        </tr>
        </table>
    </form>
	</center>
	<%}
	else if(updateUserClass.equals("d")) {
		String patients ="select UNIQUE u.user_name, last_name, first_name from users u, persons p where u.user_name = p.user_name and class = 'p' ORDER BY last_name";
		rset = stmt.executeQuery(patients);
	%>
	<center>
    <form name="choiceForm">
        <table border=0>
        <tr>
        <td valign="top" width=175>
        Patients:
        <br>
        <select name="Doctors" size=10 onchange="moveOver();">
	        <%
	        while (rset != null && rset.next())
			{
			%>
				<option value= <%=rset.getString(1)%> > <%= rset.getString(2) + ", " + rset.getString(3)%> 
			<%}
			%>
        </select>
        </td>
        <td valign="top">
        User's Patients:
        <br>
        <%
       	String doctorPatients = "select UNIQUE patient_name, last_name, first_name from persons p, family_doctor f where user_name = patient_name and doctor_name='" + updateUserName + "' ORDER BY last_name";
		rset = stmt.executeQuery(doctorPatients);
       	%>
        <select multiple name="CurrentDoctors" style="width:150;" size="10">
			<%
	        while (rset != null && rset.next())
			{
			%>
				<option value= <%=rset.getString(1)%> > <%= rset.getString(2) + ", " + rset.getString(3)%> 
			<%}
			%>
        </select>
        </td>
        </tr>
        <tr>
        <td></td>
        <td colspan=2 height=10>
        <input type="button" value="Remove" onclick="removeMe();">
        </td>
        </tr>
        </table>
    </form>
	</center>
	<%}%>
	<center><p><input type="button" value="Update" onclick="saveMe();"></p></center>

<%conn.close();%>
</body>
</html>
