<%-- Used to prevent self referencing hyperlinks--%>
<c:set var="requestedURL" scope="session" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<%-- out.println(request.getContextPath()+"/listinfractions.jsp"); 
	out.print(request.getAttribute("javax.servlet.forward.request_uri"));
--%>
<c:if test="${infractionURL eq requestedURL}"/>

		 <hr>
		  <div id="cssmenu">		 
		 	 <ul>		 	 		
		 	 		
	 	 		<%--test to see if user has successfully logged in --%>
				<c:if test="${loggedIn}">
					<li <%= ParameterChecker.activateLink(request, "login") %> <%= ParameterChecker.activateLink(request, "patrol.action") %>>
						<a title="Home" href="${pageContext.request.contextPath}/jsp/patrol/patrol.action"><span>Home</span></a>
					</li>
				</c:if>		 	 
		 	 			
				<li <%= ParameterChecker.activateLink(request, "/listinfractions.jsp")  %> >
				 	<a title="DC Traffic Infractions" href="${pageContext.request.contextPath}/listinfractions.jsp"><span>DC Traffic Infractions</span></a>
				</li>
				<%--test to see if user has successfully logged in --%>
					<c:if test="${loggedIn}">
						<li <%= ParameterChecker.activateLink(request, "/jsp/patrol/suspects.action")%>>
							<a title="Suspects" href="${pageContext.request.contextPath}/jsp/patrol/suspects.action"><span>Suspects</span></a>
						</li>
					</c:if>
			
					<c:if test="${not loggedIn}">
						 <%-- request.getAttribute("javax.servlet.forward.request_uri").toString().equalsIgnoreCase(request.getContextPath()+"/jsp/signin") ? "class='active'" : "" --%>
							<li <%= ParameterChecker.activateLink(request, "/jsp/signin") %> >
							<a title="Sign In" href="${pageContext.request.contextPath}/jsp/signin"><span>Sign In</span></a>	
						</li>
					</c:if>
						
					<c:if test="${loggedIn}">							
						<li <%= ParameterChecker.activateLink(request, "Password")%>>
							<a href="${pageContext.request.contextPath}/jsp/Password.action"><span>Change Password</span></a>
						</li>				
					</c:if>						 
					
					<c:if test="${loggedIn && loggedinUser.admin}">
						<li <%=  ParameterChecker.activateLink(request, "Admin")%> >		
							<a href="${pageContext.request.contextPath}/jsp/admin/GoAdminHome.action"><span>Admin</span></a>
						</li>	
					</c:if>
							
					<c:if test="${loggedIn}">
					   	<li>
						    <a style="text-align:right;color:white;" title="10-7" href="${pageContext.request.contextPath}/jsp/logout"><span>Sign Out</span></a>
						</li>
					</c:if>				    		
			
			</ul>
	  	   </div>				
						
