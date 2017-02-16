
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home Page</title>
		
		<style>
			table,th,td
			{
				border:1px solid black;
			}
		</style>
	</head>
	<body>
		<table>
			<tbody>
				<tr><th>Nome</th><th>Cognome</th><th>CF</th></tr>
						<c:forEach var="i" begin="1" end="5">
						   Item <c:out value="${i}"/>
						</c:forEach>				
				<c:forEach var="emp" items="${users}"  >
					<tr>
						<td><c:out value="${emp.name}"/>  </td>
						<td><c:out value='${emp.lastName}'> </c:out> </td>
						<td><c:out value='${emp.cf}'> </c:out> </td>
				 	</tr>
				</c:forEach>
			</tbody>
		</table>
		<br><br>
	</body>
	</html>