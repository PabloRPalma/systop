<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="/common/taglibs.jsp"%>
<div id="taskDiv" style="margin: -1">
<c:forEach items="${model.taskses}" var="task"	varStatus="taskStatus">  
  <c:choose>
  	<c:when test="${not empty taskId}">
  		<c:if test="${task.id eq taskId}">
  			<c:set var="currentTask" value="${taskStatus.index}"></c:set>
  		</c:if>
  	</c:when>
  	<c:otherwise>
  	 	<c:if test="${task.isCurrentTask eq '1'}">
  			<c:set var="currentTask" value="${taskStatus.index}"></c:set>
  		</c:if>
  	</c:otherwise>
  </c:choose>
	<div id="taskDiv${taskStatus.index+1}" class="x-hide-display">
		<div>
		<table class="mytable" >
				<tr>
					<td align="right" width="6%" >任务标题：</td>
					<td colspan="5" align="left" >${task.title }</td>
				</tr>
				<tr>
					<td align="right" width="6%">派遣时间：</td>
					<td width="12%"><fmt:formatDate value="${task.dispatchTime}"pattern="yyyy-MM-dd HH:mm" /></td>

					<td align="right" width="12%">规定完成时间：</td>
					<td width="12%"><fmt:formatDate value="${task.presetTime}"pattern="yyyy-MM-dd HH:mm" /></td>
					<td align="right" width="8%">事件状态：</td>
					<td width="15%"><c:if test="${task.status == '0'}">	<font color="red">未派遣</font>
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
					<td align="right" >任务描述：</td>
					<td colspan="5">
					<div style="overflow: auto;">
						${task.descn}
					</div>
					</td>
				</tr>				
				<tr>
					<td colspan="6">
					<%--
						<table id="task${taskStatus.index}AttTable" width="100%" class="mytable" style="margin-left: -1;" height="100">
						  <thead>
            		<tr>
	                <th width="20">No.</th>
	                <th width="100">标题</th>
	                <th width="300">备注</th>
	                <th width="100">操作</th>
            		</tr>
        			</thead>
        			<tbody>
								<c:forEach items="${task.taskAtts}" var="att" varStatus="attStatus">
									<tr>
										<td>${attStatus.index+1 }</td>
										<td>${att.title }</td>
										<td>${att.remark }</td>
										<td><a href="${ctx}${att.path }" target="_blank" title="下载附件">下载</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>	 
						--%>					
					</td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
		/*Ext.onReady(function() {
		 var grid${taskStatus.index} = new Ext.grid.TableGrid("task${taskStatus.index}AttTable", {
		      stripeRows: true // stripe alternate rows
		    });
		    grid${taskStatus.index}.render();
		});*/
		</script>
		<div >
			<!-- 任务附件信息的Ext GridPanel -->
			<%@include file="viewTaskAttGrid.jsp" %>
			<!-- 任务明细信息的Ext GridPanel -->				
			<%@include file="viewTaskDetailsGrid.jsp" %>
		</div>
	</div>
</c:forEach></div>
<script type="text/javascript">
<c:if test="${not empty model.taskses }">	
	Ext.onReady(function() {
		var taskDiv = new Ext.TabPanel( {
			renderTo : 'taskDiv',
			anchor : '100% 100%',
			activeTab : ${empty currentTask ? 0 : currentTask},
			frame : false,
			defaults : {
				autoHeight : true
			},
			items : [ 
			
					<c:forEach items="${model.taskses}" var="task" varStatus="taskStatus">
						<c:if test="${taskStatus.index>0}">,</c:if>
						{		contentEl : 'taskDiv${taskStatus.index+1}',
							title : '任务${taskStatus.index+1}'
						} 
					</c:forEach>					
					]
		});
		
	});	
</c:if>
</script>