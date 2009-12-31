var currentTaskDetailId; // The current role that we are assigning perm for.

var dealWithTaskDetailPanel = new Ext.Panel({
			el : 'dealWithTaskDetail',
			title : null,
			width : 500,
			height : 260,
			loadMask : true
		});

var win = new Ext.Window({
			el : 'winDealWithTaskDetail',
			layout : 'fit',
			width : 580,
			height : 263,
			closeAction : 'hide',
			plain : false,
			modal : true,
			items : [dealWithTaskDetailPanel],
			bodyStyle : 'padding:5px;',
			buttonAlign : 'center',
			buttons : []
		});

function returnTaskDetail(taskDetailId, userName) {
	currentTaskDetailId = taskDetailId;
	if (win) {
		win.show();
		var btn = Ext.get("btnReturn").dom;
		btn.handler = function() {
			alert(1122);
			Ext.Ajax.request({
						url : '/security/permission/saveRolePermissions.do',
						params : {
							'model.id' : currentTaskDetailId,
							'model.returnPeople' : Ext.get("people").dom.value,
							'model.returnReason' : Ext.get("reason").dom.value
						},
						method : 'POST',
						success : function() {
							win.hide();
							Ext.my().msg('', '您已经成功的为角色分配了许可.');
						}
					});
			return false;
		}
	}
}
/*
function submitFrm() {
	alert(1122);
	Ext.Ajax.request({
				url : '/security/permission/saveRolePermissions.do',
				params : {
					'model.id' : currentTaskDetailId,
					'model.returnPeople' : Ext.get("people").dom.value,
					'model.returnReason' : Ext.get("reason").dom.value
				},
				method : 'POST',
				success : function() {
					win.hide();
					Ext.my().msg('', '您已经成功的为角色分配了许可.');
				}
			});
	return false;
}*/