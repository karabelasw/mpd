<%@ include file="/jspheader.jsp" %>
 
<html>
	<head>
		<%@ include file="/menubar.jsp" %>
		<title>
		  Admin Home
		</title>
	</head>
	<body>
		<!-- START HEADER -->
	<%@ include file="/header.jsp" %>	
	<!-- END HEADER -->
	
	<div style="max-width:40%;background:#DADAFF;border-style: solid; border-color:lightgray; margin: 5px 3px 0px 2px; text-align:center; padding: 0 0 1cm 0;">
			<span style="color:Blue;font-weight: bold; font-size:large;">Administer</span>
			<br><br>
  			<a class="button" href="<s:url action="AdminNewUserAccounts.action"/>">New Accounts</a> 
  			<a class="button" href="<s:url action="AdminUserAccounts.action"/>">User Accounts</a> 
  			<a class="button" href="<s:url action="AdminInfractions.action"/>">Infractions</a> 		
		
	</div>
	
 	<%@ include file="/footer.jsp" %>
	</body>
</html>