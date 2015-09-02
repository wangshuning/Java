/**
 * 数字Util类
 * @author lishiming
 */
if( typeof(liming) == "undefined" ){
	liming = {};
}

liming.NumberUtil = {
};
/**
 * 文件大小字符串转换
 * @param size
 * @param scale
 * @return
 */
liming.NumberUtil.conversionUnitMemory = function(size, scale){
	var  value = new Number(0);
	var unitString = " B";
	if (size == null || size == 0 || typeof(size) == "undefined") {
		value = 0;
		unitString = " B";
	} else if (size < Math.pow(1024, 1)) {
		value = size;
		unitString = " KB";
	} else if (size >= Math.pow(1024, 1) && size < Math.pow(1024, 2)) {
		value = size / Math.pow(1024, 1);
		unitString = " KB";
	} else if (size >= Math.pow(1024, 2) && size < Math.pow(1024, 3)) {
		value = size / Math.pow(1024, 2);
		unitString = " MB";
	} else if (size >= Math.pow(1024, 3) && size < Math.pow(1024, 4)) {
		value = size / Math.pow(1024, 3);
		unitString = " GB";
	} else if (size >= Math.pow(1024, 4) && size < Math.pow(1024, 5)) {
		value = size / Math.pow(1024, 4);
		unitString = " TB";
	} else if (size >= Math.pow(1024, 5) && size < Math.pow(1024, 6)) {
		value = size / Math.pow(1024, 5);
		unitString = " PB";
	} else {
		value = size;
		unitString = " B";
	}
	return value.toFixed(scale) + unitString;
};
