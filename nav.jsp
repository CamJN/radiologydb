<div id="header">
    <span><a href="index.jsp">Search</a></span>
    <%
    	String userClass;
    	if(session.getAttribute("class") == null)
    		userClass = "n";
    	else{
		userClass = session.getAttribute("class").toString();
		}
    	
    	if(!(userClass.equals("a") || userClass.equals("p") || userClass.equals("r") || userClass.equals("d"))){%>
    		<jsp:forward page="/Login.jsp"/>
    <%}	if(userClass.equals("a")){%>
    		<span><a href="new-user.jsp">Add User</a></span>
    		<span><a href="update/index.jsp">Update User</a></span>
    		<span><a href="report/index.jsp">User Report</a></span>
    <%} else if(userClass.equals("r")){%>
		<span><a href="create_record.jsp">Create Record</a></span>
		<span><a href="upload_image.jsp">Upload Image</a></span>
    <%}%>
    <span style="float:right"><a href="profile.jsp"><%=session.getAttribute("username")%></a></span>
    <br><span style="float:right"><a href="/radiologydb/logout.jsp">Logout</a></span>
</div>