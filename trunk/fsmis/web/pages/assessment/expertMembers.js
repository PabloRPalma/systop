var currentAssessmentId; // The current user that we are assigning roles for.
var currentExpertType;
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
									name : 'level'
								}, {
									name : 'expertCategory'
								}, {									
									name : 'mobile'
								}, {
									name : 'changed'
								}]
					}),

			remoteSort : true
		});
var cm = new Ext.grid.ColumnModel([{
			header : "姓名",
			dataIndex : 'name',
			width : 100,
			sortable : true
		}, {
			header : "专家级别",
			dataIndex : 'level',
			width : 100,
			align : 'left'
		}, {
			header : "专家类别",
			dataIndex : 'expertCategory',
			width : 100,
			align : 'left'
		}, {			
			header : "手机号",
			dataIndex : 'mobile',
			width : 200,
			align : 'left'
		}, {			
			header : "选择",
			dataIndex : 'changed',
			width : 50,
			sortable : false,
			align : 'center',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex,
					store) {
			var checked = (value == true) ? 'checked' : '';
			return "<input type='checkbox' value='" + record.data["id"] + "' "
					+ checked + " id='cb' class='checkbox' "
					+ "onclick='javascript:onSelectExpert(this)'>"
			}
		}]);
var grid = new Ext.grid.GridPanel({
			el : 'role_grid',
			title : null,
			width : 500,
			height : 420,
			store : store,
			loadMask : true,
			cm : cm,
			bbar : new Ext.PagingToolbar({
						pageSize : 15,
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
	txt.id = "expert-name";
	var msg = document.createTextNode("专家名称:");
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
									limit : 15
								}
							});
				}
			}, {
				text : '保存',
				handler : function() {
					Ext.Ajax.request({
								url : '/assessment/member/saveAssessmentMembers.do',
								params : {
									'assessment.id' : currentAssessmentId,
									'expertType'    : currentExpertType			
								},
								method : 'POST',
								success : function(xhr, textStatus) {
									win.hide(textStatus);
									//Ext.my().msg('', '您已经成功的为评估对象设置了专家成员.');
									if(xhr.responseText != null){
									  if(currentExpertType == '1') {
										 Ext.my().msg('', '您已经成功的为评估对象设置了专家组长.');
										 $("#leader")[0].value = xhr.responseText;
									  }	
									  if(currentExpertType == '2') {
										 Ext.my().msg('', '您已经成功的为评估对象设置了专家成员.');										  
										 $("#member")[0].value = xhr.responseText;
									  }											
									}
									//window.location.reload();
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
	width : 580,
	height : 420,
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
				url : '/assessment/member/cancelSaveAssessmentMembers.do',
				params : {
					'assessment.id' : currentAssessmentId
				},
				method : 'POST'
			});
}

function onSelectExpert(cb) {
	var url = (cb.checked)
			? '/assessment/member/selectMember.do'
			: '/assessment/member/deselectMember.do';
	Ext.Ajax.request({
				url : url,
				params : {
					'assessment.id' : currentAssessmentId,
					'model.id' : cb.value
				},
				method : 'POST'
			});
}

function membersOfAssessment(assessmentId, expertType) {
	currentAssessmentId = assessmentId;
	currentExpertType = expertType;
	if (win) {
		win.show();
		store.proxy.conn.url = url();
		store.load({
					params : {
						start : 0,
						limit : 15
					}
				});
	}
}

function url() {
	var urlStr = '/assessment/member/membersOfAssessment.do?assessment.id='
			+ currentAssessmentId + "&expertType=" + currentExpertType;
	if (Ext.get('expert-name')) {
		urlStr = urlStr + '&model.name=' + Ext.get('expert-name').getValue();
	}
	
	return encodeURI(encodeURI(urlStr));
}