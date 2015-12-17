<%@ include file="/jspheader.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head> 
	<%@ include file="/menubar.jsp" %>
 <title>
 	Account Requested
 </title>
</head>

<body>
	<!-- START HEADER -->
	<%@ include file="/header.jsp" %>	
	<!-- END HEADER -->
	<div style="padding:4px;">
	<h5>
		<span>Account request has been received.<br>
			 Once information has been reviewed,  an activation email will be sent to ${newaccount.MPDUserName}.<br/>
			 Your email ${newaccount.MPDUserName} will also be your username/login for the website.
			 Remember, with great power, comes great responsibility.
		</span>	
	</h5>
	</div>
	<%@ include file="/footer.jsp" %>
</body>
</html>
