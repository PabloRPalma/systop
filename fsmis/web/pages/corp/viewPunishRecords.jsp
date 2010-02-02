<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	Ext.onReady(function(){
		var store = new Ext.data.Store({
			autoLoad :{params : {start : 0,limit : 10}},
			reader: new Ext.data.JsonReader({
				root : 'root',
				totalProperty : 'totalProperty',
			      id : 'id',
			      fields : [
			        {name:'id'},			
					{name : 'title'},
					{name : 'caseTypeName'},
					{name : 'address'},
					{name : 'caseTime'}]
			}),
			proxy : new Ext.data.HttpProxy({
				url:'${ctx}/corp/getPunishRecordsOfCorp.do?corpId=${model.id}' 
			})
		});
		var cm = new Ext.grid.ColumnModel([
		       {
			       header : '事件标题',
				   dataIndex : 'title',
		           width : 400,
		           sortable : true
		       },{
			       header : '事件类别',
		           dataIndex : 'caseTypeName',
		           width : 100,
		           sortable : true				     
		       },{
			       header : '事件地点',
		           dataIndex : 'address',
		           width : 150,
		           sortable : true				     
		       },{
			       header : '事发时间',
		           dataIndex : 'caseTime',
		           width : 150,
		           sortable : true
		       }
		              				     
		  ]);
		//创建Grid表格组件
		var genericCaseGrid = new Ext.grid.GridPanel({
			applyTo : 'genericCaseGridDiv',
			bodyStyle:'width:100%',
			autoWidth : true,  
			autoScroll : true,  
			height:295,			
			frame:false,
			store: store,
			loadMask:true,
			layout:'fit', 
			cm : cm,
			bbar : new Ext.PagingToolbar({
				  pageSize : 10,
				  store : store,
				  displayInfo : true,
				  displayMsg : '共{2}条记录,显示{0}到{1}',
				  emptyMsg : "没有记录"
			  })
		});
	});
  </script>
  <div id='genericCaseGridDiv' style="margin-left:-1px; margin-bottom: -1px;"></div>