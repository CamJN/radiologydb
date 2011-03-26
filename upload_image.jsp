<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
  <head> 
    <title>Upload image</title> 
  </head>
  <%@ include file="header.jsp" %>
  <body> 

    <h4> The following can be used to insert an image of BLOB type into an Oracle database.</h4>
    Note that (1) the table in the database is created by 
    <pre>
      CREATE TABLE pacs_images (
         record_id   int,
         image_id    int,
         thumbnail   blob,
         regular_size blob,
         full_size    blob,
         PRIMARY KEY(record_id,image_id),
         FOREIGN KEY(record_id) REFERENCES radiology_record
      );
    </pre>
    (2) an SQL sequence in the database is created by
    <pre>
      CREATE SEQUENCE pic_id_sequence;
    </pre>
    <p>
      <hr>
      Please input or select the path of the image!
      <form name="upload-image" method="POST" enctype="multipart/form-data" action="servlet/UploadImage">
	<table>
	  <tr>
	    <th>File path: </th>
	    <td><input name="file-path" type="file" size="30" ></input></td>
	    <th>Record_id: </th>
	    <td><input name="record_id" type="text" size="5" ></input></td>
	  </tr>
	  <tr>
	    <td ALIGN=CENTER COLSPAN="2"><input type="submit" name=".submit" value="Upload"></td>
	  </tr>
	</table>
      </form>
  </body> 
</html>
