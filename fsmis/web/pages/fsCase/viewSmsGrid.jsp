<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	Ext.onReady(function(){
		//发送短信的GridPanel
		var storeSend = new Ext.data.Store({
			autoLoad :{params : {start : 0,limit : 10}},
			reader: new Ext.data.JsonReader({
				root : 'root',
				totalProperty : 'totalProperty',
			      id : 'id',
			      fields : [
					{name : 'content'},
					{name : 'mobileNum'},
					{name : 'createTime'},
					{name : 'sendTime'},
					{name : 'isReceive'},
					{name : 'name'},
					{name : 'isNew'}]
			}),
			proxy : new Ext.data.HttpProxy({
				url:'${ctx}/fscase/getSmsByFsCaseId.do?fsCaseId=${model.id}&smsType=smsSend' 
			})
		});
		var cmSend = new Ext.grid.ColumnModel([
		       {
			       header : '短信内容',
				   dataIndex : 'content',
		           width : 330,
		           sortable : true,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
		           	   return '<font title="'+value+'">'+value+'</font>';		           	  
		            }
		       },{
			       header : '接收手机号',
		           dataIndex : 'mobileNum',
		           width : 80  				     
		       },{
			       header : '创建时间',
		           dataIndex : 'createTime',
		           width : 120	        
		       },{
			       header : '发送时间',
		           dataIndex : 'sendTime',
		           width : 120		        
		       },{
			       header : '是否接收',
		           dataIndex : 'isReceive',
		           width : 60,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
			           if(value == '0'){
			       		   return "未接收";
			           }else{
				           return "已接收";
			           }
		     	   }	        
		       },{
			       header : '发送姓名',
		           dataIndex : 'name',
		           width : 60		        
		       },{
			       header : '是否最新',
		           dataIndex : 'isNew',
		           width : 60,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
			           if(value == '0'){
			       		   return "否";
			           }else{
				           return "是";
			           }
		     	   }	        
		       }		              				    
		  ]);
		//创建Grid表格组件
		var gridSend = new Ext.grid.GridPanel({
			title : '发送的短信',
			applyTo : 'smsSendDiv',

			bodyStyle:'width:100%',
			height:190,
			frame:true,			
			store: storeSend,
			loadMask:true,
			layout:'fit', 
			cm : cmSend,
			bbar : new Ext.PagingToolbar({
				  pageSize : 10,
				  store : storeSend,
				  displayInfo : true,
				  displayMsg : '共{2}条记录,显示{0}到{1}',
				  emptyMsg : "没有记录"
			  })
		});

		//接收短信的GridPanel
		var storeReceive = new Ext.data.Store({
			autoLoad :{params : {start : 0,limit : 10}},
			reader: new Ext.data.JsonReader({
				root : 'root',
				totalProperty : 'totalProperty',
			      id : 'id',
			      fields : [
					{name : 'content'},
					{name : 'mobileNum'},
					{name : 'sendTime'},
					{name : 'receiveTime'},
					{name : 'isParsed'},
					{name : 'isReport'},
					{name : 'isTreated'},
					{name : 'isVerify'},
					{name : '是否最新'}]
			}),
			proxy : new Ext.data.HttpProxy({
				url:'${ctx}/fscase/getSmsByFsCaseId.do?fsCaseId=${model.id}&smsType=smsRecieve' 
			})
		});
		var cmReceive = new Ext.grid.ColumnModel([
		       {
			       header : '短信内容',
				   dataIndex : 'content',
		           width : 210,
		           sortable : true,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
		           	   return '<font title="'+value+'">'+value+'</font>';		           	  
		            }
		       },{
			       header : '接收手机号',
		           dataIndex : 'mobileNum',
		           width : 80  				     
		       },{
			       header : '发送时间',
		           dataIndex : 'createTime',
		           width : 120		        
		       },{
			       header : '接收时间',
		           dataIndex : 'receiveTime',
		           width : 120		        
		       },{
			       header : '是否分析',
		           dataIndex : 'isParsed',
		           width : 60,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
			           if(value == '0'){
			       		   return "未分析";
			           }else{
				           return "已分析";
			           }
		     	   }		        
		       },{
			       header : '是否举报',
		           dataIndex : 'isReport',
		           width : 60,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
			           if(value == '0'){
			       		   return "非举报短信";
			           }else{
				           return "举报短信";
			           }
		     	   }	        
		       },{
			       header : '是否处理',
		           dataIndex : 'isTreated',
		           width : 60,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
			           if(value == '0'){
			       		   return "未处理";
			           }else{
				           return "已处理";
			           }
		     	   }		        
		       },{
			       header : '是否核实',
		           dataIndex : 'isVerify',
		           width : 60,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
			           if(value == '0'){
			       		   return "非核实短信";
			           }else{
				           return "核实短信";
			           }
		     	   }		        
		       },{
			       header : '是否最新',
		           dataIndex : 'isNew',
		           width : 60,
		           renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
			           if(value == '0'){
			       		   return "否";
			           }else{
				           return "是";
			           }
		     	   }	        
		       }		              				    
		  ]);
		//创建Grid表格组件
		var gridReceive = new Ext.grid.GridPanel({
			title : '接收的短信',
			applyTo : 'smsReceiveSmsDiv',

			bodyStyle:'width:100%',
			height:190,
			frame:true,			
			store: storeReceive,
			cm : cmReceive,
			bbar : new Ext.PagingToolbar({
				  pageSize : 10,
				  store : storeReceive,
				  displayInfo : true,
				  displayMsg : '共{2}条记录,显示{0}到{1}',
				  emptyMsg : "没有记录"
			  })
		});
	});
  </script>
  <div id='smsSendDiv' style=" width: 100%; margin: -1;"></div>
  <div id='smsReceiveSmsDiv' style="width: 100%; margin: -1; " ></div>