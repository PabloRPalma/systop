var currentPermId; // The current role that we are assigning perm for.

var store = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
		url : ''// URL will defined dynamicly.
	}),
	reader : new Ext.data.JsonReader({
		root : 'root',
		totalProperty : 'totalProperty',
		id : 'id',
		fields : [{
			name : 'id'
		}, {
			name : 'name'
		}, {
			name : 'resString'
		}, {
			name : 'changed'
		}]
	}),

	remoteSort : true
});
var cm = new Ext.grid.ColumnModel([{
	header : "资源名",
	dataIndex : 'name',
	width : 300,
	sortable : true
}, {
	header : "资源串",
	dataIndex : 'resString',
	width : 300,
	align : 'left'
}, {
	header : "选择",
	dataIndex : 'changed',
	width : 50,
	sortable : false,
	align : 'center',
	renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
		var checked = (value == true) ? 'checked' : '';
		return "<input type='checkbox' value='" + record.data["id"] + "' "
				+ checked + " id='cb' class='checkbox' "
				+ "onclick='javascript:onSelectResource(this)'>"
	}
}]);
var grid = new Ext.grid.GridPanel({
	el : 'res_grid',
	title : null,
	width : 700,
	height : 500,
	store : store,
	loadMask : true,
	cm : cm,
	bbar : new Ext.PagingToolbar({
		pageSize : 20,
		store : store,
		displayInfo : true,
		displayMsg : '共{2}条记录,显示{0}到{1}',
		emptyMsg : "没有记录"
	})
});

function toolbarItmes() {
	var div = document.createElement("div");
	var txt = document.createElement("input");
	div.style.margin = "2px;";
	txt.type = "text";
	txt.name = "model.name";
	txt.id = "res-name";
	var msg = document.createTextNode("资源名:");
	div.appendChild(msg);
	div.appendChild(txt);
	return div;
}

var tbarItems = toolbarItmes();

var win = new Ext.Window({
	el : 'win',
	tbar : [tbarItems, {
		text : '查询',
		handler : function() {
			store.proxy.conn.url = url();
			store.load({
				params : {
					start : 0,
					limit : 20
				}
			});
		}
	}, {
		text : '保存',
		handler : function() {
			Ext.Ajax.request({
				url : '/security/resource/savePermissionResources.do',
				params : {
					'permission.id' : currentPermId
				},
				method : 'POST',
				success : function() {
					win.hide();
					Ext.my().msg('', '您已经成功的为许可分配了资源.');
				}
			});
		}
	}, {
		text : '取消',
		handler : function() {
			cancelAssign();
			win.hide();
		}
	}],
	layout : 'fit',
	width : 700,
	height : 530,
	closeAction : 'hide',
	plain : false,
	modal : true,
	items : [grid],
	bodyStyle : 'padding:5px;',
	buttonAlign : 'center',
	buttons : []
});

win.addListener('hide', cancelAssign); // When dialog closed, a cancel command
										// will be sent to backend.

function cancelAssign() {
	Ext.Ajax.request({
		url : '/security/resource/cancleSavePermissionResources.do',
		params : {
			'permission.id' : currentPermId
		},
		method : 'POST'
	});
}

function onSelectResource(cb) {
	var url = (cb.checked)
			? '/security/resource/selectResource.do'
			: '/security/resource/deselectResource.do';
	Ext.Ajax.request({
		url : url,
		params : {
			'permission.id' : currentPermId,
			'model.id' : cb.value
		},
		method : 'POST'
	});
}

function assignResources(permId) {
	currentPermId = permId;
	if (win) {
		win.show();
		store.proxy.conn.url = url();
		store.load({
			params : {
				start : 0,
				limit : 20
			}
		});
	}
}

function url() {
	return '/security/resource/resourceOfPermission.do?permission.id=' + currentPermId
			+ '&model.name=' + Ext.get('res-name').getValue();
}