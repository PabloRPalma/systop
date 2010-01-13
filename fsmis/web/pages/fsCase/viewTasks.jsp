<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<div id="taskDiv" style="margin: -1">
<c:forEach items="${model.taskses}" var="task"
	varStatus="varStatus">
	<div id="taskDiv${varStatus.index+1}" class="x-hide-display">
	<table id="mytable" align="left" style="border-bottom: 0">
		<tr>
			<td>			
			<table width="800px" align="left">
				<tr>
					<td align="right" width="15%">任务标题：</td>
					<td colspan="4" align="left">${task.title }</td>
				</tr>
				<tr>
					<td align="right" width="12%">派遣时间：</td>
					<td width="12%"><fmt:formatDate value="${task.dispatchTime}"
						pattern="yyyy-MM-dd HH:mm" /></td>

					<td align="right" width="12%">规定完成时间：</td>
					<td width="12%"><fmt:formatDate value="${task.presetTime}"
						pattern="yyyy-MM-dd HH:mm" /></td>
					<td align="right" width="8%">事件状态：</td>
					<td width="15%"><c:if test="${task.status == '0'}">
						<font color="red">未派遣</font>
					</c:if> <c:if test="${task.status == '1'}">
						<font color="#FF9D07">已派遣</font>
					</c:if> <c:if test="${task.status == '2'}">
						<font color="green">已处理</font>
					</c:if> <c:if test="${task.status == '3'}">
						<font color="gray">已回退</font>
					</c:if> <c:if test="${task.status == '4'}">
						<font color="blue">已核实</font>
					</c:if></td>
				</tr>
				<tr>
					<td align="right" rowspan="4">任务描述：</td>
					<td rowspan="4" colspan="5"><textarea id="taskDescn"
						style="border: 1px solid #D4D0C8;" name="task.descn" cols="80"
						rows="3" readonly="readonly">${task.descn}</textarea></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>	
			<%--				
			<s:action name="getTaskDetailsByTaskId" namespace="/taskdetail"  executeResult="true" >
				<s:param name="taskId"></s:param>
			</s:action>
			 --%>
			<!-- 任务明细信息的Ext GridPanel -->			
	    	<div id="taskDetail_grid"></div>
	    	<script type="text/javascript">
				Ext.onReady(function() {
					//document.write('${ctx}/taskdetail/getTaskDetailsByTaskId.do?taskId=${task.id}');
				var store = new Ext.data.Store({
					  autoLoad :{params : {start:0,limit:15}},
					  proxy : new Ext.data.HttpProxy({
						url:'${ctx}/taskdetail/getTaskDetailsByTaskId.do?taskId=${task.id}'  
				      }),
				      reader : new Ext.data.JsonReader({
					      root : 'root',
					      totalProperty : 'totalProperty',
					      id : 'id',
					      fields : [
							{name : 'id'},
							{name : 'task.title'},
							{name : 'dept.name'},
							{name : 'completionTime'},
							{name : 'status'},
							{name : 'remainDays'}				
						  ]
					  }),
					  remoteSort :true
				  });
				  var cm = new Ext.grid.ColumnModel([
				     {	header : '任务标题',
				     	dataIndex : 'item.task.title',
				     	width : 200,
				     	sortable : true
				     },{header : '执行部门',
					    dataIndex : 'dept.name',
					    width : 200  				     
				     },{header : '完成时间',
				    	dataIndex : 'completionTime',
					    width : 200,
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
					 },{header : '任务状态',
					    dataIndex : 'status',
						width : 200,
						renderer: function(value, cellmeta, record, rowIndex, columnIndex, store) {
							switch(value){
								case '0': return '未接收';
								case '1': return '未接收';
								case '2': return '未接收';
								case '3': return '未接收';
								case '4': return '未接收';
								default : return '';							
							}
					    }
					 }
				     
				  ]);
				  var gride = new Ext.grid.GridPanel({
					  el:'taskDetail_grid',
					  title : null,
					  width : 600,
					  height: 300,
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
				});	
				</script>
	    	</td>	
		</tr>
	</table>	
	</div>
</c:forEach></div>
<script type="text/javascript">
<c:if test="${not empty model.taskses }">	
	Ext.onReady(function() {
		var taskDiv = new Ext.TabPanel( {
			renderTo : 'taskDiv',
			anchor : '100% 100%',
			activeTab : 0,
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ 
			
					<c:forEach items="${model.taskses}" var="task" varStatus="varStatus">
						<c:if test="${varStatus.index>0}">,</c:if>
						{		contentEl : 'taskDiv${varStatus.index+1}',
							title : '任务${varStatus.index+1}'
						} 
					</c:forEach>					
					]
		});
		
	});	
</c:if>
</script>