<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<html>
<head>
    <link rel="stylesheet" type="text/css" href="default.css" />
    <link rel="stylesheet" type="text/css" href="index.css" />
    <title>RaySys</title>

    <script language="JavaScript" type="text/javascript" src="contentflow.js" load="DEFAULT"></script>
    <script language="JavaScript" src="CalendarPopup.js"></script>
    <script language="JavaScript">document.write(getCalendarStyles());</script>
    <script language="JavaScript">
        var cal = new CalendarPopup("testdiv1");
    </SCRIPT>
</head>

<body onLoad="document.searchForm.searchInput.focus()">

<%@ include file="header.jsp" %>

<div id="content">
    <div id="contentTitle">RaySys</div>
    <div id="searchDiv">
        <form name="searchForm" id="searchForm" action="search.jsp" method="get">
            <input name="searchInput" id="searchInput" value="" type="text"><br />

            <a href="#" onClick="cal.select(document.forms['searchForm'].startDate,'startDateAnchor','dd/MM/yyyy'); cal.showCalendar('startDateAnchor'); return false;" name="anchor1" id="startDateAnchor">Start Date</A>
            <input type="text" name="startDate" id="startDate" value="" SIZE=15>

            <a href="#" onClick="cal.select(document.forms['searchForm'].endDate,'endDateAnchor','dd/MM/yyyy'); cal.showCalendar('endDateAnchor'); return false;" name="endDateAnchor" id="endDateAnchor">End Date</A>
            <input type="text" name="endDate" id="endDate" value="" SIZE=15>
            <input type="checkbox" name="order" id="order" value="date"/>Order By Date
            <br />

            <input name="searchButton" id="searchButton" type="submit" value="Search">
        </form>
	<div id="testdiv1" style="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></div>
    </div>
</div>

<div id="footer">
    <span><a href="about">About RaySys</a>
</div>

</body>

</html>