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
		<section>
			<s:if test="%{listOfMPDUsers.size > 0}">	
			<br>	
				<table class="gridtable">
				<caption>New Accounts Requested</caption>
						<tr>
			    			<th>Name</th>
			    			<th>User Name</th>
			    			<th>Action</th>
			    			
		  				</tr>
		   			 <s:iterator value="listOfMPDUsers">   			  
		     			   <tr>	
		     			   		<td><s:property value="FullName"/></td>
		     			   		<td><s:property value="MPDUserName"/></td>
		     			  		<td>
									<s:form action="AdminNewUserAccounts">
				    					<s:hidden name="theId" value="%{MPDUserID}" />
		    							<s:submit cssClass="button" name="button" value="DELETE" />
		    							<s:submit cssClass="button" name="button" value="ACTIVATE" />    							
									</s:form>		
														
								</td>					
		     			   </tr>
		    		</s:iterator>
				</table>
			</s:if>	
		<s:else>
	   		<h4>No New Accounts</h4>
		</s:else>
	  </section>
	</div>	
	<s:property value="DisplayUserMessage"/>
	
	<%-- 
			<a href="<s:url action="AdminNewUserAccounts">
 					<s:param name="cmd">DELETE</s:param>
 					<s:param name="theId"><s:property value="MPDUserID"/></s:param>
			</s:url>">Delete</a>
	--%> 
 	<%@ include file="/footer.jsp" %>
	</body>
</html>