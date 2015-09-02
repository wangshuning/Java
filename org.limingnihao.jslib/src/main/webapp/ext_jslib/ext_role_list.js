/**
 * 权限管理
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";

    /** -------------------------------------数据源区------------------------------------- */
	
	/** 权限树形结构数据源 */
	var store_resourceList = Ext.create("Ext.data.Store", {
    	model: "ResourceBean",
        proxy: { type: "ajax", url: "../role/getResourceBeanTreeList.do" },
        autoLoad: true
    });
	
	/** 角色 - grid数据源 */
	var store_roleList = Ext.create("Ext.data.Store", {
		model: "RoleBean",
    	pageSize: 18,
        proxy: {
            type: "ajax",
            url: "../role/getRoleListBean_grid.do",
            reader: {root: "dataArray", totalProperty: "totalSize" }
        },
        autoLoad: true
    });
	
    /** -------------------------------------视图区------------------------------------- */
	
	/**
	 * ----------------角色列表grid-----------------
	 */
    var grid_roleList = Ext.create("Ext.grid.Panel", {
    	store: store_roleList,
        renderTo: "grid_role_div",
        stateful: false,
        collapsible: false,
        multiSelect: false,
        stateId: "stateGrid",
        title: "角色管理",
        height: 501,
        width: 1000,
        iconCls: "ext-grid-icon-cls",
        scroll: false,
        viewConfig: { stripeRows: true, enableTextSelection: true },
        columns: [
            { text: "序号",     width: 50,  align: "center", xtype: "rownumberer" },
            { text: "角色名称", width: 120, align: "center", sortable: false, menuDisabled: true, dataIndex: "roleName" },
            { text: "拥有权限", width: 720, align: "left",   sortable: false, menuDisabled: true, dataIndex: "authorityNames" },
            { text: "使用状态", width: 120, align: "center", sortable: false, menuDisabled: true, dataIndex: "useFlagName" }
        ],
        tbar: new Ext.Toolbar({
        	height: 35,
			items : [
			    { width: 5, disabled: true},
		        { text: "新增", height: 25, icon: jslibPath + "ext4.1/icons/user_add.png",  handler: role_addHandler }, "-",
		        { text: "修改", height: 25, icon: jslibPath + "ext4.1/icons/user_edit.png", handler: role_editHandler }, "-",
		        { text: "删除", height: 25, icon: jslibPath + "ext4.1/icons/user_del.png",  handler: role_deleteHandler }
		    ]
        }),
        bbar: Ext.create("Ext.PagingToolbar", {
        	store: store_roleList,
            displayInfo: true,
            displayMsg: "当前显示{0} - {1}条，共 {2} 条记录",
            emptyMsg: "当前没有任何记录"
        })
    });
    
    /**
     * 新增或修改角色界面
     */
    var formPanel_role = Ext.create("Ext.form.Panel", {
        bodyPadding: 20,
        bodyBorder: false,
        header: false,
        frame: false,
        fieldDefaults: { labelAlign: "right", labelWidth: 100, anchor: "100%" },
        items: []
    });
    var window_role = Ext.create("Ext.window.Window", {
        width: 600,
        height: "auto",
        bodyMargin: 10,
        modal: true,
        closable : true,
        closeAction: "hide",
        resizable : false,
        layout: "fit",
        items: [formPanel_role],
        buttonAlign: "right",
        buttons: [
            {id: "button_addOrEdit_submit", text: "提交", handler: role_submitHandler}, "-", 
            {id: "button_addOrEdit_cancel", text: "取消", handler: function(){ window_role.hide(); }}
        ]
    });
    
    /** -------------------------------------按钮事件方法区------------------------------------- */

    /**
     * 角色列表刷新
     */
    function roleList_refreshHandler(){
    	store_roleList.currentPage = 1;
    	store_roleList.load();
    }
    
    /**
     * 新增角色 - 窗口显示
     */
    function role_addHandler(){
    	formPanel_role.removeAll();
    	var items = [
     	    {id: "text_roleId",   xtype: "hidden",    name: "roleId"},
     	    {id: "text_roleName", xtype: "textfield", name: "roleName", fieldLabel: "角色名称", allowBlank: false, emptyText: "请输入角色名称", invalidText: "请输入角色名称！", maxWidth: 480},
     	    {id: "radio_useFlag", xtype: "radiogroup", fieldLabel: "是否启用", allowBlank: false, invalidText: "请选择是否启用！", items: [
               {boxLabel: "启用", name: "useFlag", inputValue: 1, checked: true},
               {boxLabel: "禁用", name: "useFlag", inputValue: 0}
            ]}
     	];
     	for (var i = 0; i < store_resourceList.count(); i++) {
     		var children = store_resourceList.getAt(i).get("childrenList");
     		var checkboxgroup = [];
     		for (var j = 0; j < children.length; j++) {
     			checkboxgroup.push({boxLabel: children[j].resourceName,	name: "resourceIds", inputValue: children[j].resourceId});
     		}
     		items.push({id: "checkbox_" + i, xtype: "checkboxgroup", fieldLabel: store_resourceList.getAt(i).get("resourceName"), columns: 3, items: checkboxgroup});
     	}
     	formPanel_role.add(items);
     	formPanel_role.doLayout();
 		window_role.setTitle("新增角色");
 		window_role.show();
    }
    
    /**
     * 修改角色 - 窗口显示
     */
    function role_editHandler(){
    	if (!grid_roleList.getSelectionModel().hasSelection()) {
    		liming.message_info("请先选择数据再操作！");
    		return;
    	}
    	var record = grid_roleList.getSelectionModel().getSelection()[0];
    	$.post("../role/getResourceBeanListByRoleId.do", {
			roleId : record.get("roleId")
		}, function(data){
			if (data) {
		    	formPanel_role.removeAll();
		    	var items = [
		    	    {id: "text_roleId",   xtype: "hidden",    name: "roleId",   value: record.get("roleId")},
		    	    {id: "text_roleName", xtype: "textfield", name: "roleName", value: record.get("roleName"), fieldLabel: "角色名称", allowBlank: false, emptyText: "请输入角色名称", invalidText: "请输入角色名称！", maxWidth: 480},
		    	    {id: "radio_useFlag", xtype: "radiogroup", fieldLabel: "是否启用", allowBlank: false, invalidText: "请选择是否启用！", items: [
                       {boxLabel: "启用", name: "useFlag", inputValue: 1, checked: true},
                       {boxLabel: "禁用", name: "useFlag", inputValue: 0}
                    ]}
		    	];
		    	for (var i = 0; i < store_resourceList.count(); i++) {
		    		var children = store_resourceList.getAt(i).get("childrenList");
		    		var checkboxgroup = [];
		    		for (var j = 0; j < children.length; j++) {
		    			var isChecked = false;
		    			for (var k = 0; k < data.length; k++) {
		    				if (data[k].resourceId == children[j].resourceId){
		    					isChecked = true;
		    					break;
		    				}
		    			}
		    			checkboxgroup.push({boxLabel: children[j].resourceName,	name: "resourceIds", inputValue: children[j].resourceId, checked: isChecked});
		    		}
		    		items.push({id: "checkbox_" + i, xtype: "checkboxgroup", fieldLabel: store_resourceList.getAt(i).get("resourceName"), columns: 3, items: checkboxgroup});
		    	}
		    	formPanel_role.add(items);
		    	formPanel_role.doLayout();
		    	Ext.getCmp("radio_useFlag").setValue({useFlag: record.get("useFlag")});
			    window_role.setTitle("修改角色");
				window_role.show();
			}
		});
    }
    
    /**
     * 角色表单 - 提交按钮
     */
    function role_submitHandler(){
    	var isChecked = false;
    	for (var i = 0; i < store_resourceList.count(); i++) {
    		if (typeof(Ext.getCmp("checkbox_" + i).getValue()["resourceIds"]) != "undefined") {
    			isChecked = true;
    			break;
    		}
    	}
    	var resourceIds = [];
    	for (var i = 0; i < store_resourceList.count(); i++) {
    		resourceIds.push(Ext.getCmp("checkbox_" + i).getValue()["resourceIds"]);
    	}
		if (!Ext.getCmp("text_roleName").isValid()) {
    		liming.message_error(Ext.getCmp("text_roleName").invalidText);
    	} else if (!isChecked) {
			liming.message_error("请选择权限！");
		} else {
			$.post("../role/createOrUpdate.do", {
				roleId:   Ext.getCmp("text_roleId").getValue(),
				roleName: Ext.getCmp("text_roleName").getValue(),
				resourceIds: resourceIds.toString(),
				useFlag:  Ext.getCmp("radio_useFlag").getValue().useFlag,
			}, function(data){
				if(data.success){
					window_role.hide();
					liming.message_info(data.msg);
					roleList_refreshHandler();
				}else{
					window_role.hide();
					liming.message_error(data.msg);
				}
			});
		}
    }
    
    /**
     * 删除 - 窗口显示
     */
    function role_deleteHandler(){
    	if (grid_roleList.getSelectionModel().hasSelection()) {
    		var record = grid_roleList.getSelectionModel().getSelection()[0];
    		liming.message_question("是否确定删除?", function(){
    			$.post("../role/deleteEntity.do", {
    				roleId : record.get("roleId")
    			}, function(data){
    				if (data.success) {
    					liming.message_info(data.message);
    					roleList_refreshHandler();
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