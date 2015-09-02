Ext.Loader.setConfig({enabled: true});
Ext.Loader.setPath("Ext.ux", jslibPath + "ext4.1/ux");
Ext.require([
    "Ext.form.*",
    "Ext.grid.*",
    "Ext.tree.*",
    "Ext.window.*",
    "Ext.menu.*",
    "Ext.tip.*",
    "Ext.data.*",
    "Ext.util.*",
    "Ext.state.*",
    "Ext.chart.*",
    "Ext.layout.container.Border",
    "Ext.ux.TreePicker",
    "Ext.ux.form.MultiSelect",
    "Ext.ux.form.ItemSelector",
    "Ext.ux.TreePicker",
    "Ext.ux.MyDatePicker"
]);


Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	
	/** -------------------------------------视图区------------------------------------- */	
	/**
	 * 用户密码编辑formPanel
	 */
	var formPanel_password = Ext.create("Ext.form.Panel", {
	    bodyPadding: 20,
	    bodyBorder: false,
	    header: false,
	    frame: false,
	    fieldDefaults: {
	        labelAlign: "right",
	        labelWidth: 60,
	        anchor: "100%"
	    },
	    items: [
	        {id: "old_password", 	 xtype: "textfield", inputType:"password", name: "oldPassword", 	fieldLabel: "原密码"},
	        {id: "new_password", 	 xtype: "textfield", inputType:"password", name: "newPassword", 	fieldLabel: "新密码"},
	        {id: "confirm_password", xtype: "textfield", inputType:"password", name: "confirmPassword", fieldLabel: "确认密码"}
	    ]
	});
	var window_password = Ext.create("Ext.window.Window", {
		title:"修改密码",
	    width: 350,
	    height: "auto",
	    bodyMargin: 10,
	    modal: true,
	    closable : true,
        closeAction: "hide",
	    resizable : false,
	    layout: "fit",
	    items: [formPanel_password],
	    buttonAlign: "right",
	    buttons: [
	        {text: "提交", handler: password_changeHandler}, "-",
	        {text: "取消", handler: function(){ window_password.hide(); }}
	    ]
	});
	
	/** -------------------------------------按钮事件方法区------------------------------------- */
	function password_changeHandler(){
		var oldPassword = Ext.getCmp("old_password").getValue();
		var newPassword = Ext.getCmp("new_password").getValue();
		var confirmPassword = Ext.getCmp("confirm_password").getValue();
		if (newPassword != confirmPassword) {
			liming.message_error("两次密码输入不同!");
			return;
		}
		$.post("../user/userPasswordChange.do", {
			userId: currentLoginUserId,
			oldPassword: oldPassword,
			newPassword: newPassword
		}, function(data){
			if (data.success) {
				window_password.hide();
				liming.message_info(data.message, function(){
					window.location.href = "../user/logout.do";
				});
			} else {
				liming.message_error(data.message);
			}
		});
	}
	
	/** -------------------------------------供外部使用的按钮事件方法区------------------------------------- */
	showChangePasswordWindow = function(){
    	formPanel_password.getForm().reset();
    	window_password.show();
	};
	
	logout = function(){
		liming.message_question("是否确定退出?", function(btn){
			window.location.href = "../user/logout.do";
		});
	};
	
});  
