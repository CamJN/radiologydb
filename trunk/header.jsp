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
    		<jsp:forward page="/login.jsp"/>
    <%}	if(userClass.equals("a")){%>
    		<span><a href="/radiologydb/new-user.jsp">Add User</a></span>
    		<span><a href="/radiologydb/update/index.jsp">Update User</a></span>
    		<span><a href="/radiologydb/report/index.jsp">User Report</a></span>
    <%} else if(userClass.equals("r")){%>
		<span><a href="create-record.jsp">Create Record</a></span>
		<span><a href="upload-image.jsp">Upload Image</a></span>
    <%}%>
    <span style="float:right"><a href="/radiologydb/profile.jsp"><%=session.getAttribute("username")%></a></span>
    <br><span style="float:right"><a href="/radiologydb/logout.jsp">Logout</a></span>
</div>
