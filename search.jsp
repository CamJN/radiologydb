<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="search.RecordsQuery"%>
<%@ page import="java.sql.SQLException"%>

<%
	int MAX_RESULTS = 20;
	int MAX_PAGELINKS = 5;
	
    String SEARCH_INPUT = request.getParameter("searchInput");
    if (SEARCH_INPUT == null || SEARCH_INPUT.equals("")) {
%>      <jsp:forward page="/index.jsp"/>
<%  }

	String START_DATE = request.getParameter("startDate");
	String END_DATE = request.getParameter("endDate");
	boolean ORDERBY_DATE = request.getParameter("order") == null ? false : true;
	
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

    <link rel="stylesheet" type="text/css" href="default.css" />
    <link rel="stylesheet" type="text/css" href="search.css" />
    <script language="JavaScript" type="text/javascript" src="contentflow.js" load="DEFAULT"></script>
    <script language="JavaScript" src="CalendarPopup.js"></script>
    <script language="JavaScript">document.write(getCalendarStyles());</script>
    <script language="JavaScript">
        var cal = new CalendarPopup("testdiv1");
    </SCRIPT>
    <title>RaySys</title>
</head>

<%@ include file="header.jsp" %>

<div id="content">
    <div id="searchDiv">
        <form name="searchForm" id="searchForm" action="search.jsp" method="get">
            <input name="searchInput" id="searchInput" type="text" value="<%=StringEscapeUtils.escapeHtml(SEARCH_INPUT)%>">
            <input name="searchButton" id="searchButton" type="submit" value="Search">
            <br />
                        <a href="#" onClick="cal.select(document.forms['searchForm'].startDate,'startDateAnchor','dd/MM/yyyy'); cal.showCalendar('startDateAnchor'); return false;" name="anchor1" id="startDateAnchor">Start Date</A>
            <input type="text" NAME="startDate" id="startDate" value="<%=StringEscapeUtils.escapeHtml(START_DATE)%>" SIZE=15>

            <a href="#" onClick="cal.select(document.forms['searchForm'].endDate,'endDateAnchor','dd/MM/yyyy'); cal.showCalendar('endDateAnchor'); return false;" name="endDateAnchor" id="endDateAnchor">End Date</A>
            <input type="text" NAME="endDate" id="endDate" value="<%=StringEscapeUtils.escapeHtml(END_DATE)%>" SIZE=15>
            <input type="checkbox" name="order" id="order" value="date" <%if (ORDERBY_DATE) out.print("checked");%>"/>Order By Date
        </form>
        <div id="testdiv1" style="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></div>
    </div>
    <div id="results">
         <%
             RecordsQuery records = null;
		     try {
				 records = new RecordsQuery(SEARCH_INPUT, START_DATE, END_DATE, ORDERBY_DATE);
				 int recordCount = records.getRecordCount();
				 
				 if (records.absolute(START_INDEX)) {
					 out.println("<table id=\"resultsTable\">");
					 out.println("<tr><th>Record ID</th><th>Patient Name</th><th>Doctor Name</th><th>Radiologist Name</th><th>Test Type</th><th>Prescribing Date</th><th>Test Date</th><th>Diagnosis</th><th>Description</th><th>Images</th></tr>");
					 int index = 1;
					 do {
						out.println("<tr class=\"recordRow\">");
						out.println("<td id=\"colRecordID\" class=\"recordColumn\">" + records.getRecordID() + "</td>");
						out.println("<td id=\"colPatientName\" class=\"recordColumn\">" + records.getPatientName() + "</td>");
						out.println("<td id=\"colDoctorName\" class=\"recordColumn\">" + records.getDoctorName() + "</td>");
						out.println("<td id=\"colRadiologistName\" class=\"recordColumn\">" + records.getRadiologistName() + "</td>");
						out.println("<td id=\"colTestType\" class=\"recordColumn\">" + records.getTestType() + "</td>");
						out.println("<td id=\"colPrescribingDate\" class=\"recordColumn\">" + records.getPrescribingDate() + "</td>");
						out.println("<td id=\"colTestDate\" class=\"recordColumn\">" + records.getTestDate() + "</td>");
						out.println("<td id=\"colDiagnosis\" class=\"recordColumn\">" + records.getDiagnosis() + "</td>");
						out.println("<td id=\"colDescription\" class=\"recordColumn\">" + records.getDescription() + "</td>");
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
                        //out.println("<div class=\"scrollbar\">");
                        //out.println("<div class=\"preButton\"></div>");
                        //out.println("<div class=\"nextButton\"></div>");
                        //out.println("<div class=\"slider\"><div class=\"position\"></div></div>");
                        //out.println("</div>");
	                    out.println("</div>");
                        out.println("</tr>");
						index++;
					 } while (records.nextRecord() && index <= MAX_RESULTS );
					 out.println("</table>");
					 
					 
					 out.println("<div id=\"pagination\">");
					 
					 int numPages = (int)Math.ceil((double)recordCount/MAX_RESULTS);
					 int pageNumber = CUR_PAGE;
					 
					 if (CUR_PAGE > 1) {
					 	out.println("<span><a href=\"/radiologydb/search.jsp?searchInput="+SEARCH_INPUT+"&start="+(pageNumber-2)*MAX_RESULTS+"\">Prev</a></span>");
					 }
					 if (CUR_PAGE < numPages) {
						 out.println("<span><a href=\"/radiologydb/search.jsp?searchInput="+SEARCH_INPUT+"&start="+(CUR_PAGE)*MAX_RESULTS+"\">Next</a></span>");
					 }
					 out.println("<br/>");
					 out.println("<span>Showing results "+ (START_INDEX) + " to " + (index+START_INDEX-2) + " of " + recordCount + " results</span>");
					 out.println("</div>");
				 } else {
				     out.println("No records containing your search terms were found.");
				 }
			 } catch (SQLException e) {
				 out.println("No records containing your search terms were found.");
			 } finally {
				 if (records != null) records.close();
			 }
		 %>
    </div>

</div>

<div id="footer">
    <span><a href="about">About RaySys</a>
</div>

</body>

</html>