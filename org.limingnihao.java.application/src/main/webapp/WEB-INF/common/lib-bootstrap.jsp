<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String jslibPath = org.limingnihao.application.help.ApplicationHelp.JSLIB_HTTP_PATH;
    String htmlJslibPath = jslibPath + "html_jslib/";
    String htmlStylePath = jslibPath + "html_style/" + org.limingnihao.application.help.ApplicationHelp.STYLE_HTTP_PATH;
%>
<script type="text/javascript">
    var jslibPath = "<%=jslibPath%>";
</script>

<link rel="stylesheet" type="text/css" href="<%=jslibPath%>bootstrap3.3/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=jslibPath%>bootstrap3.3/js/bootstrap.js" />


<script type="text/javascript" src="<%=jslibPath%>json/json.js"></script>
<script type="text/javascript" src="<%=jslibPath%>jquery/jquery-1.11.2.js"></script>
<script type="text/javascript" src="<%=jslibPath%>liming/liming.number_util.js"></script>
<script type="text/javascript" src="<%=jslibPath%>liming/liming.upload_util.js"></script>

<link rel="stylesheet" type="text/css" href="<%=htmlStylePath%>css/basic.css" />
