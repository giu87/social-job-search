<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<div id="container">
	<div id="banner"><img src="img/banner.png" style="width:900px; margin:0px; padding:0px;" /></div>
	<div id="contents">
		<br />
		<h2>Search Experts</h2>
		<div>
			Enter your query and select language and a set of channels in which redirect the query.
		</div>
		<br />
		<form action="CalculateResults" method="post">
			<input type="text" name="query" value="" />
			<input type="submit" name="submit" value="query!" />
			<br />
			Channel:
			<input type="checkbox" name="channels" value="twitter" /> Twitter
			<input type="checkbox" name="channels" value="facebook" checked="checked" /> Facebook
			<input type="checkbox" name="channels" value="linkedin"  /> LinkedIn
			<br />
			Language:
			<input type="radio" name="language" value="it" /> Italiano
			<input type="radio" name="language" value="en" checked="checked" /> English
		</form>
	</div>
	<div id="footer">
		<br /><br />   
        <a href="http://expertfinding.altervista.org">Home</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="http://expertfinding.altervista.org/about.html">About</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="http://expertfinding.altervista.org/contacts.php">Contacts</a><br />
        <br  />Copyright © 2012 Politecnico di Milano - All Rights Reserved
        <br /><br />
	
	</div>
</div>
</body>
</html>
