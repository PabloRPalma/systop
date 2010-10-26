var currentSpecialId; 
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
								},{
									name : 'Eq_name'
								}, {
									name : 'Location_cname'
								}, {
									name : 'Mag_val'
								}, {
									name : 'Epi_lat'
								}, {
									name : 'Epi_lon'
								}, {									
									name : 'O_time'
								}, {
									name : 'changed'
								}]
					}),

			remoteSort : true
		});
var cm = new Ext.grid.ColumnModel([{
			header : "名字",
			dataIndex : 'Eq_name',
			width : 200
		}, {
			header : "震中",
			dataIndex : 'Location_cname',
			width : 200,
			align : 'left'
		}, {
			header : "震级",
			dataIndex : 'Mag_val',
			width : 200,
			align : 'left'
		}, {
			header : "经度",
			dataIndex : 'Epi_lat',
			width : 200,
			align : 'left'
		}, {			
			header : "纬度",
			dataIndex : 'Epi_lon',
			width : 200,
			align : 'left'
		}, {			
			header : "发震时刻",
			dataIndex : 'O_time',
			width : 200,
			align : 'left'
		},{			
			header : "选择",
			dataIndex : 'changed',
			width : 50,
			sortable : false,
			align : 'center',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex,
					store) {
			var checked = (value == true) ? 'checked' : '';
			return "<input type='radio' value='" + record.data["id"] + "' "
					+ checked + " id='cb' class='radio' "
					+ "onclick='javascript:onSelectQc(this)'>"
			}
		}]);
var grid = new Ext.grid.GridPanel({
			el : 'grid',
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
	txt.name = "criteria.location_cname";
	txt.id = "location_cname";
	var msg = document.createTextNode("震中:");
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
								url : '/special/saveQc.do',
								params : {
									'model.id' : currentSpecialId,		
								},
								method : 'POST',
								success : function(xhr, textStatus) {
									win.hide(textStatus);
									//Ext.my().msg('', '您已经成功的为评估对象设置了专家成员.');
									if(xhr.responseText != null){
									  if(currentExpertType == '1') {
										 Ext.my().msg('', '您已经成功为评估信息设置了专家组长.');
										 $("#leader")[0].value = xhr.responseText;
									  }	
									  if(currentExpertType == '2') {
										 Ext.my().msg('', '您已经成功为评估信息设置了专家成员.');										  
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
				url : '/special/cancelSaveSpecialQc.do',
				params : {
					'model.id' : currentSpecialId
				},
				method : 'POST'
			});
}

function onSelectQc(cb) {
	var url = (cb.checked)
			? '/special/selectQc.do'
			: '/special/deselectQc.do';
	Ext.Ajax.request({
				url : url,
				params : {
					'model.id' : currentSpecialId,
					'criteria.id' : cb.value
				},
				method : 'POST'
			});
}

function selectQc(specialId) {
	currentSpecialId = specialId;
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
	var urlStr = '/special/qcOfSpecial.do?model.id='
			+ currentSpecialId;
	if (Ext.get('location_cname')) {
		urlStr = urlStr + '&criteria.name=' + Ext.get('location_cname').getValue();
	}
	
	return encodeURI(encodeURI(urlStr));
}