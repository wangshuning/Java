<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String jslibPath = org.limingnihao.application.help.ApplicationHelp.JSLIB_HTTP_PATH;
    String htmlJslibPath = jslibPath + "html_jslib/";
    String htmlStylePath = jslibPath + "html_style/" + org.limingnihao.application.help.ApplicationHelp.STYLE_HTTP_PATH;
%>
<script type="text/javascript">
    var jslibPath = "<%=jslibPath%>";
</script>

<link rel="stylesheet" type="text/css" href="<%=jslibPath%>ext4.1/resources/css/ext-all-gray.css" />
<link rel="stylesheet" type="text/css" href="<%=jslibPath%>ext4.1/ux/css/ItemSelector.css" />
<link rel="stylesheet" type="text/css" href="<%=jslibPath%>ext4.1/ext_custom.css" />

<script type="text/javascript" src="<%=jslibPath%>ext4.1/ext-all.js"></script>
<script type="text/javascript" src="<%=jslibPath%>ext4.1/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=jslibPath%>ext4.1/ext-liming.js"></script>

<script type="text/javascript" src="<%=jslibPath%>json/json.js"></script>
<script type="text/javascript" src="<%=jslibPath%>jquery/jquery-1.10.1.js"></script>
<script type="text/javascript" src="<%=jslibPath%>liming/liming.number_util.js"></script>
<script type="text/javascript" src="<%=jslibPath%>liming/liming.upload_util.js"></script>

<script type="text/javascript" src="<%=htmlJslibPath%>ext_before_app.js"></script>

<script type="text/javascript" src="<%=htmlJslibPath%>ext_head.js"></script>

<link rel="stylesheet" type="text/css" href="<%=htmlStylePath%>css/basic.css" />
