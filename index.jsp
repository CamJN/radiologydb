<!doctype html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<link rel="stylesheet" type="text/css" href="css/index.css" />
<script language="JavaScript" type="text/javascript" src="content-flow/contentflow.js" load="DEFAULT"></script>
<script language="JavaScript" type="text/javascript" src="js/CalendarPopup.js"></script>
<script language="JavaScript" type="text/javascript">document.write(getCalendarStyles());</script>
<script language="JavaScript" type="text/javascript">var cal = new CalendarPopup("caldiv");</script>
<title>RaySys</title>
</head>

<body onLoad="document.searchForm.searchInput.focus()">
<%@ include file="header.jsp" %>

<div id="content">
    <div id="contentTitle">RaySys</div>
    <div id="contentSub">Search Records</div>
    <div id="searchDiv">
        <form name="searchForm" id="searchForm" action="search.jsp" method="get">
            <input name="searchInput" id="searchInput" value="" type="text" />
            <br />
            <a name="anchor1" id="startDateAnchor" href="#" onClick="cal.select(document.forms['searchForm'].startDate,'startDateAnchor','dd/MM/yyyy'); cal.showCalendar('startDateAnchor'); return false;" >Start Date</a>
            <input type="text" name="startDate" id="startDate" value="" size="15" />
            <a name="endDateAnchor" id="endDateAnchor" href="#" onClick="cal.select(document.forms['searchForm'].endDate,'endDateAnchor','yyyy'); cal.showCalendar('endDateAnchor'); return false;">End Date</a>
            <input type="text" name="endDate" id="endDate" value="" size="15" />
            <input type="checkbox" name="order" id="order" value="date"/>Order By Date<br />
            <input name="searchButton" id="searchButton" type="submit" value="Search" />
        </form>
    </div>
    <div id="caldiv"></div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>