<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="search.PersonsQuery"%>
<%@ page import="java.sql.SQLException"%>

<%
	int MAX_RESULTS = 20;
	int MAX_PAGELINKS = 5;
	
    String SEARCH_INPUT = request.getParameter("searchInput");
    if (SEARCH_INPUT == null || SEARCH_INPUT.equals("")) {
%>      <jsp:forward page="/index.jsp"/>
<%  }
	
	int START_INDEX = 0;
	try {
		START_INDEX = Integer.parseInt(request.getParameter("start"));
	} catch (NumberFormatException e) {}
	START_INDEX++;
	
	int CUR_PAGE = START_INDEX/MAX_RESULTS + 1;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<head>

    <link rel="stylesheet" type="text/css" href="../css/default.css" />
    <link rel="stylesheet" type="text/css" href="../css/search.css" />
    <script language="JavaScript" type="text/javascript" src="../js/contentflow.js" load="DEFAULT"></script>
    <script language="JavaScript" src="../js/CalendarPopup.js"></script>
    <script language="JavaScript">document.write(getCalendarStyles());</script>
    <script language="JavaScript">
        var cal = new CalendarPopup("caldiv");
    </SCRIPT>
    <title>RaySys</title>
</head>

<%@ include file="../nav.jsp" %>
<%
if(request.getAttribute("updateUserError") != null)
{%>
	<p>Invalid data please try again</p>
	<%
	request.setAttribute("updateUserError", null);
}
%>

<div id="content">
    <div id="searchDiv">
        <form name="userSearchForm" id="userSearchForm" action="user-search.jsp" method="get">
            <input name="searchInput" id="searchInput" type="text" value="<%=StringEscapeUtils.escapeHtml(SEARCH_INPUT)%>">
            <input name="searchButton" id="searchButton" type="submit" value="Search">
            <br />
        </form>
        <div id="caldiv"></div>
    </div>
    <div id="results">
         <%
             PersonsQuery persons = null;
		     try {
				 persons = new PersonsQuery(SEARCH_INPUT);
				 int personCount = persons.getPersonCount();
				 
				 if (persons.absolute(START_INDEX)) {
					 out.println("<table id=\"resultsTable\">");
					 out.println("<tr><th>User Name</th><th>First Name</th><th>Last Name</th><th>Address</th><th>Email</th><th>Phone</th></tr>");
					 int index = 1;
					 do {
						out.println("<tr class=\"recordRow\">");
						out.println("<td id=\"colUserName\" class=\"recordColumn\">" + persons.getUname() + "</td>");
						out.println("<td id=\"colFirstName\" class=\"recordColumn\">" + persons.getFname() + "</td>");
						out.println("<td id=\"colLastName\" class=\"recordColumn\">" + persons.getLname() + "</td>");
						out.println("<td id=\"colAddress\" class=\"recordColumn\">" + persons.getAddress() + "</td>");
						out.println("<td id=\"colEmail\" class=\"recordColumn\">" + persons.getEmail() + "</td>");
						out.println("<td id=\"colPhone\" class=\"recordColumn\">" + persons.getPhone() + "</td>");
						out.println("<td id=\"colEdit\" class=\"recordColumn\"> <A HREF=update.jsp?uname=" + persons.getUname() + "> Edit <A/></td>");
                        
                        out.println("</tr>");
						index++;
					 } while (persons.nextRecord() && index <= MAX_RESULTS );
					 out.println("</table>");
					 
					 
					 out.println("<div id=\"pagination\">");
					 
					 int numPages = (int)Math.ceil((double)personCount/MAX_RESULTS);
					 int pageNumber = CUR_PAGE;
					 
					 if (CUR_PAGE > 1) {
					 	out.println("<span><a href=\"/radiologydb/UpdateUserSearch.jsp?searchInput="+SEARCH_INPUT+"&start="+(pageNumber-2)*MAX_RESULTS+"\">Prev</a></span>");
					 }
					 if (CUR_PAGE < numPages) {
						 out.println("<span><a href=\"/radiologydb/UpdateUserSearch.jsp?searchInput="+SEARCH_INPUT+"&start="+(CUR_PAGE)*MAX_RESULTS+"\">Next</a></span>");
					 }
					 out.println("<br/>");
					 out.println("<span>Showing results "+ (START_INDEX) + " to " + (index+START_INDEX-2) + " of " + personCount + " results</span>");
					 out.println("</div>");
				 } else {
				     out.println("No persons containing your search terms were found.");
				 }
			 } catch (SQLException e) {
				 out.println("No persons containing your search terms were found.");
			 } finally {
				 if (persons != null) persons.close();
			 }
		 %>
    </div>

</div>

<div id="footer">
    <span><a href="about">About RaySys</a>
</div>

</body>

</html>