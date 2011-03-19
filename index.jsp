<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" >
<html>
<head>
    <link rel="stylesheet" type="text/css" href="default.css" />
    <link rel="stylesheet" type="text/css" href="index.css" />
    <title>RaySys</title>
</head>

<body onLoad="document.searchForm.searchInput.focus()">

<%@ include file="header.jsp" %>

<div id="content">
    <div id="contentTitle">RaySys</div>
    <div id="searchDiv">
        <form name="searchForm" id="searchForm" action="search.jsp" method="get">
            <input name="searchInput" id="searchInput" type="text"><br />
            <input name="searchButton" id="searchButton" type="submit" value="Search">
        </form>
    </div>
</div>

<div id="footer">
    <span><a href="about">About RaySys</a>
</div>

</body>

</html>