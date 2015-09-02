/**
 * 用户管理
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	
    /** -------------------------------------数据源区------------------------------------- */
	var store_groupTree = Ext.create("Ext.data.TreeStore", {
		model: "GroupTreeBean",
		nodeParam: "groupId",
        proxy: {
            type: "ajax",
            url: "../group/getGroupTreeAllByParentId.do"
        },
	    root: { id: "1", text: "全部", groupId: "1", groupName: "全部",  expanded: true },
        autoLoad: true
    });

    var store_groupTree_picker = Ext.create("Ext.data.TreeStore", {
		model: "GroupTreeBean",
		nodeParam: "groupId",
        proxy: {
            type: "ajax",
            url: "../group/getGroupTreeByParentId.do"
        },
	    root: { id: "1", text: "全部", groupId: "1",  expanded: true },
        autoLoad: false,
        listeners: {
        	"load": function(thiz, node, records, successful, eOpts){
        		Ext.Array.each(records, function(record) {
        			record.expand();
        	    });
        	}
        }
    });
    
	var store_userGrid = Ext.create("Ext.data.Store", {
		model: "UserBean",
    	pageSize: 18,
        proxy: {
            type: "ajax",
            url: "../user/getUserListBean_grid.do",
            reader: {
            	root: "dataArray",
                totalProperty: "totalSize"
            },
            extraParams: {"groupId": 0, "roleId": 0, "nickname": ""}
        },
        autoLoad: true
    });
	
    var store_roleList_combobox = Ext.create("Ext.data.Store", {
    	model: "RoleBean",
        proxy: {
            type: "ajax",
            url: "../role/getRoleBeanList.do"
        },
        autoLoad: true
    });
    
    var store_regionTree_picker = Ext.create("Ext.data.TreeStore", {
		model: "RegionTreeBean",
		nodeParam: "regionId",
        proxy: {
            type: "ajax",
            url: "../region/getRegionTreeByParentId.do"
        },
	    root: { id: "1", regionId: "1", regionName: "全部", expanded: true },
        autoLoad: true,
        listeners: {
        	"load": function(thiz, node, records, successful, eOpts){
        		Ext.Array.each(records, function(record) {
        			record.expand();
        	    });
        	}
        }
    });
    

    
    /** -------------------------------------视图区------------------------------------- */
	var tree_groupList = Ext.create("Ext.tree.Panel", {
        store: store_groupTree,
        renderTo: "tree_group_div",
        title: "组织结构",
        width: 200,
        height: 501,
        iconCls: "ext-grid-icon-cls",
        rootVisible: true,
        columns: [
            { text: "名称", width: 150, align: "left",   dataIndex: "groupName",   sortable: false, menuDisabled: true, flex: 1, xtype: "treecolumn" },
            { text: "状态", width: 50,  align: "center", dataIndex: "useFlagName", sortable: false, menuDisabled: true }
        ],
        tbar: new Ext.Toolbar({
        	height: 35,
        	items : [
                { width: 5, disabled: true },
                { text: "向上", id: "upButton",   disabled: true,  handler: function(){groupSequence_changeHandler(0);}, icon: jslibPath + "ext4.1/icons/up.png"}, "-",
                { text: "向下", id: "downButton", disabled: true,  handler: function(){groupSequence_changeHandler(1);}, icon: jslibPath + "ext4.1/icons/down.png" }, "-",
                { text: "刷新", id: "refButton",  disabled: false, handler: groupTree_refreshHandler, icon: jslibPath + "ext4.1/icons/reset.png" }
            ]
        }),
        viewConfig: {
            stripeRows: true,
            listeners: {
                itemcontextmenu: function(view, record, node, index, e){
                	if (record.get("id") == 1) {
                		Ext.getCmp("menu_groupUpdate").disable();
                		Ext.getCmp("menu_groupDelete").disable();
                	} else {
                		Ext.getCmp("menu_groupUpdate").enable();
                		Ext.getCmp("menu_groupDelete").enable();
                	}
                    e.stopEvent();
                    menu_groupTree.showAt(e.getXY());
                    return false;
                }
            }
        },
        listeners: {
        	selectionchange: function(thiz, selected, eOpts ){
        		if( selected.length > 0 ){
        			Ext.getCmp("upButton").enable();
        			Ext.getCmp("downButton").enable();
        		}else{
        			Ext.getCmp("upButton").disable();
        			Ext.getCmp("downButton").disable();;
        		}
        		userGrid_refreshHandler();
        	}
        }
    });
	
    var menu_groupTree = Ext.create("Ext.menu.Menu", {
    	title: "操作菜单",
        items: [
            { id: "menu_groupCreate", text: "新增", icon: jslibPath + "ext4.1/icons/user_add.png",  handler: groupTree_addChildHandler },
            { id: "menu_groupUpdate", text: "修改", icon: jslibPath + "ext4.1/icons/user_edit.png", handler: groupTree_editHandler },
            { id: "menu_groupDelete", text: "删除", icon: jslibPath + "ext4.1/icons/user_del.png",  handler: groupTree_deleteHandler }
        ]
    });
    
    var formPanel_groupEdit = Ext.create("Ext.form.Panel", {
        bodyPadding: 20,
        bodyBorder: false,
        header: false,
        frame: false,
        fieldDefaults: { labelAlign: "right", labelWidth: 80, anchor: "100%" },
        items: [
            {id: "group_hidden_groupId" ,   xtype: "hidden",     name: "groupId" },
            {id: "group_hidden_parentId",   xtype: "hidden",     name: "parentId" },
            {id: "group_text_groupName",    xtype: "textfield",  name: "groupName",   fieldLabel: "名称", allowBlank: false, invalidText: "请输入名称！" },
            {id: "group_treepicker_region", xtype: "treepicker", name: "regionId",    fieldLabel: "区域", allowBlank: false, invalidText: "请选择区域！", store: store_regionTree_picker, forceSelection: true, editable: false, displayField: "regionName", valueField: "regionId"},
            {id: "group_text_description",  xtype: "textarea",   name: "description", fieldLabel: "描述" },
            {id: "group_radio_useFlag",    xtype: "radiogroup", fieldLabel: "是否启用", allowBlank: false, invalidText: "请选择是否启用！", items: [
                {boxLabel: "启用", name: "useFlag", inputValue: 1, checked: true},
                {boxLabel: "禁用", name: "useFlag", inputValue: 0}
            ]}
        ]
    });
    var window_groupEdit = Ext.create("Ext.window.Window", {
        width: 600,
        height: "auto",
        bodyMargin: 10,
        modal: true,
        closable : true,
        closeAction: "hide",
        resizable : false,
        layout: "fit",
        items: [formPanel_groupEdit],
        buttonAlign: "right",
        buttons: [
            {text: "提交", handler: groupTree_addOrEditHandler}, "-",
            {text: "取消", handler: function(){ window_groupEdit.hide(); }}
        ]
    });
    
    /**------------------------用户表--------------------------*/
    var grid_userList = Ext.create("Ext.grid.Panel", {
    	store: store_userGrid,
        renderTo: "grid_user_div",
        stateful: false,
        collapsible: false,
        multiSelect: false,
        stateId: "stateGrid",
        title: "用户列表",
        width: 790,
        height: 501,
        iconCls: "ext-grid-icon-cls",
        scroll: false,
        viewConfig: { stripeRows: true, enableTextSelection: true },
        columns: [
            { text: "序号",		width: 50,  align: "center", xtype: "rownumberer" },
            { text: "用户名",	width: 120, align: "center", sortable: false, menuDisabled: true, dataIndex: "username" },
            { text: "昵称",		width: 120, align: "center", sortable: false, menuDisabled: true, dataIndex: "nickname" },
            { text: "角色",		width: 100, align: "center", sortable: false, menuDisabled: true, dataIndex: "roleNames" },
            { text: "组织结构",	width: 100, align: "center", sortable: false, menuDisabled: true, dataIndex: "groupNames" },
            { text: "创建时间",	width: 125, align: "center", sortable: false, menuDisabled: true, dataIndex: "createTime" },
            { text: "登录时间",	width: 125, align: "center", sortable: false, menuDisabled: true, dataIndex: "lastTime" },
            { text: "状态",	    width: 50,  align: "center", sortable: false, menuDisabled: true, dataIndex: "useFlagName" }
        ],
        tbar: new Ext.Toolbar({
        	height: 35,
			items: [
		         { width: 5,   disabled: true },
		         { width: 55,  text: "新增", id: "userCreateButton", icon: jslibPath + "ext4.1/icons/user_add.png",            disabled: false, handler: userGrid_addHandler }, "-",
		         { width: 55,  text: "修改", id: "userUpdateButton", icon: jslibPath + "ext4.1/icons/user_edit.png",           disabled: true,  handler: userGrid_editHandler }, "-",
		         { width: 55,  text: "删除", id: "userDeleteButton", icon: jslibPath + "ext4.1/icons/user_del.png",            disabled: true,  handler: userGrid_validateDeleteHandler }, "-",
		         { width: 55,  text: "重置", id: "userResetButton",  icon: jslibPath + "ext4.1/icons/user_reset_password.png", disabled: true,  handler: userGrid_resetPasswordHandler }, "-",
		         { width: 150,  disabled: true },
		         { width: 150, id: "nickname_filter", xtype: "textfield", fieldLabel: "昵称", labelWidth: 40, emptyText: "昵称搜索" },
	             { width: 150, id: "role_filter", xtype: "combobox", fieldLabel: "角色", labelWidth: 40, emptyText: "角色类型", store: store_roleList_combobox, queryMode: "local", typeAhead: true, forceSelection: true, displayField: "roleName", valueField: "roleId" },
		         { width: 55,  text: "搜索", handler: userGrid_refreshHandler, icon: jslibPath + "ext4.1/icons/search.png" }
		    ]
        }),
        bbar: Ext.create("Ext.PagingToolbar", {
            store: store_userGrid,
            displayInfo: true,
            displayMsg: "当前显示{0} - {1}条，共 {2} 条记录",
            emptyMsg: "当前没有任何记录"
        }),
        listeners: {
        	selectionchange: function(thiz, selected, eOpts ){
        		if( selected.length > 0 ){
        			Ext.getCmp("userUpdateButton").enable();
        			Ext.getCmp("userDeleteButton").enable();
        			Ext.getCmp("userResetButton").enable();
        		}else{
        			Ext.getCmp("userUpdateButton").disable();
        			Ext.getCmp("userDeleteButton").disable();
        			Ext.getCmp("userResetButton").disable();
        		}
        	}
        }
    });
    
    var formPanel_userEdit = Ext.create("Ext.form.Panel", {
        bodyPadding: 20,
        bodyBorder: false,
        header: false,
        frame: false,
        fieldDefaults: { labelAlign: "right", labelWidth: 100, anchor: "100%" },
        items: [
           {id: "user_hidden_userId" , 	xtype: "hidden", 	name: "userId",  hidden: true},
           {id: "user_text_username", 	xtype: "textfield", name: "username",  fieldLabel: "用户名",		allowBlank: false, emptyText: "2~20个字符，可使用字母、数字、下划线", invalidText: "用户名不符合规则，请输入由字母、数字、下划线组成的2-20个字符！", regex: new RegExp("^[a-zA-Z0-9\_\-]{2,20}$")},
           {id: "user_text_nickname", 	xtype: "textfield", name: "nickname",  fieldLabel: "昵称",		allowBlank: false, emptyText: "2~20个字符，可使用字母、数字、中文", invalidText: "昵称不符合规则，请输入由字母、数字、中文组成的2-20个字符！", minLength: 2, maxLength: 20},
           {id: "user_text_password_1", xtype: "textfield", name: "password",  fieldLabel: "密码",		allowBlank: false, emptyText: "6~20个字符，可使用字母、数字",inputType:"password", invalidText: "登录密码不符合规则，请输入由字母、数字组成的6-20个字符！", regex: new RegExp(/^[a-zA-Z0-9]{6,20}$/)},
           {id: "user_text_password_2", xtype: "textfield", name: "password2", fieldLabel: "确认密码",	allowBlank: false, emptyText: "6~20个字符，可使用字母、数字",inputType:"password", invalidText: "确认密码不符合规则，请输入由字母、数字组成的6-20个字符！", regex: new RegExp(/^[a-zA-Z0-9]{6,20}$/), validator: validatorPasswordRepeatHandler},
	       {id: "user_radio_useFlag",    xtype: "radiogroup", fieldLabel: "是否启用", allowBlank: false, invalidText: "请选择是否启用！", items: [
              {boxLabel: "启用", name: "useFlag", inputValue: 1, checked: true},
              {boxLabel: "禁用", name: "useFlag", inputValue: 0 }
           ]},
           {xtype: "container", layout: "hbox", margin: "0 0 5 0",  items: [
				{id: "user_treepicker_group", xtype: "treepicker", width:250, fieldLabel: "组织结构", allowBlank: false, emptyText: "请选择组织结构", invalidText: "请选择组织结构！", forceSelection: true, editable: false, displayField: 'text', valueField: 'id', 
					store: store_groupTree_picker
				},
				{id: "user_combobox_roleId", xtype: "combobox",    width:230, fieldLabel: "权限角色", allowBlank: false, emptyText: "请选择权限角色", invalidText: "请选择权限角色！", queryMode: 'local', typeAhead: true, displayField: 'roleName', valueField: 'roleId', 
					store: store_roleList_combobox
				},
				{xtype: "button", width: 22, height: 22, margin: "0 0 0 12", icon: jslibPath + "ext4.1/icons/add.png" , handler: function(){formPanel_addGroup(0,0);}}  
	       ]},
        ]
    });
    
    function formPanel_addGroup(groupId, roleId){
        var store_roleList_container = Ext.create("Ext.data.Store", {
        	model: "RoleBean",
            proxy: {
                type: "ajax",
                url: "../role/getRoleBeanList.do"
            },
            autoLoad: true
        });
        
        var store_groupTree_container = Ext.create("Ext.data.TreeStore", {
        	model: "GroupTreeBean",
    		nodeParam: "groupId",
            proxy: {
                type: "ajax",
                url: "../group/getGroupTreeByParentId.do"
            },
    	    root: { id: "1", text: "全部", groupId: "1",  expanded: true },
            autoLoad: false,
            listeners: {
            	"load": function(thiz, node, records, successful, eOpts){
            		Ext.Array.each(records, function(record) {
            			record.expand();
            	    });
            	}
            }
        });
        
    	var container = Ext.create("Ext.container.Container", {
    		cls: "container_group_role", 
    		layout: "hbox", 
    		margin: "0 0 5 0"
    	});
    	
    	var combo_groupIds = Ext.create("Ext.ux.TreePicker", {
    		cls: "container_group",
    		width:250, 
    		fieldLabel: "组织结构", 
    		allowBlank: false, 
    		emptyText: "请选择组织结构", 
    		invalidText: "请选择组织结构！", 
    		forceSelection: true, 
    		editable: false,
    		displayField: 'text', 
    		valueField: 'id', 
			store: store_groupTree_container
    	});
    	
    	var combo_rooleIds = Ext.create("Ext.form.ComboBox", {
    		cls: "container_role",
    		width: 230, 
    		fieldLabel: "权限角色", 
    		allowBlank: false, 
    		emptyText: "请选择权限角色", 
    		invalidText: "请选择权限角色！", 
    		queryMode: 'local', 
    		typeAhead: true, 
    		displayField: 'roleName', 
    		valueField: 'roleId', 
			store: store_roleList_container
    	});
    	var button_add = Ext.create("Ext.Button", {
    		width: 22, height: 22, margin: "0 0 0 12", icon: jslibPath + "ext4.1/icons/delete.png" , handler: formPanel_delGroup
    	});
    	if(groupId !=0 ){
        	combo_groupIds.setValue(groupId);
    	}
    	if(roleId !=0 ){
        	combo_rooleIds.setValue(roleId);
    	}
    	container.add(combo_groupIds); container.add(combo_rooleIds); container.add(button_add); formPanel_userEdit.add(container);
    }
    function formPanel_delGroup(){
    	formPanel_userEdit.remove(this.ownerCt);
    }
    var window_userEdit = Ext.create("Ext.window.Window", {
        width: 600,
        height: "auto",
        bodyMargin: 10,
        modal: true,
        closable : true,
        closeAction: "hide",
        resizable : false,
        layout: "fit",
        items: [formPanel_userEdit],
        buttonAlign: "right",
        buttons: [
			{text: "提交", handler: userForm_submitHandler}, "-",
			{text: "取消", handler: function(){ window_userEdit.hide(); }}
	    ]
    });
    
    var formPanel_resetPassword = Ext.create("Ext.form.Panel", {
        bodyPadding: 20,
        bodyBorder: false,
        header: false,
        frame: false,
        fieldDefaults: { labelAlign: "right", labelWidth: 60, anchor: "100%" },
        items: [
            {id: "reset_hidden_userId" ,  xtype: "hidden", 	  name: "userId", hidden: true},
            {id: "reset_text_username",   xtype: "textfield", name: "username",  fieldLabel: "用户名",   allowBlank: false, readOnly: true},
            {id: "reset_text_password_1", xtype: "textfield", name: "password",  fieldLabel: "登录密码", allowBlank: false, emptyText: "请输入登录密码", inputType:"password", invalidText: "登录密码不符合规则，请输入由数字、字母组成的6-20个字符！", regex: new RegExp(/^[a-zA-Z0-9]{6,20}$/)},
            {id: "reset_text_password_2", xtype: "textfield", name: "password2", fieldLabel: "确认密码", allowBlank: false, emptyText: "请再次输入密码", inputType:"password", invalidText: "确认密码不符合规则，请输入由数字、字母组成的6-20个字符！", regex: new RegExp(/^[a-zA-Z0-9]{6,20}$/), validator : validatorResetPasswordRepeatHandler}
        ]
    });
    var window_resetPassword = Ext.create("Ext.window.Window", {
    	title: "重置密码",
        width: 500,
        height: "auto",
        bodyMargin: 10,
        modal: true,
        closable : true,
        closeAction: "hide",
        resizable : false,
        layout: "fit",
        items: [formPanel_resetPassword],
        buttonAlign: "right",
        buttons: [
	        { text: "提交", handler: resetForm_submitHandler }, "-", 
	        { text: "取消", handler: function(){ window_resetPassword.hide(); }}
        ]
    });

    /** -------------------------------------按钮事件方法区------------------------------------- */
    
    /**
     * 组织结构 - 刷新
     */
    function groupTree_refreshHandler(){
    	store_groupTree.load();
    	store_groupTree_picker.load();
    	tree_groupList.getSelectionModel().select(store_groupTree.getRootNode());
    	Ext.getCmp("nickname_filter").setValue("");
    	Ext.getCmp("role_filter").setValue("");
    	userGrid_refreshHandler();
    }

    /**
     * 组织结构 - 更改排序
     */
    function groupSequence_changeHandler(moveType){
    	if (tree_groupList.getSelectionModel().hasSelection()) {
			var record = tree_groupList.getSelectionModel().getSelection()[0];
			$.post("../group/updateSequence.do", {
				groupId: record.get("groupId"),
				parentId: record.get("parentId"),
				moveType: moveType
			}, function(data){
				if (data.success) {
					groupTree_refreshHandler();
				} else {
					liming.message_error(data.message);
				}
			});
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
    }
    	
	/**
	 * 组织结构 - 创建
	 */
	function groupTree_addChildHandler(){
    	if (tree_groupList.getSelectionModel().hasSelection()) {
    		formPanel_groupEdit.getForm().reset();
    		var record = tree_groupList.getSelectionModel().getSelection()[0];
    		Ext.getCmp("group_hidden_parentId").setValue(record.get("groupId"));
        	window_groupEdit.setTitle("创建");
    		window_groupEdit.show();
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
	}
	
	/**
     * 组织结构 - 修改
     */
	function groupTree_editHandler(){
    	if (tree_groupList.getSelectionModel().hasSelection()) {
    		formPanel_groupEdit.getForm().reset();
    		var record = tree_groupList.getSelectionModel().getSelection()[0];
    		formPanel_groupEdit.getForm().loadRecord(record);
    		window_groupEdit.setTitle("修改");
    		window_groupEdit.show();
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
	}
	
	/**
	 * 组织结构 - 提交
	 */
	function groupTree_addOrEditHandler(){
		if (!Ext.getCmp("group_text_groupName").isValid()) {
			liming.message_error(Ext.getCmp("group_text_groupName").invalidText);
		} else if (!Ext.getCmp("group_treepicker_region").getValue() || Ext.getCmp("group_treepicker_region").getValue() == 1) {
    		liming.message_error(Ext.getCmp("group_treepicker_region").invalidText);
    	} else {
    		$.post("../group/createOrUpdate.do", {
    			groupId:     Ext.getCmp("group_hidden_groupId").getValue(),
    			parentId:    Ext.getCmp("group_hidden_parentId").getValue(),
    			groupName:   Ext.getCmp("group_text_groupName").getValue(),
    			regionId:    Ext.getCmp("group_treepicker_region").getValue(),
    			description: Ext.getCmp("group_text_description").getValue(),
    			useFlag:     Ext.getCmp("group_radio_useFlag").getValue().useFlag
    		}, function(data){
    			if(data.success){
                	window_groupEdit.hide();
                	liming.message_info(data.message);
                	groupTree_refreshHandler();	
    			}else{
                	liming.message_error(data.message);
    			}
    		});
		}
	}
	
	/**
     * 组织结构 - 删除
     */
	function groupTree_deleteHandler(){
		if (tree_groupList.getSelectionModel().hasSelection()) {
			liming.message_question("是否确定删除组织结构?", function(btn){
    			var record = tree_groupList.getSelectionModel().getSelection()[0];
        		$.post("../group/deleteGroup.do", {
        			groupId: record.get("groupId")
        		}, function(data){
        			if (data.success) {
        				liming.message_info(data.message);
        				groupTree_refreshHandler();
        			} else {
        				liming.message_error(data.message);
        			}
        		});
	    	});
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
	}

	/**------------------------------------用户方法区-------------------------------*/
    /**
     * 用户刷新
     */
    function userGrid_refreshHandler(){
    	var groupId = 1;
    	if (tree_groupList.getSelectionModel().hasSelection()) {
    		var record = tree_groupList.getSelectionModel().getSelection()[0];
    		groupId = record.get("groupId");
    	}
    	if( groupId == 1 ){
    		groupId = 0;
    	}
    	var roleId = Ext.getCmp("role_filter").getValue();
    	var nickname = Ext.getCmp("nickname_filter").getValue();
    	store_userGrid.currentPage = 1;
    	store_userGrid.proxy.extraParams = {"groupId": groupId, "roleId": roleId, "nickname": encodeURIComponent(nickname)};
    	store_userGrid.load();
    }
    
    /**
     * 用户 - 新增按钮事件
     */
    function userGrid_addHandler(){
    	formPanel_userEdit.getForm().reset();
    	Ext.getCmp("user_text_username").setReadOnly(false);
    	Ext.getCmp("user_text_password_1").show();
    	Ext.getCmp("user_text_password_2").show();
    	var record = tree_groupList.getSelectionModel().getSelection()[0];
    	if (record) {
    		Ext.getCmp("user_treepicker_group").setValue(record.get("groupId"));
    	}
    	$(".container_group_role").each(function(index, thiz){
    		formPanel_userEdit.remove(Ext.getCmp($(thiz).attr("id")));
    	});
    	window_userEdit.setTitle("创建用户");
    	window_userEdit.show();
    }
    
    /**
     * 用户 - 修改按钮事件
     */
    function userGrid_editHandler(){
    	if (grid_userList.getSelectionModel().hasSelection()) {
    		formPanel_userEdit.getForm().reset();
        	var record = grid_userList.getSelectionModel().getSelection()[0];
        	formPanel_userEdit.getForm().loadRecord(record);
        	Ext.getCmp("user_text_username").setReadOnly(true);
        	Ext.getCmp("user_text_password_1").setValue("123456");
        	Ext.getCmp("user_text_password_2").setValue("123456");
        	Ext.getCmp("user_text_password_1").hide();
        	Ext.getCmp("user_text_password_2").hide();
        	$(".container_group_role").each(function(index, thiz){
        		formPanel_userEdit.remove(Ext.getCmp($(thiz).attr("id")));
        	});
        	var groupIds = record.get("groupIds");
        	var roleIds = record.get("roleIds");
        	if(groupIds.length >= 1){
        		Ext.getCmp("user_treepicker_group").setValue(groupIds[0]);
        		Ext.getCmp("user_combobox_roleId").setValue(roleIds[0]);
        		for(var i=1; i<groupIds.length; i++){
            		formPanel_addGroup(groupIds[i], roleIds[i]);
        		}
        	}
        	window_userEdit.setTitle("修改用户 - " + record.get("nickname"));
        	window_userEdit.show();
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
    }
    
    /**
     * 用户 - 重置密码按钮事件
     */
    function userGrid_resetPasswordHandler(){
    	if (grid_userList.getSelectionModel().hasSelection()) {
    		formPanel_resetPassword.getForm().reset();
    		var record = grid_userList.getSelectionModel().getSelection()[0];
    		Ext.getCmp("reset_hidden_userId").setValue(record.get("userId"));
    		Ext.getCmp("reset_text_username").setValue(record.get("username"));
        	window_resetPassword.show(); 
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
    }
    
    /**
     * 用户 - 删除按钮事件
     */
    function userGrid_validateDeleteHandler(){
    	if (grid_userList.getSelectionModel().hasSelection()) {
    		liming.message_question("是否确定删除用户，这将删除与此用户关联的所有信息?", function(){
    			var userId = grid_userList.getSelectionModel().getSelection()[0].get("userId");
    	    	$.post("../user/deleteUser.do", {
    	    		userId: userId
    	    	}, function(data){
    				if (data.success) {
    					liming.message_info(data.message);
    					userGrid_refreshHandler();
    				} else {
    					liming.message_error(data.message);
    				}
    	    	});
	    	});
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
    }
    
    /**
     * 用户form - 提交事件
     */
    function userForm_submitHandler(){
		if (!Ext.getCmp("user_text_username").isValid()) {
			liming.message_error(Ext.getCmp("user_text_username").invalidText);
		} else if (!Ext.getCmp("user_text_password_1").isValid()) {
			liming.message_error(Ext.getCmp("user_text_password_1").invalidText);
		} else if (!Ext.getCmp("user_text_password_2").isValid()) {
			liming.message_error(Ext.getCmp("user_text_password_2").invalidText);
		} else if (!Ext.getCmp("user_text_nickname").isValid()) {
			liming.message_error(Ext.getCmp("user_text_nickname").invalidText);
		} else if (!Ext.getCmp("user_combobox_roleId").isValid()) {
			liming.message_error(Ext.getCmp("user_combobox_roleId").invalidText);
		} else if (!Ext.getCmp("user_treepicker_group").getValue() || Ext.getCmp("user_treepicker_group").getValue() == 1) {
			liming.message_error(Ext.getCmp("user_treepicker_group").invalidText);
		} else {
			var groupIds = [];
			var roleIds = [];
			groupIds.push(Ext.getCmp("user_treepicker_group").getValue());
			roleIds.push(Ext.getCmp("user_combobox_roleId").getValue());
			$(".container_group_role").each(function(index, thiz){
				groupIds.push(Ext.getCmp($(thiz).attr("id")).items.items[0].getValue());
				roleIds.push(Ext.getCmp($(thiz).attr("id")).items.items[1].getValue());
	    	});
			$.post("../user/createOrUpdate.do", {
				userId:   Ext.getCmp("user_hidden_userId").getValue(), 
				username: Ext.getCmp("user_text_username").getValue(), 
				nickname: Ext.getCmp("user_text_nickname").getValue(), 
				password: Ext.getCmp("user_text_password_2").getValue(), 
				groupIds: groupIds.toString(), 
				roleIds:  roleIds.toString(),
				useFlag:  Ext.getCmp("user_radio_useFlag").getValue().useFlag
			}, function(data){
				if (data.success) {
					liming.message_info(data.message);
					window_userEdit.hide();
					userGrid_refreshHandler();
				} else {
					liming.message_error(data.message);
				}
			});
		}
    }
    
    function resetForm_submitHandler(){
    	if (!Ext.getCmp("reset_text_password_1").isValid()) {
			liming.message_error(Ext.getCmp("reset_text_password_1").invalidText);
		} else if (!Ext.getCmp("reset_text_password_2").isValid()) {
			liming.message_error(Ext.getCmp("reset_text_password_2").invalidText);
		} else {
			$.post("../user/userPasswordReset.do", {
				userId: Ext.getCmp("reset_hidden_userId").getValue(),
				oldPassword: Ext.getCmp("reset_text_password_1").getValue(),
				newPassword: Ext.getCmp("reset_text_password_2").getValue()
			}, function(data){
				if(data.success){
					window_resetPassword.hide();
					liming.message_info(data.message);
				}else{
					liming.message_error(data.message);
				}
			});
		}
    }
    
    /** -------------------------------------form验证方法区------------------------------------- */
    /**
     * 新增用户时 - 二次验证密码方法
     */
    function validatorPasswordRepeatHandler(){
    	if (Ext.getCmp("user_text_password_2").getValue().length > 0 && Ext.getCmp("user_text_password_1").getValue() != Ext.getCmp("user_text_password_2").getValue()) {
			Ext.getCmp("user_text_password_2").invalidText = "两次输入的密码不相同！";
			return Ext.getCmp("user_text_password_2").invalidText;
		}
    	return true;
    }

    /**
     * 修改密码时 - 二次验证密码方法
     */
    function validatorResetPasswordRepeatHandler(){
    	if (Ext.getCmp("reset_text_password_2").getValue().length > 0 && Ext.getCmp("reset_text_password_1").getValue() != Ext.getCmp("reset_text_password_2").getValue()) {
			Ext.getCmp("reset_text_password_2").invalidText = "两次输入的密码不相同！";
			return Ext.getCmp("reset_text_password_2").invalidText;
		}
    	return true;
    }
});