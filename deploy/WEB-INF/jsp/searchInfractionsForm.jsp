

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
							<strong>
								<input title="Any word(s) contained in description" type="radio" name="sgroup" value="any" <c:if test = "${empty param.sgroup}"> checked </c:if>
																		  	  <c:if test = "${param.sgroup == 'any'}">checked </c:if>>Any
								<input title="All words contained in description" type="radio" name="sgroup" value="all"<c:if test = "${param.sgroup == 'all'}"> checked</c:if>>All
								<input title="Exact words matched in description" type="radio" name="sgroup" value="exact" <c:if test = "${param.sgroup == 'exact'}"> checked </c:if>>Exactly
							
							</strong>
					</td>
			
				</tr>			
										
				<tr>					
					<td colspan="2">
						<input class="button" name="submit" type="submit" value="Go Fetch" />
						&nbsp;<input class="button" type="submit" name="Reset" value="Reset" />
						<strong><span><input type="checkbox" value="codeinclusivesearch" name="include_codes" <c:if test="${param.include_codes != null}">checked </c:if>>Include Codes In Your Search</span></strong>
										
					</td>									
				</tr>	
					<c:if test = "${not empty providesearchcriteria}" >
					<tr><td>${providesearchcriteria}</td></tr>
					</c:if>
			</table>
		</fieldset>
</form>