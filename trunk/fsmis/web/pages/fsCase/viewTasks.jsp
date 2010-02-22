<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="/common/taglibs.jsp"%>
<div id="taskDiv" style="margin: -1; width: 100%;">
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
	<div id="taskDiv${taskStatus.index+1}" class="x-hide-display" style="width: 100%">
		<div style="width: 100%">
		<table class="mytable" align="center"  cellpadding="0" cellspacing="0">	
				<tr>
					<td class="title">任务标题：</td>
					<td colspan="5" class="content" >${task.title }</td>
				</tr>
				<tr>
					<td class="title">派遣时间：</td>
					<td class="content" ><fmt:formatDate value="${task.dispatchTime}"pattern="yyyy-MM-dd HH:mm" /></td>

					<td class="title">规定完成时间：</td>
					<td class="content" ><fmt:formatDate value="${task.presetTime}"pattern="yyyy-MM-dd HH:mm" /></td>
					<td class="title">任务状态：</td>
					<td class="content" >
					<c:if test="${task.status == '0'}">
						<font color="red">未接收</font>
					</c:if> <c:if test="${task.status == '1'}">
						<font color="#FF9D07">处理中</font>
					</c:if> <c:if test="${task.status == '2'}">
						<font color="green">已处理</font>
					</c:if> <c:if test="${task.status == '3'}">
						<font color="gray">已回退</font>
					</c:if> <c:if test="${task.status == '4'}">
						<font color="blue">已核实</font>
					</c:if>
					</td>
				</tr>
				<%-- 由于页面下方有任务附件的GridPanel,这里就不列出任务附件了.
				<tr>
					<td align="right" valign="top">任务附件：</td>
					<td colspan="5">
					<c:forEach var="att" items="${task.taskAtts}">
					<a href="${ctx}${att.path}" target="_blank">
	  	      		  <img src="${ctx}/images/icons/attachment.gif" />&nbsp;<font size="2">${att.title}</font>
					</a>
					<br>
					</c:forEach>
					</td>
				</tr>
				 --%>
				<tr>					
					<td class="content" colspan="6" style="border-top:1px dashed #99BBE8; padding: 4 10 4 10;"><div style="overflow: auto; width:100%; height: 65px;">	${task.descn}	</div>			</td>
				</tr>		
				<%--		
				<tr>
					<td colspan="6">
					
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
											
					</td>
				</tr>--%>
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
		<div style="width: 100%;">
			<!-- 任务附件信息的Ext GridPanel -->
			<%@include file="viewTaskAttGrid.jsp" %>
		</div>
		<div style="width: 100%">
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