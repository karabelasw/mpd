<%@ include file="/jspheader.jsp" %>
 
<html>
	<head>
		<%@ include file="/menubar.jsp" %>
		<title>
		District of Columbia Vehicle Parking and Moving Infractions
		</title>
	</head>
	<body>
		<!-- START HEADER -->
	<%@ include file="/header.jsp" %>	
	<!-- END HEADER -->			
	<br/>
		<a class="button" href="${pageContext.request.contextPath}/jsp/admin/GoAdminHome.action"><span>Go Back</span></a>		
	<br/>	
		<table border="1" width="100%">	
			<caption>Administer Infractions</caption>
			<tr>
			   <th>Code</th>
			   <th>Description</th>
				<th>Short Description</th>
			   <th>Fine</th>
			</tr>		
			<%-- --%>			
			<c:forEach var="aninfraction" items="${lstinfractions}">	
			<tr>			   
			   <td><c:out value="${aninfraction.code}"/></td>
			   <td><c:out value="${aninfraction.description}"/></td>
			   <td><c:out value="${aninfraction.shortDescription}"/></td>
			   <td>$<c:out value="${aninfraction.fine}"/></td>
			   <td>
		   		<form onsubmit="return true;" action="AdminInfractions.action" method="get">
			    					<input type="hidden" name="theId" value="<s:property value='MPDUserID'/>" />
			    					
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
			</c:forEach>
		</table>
 	<%@ include file="/footer.jsp" %>
	</body>
</html>