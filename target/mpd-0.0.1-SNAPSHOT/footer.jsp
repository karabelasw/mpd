
<footer>
  		<div style="text-align:center;">
			<section>
				<a href="mailto:mpd@karabelas.com"><span>Contact Me</span></a>|| 
					<c:if test="${empty loggedIn}">	
							<a href="${pageContext.request.contextPath}/jsp/accountrequestform.jsp"><span>Request an Account</span></a>|
					</c:if>
					<a href="${pageContext.request.contextPath}/sitemap.jsp"><span>Site Map</span></a>		

					<c:if test ="${not empty loggedIn}">				
				           |<a title="10-7" href="${pageContext.request.contextPath}/jsp/logout">Sign Out</a>
					</c:if>	
				</section>
				
			 <!--  	<script type="text/javascript" src="http://www.brainyquote.com/link/quotebr.js"></script> -->
				
		</div>
</footer>  