<%@page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" href="css/style.css">

<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.2.min.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	$("input[type=radio]").click(function() { 
	
        var val = $(this).val();
		if(val == "probabilistic") {
			var $all_radios = $("input[type=radio]");
			$all_radios.attr("disabled", "disabled");
			$(this).removeAttr("disabled");
			$("input[type=radio][value=textual]").removeAttr("disabled");
		}
		else{
			var $all_radios = $("input[type=radio]");
			$all_radios.removeAttr("disabled");
		}
	});
});
</script>
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
			Method:
			<input type="radio" name="method" value="textual" checked="checked" /> Text
			<input type="radio" name="method" value="probabilistic" /> Probabilistic
			<br /><br />
			Channel:
			<input type="radio" name="channels" value="all" checked="checked" /> All
			<input type="radio" name="channels" value="twitter" /> Twitter
			<input type="radio" name="channels" value="facebook" /> Facebook
			<input type="radio" name="channels" value="linkedin"  /> LinkedIn
			<br />
			Language:
			<input type="radio" name="language" value="it" /> Italiano
			<input type="radio" name="language" value="en" checked="checked" /> English
			<br />
			Entities:
			<input type="radio" name="entityType" value="no" checked="checked" /> Don't use entities
			<input type="radio" name="entityType" value="yes" /> use entities 
			<input type="radio" name="entityType" value="bd" /> use entities and domains weight
			<br />
			Text:
			<input type="radio" name="textType" value="textShort" checked="checked" /> short text (group/page and posts alone)
			<input type="radio" name="textType" value="text" /> long text (group/page with posts)
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
