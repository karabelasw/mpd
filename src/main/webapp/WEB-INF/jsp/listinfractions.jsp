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
	
  	
  	<%@ include file="/WEB-INF/jsp/searchInfractionsForm.jsp" %>
		
		<table border="1" width="100%">
		<c:if test = "${fn:length(lstinfractions) > 0}">
			<caption>Vehicle Parking and Moving Infractions For District of Columbia:
			<span style="font:bold;"> Displaying ${fn:length(lstinfractions)} records</span>
			</caption>
			<tr>
			   <th>Code</th>
			   <th>Description</th>
				<th>Short Description</th>
			   <th>Fine</th>
			</tr>
		</c:if>
			<%-- Page has been processed by TrafficParkingInfraction Servlet
				application level attribute placed upon servlet init method
				using the ParkingMovingInfractionDAOs
			--%>
			<c:if test = "${fn:length(lstinfractions) <= 0}"> NEGATIVE RESULTS ON THE CANVASS </c:if>
			<c:forEach var="aninfraction" items="${lstinfractions}">	
			<tr>			   
			   <td><c:out value="${aninfraction.code}"/></td>
			   <td><c:out value="${aninfraction.description}"/></td>
			   <td><c:out value="${aninfraction.shortDescription}"/></td>
			   <td>$<c:out value="${aninfraction.fine}"/></td>
			</tr>
			</c:forEach>
		</table>
 	<%@ include file="/footer.jsp" %>
	</body>
</html>