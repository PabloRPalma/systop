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
					{name : 'deptName'},
					{name : 'completionTime'},
					{name : 'status'},
					{name : 'remainDays'}]
			}),
			proxy : new Ext.data.HttpProxy({
				url:'${ctx}/taskdetail/getTaskDetailsByTaskId.do?taskId=${task.id}' 
			})
		})
		var cm = new Ext.grid.ColumnModel([
		       {
			       header : '任务标题',
				   dataIndex : 'taskTitle',
		           width : 400,
		           sortable : true,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
		           	   if(record.data["status"] == '4'){
			           	   return '<a href="#" onclick = "showResultWindow('+record.data["id"]+')"><font color="blue" title="点击查看任务处理结果">'+record.data["taskTitle"]+'</font></a>';			           	
		           	   }else if(record.data["status"] == '3'){
		           		return '<a href="#" onclick = "showReturnWindow('+record.data["id"]+')"><font color="blue" title="点击查看任务回退结果">'+record.data["taskTitle"]+'</font></a>';
		           	   }else{
			           	   return record.data["taskTitle"];
		           	   }		           	
		            }
		       },{
			       header : '执行部门',
		           dataIndex : 'deptName',
		           width : 200,
		           sortable : true				     
		       },{
			       header : '完成时间',
		           dataIndex : 'completionTime',
		           width : 100,
		           sortable : true,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
		               /*已退回或者已处理显示完成时间*/
			           if(value == '3' || value == '4' ){
			       		   return value;
			           }else{/*否则显示剩余或者逾期天数*/
			       		   if(record.data["remainDays"] >=0){
			        		  return '剩余天数'+record.data["remainDays"] ;
						   }else{
			              	  return '逾期天数'+ eval(-record.data["remainDays"]) ;
			               }
			           }
		     	   }
		       },{
			       header : '任务状态',
		           dataIndex : 'status',
		           width : 100,
		           sortable : true,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
		           		switch(value){
		              		case '0': return '<font color="red">未接收</font>';
		              		case '1': return '<font color="blue">已查看</font>';
		              		case '2': return '<font color="#FF9D07">处理中...</font>';
		              		case '3': return '<font color="gray">已退回</font>';
		              		case '4': return '<font color="green">处理完毕</font>';
		              		default : return '';							
		              	}
		            }
		      }
		              				     
		  ]);
		//创建Grid表格组件
		var grid = new Ext.grid.GridPanel({
			title : '任务${taskStatus.index+1}明细',
			applyTo : 'taskDetailGridDiv${taskStatus.index+1}',
			width:853,
			height:100,
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
  <div id='taskDetailGridDiv${taskStatus.index+1}'></div>