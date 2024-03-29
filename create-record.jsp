<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<style type="text/css">
th {
    text-align:right
}

form.input, form.textarea {
    width:250px
}
</style>
<script language="JavaScript" src="js/CalendarPopup.js"></script>
<script language="JavaScript">document.write(getCalendarStyles());</script>
<script language="JavaScript">var cal = new CalendarPopup("caldiv");</SCRIPT>
<title>Create a Record</title>
</head>
<%@ include file="header.jsp" %>
<body>
<div id="content">
    <div id="contentTitle">RaySys</div>
    <div id="contentSub">Create Record</div>
    <form name="create-record" method="POST" enctype="multipart/form-data" action="CreateRecord">
        <table align=center>
            <tr>
                <th>Patient_Name:</th>
                <td><input name="patient_name" type="text" size="50" maxlength="24" >
                    </input></td>
            </tr>
            <tr>
                <th>Doctor_Name:</th>
                <td><input name="doctor_name" type="text" size="50" maxlength="24" >
                    </input></td>
            </tr>
            <tr>
                <th>Radiologist_Name:</th>
                <td><input name="radiologist_name" type="text" size="50" maxlength="24" >
                    </input></td>
            </tr>
            <tr>
                <th>Test_Type:</th>
                <td><input name="test_type" type="text" size="50" maxlength="24" >
                    </input></td>
            </tr>
            <tr>
                <th><a href="#" name="prescribing_dateAnchor" id="prescribing_dateAnchor"
onClick="cal.select(document.forms['create-record'].prescribing_date,'prescribing_dateAnchor','yyyy-MM-dd'); cal.showCalendar('prescribing_dateAnchor'); return false;">Prescribing_Date</a>:</th>
                <td><input name="prescribing_date" id="prescribing_date" type="date" size="50" placeholder="yyyy-mm-dd" >
                    </input></td>
            </tr>
            <tr>
                <th><a href="#" name="test_dateAnchor" id="test_dateAnchor"
onClick="cal.select(document.forms['create-record'].test_date,'test_dateAnchor','yyyy-MM-dd'); cal.showCalendar('test_dateAnchor'); return false;">Test_Date</a>:</th>
                <td><input name="test_date" type="date" size="50" maxlength="30" placeholder="yyyy-mm-dd" >
                    </input></td>
            </tr>
            <tr>
                <th>Diagnosis:</th>
                <td><input name="diagnosis" type="text" size="50" maxlength="128" >
                    </input></td>
            </tr>
            <tr>
                <th style="vertical-align:text-top">Description:</th>
                <td><TEXTAREA NAME="description" ROWS="8" COLS="57" maxlength="1024"></TEXTAREA></td>
            </tr>
            <tr>
                <td ALIGN=CENTER COLSPAN="2"><input type="submit" name=".submit" value="Upload" style="width:60px !important"></td>
            </tr>
        </table>
    </form>
</div>
<div id="caldiv"></div>
<%@ include file="footer.jsp"%>
</body>
</html>