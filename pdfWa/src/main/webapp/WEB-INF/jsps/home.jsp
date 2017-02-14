<!DOCTYPE html>
<html>
	<head> 
		<title>PDF</title>
	</head> 
	<body> 
		<form action="GotPDF" method="post" enctype="multipart/form-data">
		 Upload the file <input type="text" name="testo1" >
		  FILE <input type="file" name="PDFtoSPlit" accept="application/pdf">
		  <input type="submit" value="Split" >
		</form>
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
</html>


		