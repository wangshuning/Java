var liming = {};
liming.message_info = function (msg, resultHandler){
	Ext.MessageBox.show({
		title: '提示消息',
		width: 300,
		msg: msg,
		buttons: Ext.MessageBox.OK,
		fn: resultHandler,
		icon:  Ext.MessageBox.INFO
	});
};

liming.message_error = function (msg, resultHandler){
	Ext.MessageBox.show({
		title: '操作错误',
		width: 300,
		msg: msg,
		buttons: Ext.MessageBox.OK,
		fn: resultHandler,
		icon:  Ext.MessageBox.ERROR
	});
};
liming.message_question = function (msg, yesHansler, noHansler){
	Ext.MessageBox.show({
		title: '操作确认',
		width: 300,
		msg: msg,
		buttons: Ext.MessageBox.YESNO,
		fn: function(val){
			if( val == "yes" && typeof(yesHansler) == "function" ){
				yesHansler();
			}else if( val == "no" && typeof(noHansler) == "function" ){
				noHansler();
			}
		},
		icon:  Ext.MessageBox.QUESTION
	});
};

liming.message_progress = function(msg){
	Ext.MessageBox.show({
        msg: msg,
        progressText: '请稍等...',
        width:300,
        wait:true,
        waitConfig: {interval:200},
        icon:'ext-mb-download'
    });
};

liming.message_close = function(){
	Ext.MessageBox.hide();
};