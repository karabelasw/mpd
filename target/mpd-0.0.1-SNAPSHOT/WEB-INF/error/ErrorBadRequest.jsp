<%-- Error page for malformed requests. Configured in web.xml. --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/jspheader.jsp" %>
<!DOCTYPE HTML> 
<html>
	<head>
	  		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>
			BAD REQUEST
		</title>
	    
	</head>
	<body>

		<div class="body">
				<h3>Problem Detected</h3>
			 		A problem with the underlying HTTP request has been detected.  
			 	<p>Possible problems include :
			  	<ul>
				   	<li>the request URL has an unexpected form
				   	<li>the request includes spam
				   	<li>the request has an unexpectedly large size
				   	<li>the request includes an unexpected request parameter
				   	<li>the request uses a 'GET', and should use a 'POST' instead
			  	</ul>
		</div>

		
		<jsp:include page="/footer.jsp"></jsp:include>

	</body>
</html>
