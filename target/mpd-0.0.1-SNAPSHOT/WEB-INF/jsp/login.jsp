<%@ include file="/jspheader.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head> 
		<%@ include file="/menubar.jsp" %>
	 <title>
	 	Login
	 </title>
</head>

<body>
	<!-- START HEADER -->
	<%@ include file="/header.jsp" %>	
	<!-- END HEADER -->
	
 
<div style="max-width:40%; margin: 50px 100px 25px 25px; text-align:center;" >
	<form method="post" action="<%= response.encodeURL(request.getContextPath()+"/jsp/login") %>" class="user-input" >
<%-- 
		 <c:url var="imageURL" value="/images/backoff.jpg"/>
 		 <img class="no-margin" src="${imageURL}" alt="Login">	
--%>
	
	 <table>
		 <tr>
		  <td style="text-align:right"><label>User Name:</label></td>
		  <td style="text-align:right"><input type="text" value="${param.j_username }" name="j_username" ></td>		
		</tr>
		 <tr>
			<td colspan="2" style="text-align:left;" >
		  		<c:if test="${not empty nousername}"> 
		  			<span style="text-align:left" class="warning">${nousername}</span>
		  		</c:if>
			</td>
		</tr>		
		 <tr>
		  <td style="text-align:right"><label>Password:</label></td>
		  <%-- 'autocomplete' is a non-HTML attribute, supported by some browsers. Prevents prepopulation of passwords.--%>
		  	<td style="text-align:right">
				<input type="password" value="${param.j_password}" name="j_password" >				
			</td>
		 </tr>
		<tr><td colspan="2" style="text-align:left;" >
				<c:if test="${not empty nopassword}"> 
		  			<span style="text-align:left" id="warning-password" class="warning">${nopassword}</span>
		  		</c:if>
			</td>
		</tr>
		 <tr>
		  <td colspan="2">
				<c:if test="${not empty invalidusernameorpassword }">
				<span style="text-align:center" class="warning"> ${invalidusernameorpassword }</span>
				</c:if>
			</td>
		 </tr>
		<tr>
			<td colspan="2" align="right">
				<input class="button" name="submit" type="submit" value="Login">
				<a class="button" title="Request Account" href="${pageContext.request.contextPath}/jsp/accountrequestform.jsp">New User</a>
			</td>
		</tr>
		
	 </table>
	 	
	 </form>
	
</div>

 	<img src="${pageContext.request.contextPath}/images/fearlessfife.jpg" style="float:inherit; margin-left:400px; margin-right:400px;margin-bottom: 130px;;margin-top: -180px;" alt="Deputy Fife" height="390" width="382" >
	
	
	
	<%@ include file="/footer.jsp" %>
</body>
</html>
