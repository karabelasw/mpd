<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="/jspheader.jsp" %>
<!DOCTYPE html>
<body>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ERROR</title>
    </head>
		
		<section>
		  <h3>OOPS, The page you have requested does not exist!</h3>
		  <p>These aren't the droids you're looking for.....you may go about your business. 
			 Move <a href="${pageContext.request.contextPath}/index.jsp" title="Home Link">along</a>...</p>
		</section>	
	
		<jsp:include page="/footer.jsp"></jsp:include>

  </body>
</html>