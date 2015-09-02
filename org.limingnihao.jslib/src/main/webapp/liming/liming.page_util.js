/**
 * 分页Util类
 * @author lishiming
 */
if( typeof(liming) == "undefined" ){
	liming = {};
}

liming.PageUtil = {
	FIRST : "first",
	LAST : "last",
	NEXT : "next",
	PREVIOUS : "previous"
};
liming.PageUtil.getNowPageList = function(pageNow, pageSize, pageAcion, dataArray, backHandler){
	var newPageNow = liming.PageUtil.getPageNow(pageAcion, pageNow, pageTotal);
	var pageTotal = liming.PageUtil.getPageTotal(dataArray.length, pageSize);
	var firstResult = liming.PageUtil.getFirstResult(newPageNow, pageSize);
	var numberTotal = dataArray.length;
	var maxResult = liming.PageUtil.getMaxResult(firstResult, pageSize, numberTotal);
	var resultDataArray = [];
	for( var i=firstResult; i<maxResult; i++){
		resultDataArray.push(dataArray[i]);
	}
	if( typeof(backHandler) == "function" ){
		backHandler({pageNow: newPageNow, pageTotal: pageTotal, resultData: resultDataArray});
	}
};
liming.PageUtil.getFirstResult = function(pageNow, pageSize) {
	if (pageSize <= 0) {
		return 0;
	}
	var firstResult = (pageNow - 1) * pageSize;
	if (firstResult < 0) {
		firstResult = 0;
	}
	return firstResult;
};
liming.PageUtil.getMaxResult = function(firstResult, pageSize, numberTotal) {
	if (pageSize <= 0) {
		return firstResult;
	}else if( (firstResult + pageSize) >= numberTotal ){
		return numberTotal;
	}else{
		return firstResult + pageSize;
	}
};
liming.PageUtil.getPageTotal = function(total, pageSize) {
	if (pageSize <= 0) {
		return 1;
	}
	var pageTotal = Math.round(total / pageSize);
	if (pageTotal < 1) {
		pageTotal = 1;
	}
	return pageTotal;
};

liming.PageUtil.getPageNow = function(action, pageNow, pageTotal) {
	var pageNum = pageNow;
	if (action == (liming.PageUtil.FIRST) ) {
		pageNum = 0;
	} else if (action == (liming.PageUtil.LAST)) {
		pageNum = pageTotal;
	} else if (action == (liming.PageUtil.NEXT)) {
		pageNum++;
	} else if (action == (liming.PageUtil.PREVIOUS)) {
		pageNum--;
	} else {
		pageNum = 1;
	}
	if (pageNum > pageTotal) {
		pageNum = pageTotal;
	}
	if (pageNum < 1) {
		pageNum = 1;
	}
	return pageNum;
};