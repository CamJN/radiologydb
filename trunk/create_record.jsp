<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
  <head> 
    <title>Create a Record</title> 
  </head>
  <%@ include file="header.jsp" %>
  <body> 
    <form name="create-record" method="POST" enctype="multipart/form-data" action="servlet/CreateRecord">
      Please enter record data!
      <table>
	<tr>
	  <th>Record_id: </th>
	  <td><input type="number" min="0" size="24" name="record_id"></input></td>
	</tr>
	<tr>  
	  <th>Patient_Name: </th>
	  <td><input name="patient_name" type="text" size="24" ></input></td>
	</tr>
	<tr>
	  <th>Doctor_Name: </th>
	  <td><input name="doctor_name" type="text" size="24" ></input></td>
	</tr>
	<tr>
	  <th>Radiologist_Name: </th>
	  <td><input name="radiologist_name" type="text" size="24" ></input></td>
	</tr>
	<tr>
	  <th>Test_Type: </th>
	  <td><input name="test_type" type="text" size="24" ></input></td>
	</tr>
	<tr>
	  <th>Prescribing_Date: </th>
	  <td><input name="prescribing_date" type="text" size="30" placeholder="yyyy-mm-dd" ></input></td>
	</tr>
	<tr>
	  <th>Test_Date: </th>
	  <td><input name="test_date" type="text" size="30" placeholder="yyyy-mm-dd" ></input></td>
	</tr>
	<tr>
	  <th>Diagnosis: </th>
	  <td><input name="diagnosis" type="text" size="128" ></input></td>
	</tr>
	<tr>
	  <th>Description: </th>
	  <td>
	    <TEXTAREA NAME="description" ROWS="8" COLS="128"></TEXTAREA>
	  </td>
	</tr>
	<tr>
	  <td ALIGN=CENTER COLSPAN="2"><input type="submit" name=".submit" value="Upload"></td>
	</tr>
      </table>
    </form>
  </body> 
</html>
