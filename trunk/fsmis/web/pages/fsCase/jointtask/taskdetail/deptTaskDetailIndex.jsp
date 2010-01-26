<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/autocomplete.jsp"%>
<script type="text/javascript">

</script>
<title>联合整治管理</title>
<script type="text/javascript">
function view(id){
	window.location.href="${ctx}/jointTask/deptTaskDetail/view.do?model.id=" + id;
}

function result(id, jointTaskId){  
    $.ajax({
		url: '${ctx}/jointTask/deptTaskDetail/checkResult.do',
		type: 'post',
		dataType: 'json',
		data: {jointTaskId : jointTaskId},
		success: function(rst, textStatus){
	  		if(rst.result != null){
	  		  Ext.Msg.alert('', rst.result + '还没有查看任务，不能进行任务处理！');		   	  		
	  	  	}else{
	  	  	  window.location.href='${ctx}/jointTask/deptTaskDetail/result.do?model.id=' + id;
	  	  	}
		}
   });
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">联合整治&nbsp;&gt;&nbsp;任务列表</div>
<div class="x-toolbar">
<table width="99%">
  <tr>
    <td><s:form action="deptTaskDetailIndex.do" method="post"> 
              任务标题：<s:textfield id="taskTitle" name="model.jointTask.title" size="30"></s:textfield>          
      <input type="submit" value="查询" class="button" />
    </s:form>
    </td>
  </tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
  items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
  action="deptTaskDetailIndex.do" 
  useAjax="false"
  doPreload="false" 
  pageSizeList="20,50,100,200" 
  editable="false"
  sortable="true" 
  rowsDisplayed="20" 
  generateScript="true"
  resizeColWidth="false" 
  classic="false" 
  width="100%" 
  height="400px"
  minHeight="400"
  toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
	    <ec:column width="30" property="_num" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center" />
		
		<ec:column width="200" property="jointTask.title" title="任务标题" />
		<ec:column width="90" property="_mt" title="牵头部门" >
		    <c:forEach var="mtd" items="${item.jointTask.taskDetailses}">				
				<c:if test="${mtd.isLeader eq '1'}">
					${mtd.dept.name}
				</c:if>
			</c:forEach>
		</ec:column>
		<ec:column width="230" property="_mts" title="协办部门" >			
			<c:forEach var="mtd" items="${item.jointTask.taskDetailses}">				
			    <c:if test="${mtd.isLeader eq '0'}">
					${mtd.dept.name}
				</c:if>
			</c:forEach>
		</ec:column>
		<ec:column width="70" property="jointTask.createDate" title="创建时间" style="text-align:center" cell="date" />
		<ec:column width="140" property="status" style="text-align:center" title="任务状态" >
			<c:if test="${item.status == '0'}"><font color="red">未接收</font></c:if>
			<c:if test="${item.status == '1'}"><font color="blue">已查看</font></c:if>
			<c:if test="${item.status == '2'}">
		      <c:if test="${item.isLeader eq '1'}">
				   <font color="#FF9D07">处理中...</font>
				   	<c:choose>
						<c:when test="${item.jointTask.remainDays >= 0}">剩余${item.jointTask.remainDays}天</c:when>
						<c:otherwise>逾期${-item.jointTask.remainDays}天</c:otherwise>
					</c:choose>
			   </c:if>
			   <c:if test="${item.isLeader eq '0'}">
			         <font color="blue">已查看</font>
			   </c:if>
			</c:if>			
			<c:if test="${item.status == '4'}"><font color="green">已处理</font></c:if>
		</ec:column>
		 <ec:column width="80" property="_0" title="操作" style="text-align:center" sortable="false">
		 <c:if test="${item.isLeader == '1'}">
			  <c:if test="${item.status == '2'}"><a href="javascript:result(${item.id},${item.jointTask.id})">处理</a>|</c:if>
		 </c:if>
		 <a href="javascript:view(${item.id})">查看</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>