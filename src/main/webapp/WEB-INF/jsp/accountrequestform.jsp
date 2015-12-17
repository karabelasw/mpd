<%@ include file="/jspheader.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head> 
		<%@ include file="/menubar.jsp" %>
 <title>
 Request Account
 </title>
</head>

<body>
	<!-- START HEADER -->
	<%@ include file="/header.jsp" %>	
	<!-- END HEADER -->
<div style="padding:4px;">
	<form method="post" action="<%= response.encodeURL(request.getContextPath()+"/jsp/accountrequestform") %>" class="user-input" >
	 <table width="25%">
		<caption align="left" >Request Account:</caption>
			 <tr>
				  <td><label>First Name:</label></td>
				  <td><input type="text" value="${param.firstname}" name="firstname">
					<c:if test="${not empty nofirstname}"> 
		  				<span style="text-align:left" id="warning-nofirstname" class="warning">${nofirstname}</span>
		  			</c:if>
				  </td>
			 </tr>
			<tr>
			  <td><label>Middle Name:</label></td>
			  <td><input type="text" value="${param.middlename }" name="middlename"></td>
		 	</tr>
			<tr>  
				<td><label>Last Name:</label></td>		  
			  	<td>
					<input type="text" value="${param.lastname }" name="lastname" >
					<c:if test="${not empty nolastname}"> 
		  				<span style="text-align:left" id="warning-nolastname" class="warning">${nolastname}</span>
		  			</c:if>
				</td>
			 </tr>
			<tr>  
				<td><label>Username/Email Address:</label></td>		  
			  	<td>
					<input type="text" value="${param.username }" name="username" >
					<c:if test="${not empty nousername}"> 
		  				<span style="text-align:left" id="warning-nousername" class="warning">${nousername}</span>
		  			</c:if>
					<c:if test="${not empty usernameexists}"> 
		  				<span style="text-align:left" id="warning-accountExists" class="warning">${usernameexists}</span>
		  			</c:if>
					<c:if test="${not empty invalidemail}"> 
		  				<span style="text-align:left" id="warning-invalidemail" class="warning">${invalidemail}</span>
		  			</c:if>
				</td>
			 </tr>

			<tr>  
				<td><label>Password:</label></td>		  
			  	<td>
					<input type="password" value="${param.password }" name="password" >
					<c:if test="${not empty nopassword}"> 
		  				<span style="text-align:left" id="warning-password" class="warning">${nopassword}</span>
		  			</c:if>
				</td>
			 </tr>
			<tr>  
				<td><label>Re-Enter Password:</label></td>		  
			  	<td>
					<input type="password" value="${param.confirmpassword }" name="confirmpassword" >
					<c:if test="${not empty noconfirmpassword}"> 
		  				<span style="text-align:left" id="warning-confirmpassword" class="warning">${noconfirmpassword}</span>
		  			</c:if>
					<c:if test="${not empty passwordsmismatch}"> 
		  				<span style="text-align:left" id="warning-passwordsmismatch" class="warning">${passwordsmismatch}</span>
		  			</c:if>

				</td>
			 </tr>

			 <tr>	
			  <td align="right" colspan="2"><input class="button" name="submit" type="submit" value="Submit Request"></td>
			 </tr>				
	 </table>
	 </form>

</div>
	<%@ include file="/footer.jsp" %>
</body>
</html>
