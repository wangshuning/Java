<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户列表 - <%=ApplicationHelp.PROJECT_NAME%></title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="/WEB-INF/common/lib-ext4.jsp"%>
	<style type="text/css">
		#tree_group_div {
			float: left;
			width: 200px;
			margin: 10px 0 0 0;
		}
		
		#grid_user_div {
			float: left;
			width: 500px;
			margin: 10px 0 0 10px;
		}
	</style>
	<script type="text/javascript" src="<%=htmlJslibPath%>/app/ext_user_list.js"></script>
</head>
<body>
<div id="body_content">
	<%@ include file="/WEB-INF/common/head.jsp"%>
	<div id="center_div">
		<div id="ext_content_div">
			<div id="tree_group_div"></div>
			<div id="grid_user_div"></div>
		</div>
	</div>
</div>
</body>
</html>