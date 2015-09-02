/**
 * 版本管理
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";

    /** -------------------------------------数据源区------------------------------------- */
	
	/** 设备类型列表数据源 */
    var store_deviceType_attributeList = Ext.create("Ext.data.Store", {
    	model: "AttributeBean",
        proxy: {
            type: "ajax",
            url: "../version/getDeviceTypeList.do"
        },
        autoLoad: true
    });

	/** 版本grid数据源 */
	var store_version = Ext.create("Ext.data.Store", {
        model: "VersionBean",
        pageSize: 18,
        proxy: {
            type: "ajax",
            url: "../version/getListBeanByDeviceType_grid.do",
            reader: { root: "dataArray", totalProperty: "totalSize" }
        },
        autoLoad: true
    });
	
    /** -------------------------------------视图区------------------------------------- */
	
	/**
	 * ----------------设备版本列表grid-----------------
	 */
    var grid_version = Ext.create("Ext.grid.Panel", {
    	store: store_version,
        renderTo: "grid_version_div",
        stateful: false,
        collapsible: false,
        multiSelect: false,
        stateId: "stateGrid",
        title: "版本列表",
        width: 1000,
        height: 501,
        iconCls: "ext-grid-icon-cls",
        scroll: false,
        viewConfig: { stripeRows: true, enableTextSelection: true },
        columns: [
            { text: "序号", 	   width: 50,  align: "center", xtype: "rownumberer" },
			{ text: "版本名称", width: 80,  align: "left",   sortable: false, menuDisabled: true, dataIndex: "versionName" },
			{ text: "版本编号", width: 80,  align: "center", sortable: false, menuDisabled: true, dataIndex: "versionCode" },
			{ text: "设备类型", width: 110, align: "center", sortable: false, menuDisabled: true, dataIndex: "deviceTypeName" },
			{ text: "文件名称", width: 200, align: "left",   sortable: false, menuDisabled: true, dataIndex: "fileName" },
			{ text: "下载地址", width: 215, align: "left",   sortable: false, menuDisabled: true, dataIndex: "downUrl" },
			{ text: "上传时间", width: 115, align: "center", sortable: false, menuDisabled: true, dataIndex: "uploadTime" },
			{ text: "版本说明", width: 150, align: "left",   sortable: false, menuDisabled: true, dataIndex: "description" }
        ],
        tbar: new Ext.Toolbar({
        	height: 35,
			items : [
			    { width: 5,   disabled: true},
			    { width: 55,  text: "新增", icon: jslibPath + "ext4.1/icons/add.png",    handler: function(){versionAddOrEdit_windowShow(0);} },
			    { width: 55,  text: "查看", icon: jslibPath + "ext4.1/icons/detail.png", handler: versionInfo_windowShow}, "-",
			    { width: 55,  text: "修改", icon: jslibPath + "ext4.1/icons/edit.png",   handler: function(){versionAddOrEdit_windowShow(1);}}, "-",
			    { width: 55,  text: "删除", icon: jslibPath + "ext4.1/icons/delete.png", handler: versionDelete_windowShow}, "-",
			    { width: 250, disabled: true}, 
		        { width: 140, id: "combobox_deviceType_filter", xtype: "combobox", store: store_deviceType_attributeList, queryMode: "local", emptyText: "请选择设备类型", editable: true, displayField: "attributeName", valueField: "attributeValue"},
		        { width: 55,  text: "搜索", icon: jslibPath + "ext4.1/icons/search.png", handler: versionGrid_refreshHandler}
		    ]
        }),
        bbar: Ext.create("Ext.PagingToolbar", {
        	store: store_version,
            displayInfo: true,
            displayMsg: "当前显示{0} - {1}条，共 {2} 条记录",
            emptyMsg: "当前没有任何记录"
        })
    });
    
    /**
     * 上传新版本
     */
    var formPanel_edit_version = Ext.create("Ext.form.Panel", {
        bodyPadding: 20,
        bodyBorder: false,
        header: false,
        frame: false,
        fieldDefaults: { labelAlign: "right", labelWidth: 80, anchor: "100%" },
        items: [
            {id: "text_edit_versionId", 	xtype: "hidden",    name: "versionId" },
            {id: "radio_edit_versionType",	xtype: "radiogroup",fieldLabel: "版本类型", columns: 4, items: [{boxLabel: "必须的", name: "versionType", inputValue: 0, checked: true}, {boxLabel: "可选的", name: "versionType", inputValue: 1}]},
            {id: "text_edit_deviceType",   xtype: "combobox",  name: "deviceType",  fieldLabel: "设备类型", allowBlank: false, emptyText: "请选择设备类型", invalidText: "请选择设备类型！", queryMode: "local", loadingText: "加载中...", typeAhead: true, forceSelection: true, editable: false,  displayField: "attributeName", valueField: "attributeValue", store: store_deviceType_attributeList},
            {id: "text_edit_versionName",  xtype: "textfield", name: "versionName", fieldLabel: "版本名称", allowBlank: false, emptyText: "请输入版本号(形如v1.0、V1.0.1)", invalidText: "请输入版本号(形如v1.0、V1.0.1)！", regex: new RegExp("^[V|v]?[0-9\.]+$")},
            {id: "text_edit_versionCode",  xtype: "textfield", name: "versionCode", fieldLabel: "版本编号", allowBlank: false, emptyText: "只能输入数字", invalidText: "版本只能输入数字！", regex: new RegExp("^[0-9]+$")},
            {id: "text_edit_uploadFile",   xtype: "filefield", name: "uploadFile",  fieldLabel: "文件上传", emptyText: "请选择zip、rar或apk文件", invalidText: "上传的文件类型必须是zip、rar或apk！", buttonText: "浏览...", regex: new RegExp("\.(msi|zip|rar|apk|MSI|ZIP|RAR|APK)$")},
            {id: "text_edit_description",  xtype: "textarea",  name: "description", fieldLabel: "版本说明" }
        ]
    });
    var window_edit_version = Ext.create("Ext.window.Window", {
        width: 600,
        height: "auto",
        bodyMargin: 10,
        modal: true,
        closable : true,
        closeAction: "hide",
        resizable : false,
        layout: "fit",
        items: [formPanel_edit_version],
        buttonAlign: "right",
        buttons: [
            {id: "button_edit_submit", text: "提交", handler: button_edit_version_handler}, "-", 
            {id: "button_edit_cancel", text: "取消", handler: function(){ window_edit_version.hide(); }}
        ]
    });
    
    /**
	 * 查看
	 */
    var formPanel_info_version = Ext.create("Ext.form.Panel", {
    	id: "formPanel_info_version",
        bodyPadding: 20,
        bodyBorder: false,
        header: false,
        frame: false,
        fieldDefaults: { labelAlign: "right", labelWidth: 60, anchor: "100%" },
        items: [
			{id: "text_info_deviceType",  xtype: "textfield", name: "deviceTypeName", 	fieldLabel: "设备类型", readOnly: true},
			{id: "text_info_versionType", xtype: "textfield", name: "versionTypeName",  fieldLabel: "版本类型", readOnly: true},
			{id: "text_info_versionName", xtype: "textfield", name: "versionName", 		fieldLabel: "版本名称", readOnly: true},
			{id: "text_info_versionCode", xtype: "textfield", name: "versionCode", 		fieldLabel: "版本编号", readOnly: true},
			{id: "text_info_fileName", 	  xtype: "textfield", name: "fileName", 		fieldLabel: "文件名称", readOnly: true},
			{id: "text_info_downUrl", 	  xtype: "textfield", name: "downUrl", 			fieldLabel: "文件地址", readOnly: true},
			{id: "text_info_fileMd5", 	  xtype: "textfield", name: "fileMd5", 			fieldLabel: "文件MD5",  readOnly: true},
			{id: "text_info_uploadTime",  xtype: "textfield", name: "uploadTime",       fieldLabel: "上传时间", readOnly: true},
			{id: "text_info_description", xtype: "textarea",  name: "description",      fieldLabel: "版本说明", readOnly: true}
        ]
    });
    var window_info_version = Ext.create("Ext.window.Window", {
    	title: "版本信息",
        width: 500,
        height: "auto",
        bodyMargin: 10,
        modal: true,
        closable : true,
        closeAction: "hide",
        resizable : false,
        layout: "fit",
        items: [formPanel_info_version],
        buttonAlign: "right",
        buttons: [
            {id: "button_info_cancel", text: "关闭", handler: function(){ window_info_version.hide(); }}
	    ]
    });
    
    /**
     * 进度条
     */
    var window_progressBar = Ext.create("Ext.window.Window", {
    	title: "上传进度",
        width: 400,
        height: 150,
        bodyMargin: 20,
        bodyPadding: 10,
        modal: true,
        frame: false,
        closable: false,
        resizable: false,
        layout: {type: "vbox", align: "center"},
        items: [
            { xtype: "label", text: "上传中，请稍后...", margin: "15 0 20 0" }, 
            { xtype: "progressbar", id: "progressBar_version", cls: "left-align", width: 360}
        ]
    });

    /** -------------------------------------按钮事件方法区------------------------------------- */

    /**
     * 设备列表刷新
     */
    function versionGrid_refreshHandler(){
    	var deviceType = Ext.getCmp("combobox_deviceType_filter").getValue();
    	if (deviceType == null || !Ext.isNumber(deviceType)) {
    		deviceType = -1;
    	}
    	store_version.currentPage = 1;
    	store_version.proxy.extraParams = {"deviceType": deviceType};
    	store_version.load();	
    }
    
    /**
     * 上传新版本 - 窗口显示
     */
    function versionAddOrEdit_windowShow(sign){
    	// 0 add, 1 edit
    	if (sign == 1 && !grid_version.getSelectionModel().hasSelection()) {
    		liming.message_info("请先选择数据再操作！");
    		return;
    	}
    	formPanel_edit_version.getForm().reset();
    	if (sign == 0) {
    		Ext.getCmp("text_edit_uploadFile").show();
    		window_edit_version.setTitle("新增");
    	} else {
    		Ext.getCmp("text_edit_uploadFile").hide();
	    	var record = grid_version.getSelectionModel().getSelection()[0];
	    	formPanel_edit_version.getForm().loadRecord(record);
	    	window_edit_version.setTitle("修改");
    	}
    	window_edit_version.show();
    }
    
    /**
     * 详情 - 窗口显示
     */
    function versionInfo_windowShow(){
    	formPanel_info_version.getForm().reset();
    	if (grid_version.getSelectionModel().hasSelection()) {
    		var record = grid_version.getSelectionModel().getSelection()[0];
    		formPanel_info_version.getForm().loadRecord(record);
    		window_info_version.show();
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
    }
    
    /**
     * 提交按钮
     */
    function button_edit_version_handler(){
    	if (!Ext.getCmp("text_edit_deviceType").isValid()) {
    		liming.message_error(Ext.getCmp("text_edit_deviceType").invalidText);
    	} else if (!Ext.getCmp("text_edit_versionName").isValid()) {
			liming.message_error(Ext.getCmp("text_edit_versionName").invalidText);
		} else if (!Ext.getCmp("text_edit_versionCode").isValid()) {
			liming.message_error(Ext.getCmp("text_edit_versionCode").invalidText);
		} else if((!Ext.getCmp("text_edit_uploadFile").isHidden() && $.trim(Ext.getCmp("text_edit_uploadFile").getValue()) == "") || !Ext.getCmp("text_edit_uploadFile").isValid()) {
			liming.message_error(Ext.getCmp("text_edit_uploadFile").invalidText);
		} else {
	    	var fileObj = document.getElementById(Ext.getCmp("text_edit_uploadFile").fileInputEl.id).files[0];
			var parameterObj = {
				versionId:   Ext.getCmp("text_edit_versionId").getValue(),
				versionType: Ext.getCmp("radio_edit_versionType").getValue().versionType,
				deviceType:  Ext.getCmp("text_edit_deviceType").getValue(),
				versionName: Ext.getCmp("text_edit_versionName").getValue(),
				versionCode: Ext.getCmp("text_edit_versionCode").getValue(),
				description: Ext.getCmp("text_edit_description").getValue()
			};
			// 新增
	    	if( typeof(fileObj) != "undefined" ){
				$.post("../version/validateVersionRepeat.do", parameterObj, function(data){
					if (data.success) {
						if (data.msg) {
							liming.message_question(data.msg, function(){
								submit_create_handler(fileObj, parameterObj);
							});
						} else {
							submit_create_handler(fileObj, parameterObj);
						}
					} else {
						liming.message_info(data.msg);
					}
				});
	    	}
	    	// 修改
	    	else{
	    		$.post("../version/update.do", parameterObj, function(data){
	    			window_edit_version.hide();
	    			versionGrid_refreshHandler();
	    			if(data.success){
	    				liming.message_info(data.message);
	    			}else{
	    				liming.message_error(data.message);
	    			}
	    		});
	    	}
		}
    }
    
    /**
     * 提交表单
     */
    function submit_create_handler(fileObj, parameterObj){
    	window_progressBar.show();
		window_edit_version.hide();
    	liming.UploadUtil.postFile("../version/create.do", fileObj, parameterObj, function(percent, loadedString, totalString){
    		Ext.getCmp("progressBar_version").updateProgress(percent * 0.01, "当前完成 - " + percent + "%...");
    	}, function(success, message){
    		window_progressBar.hide();
    		if(success){
    			versionGrid_refreshHandler();
        		liming.message_info(message);
    		}else{
        		liming.message_error(message);
    		}
    	});
    }
    
    
    /**
     * 删除 - 窗口显示
     */
    function versionDelete_windowShow(){
    	if (grid_version.getSelectionModel().hasSelection()) {
    		var record = grid_version.getSelectionModel().getSelection()[0];
    		liming.message_question("是否确定删除?", function(){
    			$.post("../version/deleteEntity.do", {
    				versionId: record.get("versionId")
    			}, function(data){
    				if (data.success) {
    					liming.message_info(data.message);
    					versionGrid_refreshHandler();
    				} else {
    					liming.message_error(data.message);
    				}
    			});
	        });
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
	}
});