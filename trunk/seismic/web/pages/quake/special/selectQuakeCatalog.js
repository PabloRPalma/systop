var qcTableName; 
var qcId;
var specialId;
var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : ''// URL will defined dynamicly.
					}),
			reader : new Ext.data.JsonReader({
						root : 'root',
						totalProperty : 'totalProperty',
						id : 'id',
						fields : [{
									name : 'ID'
								},{
									name : 'LOCATION_CNAME'
								}, {
									name : 'M'
								}, {
									name : 'EPI_LAT'
								}, {
									name : 'EPI_LON'
								}, {									
									name : 'EQ_TIME'
								}, {
									name : 'changed'
								}]
					}),

			remoteSort : true
		});
var cm = new Ext.grid.ColumnModel([{
			header : "震中",
			dataIndex : 'LOCATION_CNAME',
			width : 120,
			align : 'center'
		}, {
			header : "震级",
			dataIndex : 'M',
			width : 60,
			align : 'center'
		}, {
			header : "经度",
			dataIndex : 'EPI_LAT',
			width : 100,
			align : 'center'
		}, {			
			header : "纬度",
			dataIndex : 'EPI_LON',
			width : 100,
			align : 'center'
		}, {			
			header : "发震时刻",
			dataIndex : 'EQ_TIME',
			width : 200,
			align : 'center'
		},{			
			header : "选择",
			dataIndex : 'changed',
			width : 60,
			sortable : false,
			align : 'center',
			renderer : function(value, cellmeta, record, rowIndex, columnIndex,
					store) {
			var checked = (value == true) ? 'checked' : '';
			return "<input type='radio' value='" + record.data["ID"] + "' "
					+ checked + " id='cb' name='cb' "
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
	div.style.margin = "2px;";
	var to=document.createTextNode("至:")
	/*
	var qcDate=document.createTextNode("发震日期:")
	var star = document.createElement("input");
	star.type = "text";
	star.name = "criteria.startDate";
	star.id = "startDate";
	star.onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'now\'}',skin:'whyGreen'})";
	//star.style="width:80px;";
	
	var end = document.createElement("input");
	end.type = "text";
	end.name = "criteria.endDate";
	end.id = "endDate";
	end.onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'now\'}',skin:'whyGreen'})";
	
	*/
	var m=document.createTextNode("震级:")
	var mStar = document.createElement("input");
	mStar.type = "text";
	mStar.name = "criteria.startM";
	mStar.id = "startM";

	var mEnd = document.createElement("input");
	mEnd.type = "text";
	mEnd.name = "criteria.endM";
	mEnd.id = "endM";
	/*
	var lon=document.createTextNode("经度:")
	var lonStar = document.createElement("input");
	lonStar.type = "text";
	lonStar.name = "criteria.startLon";
	lonStar.id = "startLon";
	
	var lonEnd = document.createElement("input");
	lonEnd.type = "text";
	lonEnd.name = "criteria.endLon";
	lonEnd.id = "endLon";
	
	var lat=document.createTextNode("纬度:")
	var latStar = document.createElement("input");
	latStar.type = "text";
	latStar.name = "criteria.startLat";
	latStar.id = "startLat";
	
	var latEnd = document.createElement("input");
	latEnd.type = "text";
	latEnd.name = "criteria.endLat";
	latEnd.id = "endLat";
	
	div.appendChild(qcDate);
	div.appendChild(star);
	div.appendChild(to);
	div.appendChild(end);
	*/
	div.appendChild(m);
	div.appendChild(mStar);
	div.appendChild(to);
	div.appendChild(mEnd);
	/*
	div.appendChild(lon);
	div.appendChild(lonStar);
	div.appendChild(to);
	div.appendChild(lonEnd);
	
	
	div.appendChild(lat);
	div.appendChild(latStar);
	div.appendChild(to);
	div.appendChild(latEnd);
	*/
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
				text : '确定',
				handler : function() {
					Ext.Ajax.request({
								url : '/admin/special/ensureQc.do',
								params : {
									'qcId' : qcId,
									'tableName' : qcTableName
								},
								method : 'POST',
								success : function(response) {
							
										 Ext.my().msg('', '您已经成功选择地震目录.');
										 var jsonResult = Ext.util.JSON.decode(response.responseText);
										 document.getElementById("location").value=jsonResult.LOCATION_CNAME;
										 document.getElementById("longitude").value=jsonResult.EPI_LON;
										 document.getElementById("latitude").value=jsonResult.EPI_LAT;
										 document.getElementById("magnitude").value=jsonResult.M;
										 document.getElementById("quakeTime").value=jsonResult.EQ_TIME;
										 document.getElementById("model.qc_id").value=qcId;
										 document.getElementById("model.tableName").value=qcTableName;
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
	width : 680,
	height : 500,
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
}

function onSelectQc(cb) {
	if(cb.checked){
		qcId=cb.value;
	}
}

function selectQc(tableName,id) {
	specialId=id;
	qcTableName=tableName;
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
	var urlStr = '/admin/special/qcOfSpecial.do?tableName='+qcTableName+'&specialId='+specialId;
	if (Ext.get('startM')) {
		urlStr = urlStr + '&criteria.startM=' + Ext.get('startM').getValue();
	}
	if (Ext.get('endM')) {
		urlStr = urlStr + '&criteria.endM=' + Ext.get('endM').getValue();
	}
	return encodeURI(encodeURI(urlStr));
}