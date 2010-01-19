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
					{name : 'taskTitle'},
					{name : 'title'},
					{name : 'path'},
					{name : 'remark'}]
			}),
			proxy : new Ext.data.HttpProxy({
				url:'${ctx}/taskatt/getTaskAttsByTaskId.do?taskId=${task.id}' 
			})
		})
		var cm = new Ext.grid.ColumnModel([
		       {
			       header : '任务标题',
				   dataIndex : 'taskTitle',
		           width : 400,
		           sortable : true
		       },{
			       header : '附件标题',
		           dataIndex : 'title',
		           width : 200,
		           sortable : true,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {		           	   
			           return '<a href="'+ ${ctx}record.data['path'] +'" target="_blank" title="下载附件"> <font color="blue" >'+record.data["title"]+'</font></a>';			           			           	 	           
		            }				     
		       },{
			       header : '附件路径',
		           dataIndex : 'path',
		           width : 100,
		           sortable : true
		       },{
			       header : '备注',
		           dataIndex : 'remark',
		           width : 100,
		           sortable : true
		       } 				     
		  ]);
		//创建Grid表格组件
		var taskAttGrid${taskStatus.index} = new Ext.grid.GridPanel({
			title : '任务${taskStatus.index+1}附件',
			applyTo : 'taskAttGridDiv${taskStatus.index+1}',
			//width:853,
			bodyStyle:'width:100%',
			height:150,
			frame:true,			
			store: store,
			cm : cm,
			bbar : new Ext.PagingToolbar({
				  pageSize : 10,
				  store : store,
				  displayInfo : true,
				  displayMsg : '共{2}条记录,显示{0}到{1}',
				  emptyMsg : "没有记录"
			  })
		})
	});
  </script>
  <div id='taskAttGridDiv${taskStatus.index+1}'  style="width: 100%"></div>