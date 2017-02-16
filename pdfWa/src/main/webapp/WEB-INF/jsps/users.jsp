
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>

<%@ page isELIgnored="false" %> 

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home Page</title>
		
		<style type="text/css">
			table,th,td
			{
				border:1px solid black;
			}
		</style>
	</head>
	<body>
	<form action="DeleteUser" method="POST" id="toDel">
		<table class= "table">
			<tbody>
			
				<tr><th>Nome</th><th>Cognome</th><th>CF</th></tr>			
				<c:forEach  items="${requestScope.users}" var= "emp"  >
					<tr>
						<td><c:out value="${emp.name}"> </c:out>  </td>
						<td><c:out value="${emp.lastName}"> </c:out> </td>
						<td><c:out value="${emp.cf}"> </c:out> </td>
						<td><input type="radio" name="toDel" value="${emp.cf}"></td>
						
				 	</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
		<button form="toDel" type="submit" value="Submit" onclick="clicked();">delete</button>
		<p>Add a new user</p>
		
		<form action="AddUser" method="POST" id="add">
		  First name: <input type="text" name="fname"><br>
		  Last name: <input type="text" name="lname"><br>
		  CF: <input type="text" name="cf"><br>
		</form>
		<button type="submit" form="add" value="Submit">add</button>	
		
	</body>
	<script type="text/javascript">
		var cookie = ("" + document.cookie);
		if(!cookie.match(/\S/))
		{
		    document.cookie = "refresh";
		    location.reload(true);
		}
		else
		{
		    document.cookie = "refresh; expires=Thu, 01 Jan 1970 00:00:00 GMT";
		}
	</script>
	<script type="text/javascript">
	    function clicked() {
	       if (confirm('are you sure?')) {
	           yourformelement.submit();
	       } else {
	           return false;
	       }
	    }
    </script>
	</html>