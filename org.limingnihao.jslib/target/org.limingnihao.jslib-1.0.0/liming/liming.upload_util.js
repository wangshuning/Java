/**
 * 数字Util类
 * @author lishiming
 */
if( typeof(liming) == "undefined" ){
	liming = {};
}

liming.UploadUtil = {
};

liming.UploadUtil.checkHtml5 = function () {   
	if (typeof(Worker) !== "undefined") {   
		//alert("支持HTML5");
		return true;
	} else {  
		//alert("不支持HTML5");
		return false;
	} 
};

/** 单个文件上传的方法 */
liming.UploadUtil.postFile = function(httpUrl, fileObj, parameterObj, processHandler, callbackHandler){
	console.info("postFile - httpUrl=" + httpUrl+ ", name=" + fileObj.name + ", size=" + fileObj.size + ", parameterObj=" + parameterObj);
	if( typeof(parameterObj) != "object" ){
		parameterObj = {};
	}
	if( typeof(processHandler) != "function"){
		processHandler = function(){};
	}
    if( typeof(callbackHandler) != "function"){
    	callbackHandler = function(){};
	}
	var xhr = null;
	if ( window.XMLHttpRequest ){
		xhr = new XMLHttpRequest();
	} else if (window.ActiveXObject){
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	} else{
		return false;
	}
	xhr.open("post", httpUrl, true);

	// 上传开始回调
	xhr.upload.onloadstart = function(event) {
		console.info("postFile - onloadstart - event=" + event );
	};
	
	// 上传完成回调
	xhr.onload = function(event) {
		if( this.status == 200 ){
			var resultObj = eval("(" + this.responseText + ")");
			console.info("postFile - onload - status=" + this.status + ", message=" + resultObj.message + ", event=" + event );
			callbackHandler(resultObj.success, resultObj.message);
		}else {
			//var resultObj = eval("(" + this.responseText + ")");
			console.info("postFile - onload - status=" + this.status + ", message=" + this.responseText + ", event=" + event );
			callbackHandler(false, "上传文件出现错误");
		}
	};
	// 上传错误回调
	xhr.upload.onerror = function(event) {
		console.info("postFile - onerror - 上传失败- event=" + event );
		callbackHandler(false, "上传文件出现错误");
	};
	// 上传过程回调
	xhr.upload.onprogress = function(event) {
		var percent = Math.round(event.loaded / event.total * 100); 
		var loadedString = liming.NumberUtil.conversionUnitMemory(event.loaded, 2);
		var totalString = liming.NumberUtil.conversionUnitMemory(event.total, 2);
		console.info("postFile - onprogress - percent=" + percent + ", " + event.total + "/" + event.loaded + ", " + loadedString + "/" + totalString);
		processHandler(percent, loadedString, totalString);
	};
	var formData = new FormData();
	formData.append("fileData", fileObj);
	for(var attr in parameterObj){
		formData.append(attr, parameterObj[attr]);
	}
	xhr.send(formData);
};

/**
 * 获取文件类型
 */
liming.UploadUtil.getFileType = function(fileName){
	var value = fileName.substr(fileName.lastIndexOf(".") + 1, fileName.length);
	if (value == "doc" || value == "docx") {
		return "doc";
	} else if (value == "xls" || value == "xlsx") {
		return "xls";
	} else if (value == "ppt" || value == "pptx") {
		return "ppt";
	} else if (value == "pdf") {
		return "pdf";
	} else if (value == "txt") {
		return "txt";
	} else if (value == "rm" || value == "rmvb" || value == "mp4" || value == "mkv" || value == "avi" || value == "mov" || value == "mpg" || value == "3gp" || value == "flv" || value == "wmv") {
		return "video";
	} else if (value == "mp3" || value == "wav" || value == "midi" || value == "wma" || value == "m4a" || value == "mid" || value == "xmf" ) {
		return "music";
	} else if (value == "jpeg" || value == "jpg" || value == "gif" || value == "png" || value == "bmp" || value == "wmf" || value == "psd" || value == "ico") {
		return "pic";
	} else if (value == "swf") {
		return "swf";
	} else {
		return "none";
	}
};
