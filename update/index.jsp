<!DOCTYPE html>
<html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="../css/default.css" />
    <link rel="stylesheet" type="text/css" href="../css/index.css" />
    <title>RaySys</title>

    <script language="JavaScript" type="text/javascript" src="contentflow.js" load="DEFAULT"></script>
</head>

<body onLoad="document.searchForm.searchInput.focus()">

<%@ include file="../header.jsp" %>
	
<div id="content">
	<div id="contentTitle">RaySys</div>
    <div id="contentSub">User Search</div>
    <div id="searchDiv">
        <form name="userSearchForm" id="userSearchForm" action="user-search.jsp" method="get">
            <input name="searchInput" id="searchInput" value="" type="text"><br />

            <input name="searchButton" id="searchButton" type="submit" value="Search">
        </form>
	<div id="testdiv1" style="position:absolute;visibility:hidden;background-color:white;layer-background-color:white;"></div>
    </div>
</div>

<%@ include file="../footer.jsp" %>
</body>

</html>