<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="it.expertfinding.datamodel.query.AnswerResource"%>
<%@page import="java.util.*,java.text.DecimalFormat, it.expertfinding.datamodel.users.CrowdUser, it.expertfinding.datamodel.query.Query, java.lang.String, it.expertfinding.utils.facebook.FacebookUtils, it.expertfinding.utils.Facade, java.util.Map.Entry"%>
<html>
<head>
<%
    Query q = (Query) request.getAttribute("query");
	List<CrowdUser> listUsers = (List<CrowdUser>) request.getAttribute("users");
	DecimalFormat df = new DecimalFormat("###.##");
%>

<META http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<title>Social Job Search</title>
<link rel="stylesheet" href="css/style.css">

<script>

function getResourceText(text){
	document.getElementById("resource_detail").innerHTML = text;
}
 
function getXMLObject()  //XML OBJECT
{
   var xmlHttp = false;
   try {
     xmlHttp = new ActiveXObject("Msxml2.XMLHTTP")  // For Old Microsoft Browsers
   }
   catch (e) {
     try {
       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP")  // For Microsoft IE 6.0+
     }
     catch (e2) {
       xmlHttp = false   // No Browser accepts the XMLHTTP Object then false
     }
   }
   if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
     xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
   }
   return xmlHttp;  // Mandatory Statement returning the ajax object created
}
 
var xmlhttp = new getXMLObject();	//xmlhttp holds the ajax object
 
function ajaxFunction(id) {
  var getdate = new Date();  //Used to prevent caching during ajax call
  if(xmlhttp) {
    xmlhttp.open("GET","ResourceDetailAjax?id="+id+"&qId=<%=q.get_id().toString()%>",true); //gettime will be the servlet name
    xmlhttp.onreadystatechange  = handleServerResponse;
    xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xmlhttp.send(null);
  }
}
 
function handleServerResponse() {
   if (xmlhttp.readyState == 4) {
     if(xmlhttp.status == 200) {
       document.getElementById("resource_detail").innerHTML=xmlhttp.responseText; 
     }
     else {
        alert("Error during AJAX call. Please try again");
     }
   }
}

function showDetails(){
	document.getElementById("resource_detail").style.display="";
}

function showResources(user){

	if(document.getElementById("user_"+user).style.display === "none"){
		document.getElementById("user_"+user).style.display = "";
		document.getElementById("user_content_"+user).style.padding = "0 0 40px 0";
	}
	else{ 
		document.getElementById("user_"+user).style.display = "none";
		document.getElementById("user_content_"+user).style.padding = "0 0 60px 0";
	}
}

</script>
</head>
<body>
<div id="container">
	<div id="banner"><img src="img/banner.png" id="img_banner" /></div>
	<div id="contents">
		<div id="resource_detail" style="display:none"></div>
	
	    <h2>Best Users:</h2>
	    <div> Click on one of the following to show how its score was calculated.</div>
	    <br />
	    <div>
	        <% 
	     	for(CrowdUser user : listUsers) {

	        	String name = "";
	        	if(user.getFacebook()!= null) name = user.getFacebook().getName(); 
				else 
					if(user.getLinkedin() != null) name = user.getLinkedin().getFirstName() + " "+ user.getLinkedin().getLastName();
					else name = user.getTwitter().getName();

	     	 	Facade.log.debug("name = "+name);
	        	String urlImg = "";
				if(user.getTwitter() != null)
				   urlImg = user.getTwitter().getProfileImageUrl();
				if(user.getLinkedin() != null)
				   urlImg = user.getLinkedin().getPictureUrl();
				if(user.getFacebook() != null){
				   urlImg = FacebookUtils.getUtilsInstance(user.getFacebook().getToken()).getProfilePictures(user.getFacebook().get_id()).getPic_small();
				}
			%>
			<div class="user">
				<div class="user_picture_div"> 
					<img src="<%=urlImg %>" style="width:55px;" />
				</div> 
				<div id="user_content_<%=user.get_id().toString()%>" style="overflow:hidden; _height:1%; padding:0 0 60px 0;">
	            	<a style="font-weight: bold" onclick="javascript:showResources('<%=user.get_id().toString()%>')"><%=name%>: <%=df.format((q.getMethod().equals("textual")) ? q.getScoreFromResources(user.get_id().toString()) : 100 * q.getBestUsersProb().get(user.get_id().toString()).getScore())%></a>
	            	<br /><br />
	            	<div id="user_<%=user.get_id()%>" style="display:none;" >
	            	<% 
	            	if(q.getMethod().equals("textual")){ 
	            	      for(AnswerResource a : q.getOrderedResourcesOfUser(user.get_id().toString())) { %>
	            
	           			<div style="width:450px;" onclick="javascript:ajaxFunction('<%=a.getId()%>','<%=user.get_id()%>');showDetails()">
	            	   		<%=df.format(a.getScore())%>
	            	   		:
	            	   		<% out.print(a.getShortText());%>
							...
						</div>
	            		<% } 
	            	}
	            	else {
	            	   for(Entry<String,Double> domain :q.getBestUsersProb().get(user.get_id().toString()).getDomains().entrySet()){ %>
	            	      
		           		<div style="width:450px;">
            	   		<%=df.format(domain.getValue())%>
            	   		:
            	   		<% out.print(domain.getKey());%>
					</div>
	            	<% }
	            	}%>
	            	</div>
	        	</div>
			</div>
			<% } %>
	    </div>
	    
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