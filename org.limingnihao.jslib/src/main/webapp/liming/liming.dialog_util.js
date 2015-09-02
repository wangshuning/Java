/**
 * 操作弹出对话框的js类库
 * @author lishiming
 */

if( typeof(liming) == "undefined" ){
	liming = {};
}

liming.DialogUtil = {
	client_width  : window.outerWidth,	//界面宽度
	client_height : window.outerHeight,	//界面高度
	click_interval : 1000
};

/**
 * 打开一个dialog
 * @param divId - dialog的div的id
 */
liming.DialogUtil.showDialog = function(divId, enabled){
	liming.DialogUtil.client_width = document.body.clientWidth;
	liming.DialogUtil.client_height = window.outerHeight;
	var dialog_div = document.getElementById(divId);
	var back_div = null;
	if( dialog_div == null ){
		return;
	}
	if( dialog_div.style.display == "block" ){
		return;
	}else{
		var zIndexValue = 10;
		if( typeof(dialog_div.attributes["z-index-value"]) != "undefined" && typeof(dialog_div.attributes["z-index-value"].nodeValue) != "undefined" && !isNaN(dialog_div.attributes["z-index-value"].nodeValue)){
			zIndexValue = dialog_div.attributes["z-index-value"].nodeValue;
		}
		dialog_div.style.display = "block";
		dialog_div.style.zIndex = zIndexValue;
		var w = dialog_div.offsetWidth;
		var h = dialog_div.offsetHeight;
		var left = liming.DialogUtil.client_width/2 - w/2;
		var top = liming.DialogUtil.client_height/2 - h/2 - 20;
		dialog_div.style.top = top + "px";
		dialog_div.style.left = left + "px";
		
		back_div = document.createElement("div");
		back_div.id = divId + "_back";
		back_div.style.position = "absolute";
		back_div.style.top = 0;
		back_div.style.left = 0;
		back_div.style.width = liming.DialogUtil.client_width + "px";
		back_div.style.height = liming.DialogUtil.client_height + "px";
		back_div.style.background = "#cccccc";
		back_div.style.display = "block";
		back_div.style.zIndex = zIndexValue - 1;
		back_div.style.filter = "alpha(opacity=50)";
		back_div.style.opacity = "0.5";
		document.body.appendChild(back_div);
		
		if( enabled == true || typeof(enabled) == "undefined" ){
			back_div.onclick = function(){
				var select_back_div = document.getElementById(divId + "_back");
				var currentTimestamp = new Date().getTime();
				if( typeof(select_back_div.attributes["lastTimeStamp"]) != "undefined" && typeof(select_back_div.attributes["lastTimeStamp"].nodeValue) != "undefined" && !isNaN(select_back_div.attributes["lastTimeStamp"].nodeValue)){
					var lastTimeStamp = select_back_div.attributes["lastTimeStamp"].nodeValue;
					var clickCount = isNaN(select_back_div.attributes["clickCount"].nodeValue) ? "1" : select_back_div.attributes["clickCount"].nodeValue;
					if( currentTimestamp - lastTimeStamp > liming.DialogUtilclick_interval ){
						select_back_div.setAttribute("lastTimeStamp", currentTimestamp);
						select_back_div.setAttribute("clickCount", "1");
					}else{
						clickCount = clickCount + "1";
						if( clickCount.length >= 3 ){
							liming.DialogUtil.closeDialog(divId);
						}else{
							select_back_div.setAttribute("lastTimeStamp", currentTimestamp);
							select_back_div.setAttribute("clickCount", clickCount);
						}
					}
				}else{
					select_back_div.setAttribute("lastTimeStamp", currentTimestamp);
					select_back_div.setAttribute("clickCount", "1");
				}
			};
		}		
	}
	back_div = null;
	dialog_div = null;
	delete back_div;
	delete dialog_div;
	if( typeof(CollectGarbage) == "function" ){
		CollectGarbage();
	}
};


/**
 * 关闭dialog
 * @param divId
 */
liming.DialogUtil.closeDialog = function (divId){
	var dialog_div = document.getElementById(divId);
	var back_div = document.getElementById(divId + "_back");
	dialog_div.style.display = "none";
	if( back_div ){
		back_div.onclick = null;
		purge(back_div);
		document.body.removeChild(back_div);
	}
	back_div = null;
	dialog_div = null;
	delete back_div;
	delete dialog_div;
	if( typeof(CollectGarbage) == "function" ){
		CollectGarbage();
	}
};

purge = function (d) {
    var a = d.attributes, i, l, n;
    if (a) {
        l = a.length;
        for (i = 0; i < l; i += 1) {
            n = a[i].name;
            if (typeof d[n] === 'function') {
                d[n] = null;
            }
        }
    }
    a = d.childNodes;
    if (a) {
        l = a.length;
        for (i = 0; i < l; i += 1) {
            purge(d.childNodes[i]);
        }
    }
};

