<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="css/default.css" /> 
    <style type="text/css">
      th {text-align:right}
      td {text-align:left}
    </style>
    <title>Upload an Image</title> 
  </head>
  <%@ include file="header.jsp" %>
  <body>
    <h1><%= (null!=request.getParameter("state"))? request.getParameter("state") : ' ' %></h1>
      <h1>Please input or select the path of an image!</h1>
      <form name="upload-image" method="POST" enctype="multipart/form-data" action="UploadImage">
	<table align="center">
	  <tr>
	    <th>File path:</th>
	    <td><input name="file-path" type="file" style="width:100%; background-color: #ffffff;"></input></td>
	  </tr>
	  <tr>
	    <th>Record_id:</th>
	    <td><input name="record_id" type="text" value="<%= (null!=request.getParameter("record_id"))? request.getParameter("record_id") : ' ' %>" style="width:66%"></input><input type="submit" name=".submit" value="Upload" style="width:32%"></td>
	  </tr>
	</table>
      </form>
    <%@ include file="footer.jsp"%>
  </body> 
</html>
