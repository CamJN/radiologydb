<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="search.RecordsQuery"%>
<%@ page import="java.sql.SQLException"%>

<%
    String searchInput = request.getParameter("searchInput");
    if (searchInput == null || searchInput.equals("")) {
%>
    <jsp:forward page="/index.jsp"/>
<%}%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<html>
<head>

    <link rel="stylesheet" type="text/css" href="default.css" />
    <link rel="stylesheet" type="text/css" href="search.css" />
    <script language="JavaScript" type="text/javascript" src="contentflow.js" load="DEFAULT"></script>
    <title>RaySys</title>
</head>

<%@ include file="header.jsp" %>

<div id="content">
    <div id="searchDiv">
        <form name="searchForm" id="searchForm" action="search.jsp" method="get">
            <input name="searchInput" id="searchInput" type="text" value="<%=StringEscapeUtils.escapeHtml(searchInput)%>">
            <input name="searchButton" id="searchButton" type="submit" value="Search">
        </form>
    </div>
    <div id="results">
         <%
             RecordsQuery records = null;
		     try {
				 records = new RecordsQuery(searchInput);
				 
				 if (records.absolute(1)) {
					 out.println("<table id=\"resultsTable\">");
					 out.println("<tr><th>Record ID</th><th>Patient Name</th><th>Doctor Name</th><th>Radiologist Name</th><th>Test Type</th><th>Prescribing Date</th><th>Test Date</th><th>Diagnosis</th><th>Description</th></tr>");
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
					 } while (records.nextRecord());
					 
					 out.println("</table>");
				 } else {
				     out.println("No records containing yuor search terms were found.");
				 }
			 } catch (SQLException e) {
				 out.println(e.getMessage());
				 e.printStackTrace();
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