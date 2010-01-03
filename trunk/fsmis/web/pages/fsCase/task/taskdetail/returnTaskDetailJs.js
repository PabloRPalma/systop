var currentTaskDetailId; // The current role that we are assigning perm for.
var userId;
var frmReturn = new Ext.form.FormPanel({
  regin:'north',
  width:460,
  height:200,
  labelAlign:'right',
  labelWidth:60,
  
  defaultType: 'textfield',
  defaults: {
    width: 200,
    allowBlank: false
  },
  items:[{
    xtype:'hidden',
    name:'model.id'
  },{
    fieldLabel: '负责人',
    name: 'model.returnPeople',
    blankText : '负责人姓名不能为空' 
  },{
     fieldLabel: '退回原因',
     name: 'model.returnReason',
     xtype:'textarea',
     blankText : '退回原因不能为空' ,
     height:100,
     width:360
  }],
  buttons:[{
    text:'退回',
    handler: function() {/*
    	form.getForm().findField('model.id').setValue(currentTaskDetailId);
      if (!form.getForm().isValid()) {
        return;
      }
      form.getForm().submit({
        url: '/taskdetail/doReturnTaskDetail.do',
        success: function(f, action) {
        	win.hide();
        	Ext.my().msg('','您已经成功退回了任务.');
	        if (action.result.success) {
		        Ext.Msg.alert('消息', action.result.msg, function() {
	        });
	      }
	    }
      });*/
	if (!frmReturn.getForm().isValid()) {
        return;
      }
	  Ext.Ajax.request({
			url : '/taskdetail/doReturnTaskDetail.do',
			params : {
				'model.id' : currentTaskDetailId,
				'model.returnPeople':frmReturn.getForm().findField('model.returnPeople').getValue(),
				'model.returnReason':frmReturn.getForm().findField('model.returnReason').getValue()				
			},
			method : 'POST',
			success : function() {
				
				
				winReturn.hide();
				Ext.my().msg('', '您已经成功退回了任务.');
				ECSideUtil.reload('ec');
			},
			 failure: function() {
		          Ext.Msg.alert('错误', "退回失败");
		    }
		});
    }
  }]
});



var taskDetailPanelReturn = new Ext.Panel({
      el : 'returnTaskDetail',
      title : null,
      width : 500,
      height : 300,
      items : [frmReturn],
      loadMask : true
      
    });


var winReturn = new Ext.Window({
      el : 'winReturnTaskDetail',
      layout : 'fit',
      width : 500,
      height : 255,
      closeAction : 'hide',
      plain : false,
      modal : true,
      items : [taskDetailPanelReturn],
      bodyStyle : 'padding:5px;',
      buttonAlign : 'center',
      buttons : []
    });
function returnTaskDetail(taskDetailId, userName) {
  currentTaskDetailId = taskDetailId;
  userId = userName;
  if (winReturn) {  	
    winReturn.show();
    frmReturn.getForm().findField('model.returnPeople').setValue(userId);
  }
}

//--------------------------------------------------------
/*
var frmDealWith = new Ext.form.FormPanel({
  regin:'north',
  width:300,
  height:160,
  labelAlign:'right',
  labelWidth:60,
  
  defaultType: 'textfield',
  defaults: {
    width: 200,
    allowBlank: false
  },
  items:[{
    xtype:'hidden',
    name:'model.id'
  },{
    fieldLabel: '负责人',
    name: 'model.returnPeople',
    blankText : '负责人姓名不能为空' 
  },{
     fieldLabel: '退回原因',
     name: 'model.returnReason',
     xtype:'textarea',
     blankText : '退回原因不能为空' ,
     height:100,
     width:100
  }],
  buttons:[{
    text:'退回',
    handler: function() {/*
    	form.getForm().findField('model.id').setValue(currentTaskDetailId);
      if (!form.getForm().isValid()) {
        return;
      }
      form.getForm().submit({
        url: '/taskdetail/doReturnTaskDetail.do',
        success: function(f, action) {
        	win.hide();
        	Ext.my().msg('','您已经成功退回了任务.');
	        if (action.result.success) {
		        Ext.Msg.alert('消息', action.result.msg, function() {
	        });
	      }
	    }
      });/
	if (!frmDealWith.getForm().isValid()) {
        return;
      }
	  Ext.Ajax.request({
			url : '/taskdetail/doReturnTaskDetail.do',
			params : {
				'model.id' : currentTaskDetailId,
				'model.returnPeople':frmDealWith.getForm().findField('model.returnPeople').getValue(),
				'model.returnReason':frmDealWith.getForm().findField('model.returnReason').getValue()				
			},
			method : 'POST',
			success : function() {
				
				
				winDealWith.hide();
				Ext.my().msg('', '您已经成功退回了任务.');
				ECSideUtil.reload('ec');
			},
			 failure: function() {
		          Ext.Msg.alert('错误', "退回失败");
		    }
		});
    }
  }]
});



var taskDetailPanelDealWith = new Ext.Panel({
      el : 'returnTaskDetail',
      title : null,
      width : 300,
      height : 200,
      items : [frmDealWith],
      loadMask : true
      
    });


var winDealWith = new Ext.Window({
      el : 'winReturnTaskDetail',
      layout : 'fit',
      width : 380,
      height : 200,
      closeAction : 'hide',
      plain : false,
      modal : true,
      items : [taskDetailPanelDealWith],
      bodyStyle : 'padding:5px;',
      buttonAlign : 'center',
      buttons : []
    });

function dealWithTaskDetail(taskDetailId) {
	alert(33);
	currentTaskDetailId = taskDetailId;
	if (winDealWith) {
		winDealWith.show();
		var btn = Ext.get("btnReturn").dom;
		btn.handler = function() {
			alert(1122);
			Ext.Ajax.request({
						url : '/taskdetail/doDealWithTaskDetail.do',
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