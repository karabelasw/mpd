

<form method="get" action="${pageContext.request.contextPath}/listinfractions.jsp">
<fieldset>
			<legend>Search Description</legend>
			<table width="50%">					
				<tr>
					<td colspan="2" style="text-align:left;">Containing Words (or just even a letter):</td>												
				</tr>
				<tr id="searchbydescription">
					 <td colspan="8">			
						<input type="text" id="searchdescription" name="description" maxlength="50" length="5" value="${param.description}"/>
						<script type="text/javascript">document.getElementById('searchdescription').focus();</script>
						<b>
							<input type="radio" name="sgroup" value="any" <c:if test = "${empty param.sgroup}"> checked </c:if>
																		  <c:if test = "${param.sgroup == 'any'}">checked </c:if>>Any
							<input type="radio" name="sgroup" value="all"<c:if test = "${param.sgroup == 'all'}"> checked</c:if>>All
							<input type="radio" name="sgroup" value="exact" <c:if test = "${param.sgroup == 'exact'}"> checked </c:if>>Exactly
						</b>
					</td>
				</tr>			
										
				<tr>					
					<td colspan="2">
						<input class="button" name="submit" type="submit" value="Go Fetch" />
						&nbsp;<input class="button" type="submit" name="Reset" value="Reset" />
						<c:if test = "${not empty providesearchcriteria}" >${providesearchcriteria}</c:if>
						
					</td>	
								
										
				</tr>	
			</table>
		</fieldset>
</form>