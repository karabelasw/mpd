<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<html>
<head>
<title>SELECT Operation</title>
</head>
<body>



<sql:query dataSource="${pool}" var="result">
SELECT * from parkingmovinginfractions order by code;
</sql:query>

<table border="1" width="100%">
	<tr>
		<th>Code</th>
		<th>Description</th>
		<th>Short Description</th>
		<th>Fine</th>
	</tr>
	<c:forEach var="row" items="${result.rows}">
		<tr>

			<td><c:out value="${row.Code}" /></td>
			<td><c:out value="${row.Description}" /></td>
			<td><c:out value="${row.ShortDescription}" /></td>
			<td><c:out value="${row.Fine}" /></td>
		</tr>
	</c:forEach>
</table>

</body>
</html>