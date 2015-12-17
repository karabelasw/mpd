<%@ include file="/jspheader.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
	
<html>
 
	<head>
		<%@ include file="/menubar.jsp" %>
    	<title>Change Password</title>
		<s:head /> 
	</head>

	<body>
	<!-- START HEADER -->
	<%@ include file="/header.jsp" %>	
	<!-- END HEADER -->	
	    <hr>
		<h4>Change Password</h4> 	
		<div>
			<s:form action="Changepassword">
		    		<s:password cssStyle="background:#DADAFF; solid; border-color:lightgray; " name="newPassword" label="New Password"/>
		    		<s:password cssStyle="background:#DADAFF; solid; border-color:lightgray; " name="confirmPassword" label="Confirm New Password"/>
		    		<s:password cssStyle="background:#DADAFF; solid; border-color:lightgray; " name="currentPassword" label="Current Password"/>
    				<s:submit cssClass="button" />
			</s:form>		
		</div>
	    <hr>
	    
	    <%@ include file="/footer.jsp" %>	
	</body>
	
</html>
