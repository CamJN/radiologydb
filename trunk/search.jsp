<!doctype html>
<html>

<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="search.RecordsQuery"%>
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
    <jsp:forward page="/index.jsp"/>
<%  }

	int START_INDEX = 0;
	try {
		START_INDEX = Integer.parseInt(request.getParameter("start"));
	} catch (NumberFormatException e) {}
	START_INDEX++;
	
	int CUR_PAGE = START_INDEX/MAX_RESULTS + 1;
%>

<head>
	<%@ include file="header.html" %>
    <link rel="stylesheet" type="text/css" href="search.css" />
    <script language="JavaScript" type="text/javascript" src="contentflow.js" load="DEFAULT"></script>
    <script language="JavaScript" src="CalendarPopup.js" type="text/javascript"></script>
    <script language="JavaScript" type="text/javascript">document.write(getCalendarStyles());</script>
    <script language="JavaScript" type="text/javascript">var cal = new CalendarPopup("caldiv");</script>
</head>

<body>
<%@ include file="header.jsp" %>

<div id="content">
    <div id="searchDiv">
        <form name="searchForm" id="searchForm" action="search.jsp" method="get">
            <input name="searchInput" id="searchInput" type="text" value="<%=StringEscapeUtils.escapeHtml(SEARCH_INPUT)%>" />
            <input name="searchButton" id="searchButton" type="submit" value="Search" />
            <br />
            <a name="startDateAnchor" id="startDateAnchor" href="#" onClick="cal.select(document.forms['searchForm'].startDate,'startDateAnchor','dd/MM/yyyy'); cal.showCalendar('startDateAnchor'); return false;">Start Date</a>
            <input type="text" name="startDate" id="startDate" value="<%=StringEscapeUtils.escapeHtml(START_DATE)%>" size="15" />
            <a name="endDateAnchor" id="endDateAnchor" href="#" onClick="cal.select(document.forms['searchForm'].endDate,'endDateAnchor','dd/MM/yyyy'); cal.showCalendar('endDateAnchor'); return false;" >End Date</a>
            <input type="text" name="endDate" id="endDate" value="<%=StringEscapeUtils.escapeHtml(END_DATE)%>" size="15" />
            <input type="checkbox" name="order" id="order" value="date" <%if (ORDERBY_DATE) out.print("checked");%>/>
            Order By Date
        </form>
    </div>
    <div id="caldiv"></div>
    <div id="results"><%
	RecordsQuery records = null;
	 try {
		 records = new RecordsQuery(SEARCH_INPUT, USER_NAME, USER_CLASS, START_DATE, END_DATE, ORDERBY_DATE);
		 int recordCount = records.getRecordCount();
		 
		 if (records.absolute(START_INDEX)) {
			 out.println("<table id=\"resultsTable\">");
			 out.println("<tr><th>Record ID</th><th>Patient Name</th><th>Doctor Name</th><th>Radiologist Name</th><th>Test Type</th><th>Prescribing Date</th><th>Test Date</th><th>Diagnosis</th><th>Description</th><th>Images</th></tr>");
			 int index = 1;
			 do {
				out.println("<tr class=\"recordRow\">");
				out.println("<td id=\"colRecordID\">" + records.getRecordID() + "</td>");
				out.println("<td id=\"colPatientName\">" + records.getPatientName() + "</td>");
				out.println("<td id=\"colDoctorName\">" + records.getDoctorName() + "</td>");
				out.println("<td id=\"colRadiologistName\">" + records.getRadiologistName() + "</td>");
				out.println("<td id=\"colTestType\">" + records.getTestType() + "</td>");
				out.println("<td id=\"colPrescribingDate\">" + records.getPrescribingDate() + "</td>");
				out.println("<td id=\"colTestDate\">" + records.getTestDate() + "</td>");
				out.println("<td id=\"colDiagnosis\">" + records.getDiagnosis() + "</td>");
				out.println("<td id=\"colDescription\">" + records.getDescription() + "</td>");
				out.println("<td id=\"colThumbnails\">");
				out.println("<div id=\"contentFlow\" class=\"ContentFlow\" useAddOns=\"DEFAULT\">");
				out.println("<div class=\"flow\">");

				String[] thumbnailURLs = records.getThumbnailURLs();
				String[] fullsizeURLs = records.getFullsizeURLs();
				
				int imageCount = thumbnailURLs.length;
				for (int i = 0; i < imageCount; i++) {
					out.println("<div class=\"item\">");
					out.println("<img class=\"content\" src=\""+thumbnailURLs[i]+"\" href=\""+fullsizeURLs[i]+"\" target=\"_blank\">");
					out.println("<div class=\"caption\">yo</div></div>");
				}

				out.println("</div>");
				out.println("</div>");
				out.println("</tr>");
				index++;
			 } while (records.nextRecord() && index <= MAX_RESULTS );
			 
			 out.println("</table>");
			 out.println("<div id=\"pagination\">");
			 
			 int numPages = (int)Math.ceil((double)recordCount/MAX_RESULTS);
			 int pageNumber = CUR_PAGE;
			 
			 if (CUR_PAGE > 1) {
				out.println("<span><a href=\"/radiologydb/search.jsp?searchInput="+SEARCH_INPUT+"&start="+(pageNumber-2)*MAX_RESULTS+"\">Prev</a></span>");		     }
				
			 if (CUR_PAGE < numPages) {
				 out.println("<span><a href=\"/radiologydb/search.jsp?searchInput="+SEARCH_INPUT+"&start="+(CUR_PAGE)*MAX_RESULTS+"\">Next</a></span>");
			 }
			 
			 out.println("<br/>");
			 out.println("<span>Showing "+ (START_INDEX) + " - " + (index+START_INDEX-2) + " of " + recordCount + " results</span>");
			 out.println("</div>");
		 } else {
			 out.println("No records containing your search terms were found. lol");
		 }
	 } catch (SQLException e) {
		 out.println("No records containing your search terms were found." + e.getMessage());
	 } finally {
		 if (records != null) records.close();
	 }
    %></div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>