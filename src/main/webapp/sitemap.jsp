<%@ include file="/jspheader.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<!-- BEGIN MENU TABS -->
			<%@ include file="/menubar.jsp" %>
			<!-- END MENU TABS PERHAPS A SEPARATE FILE-->
        <title>Entrance</title>       
    </head>

	 <body>

			<!-- START HEADER -->
			<%@ include file="/header.jsp" %>	
			<!-- END HEADER -->
			<sub>ver .15-11-6</sub>		
					<div class="toc">
					    <ul>
					     <li>  
					     
					     	1. <a href="${pageContext.request.contextPath}/index.jsp">Home Page</a> 
					       <ul>
						    
								<li>	
										1.1 <a href="${pageContext.request.contextPath}/listinfractions.jsp">DC Traffic Infractions</a>	 
								</li>
							
									<li>										
										1.2 <a title="Sign In" href="${pageContext.request.contextPath}/jsp/signin"><span>Sign In</span></a> 
									</li>						
								 
									<li>										
										1.3 <a title="Request Account" href="${pageContext.request.contextPath}/jsp/accountrequestform"><span>Request Account</span></a> 
									</li>
								</ul>
							
						<c:if test="${loggedIn}">
						
							<li>
					           2. <a title="Home" href="${pageContext.request.contextPath}/jsp/patrol/patrol.action"><span>Home (Logged In)</span></a>				        						
							</li>
							
							<li>
					           3. <a title="Suspects" href="${pageContext.request.contextPath}/jsp/patrol/suspects.action"><span>Suspects</span></a>				        						
							</li>
							
							<li>
					           4. <a title="Change Password" href="${pageContext.request.contextPath}/jsp/Password.action"><span>Change Password</span></a>				        						
							</li>
							<li>
					           5. <a title="Admin" href="${pageContext.request.contextPath}/jsp/admin/GoAdminHome.action"><span>Admin</span></a>			        						
								<ul>
									<li>
										5.1 <a title="Admin New User" href="${pageContext.request.contextPath}/jsp/admin/AdminNewUserAccounts.action"><span>New User Accounts</span></a>
									
									</li>
									
									<li>
										5.2 <a title="Admin Accounts" href="${pageContext.request.contextPath}/jsp/admin/AdminUserAccounts.action"><span>Admin User Accounts</span></a>
									</li>						
								
								</ul>
							
							</li>	
							
												
						</c:if>
				</li>
			</ul>
			</div>
			
			
			
			
			
			
			
			<%@ include file="/footer.jsp" %>
		</body>


</html>	
		
