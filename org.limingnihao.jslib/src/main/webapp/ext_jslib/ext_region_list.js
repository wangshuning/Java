/**
 * 区域管理
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	
    /** -------------------------------------数据源区------------------------------------- */
	var store_regionTree = Ext.create("Ext.data.TreeStore", {
		model: "RegionTreeBean",
		nodeParam: "regionId",
        proxy: {
            type: "ajax",
            url: "../region/getRegionTreeAllByParentId.do"
        },
	    root: { id: "1", text: "全部",regionId: "1", regionName: "全部", expanded: true },
        autoLoad: true
    });
	
    /** -------------------------------------视图区------------------------------------- */
    var tree_regionList = Ext.create("Ext.tree.Panel", {
    	store: store_regionTree,
        renderTo: "tree_region_div",
        stateful: false,
        collapsible: false,
        multiSelect: false,
        stateId: "stateGrid",
        title: "基础数据 - 区域列表",
        height: 501,
        width: 1000,
        iconCls: "ext-grid-icon-cls",
        rootVisible: true,
        columns: [
	          { text: "名称", width: 150, align: "left",   dataIndex: "regionName",  sortable: false, menuDisabled: true, flex: 1, xtype: "treecolumn" },
	          { text: "编号", width: 100, align: "center", dataIndex: "regionCode",  sortable: false, menuDisabled: true },
	          { text: "状态", width: 100, align: "center", dataIndex: "useFlagName", sortable: false, menuDisabled: true }
        ],
        tbar: new Ext.Toolbar({
        	height: 35,
			items: [
		        { width: 5,   disabled: true },
		        { width: 55,  text: "新增", id: "createButton", disabled: true, handler: function(){regionEdit_windowShow(0);}, icon: jslibPath + "ext4.1/icons/add.png" }, "-",
		        { width: 55,  text: "修改", id: "updateButton", disabled: true, handler: function(){regionEdit_windowShow(1);}, icon: jslibPath + "ext4.1/icons/edit.png" }, "-",
		        { width: 55,  text: "删除", id: "deleteButton", disabled: true, handler: regionTree_deleteHandler, icon: jslibPath + "ext4.1/icons/delete.png" }, "-",
		        { width: 55,  text: "向上", id: "upButton",     disabled: true, handler: function(){regionSequence_changeHandler(0);}, icon: jslibPath + "ext4.1/icons/upload.png" }, "-",
		        { width: 55,  text: "向下", id: "downButton",   disabled: true, handler: function(){regionSequence_changeHandler(1);}, icon: jslibPath + "ext4.1/icons/down.png" }
		    ]
        }),
        listeners: {
        	"select": region_selectHandler
        },
        viewConfig: {
            stripeRows: true,
            listeners: {
                itemcontextmenu: function(view, record, node, index, e){
                	if (record.get("id") == 1) {
                		Ext.getCmp("menu_regionUpdate").disable();
                		Ext.getCmp("menu_regionDelete").disable();
                	} else {
                		Ext.getCmp("menu_regionUpdate").enable();
                		Ext.getCmp("menu_regionDelete").enable();
                	}
                    e.stopEvent();
                    menu_regionTree.showAt(e.getXY());
                    return false;
                }
            }
        }
    });
    var menu_regionTree = Ext.create("Ext.menu.Menu", {
    	title: "操作菜单",
        items: [
            { id: "menu_regionAddChild", text: "新增", icon: jslibPath + "ext4.1/icons/add.png",    handler: function(){regionEdit_windowShow(0);} },
            { id: "menu_regionUpdate",	 text: "修改", icon: jslibPath + "ext4.1/icons/edit.png",   handler: function(){regionEdit_windowShow(1);} },
            { id: "menu_regionDelete",   text: "删除", icon: jslibPath + "ext4.1/icons/delete.png", handler: regionTree_deleteHandler }
        ]
    });
    
    /**
     * 创建或修改
     */
    var formPanel_regionEdit = Ext.create("Ext.form.Panel", {
    	id: "formPanel_regionEdit",
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
            {id: "text_edit_regionId",   xtype: "hidden",    name: "regionId", hidden: true},
            {id: "text_edit_parentId",   xtype: "hidden",    name: "parentId", hidden: true},
            {id: "text_edit_regionName", xtype: "textfield", name: "regionName", fieldLabel: "区域名称", allowBlank: false, invalidText: "请输入区域名称！", emptyText: "请输入区域名称"},
            {id: "text_edit_regionCode", xtype: "textfield", name: "regionCode", fieldLabel: "区域编号", allowBlank: false, invalidText: "请输入区域编号！", emptyText: "请输入区域编号"},
            {id: "radio_useFlag", xtype: "radiogroup", fieldLabel: "是否启用", allowBlank: false, invalidText: "请选择是否启用！", items: [
               {boxLabel: "启用", name: "useFlag", inputValue: 1, checked: true},
               {boxLabel: "禁用", name: "useFlag", inputValue: 0}
            ]}
        ]
    });
    var window_regionEedit = Ext.create("Ext.window.Window", {
        width: 500,
        height: "auto",
        bodyMargin: 10,
        modal: true,
        closable : true,
        closeAction: "hide",
        resizable : false,
        layout: "fit",
        items: [formPanel_regionEdit],
        buttonAlign: "right",
        buttons: [
            {id: "button_edit_submit", text: "提交", handler: regionTree_addOrEditHandler}, "-", 
            {id: "button_edit_cancel", text: "取消", handler: function(){ window_regionEedit.hide(); }}
        ]
    });
    
    /** -------------------------------------按钮事件方法区------------------------------------- */
    
    function region_selectHandler(){
    	if (tree_regionList.getSelectionModel().hasSelection()) {
    		var record = tree_regionList.getSelectionModel().getSelection()[0];
			Ext.getCmp("createButton").enable();
    		if (record.get("id") == 1) {
    			Ext.getCmp("updateButton").disable();
    			Ext.getCmp("deleteButton").disable();
    			Ext.getCmp("upButton").disable();
    			Ext.getCmp("downButton").disable();
    		} else {
    			Ext.getCmp("updateButton").enable();
    			Ext.getCmp("deleteButton").enable();
    			Ext.getCmp("upButton").enable();
    			Ext.getCmp("downButton").enable();
    		}
    	}else{
			Ext.getCmp("createButton").disable();
    	}
    }
    
    /**
     * 区域tree - 刷新
     */
    function regionTree_refreshHandler(){
    	store_regionTree.load();
    }

    /**
     * 更改排序
     */
    function regionSequence_changeHandler(moveType){
    	if (tree_regionList.getSelectionModel().hasSelection()) {
			var record = tree_regionList.getSelectionModel().getSelection()[0];
			$.post("../region/updateSequence.do", {
				regionId: record.get("regionId"),
				parentId: record.get("parentId"),
				moveType: moveType
			}, function(data){
				if (data.success) {
					regionTree_refreshHandler();
				} else {
					liming.message_error(data.message);
				}
			});
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
    }
    
    /**
	 * 区域tree - 创建或修改
	 */
	function regionEdit_windowShow(sign){
		// 0 add, 1 edit
    	if (tree_regionList.getSelectionModel().hasSelection()) {
    		formPanel_regionEdit.getForm().reset();
    		var record = tree_regionList.getSelectionModel().getSelection()[0];
    		if (sign == 0) {
    			Ext.getCmp("text_edit_parentId").setValue(record.get("regionId"));
    			window_regionEedit.setTitle("新增区域");
    		} else {
    			formPanel_regionEdit.getForm().loadRecord(record);
    			window_regionEedit.setTitle("新增区域");
    		}
    		window_regionEedit.show();
    	} else {
    		liming.message_info("请先选择数据再操作！");
    	}
	}
	
	/**
	 * 区域窗口 - 提交事件
	 */
	function regionTree_addOrEditHandler(){
		
		if (!Ext.getCmp("text_edit_regionName").isValid()) {
			liming.message_error(Ext.getCmp("text_edit_regionName").invalidText);
		} else if (!Ext.getCmp("text_edit_regionCode").isValid()) {
			liming.message_error(Ext.getCmp("text_edit_regionCode").invalidText);
		} else {
			formPanel_regionEdit.getForm().submit({
				url: "../region/createOrUpdate.do",
    			method: "POST",
				success: function(form, action){
                	window_regionEedit.hide();
                	liming.message_info(action.result.message);
                	regionTree_refreshHandler();	
                },
                failure: function(form, action){
                	liming.message_error(action.result.message);
                }
            });
		}
	}
	
	/**
     * 区域tree - 删除
     */
	function regionTree_deleteHandler(){
		if (tree_regionList.getSelectionModel().hasSelection()) {
			liming.message_question("是否确定删除区域?", function(){
    			var regionId = tree_regionList.getSelectionModel().getSelection()[0].get("regionId");
        		$.post("../region/deleteRegion.do", {
        			regionId: regionId
        		}, function(data){
        			if (data.success) {
        				liming.message_info(data.message);
        				regionTree_refreshHandler();
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