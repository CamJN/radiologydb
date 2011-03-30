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
    		<span><a href="NewUser.jsp">Add User</a></span>
    		<span><a href="UserSearchIndex.jsp">Update User</a></span>
    <%} else if(userClass.equals("r")){%>
		<span><a href="createRecord.jsp">Create Record</a></span>
		<span><a href="uploadImage.jsp">Upload Image</a></span>
    <%}%>
    <span style="float:right"><%=session.getAttribute("username")%></span>
    <br><span style="float:right"><a href="/radiologydb/Logout.jsp">Logout</a></span>
</div>