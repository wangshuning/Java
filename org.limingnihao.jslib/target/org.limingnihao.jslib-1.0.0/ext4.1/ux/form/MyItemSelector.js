/*
 * Note that this control will most likely remain as an example, and not as a core Ext form
 * control.  However, the API will be changing in a future release and so should not yet be
 * treated as a final, stable API at this time.
 */

/**
 * A control that allows selection of between two Ext.ux.form.MultiSelect controls.
 */
Ext.define('Ext.ux.form.MyItemSelector', {
    extend: 'Ext.ux.form.MultiSelect',
    alias: ['widget.myitemselectorfield', 'widget.myitemselector'],
    alternateClassName: ['Ext.ux.MyItemSelector'],
    requires: [
        'Ext.button.Button',
        'Ext.ux.form.MultiSelect'
    ],

    /**
     * @cfg {Boolean} [hideNavIcons=false] True to hide the navigation icons
     */
    hideNavIcons:false,

    /**
     * @cfg {Array} buttons Defines the set of buttons that should be displayed in between the ItemSelector
     * fields. Defaults to <tt>['top', 'up', 'add', 'remove', 'down', 'bottom']</tt>. These names are used
     * to build the button CSS class names, and to look up the button text labels in {@link #buttonsText}.
     * This can be overridden with a custom Array to change which buttons are displayed or their order.
     */
    buttons: ['top', 'up', 'add', 'remove', 'down', 'bottom'],

    /**
     * @cfg {Object} buttonsText The tooltips for the {@link #buttons}.
     * Labels for buttons.
     */
    buttonsText: {
        top: "Move to Top",
        up: "Move Up",
        add: "添加",
        remove: "移除",
        down: "Move Down",
        bottom: "Move to Bottom"
    },

    initComponent: function() {
        var me = this;

        me.ddGroup = me.id + '-dd';
        me.callParent();

        // bindStore must be called after the fromField has been created because
        // it copies records from our configured Store into the fromField's Store
        me.bindStore(me.store);
    },

    createList: function(){
        var me = this;

        return Ext.create('Ext.ux.form.MultiSelect', {
            submitValue: false,
            flex: 1,
            dragGroup: me.ddGroup,
            dropGroup: me.ddGroup,
            store: {
                model: me.store.model,
                data: []
            },
            displayField: me.displayField,
            disabled: me.disabled,
            listeners: {
                boundList: {
                    scope: me,
                    itemdblclick: me.onItemDblClick,
                    drop: me.syncValue
                }
            }
        });
    },

    setupItems: function() {
        var me = this;

        me.fromField = me.createList();
        me.toField = me.createList();

        return {
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            items: [
                me.fromField,
                {
                    xtype: 'container',
                    margins: '0 4',
                    width: 22,
                    layout: {
                        type: 'vbox',
                        pack: 'center'
                    },
                    items: me.createButtons()
                },
                me.toField
            ]
        };
    },

    createButtons: function(){
        var me = this,
            buttons = [];

        if (!me.hideNavIcons) {
            Ext.Array.forEach(me.buttons, function(name) {
                buttons.push({
                    xtype: 'button',
                    tooltip: me.buttonsText[name],
                    handler: me['on' + Ext.String.capitalize(name) + 'BtnClick'],
                    cls: Ext.baseCSSPrefix + 'form-itemselector-btn',
                    iconCls: Ext.baseCSSPrefix + 'form-itemselector-' + name,
                    navBtn: true,
                    scope: me,
                    margin: '4 0 0 0'
                });
            });
        }
        return buttons;
    },

    getSelections: function(list){
        var store = list.getStore(),
            selections = list.getSelectionModel().getSelection();

        return Ext.Array.sort(selections, function(a, b){
            a = store.indexOf(a);
            b = store.indexOf(b);

            if (a < b) {
                return -1;
            } else if (a > b) {
                return 1;
            }
            return 0;
        });
    },

    onTopBtnClick : function() {
        var list = this.toField.boundList,
            store = list.getStore(),
            selected = this.getSelections(list);

        store.suspendEvents();
        store.remove(selected, true);
        store.insert(0, selected);
        store.resumeEvents();
        list.refresh();
        this.syncValue(); 
        list.getSelectionModel().select(selected);
    },

    onBottomBtnClick : function() {
        var list = this.toField.boundList,
            store = list.getStore(),
            selected = this.getSelections(list);

        store.suspendEvents();
        store.remove(selected, true);
        store.add(selected);
        store.resumeEvents();
        list.refresh();
        this.syncValue();
        list.getSelectionModel().select(selected);
    },

    onUpBtnClick : function() {
        var list = this.toField.boundList,
            store = list.getStore(),
            selected = this.getSelections(list),
            i = 0,
            len = selected.length,
            index = store.getCount();

        // Find index of first selection
        for (; i < len; ++i) {
            index = Math.min(index, store.indexOf(selected[i]));
        }
        // If first selection is not at the top, move the whole lot up
        if (index > 0) {
            store.suspendEvents();
            store.remove(selected, true);
            store.insert(index - 1, selected);
            store.resumeEvents();
            list.refresh();
            this.syncValue();
            list.getSelectionModel().select(selected);
        }
    },

    onDownBtnClick : function() {
        var list = this.toField.boundList,
            store = list.getStore(),
            selected = this.getSelections(list),
            i = 0,
            len = selected.length,
            index = 0;

        // Find index of last selection
        for (; i < len; ++i) {
            index = Math.max(index, store.indexOf(selected[i]));
        }
        // If last selection is not at the bottom, move the whole lot down
        if (index < store.getCount() - 1) {
            store.suspendEvents();
            store.remove(selected, true);
            store.insert(index + 2 - len, selected);
            store.resumeEvents();
            list.refresh();
            this.syncValue();
            list.getSelectionModel().select(selected);
        }
    },

    onAddBtnClick : function() {
        var me = this,
            fromList = me.fromField.boundList,
            selected = this.getSelections(fromList);

        fromList.getStore().remove(selected);
        this.toField.boundList.getStore().add(selected);
        this.syncValue();
    },

    onRemoveBtnClick : function() {
        var me = this,
            toList = me.toField.boundList,
            selected = this.getSelections(toList);

        toList.getStore().remove(selected);
        this.fromField.boundList.getStore().add(selected);
        this.syncValue();
    },

    syncValue: function() {
        this.setValue(this.toField.store.getRange()); 
    },

    onItemDblClick: function(view, rec){
        var me = this,
            from = me.fromField.store,
            to = me.toField.store,
            current,
            destination;

        if (view === me.fromField.boundList) {
            current = from;
            destination = to;
        } else {
            current = to;
            destination = from;
        }
        current.remove(rec);
        destination.add(rec);
        me.syncValue();
    },

    setValue: function(value){
        var me = this,
            fromStore = me.fromField.store,
            toStore = me.toField.store,
            selected;

        /* Wait for from store to be loaded 
        if (!me.fromField.store.getCount()) {
            me.fromField.store.on({
                load: Ext.Function.bind(me.setValue, me, [value]),
                single: true
            });
            return;
        }
        */
        toStore.valueField = me.valueField;
        fromStore.valueField = me.valueField;
        valueObj = value;
        value = me.setupValue(value);
        me.mixins.field.setValue.call(me, value);
        selected = me.getRecordsForValue(value);

        //去重 
        Ext.Array.forEach(toStore.getRange(), function(rec){
        	var count = 0;
        	Ext.Array.forEach(toStore.getRange(), function(rec2){
				if(rec.get(toStore.valueField) == rec2.get(toStore.valueField)){
					count++;
				}
        	});
        	if(count > 1){
        		toStore.remove(rec);
        	}
        });
        Ext.Array.forEach(fromStore.getRange(), function(rec){
        	var count = 0;
        	Ext.Array.forEach(fromStore.getRange(), function(rec2){
				if(rec.get(fromStore.valueField) == rec2.get(fromStore.valueField)){
					count++;
				}
        	});
        	if(count > 1){
        		fromStore.remove(rec);
        	}
        });
    	//console.info("myitemselector setValue!");
    },
    // 修改设置值
    setUpdateValue: function(value){
        var me = this,
	        fromStore = me.fromField.store,
	        toStore = me.toField.store;

        fromStore.removeAll();
        toStore.removeAll();
        
        toStore.valueField = me.valueField;
        fromStore.valueField = me.valueField;
        
    	toStore.add(value);
        //去重 
        Ext.Array.forEach(toStore.getRange(), function(rec){
        	var count = 0;
        	Ext.Array.forEach(toStore.getRange(), function(rec2){
				if(rec.get(toStore.valueField) == rec2.get(toStore.valueField)){
					count++;
				}
        	});
        	if(count > 1){
        		toStore.remove(rec);
        	}
        });
    	//console.info("myitemselector setUpdateValue!");
    },
    // 获取值
    getValue: function(){
        var me = this,
        toStore = me.toField.store;
        toStore.valueField = me.valueField;
        var valueArray = [];
        Ext.Array.forEach(toStore.getRange(), function(rec){
        	valueArray.push( rec.get(toStore.valueField) );
        });
        return valueArray;
    },
    onBindStore: function(store, initial) {
        var me = this;

        if (me.fromField) {
            me.fromField.store.removeAll()
            me.toField.store.removeAll();

            // Add everything to the from field as soon as the Store is loaded
            if (store.getCount()) {
                me.populateFromStore(store);
            } else {
                me.store.on('load', me.populateFromStore, me);
            }
        }
    },

    populateFromStore: function(store) {
        this.fromField.store.add(store.getRange());
        
        // setValue wait for the from Store to be loaded
        this.fromField.store.fireEvent('load', this.fromField.store);
    },

    onEnable: function(){
        var me = this;

        me.callParent();
        me.fromField.enable();
        me.toField.enable();

        Ext.Array.forEach(me.query('[navBtn]'), function(btn){
            btn.enable();
        });
    },

    onDisable: function(){
        var me = this;

        me.callParent();
        me.fromField.disable();
        me.toField.disable();

        Ext.Array.forEach(me.query('[navBtn]'), function(btn){
            btn.disable();
        });
    },

    onDestroy: function(){
        this.bindStore(null);
        this.callParent();
    },
    
    reset: function(){
        var me = this;
        me.fromField.store.removeAll();
        me.toField.store.removeAll();
    	//console.info("myitemselector reset!");
    }
});
