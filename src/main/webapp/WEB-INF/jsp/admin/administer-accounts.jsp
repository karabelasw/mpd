<%@ include file="/jspheader.jsp" %>
 
<html>
	<head>
		<%@ include file="/menubar.jsp" %>
		<title>
		   New User Accounts Requested
		</title>
	</head>
	<body>
		<!-- START HEADER -->
	<%@ include file="/header.jsp" %>	
	<!-- END HEADER -->  	
	<br>

	<a class="button" href="${pageContext.request.contextPath}/jsp/admin/GoAdminHome.action"><span>Go Back</span></a>	
	<div>
		
		<s:if test="%{listOfMPDUsers.size > 0}">	
		<br>	
		<table class="gridtable">
		<caption>User Accounts</caption><span class="warning"><s:property value="mpdUser" /> <s:property value="DisplayUserMessage"/>	</span>
				<tr>
	    			<th>First Name</th>
	    			<th>Last Name</th> 
	    			<th>User Name</th>
	    			<th>Status</th>
	    			<th>Action</th>	    			
  				</tr>
   			 <s:iterator value="listOfMPDUsers">   			  
     			   <tr>	
     			   		<td><s:property value="FirstName"/></td>
     			   		<td><s:property value="LastName"/></td>
     			   		<td><s:property value="MPDUserName"/></td>
     			   		<td>
     			   			<s:if test="locked eq 0">Active</s:if>
     			   		<!--  <img src="${pageContext.request.contextPath}/images/do-not-enter-access-denied.png" alt="Locked" height="32" width="32"> -->
     			   			<s:else>Locked</s:else>     			   		
     			   		</td>
     			   		<td>
	     			   		<form onsubmit="return true;" action="AdminUserAccounts.action" method="post">
			    					<input type="hidden" name="theId" value="<s:property value='MPDUserID'/>" />
			    					<input type="hidden" name="mpdUser" value="<s:property value='MPDUserName'/>" />
	    							<s:if test="locked eq 0">
	    								<input type="submit" name="button" value="Lock" />
    								</s:if>
	    							<s:else>
	    								<input type="submit" name="button" value="Unlock">
	    							</s:else>
	    							<input type="submit" name="button" value="Reset Password">							
							</form>     			   		
     			   		</td>     			  							
     			   </tr>
    		</s:iterator>
		</table>
	</s:if>	
	<s:else>
   		<h4></h4>
	</s:else>
	 
	</div>	
	
	<%-- 
			<a href="<s:url action="AdminNewUserAccounts">
 					<s:param name="cmd">DELETE</s:param>
 					<s:param name="theId"><s:property value="MPDUserID"/></s:param>
			</s:url>">Delete</a>
	--%> 
 	<%@ include file="/footer.jsp" %>
	</body>
</html>