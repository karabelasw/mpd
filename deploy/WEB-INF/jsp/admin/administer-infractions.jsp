<%@ include file="/jspheader.jsp" %>
 
<html>
	<head>
		<%@ include file="/menubar.jsp" %>
		
		<script src="${pageContext.request.contextPath}/js/ajax.js" type="text/javascript"></script>	
		<title>
		  Administer Infractions
		</title>
	</head>
	<body>
		<!-- START HEADER -->
	<%@ include file="/header.jsp" %>	
	<!-- END HEADER -->			
	<br/>
		<a class="button" href="${pageContext.request.contextPath}/jsp/admin/GoAdminHome.action"><span>Go Back</span></a>		
	<br/> <br/>	<br/>
		<table class="gridtable" border="1" width="100%">	
			<caption>Administer Infractions</caption>
			<tr>
			   <th>Code</th>
			   <th>Description</th>
				<th>Short Description</th>
			   <th>Fine</th>
			   <th>Is Displayed</th>
		    	<th>Action</th>
			</tr>		
			<%-- --%>			
			<s:iterator value="listOfInfractions">	
			<tr>			   
			   <td><s:property value="code"/></td>
			   <td><s:property value="description"/></td>
			   <td><s:property value="shortDescription"/></td>
			   <td id="x<s:property value='code'/>">$<s:property value="fine"/>
			   		<span id="<s:property value='code'/>" class="hidden">
			   				
			   				<input id="fine<s:property value='id'/>" name="fine<s:property value='id'/>" type="number" value="<s:property value="fine"/>" /> 
			   					   		
			   		</span>
		   		</td>
			   
			   <td <s:if test="display eq 1">style="background-color: red;"</s:if>>
			   		<s:if test="display eq 0">yes</s:if>	
			   		<s:else>No</s:else>			   
			   <td>
			   		<form onsubmit="return true;" action="AdminInfractions.action" method="post">
				    		<input type="hidden" name="theId" value="<s:property value='id'/>" />   						
				  			<s:if test="display eq 0">
    								<input type="submit" name="button" value="Hide" />
							</s:if>
   							<s:else>
    								<input type="submit" name="button" value="Show">
    						</s:else>   							
					</form>
					     			   		
			   	   &nbsp;<input id="<s:property value='id'/>" type="button" value="Edit"  onClick="editButtonText('<s:property value="id"/>', '<s:property value="code"/>');" />
			   	      
			   	   
			   	</td>     	
			</tr>
			</s:iterator>
		</table>
 	<%@ include file="/footer.jsp" %>
	</body>
</html>