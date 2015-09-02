/**
 * 配置管理
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = "under";
	
    /** -------------------------------------数据源区------------------------------------- */
	
	/** 属性请求store */
    var store_propertyGrid = Ext.create("Ext.data.Store", {
    	model: "ApplicationBean",
    	pageSize: 18,
        proxy: {
            type: "ajax",
            url: "../config/getApplicationListBean_grid.do",
            reader: {
            	root: "dataArray",
                totalProperty: "totalSize"
            }
        },
        autoLoad: true
    });
	
    /** -------------------------------------视图区------------------------------------- */
    var grid_property = Ext.create("Ext.grid.Panel", {
    	store: store_propertyGrid,
        renderTo: "grid_property_div",
        selModel: {selType: "cellmodel"},
        title: "系统配置",
        width: 1000,
        height: 501,
        iconCls: "ext-grid-icon-cls",
        frame: false,
        plugins: [
			Ext.create("Ext.grid.plugin.CellEditing", {
				clicksToEdit: 1
			})
	    ],
        columns: [
            { header: "序号", 	width: 50,   align: "center", xtype: "rownumberer" },
            { header: "配置名称", width: 150,  align: "left", sortable: false, dataIndex: "propertyName" },
            { header: "参数取值", width: 250,  align: "left", sortable: false, dataIndex: "propertyValue", editor: new Ext.form.field.Text() }, 
            { header: "配置标识", width: 250,  align: "left", sortable: false, dataIndex: "propertyFlag"}, 
            { header: "参数说明", width: 298,  align: "left", sortable: false, dataIndex: "description"}
	    ],
	    tbar: new Ext.Toolbar({
	    	height: 35,
			items : [
			    { width: 5,   disabled: true },
			    { width: 80,  text: "提交配置", handler: property_submitHandler, icon: jslibPath + "ext4.1/icons/submit.png" }, "-",
			    { width: 80,  text: "重新加载", handler: property_reloadHandler, icon: jslibPath + "ext4.1/icons/reset.png" },
			    { width: 640,   disabled: true },
			    { width: 60,  text: "连接状态：", xtype: "label" },"-",
			    { width: 16,  text: "", height: 16, xtype: "image", id: "label_connectStatus", src: jslibPath + "ext4.1/icons/state_error.png" },
			    { width: 5,   disabled: true },
			    { width: 80,  text: "重新连接", width: 80, id: "button_connect", handler: xmpp_reconnectHandler, icon: jslibPath + "ext4.1/icons/reset.png" }
		    ]
        }),
        bbar: Ext.create("Ext.PagingToolbar", {
        	store: store_propertyGrid,
            displayInfo: true,
            displayMsg: "当前显示{0} - {1}条，共 {2} 条记录",
            emptyMsg: "当前没有任何记录"
        })
    });
    
    /** -------------------------------------方法区------------------------------------- */

    /**
     * 连接状态
     */
    function xmpp_statusHandler(sign){
    	$.post("../config/isConnection.do", function(data){
			if (data) {
				Ext.getCmp("label_connectStatus").setSrc(jslibPath + "ext4.1/icons/state_success.png");
				if (sign != 0) {
					liming.message_info("连接xmpp服务器操作成功！");
				}
    		} else {
    			Ext.getCmp("label_connectStatus").setSrc(jslibPath + "ext4.1/icons/state_error.png");
    			if (sign != 0){
    				liming.message_error("连接xmpp服务器操作失败！");
    			}
    		}
		});
    }

    /**
     * 重新连接
     */
    function xmpp_reconnectHandler(){
    	liming.message_question("是否确认重新连接xmpp服务器?", function(){
    		liming.message_progress("重新连接xmpp服务器中，请稍后...");
        	$.post("../config/reconnectOpenfire.do", function(data){
    			if (data.success) {
    				xmpp_statusHandler(1);
        		} else {
        			Ext.getCmp("label_connectStatus").setSrc(jslibPath + "ext4.1/icons/state_error.png");
        			liming.message_error(data.message);
        		}
    		});
    	});
    }
    
    /**
     * 重新加载配置
     */
    function property_reloadHandler(){
    	liming.message_question("是否确认重新加载系统配置?", function(){
        	$.post("../config/reloadApplicationConfig.do", function(data){
    			if (data.success) {
    				store_propertyGrid.load();
        			liming.message_info(data.message);
        		} else {
        			liming.message_error(data.message);
        		}
    		});
    	});
    }
    
    /**
     * 提交配置
     */
    function property_submitHandler(){
    	var ids = [], values = [];
    	store_propertyGrid.each(function(item, index){
    		if (item.dirty) {
    			ids.push(item.get("uid")),
    			values.push(item.get("propertyValue"));
    		}
    	});
    	
    	liming.message_question("是否提交当前的系统配置修改?", function(){
    		$.post("../config/updateApplicationConfig.do", {
    			ids: ids.toString(),
    			values: values.toString()
    		}, function(data){
    			if (data.success) {
    				store_propertyGrid.load();
        			liming.message_info(data.message);
        		} else {
        			liming.message_error(data.message);
        		}
    		});
    	});
    }
    
    xmpp_statusHandler(0);

});