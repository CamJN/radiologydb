<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >

<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="search.DiagnosisQuery"%>
<%@ page import="java.sql.SQLException"%>
<%
	String USER_NAME = (String)session.getAttribute("username");
	String USER_CLASS = (String)session.getAttribute("class");
	String START_DATE = request.getParameter("startDate");
	String END_DATE = request.getParameter("endDate");
	boolean ORDERBY_DATE = request.getParameter("order") == null ? false : true;
	
	int MAX_RESULTS = 20;
	int MAX_PAGELINKS = 5;
	
    String SEARCH_INPUT = request.getParameter("searchInput");
    if (SEARCH_INPUT == null || SEARCH_INPUT.equals("")) {
%>
    <jsp:forward page="/radiologydb/index.jsp"/>
<%  }

	int START_INDEX = 0;
	try {
		START_INDEX = Integer.parseInt(request.getParameter("start"));
	} catch (NumberFormatException e) {}
	START_INDEX++;
	
	int CUR_PAGE = START_INDEX/MAX_RESULTS + 1;
%>

<head>
	<link rel="stylesheet" type="text/css" href="../css/default.css" />
    <link rel="stylesheet" type="text/css" href="../css/search.css" />
    <script language="JavaScript" type="text/javascript" src="../js/contentflow.js" load="DEFAULT"></script>
    <script language="JavaScript" src="../js/CalendarPopup.js" type="text/javascript"></script>
    <script language="JavaScript" type="text/javascript">document.write(getCalendarStyles());</script>
    <script language="JavaScript" type="text/javascript">var cal = new CalendarPopup("caldiv");</script>
</head>

<body>
<%@ include file="../nav.jsp" %>

<div id="content">
    <div id="searchDiv">
        <form name="searchForm" id="searchForm" action="search.jsp" method="get">
            <input name="searchInput" id="searchInput" type="text" value="<%=StringEscapeUtils.escapeHtml(SEARCH_INPUT)%>" />
            <input name="searchButton" id="searchButton" type="submit" value="Search" />
            <br />
            <a name="startDateAnchor" id="startDateAnchor" href="#" onclick="cal.select(document.forms['searchForm'].startDate,'startDateAnchor','dd/MM/yyyy'); cal.showCalendar('startDateAnchor'); return false;">Start Date</a>
            <input type="text" name="startDate" id="startDate" value="<%=StringEscapeUtils.escapeHtml(START_DATE)%>" size="15" />
            <a name="endDateAnchor" id="endDateAnchor" href="#" onclick="cal.select(document.forms['searchForm'].endDate,'endDateAnchor','dd/MM/yyyy'); cal.showCalendar('endDateAnchor'); return false;" >End Date</a>
            <input type="text" name="endDate" id="endDate" value="<%=StringEscapeUtils.escapeHtml(END_DATE)%>" size="15" />
        </form>
    </div>
    <div id="caldiv"></div>
    <div id="results">
         <%
             DiagnosisQuery diagnosis = null;
		     try {
				 diagnosis = new DiagnosisQuery(SEARCH_INPUT, START_DATE, END_DATE);
				 int personCount = diagnosis.getPersonCount();
				 
				 if (diagnosis.absolute(START_INDEX)) {
					 out.println("<table id=\"resultsTable\">");
					 out.println("<tr><th>User Name</th><th>First Name</th><th>Last Name</th><th>Address</th><th>Phone</th><th>Date</th></tr>");
					 int index = 1;
					 do {
						out.println("<tr class=\"recordRow\">");
						out.println("<td id=\"colUserName\" class=\"recordColumn\">" + diagnosis.getUname() + "</td>");
						out.println("<td id=\"colFirstName\" class=\"recordColumn\">" + diagnosis.getFname() + "</td>");
						out.println("<td id=\"colLastName\" class=\"recordColumn\">" + diagnosis.getLname() + "</td>");
						out.println("<td id=\"colAddress\" class=\"recordColumn\">" + diagnosis.getAddress() + "</td>");
						out.println("<td id=\"colPhone\" class=\"recordColumn\">" + diagnosis.getPhone() + "</td>");
						out.println("<td id=\"colDate\" class=\"recordColumn\">" + diagnosis.getDate() + "</td>");
                        out.println("</tr>");
						index++;
					 } while (diagnosis.nextRecord() && index <= MAX_RESULTS );
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
				     out.println("No diagnosis containing your search terms were found.");
				 }
			 } catch (SQLException e) {
				 out.println("No diagnosis containing your search terms were found.");
			 } finally {
				 if (diagnosis != null) diagnosis.close();
			 }
		 %>
    </div>

</div>

<%@ include file="../footer.jsp" %>

</body>

</html>