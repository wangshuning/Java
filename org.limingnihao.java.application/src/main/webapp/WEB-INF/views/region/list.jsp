<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>区域管理 - <%=ApplicationHelp.PROJECT_NAME%></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="/WEB-INF/common/lib-ext4.jsp" %>
	<style type="text/css">
		#tree_region_div {
			float: left;
			margin: 10px 0 0 0;
		}
	</style>
	<script type="text/javascript" src="<%=htmlJslibPath%>/app/ext_region_list.js"></script>
</head>
<body>
<div id="body_content">
	<%@ include file="/WEB-INF/common/head.jsp"%>
	<div id="center_div">
		<div id="ext_content_div">
			<div id="tree_region_div"></div>
		</div>
	</div>
</div>
</body>
</html>