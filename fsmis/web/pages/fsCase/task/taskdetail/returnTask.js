var currentRoleId; // The current role that we are assigning perm for.

var grid = new Ext.Panel({
	el : 'returnTaskDetail',
	title : null,
	width : 500,
	height : 420,
	loadMask : true
});

var win = new Ext.Window({
	el : 'winReturnTaskDetail',
	tbar : [{
		text : '保存',
		handler : function() {
			Ext.Ajax.request({
				url : '/security/permission/saveRolePermissions.do',
				params : {
					'role.id' : currentRoleId
				},
				method : 'POST',
				success : function() {
					win.hide();
					Ext.my().msg('', '您已经成功的为角色分配了许可.');
				}
			});
		}
	}, {
		text : '取消',
		handler : function() {
			win.hide();
		}
	}],
	layout : 'fit',
	width : 580,
	height : 380,
	closeAction : 'hide',
	plain : false,
	modal : true,
	items : [grid],
	bodyStyle : 'padding:5px;',
	buttonAlign : 'center',
	buttons : []
});

function returnTask(roleId) {
	currentRoleId = roleId;
	if (win) {
		win.show();
	}
}