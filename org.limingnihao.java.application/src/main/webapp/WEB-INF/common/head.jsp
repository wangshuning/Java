<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="org.limingnihao.application.help.ApplicationHelp"%>
<%@ page import="org.limingnihao.application.service.model.UserBean"%>
<%@ page import="org.limingnihao.application.service.model.ResourceBean"%>
<%
	UserBean currentUserBean = (UserBean) session.getAttribute(ApplicationHelp.ATTRIBUTE_CURRENT_USER);
	int currentLoginUserId = 0;
	String currentLoginUserNickname = "未登录";
	if (currentUserBean != null) {
		currentLoginUserId = currentUserBean.getUserId();
		currentLoginUserNickname = currentUserBean.getNickname();
	}
	List<ResourceBean> resourceBeanList = (List<ResourceBean>) session.getAttribute(ApplicationHelp.ATTRIBUTE_RESOURCE_LIST);
	request.setAttribute("resourceBeanList", resourceBeanList);
	
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<script type="text/javascript">
	var currentLoginUserId = "<%=currentLoginUserId%>";
	var currentLoginUserNickname = "<%=currentLoginUserNickname%>";
	var basePath = "<%=basePath%>";
	var current_url = window.location.href.replace(basePath, "");
	
	$(document).ready(function(){
		setDateTime();
		var one_title = $(".navigate_url_div[item_url='" + current_url + "']").prevAll(".navigate_menu_div").html();
		var two_title = $(".navigate_url_div[item_url='" + current_url + "']").html();
		$("#navigate_title_div").html((typeof(one_title) == "string" ? one_title + " > " : "")  + (typeof(two_title) == "string" ? two_title : "" ));
		$(".navigate_item_div").hover(function(){
			var item_url = $(".navigate_item_div div").children().first().attr("item_url");
			$(".navigate_url_div[item_url='" + item_url + "']").prevAll(".navigate_menu_div").attr("class", "navigate_menu_div_hover");
			$(this).show();
		}, function(){
			$(".navigate_menu_div_hover").attr("class", "navigate_menu_div");
			$(this).hide();
		});
	});
	function setDateTime(){
		$.post(basePath + "getCurrentSystemDateTime.do", function(data){
			var welcome = data.year + "年" + data.month + "月" + data.day + "日 , " + currentLoginUserNickname;
			$("#welcome").text(welcome);
		});
	}
	
	function onMenu_mouseOver_handler(thiz){
		$(".navigate_item_div").html("");
		var $items = $("<div style='float:left;'></div>");
		var children = $(thiz).children(".navigate_url_div");
		$.each(children, function(i){
			var $label = $("<label class='navigate_item_div_content' item_url='" + $(this).attr("item_url") + "' onclick='gotoPage(this)'>" + $(this).html() + "</label>");
			$label.hover(function(){
				$(this).attr("class", "navigate_item_div_content_hover");
			}, function(){
				$(this).attr("class", "navigate_item_div_content");
			});
			$items.append($label);
			if (i != children.length - 1) {
				$items.append("&nbsp;&nbsp;|&nbsp;&nbsp;");
			}
		});
		if (children.length != 0) {
			$(".navigate_item_div").append($items).show();
		}
		$(thiz).children().first().attr("class", "navigate_menu_div_hover");
		
		if ($(thiz).position().left + 52 + $items.width() > $("#navigate_div").width()) {
			$(".navigate_item_div").css("padding-left", ($("#navigate_div").width() - 52 - $items.width()) + "px");
		} else {
			$(".navigate_item_div").css("padding-left", ($(thiz).position().left + 52) + "px");
		}
	}
	function onMenu_mouseOut_handler(thiz){
		$(".navigate_item_div").hide();
		$(thiz).children().first().attr("class", "navigate_menu_div");
	}

	function gotoPage(thiz){
		var item_url = $(thiz).attr("item_url");
		if (item_url) {
			window.location.href = basePath + item_url;
		}
	}
</script>
<div id="header_div">
    <div id="personal_set_div">
		<div id="button_logout" onclick="logout()">退出</div>
		<div id="button_changePassword" onclick="showChangePasswordWindow()">修改密码</div>
		<div id="welcome"></div>
    </div>
    <div id="version_set_div"><%=ApplicationHelp.SYSTEM_CURRENT_VERSION%></div>
</div>
<div id="navigate_div">
	<div class="navigate_menu_div_left"></div>
	<c:forEach var="resourceBean" items="${resourceBeanList}">
		<div class="navigate_content_div" onMouseOut="onMenu_mouseOut_handler(this)" onMouseOver="onMenu_mouseOver_handler(this)">
		 	<div class="navigate_menu_div" item_url="${resourceBean.resourceUrl}" onclick="gotoPage(this)">${resourceBean.resourceName}</div>
		 	<c:forEach var="childBean" items="${resourceBean.childrenList}">
			 	<div class="navigate_url_div" item_url="${childBean.resourceUrl}">${childBean.resourceName}</div>
		 	</c:forEach>
		</div>
	</c:forEach>
	<div class="navigate_item_div"></div>
</div>
<div id="navigate_title_div"></div>
<div id="navigate_line_div"></div>
