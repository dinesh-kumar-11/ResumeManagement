<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Resume Search</title>
        <script src="http://code.jquery.com/jquery-latest.js">   
        </script>
        <script>
            $(document).ready(function() {                        
                $('#submit').click(function(event) {  
                    var username=$('#freeTextSearch').val();
                 $.get('ajaxServlet',{user:username},function(responseText) { 
                        $('#welcometext').html(responseText);         
                    });
                });
            });
        </script>
</head>
<body>
<form id="form1">
<h1>Resume Search:</h1>
Type any skill set, Email address or any free text to search in a resume
<input type="text" id="freeTextSearch"/>
<input type="button" id="submit" value="Search"/>
<br/>
<div id="welcometext">
</div>
</form>
</body>
</html>
