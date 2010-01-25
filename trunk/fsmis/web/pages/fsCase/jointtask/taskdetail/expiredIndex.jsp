<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/autocomplete.jsp"%>
<title>联合任务管理</title>
<script type="text/javascript">
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">联合整治&nbsp;&gt;&nbsp;逾期未处理任务列表</div>
<div class="x-toolbar">
<table width="99%">
  <tr>
    <td><s:form action="expiredIndex.do" method="post"> 
              任务标题：<s:textfield id="taskTitle" name="model.jointTask.title" size="30"></s:textfield>&nbsp;&nbsp; 
	   规定完成时间：
			<input type="text" name="presetBeginTime" style="width: 120px"
				value='<s:date name="presetBeginTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" /> 
	   &nbsp;至
			<input type="text" name="presetEndTime" style="width: 120px"
				value='<s:date name="presetEndTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />				       
      <input type="submit" value="查询" class="button" />
    </s:form>
    </td>
    <td align="right">
      <a href="${ctx}/jointTask/index.do">任务列表</a>
    </td>    
  </tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
  items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
  action="expiredIndex.do" 
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
		<ec:column width="200" property="_title" title="任务标题" >
		  	   <a href="${ctx}/jointTask/view.do?model.id=${item.jointTask.id}"><font color="blue">${item.jointTask.title}</font></a> 		
        </ec:column>
		<ec:column width="200" property="_mt" title="部门名称">	 						
			<c:forEach var="mtd" items="${item.jointTask.taskDetailses}">
				<c:if test="${mtd.isLeader eq '1'}">
					${mtd.dept.name}
				</c:if>
			</c:forEach>
		</ec:column>
		<ec:column width="100" property="jointTask.createDate" title="创建日期" style="text-align:center" cell="date" />
		<ec:column width="100" property="jointTask.presetTime" title="规定完成日期" style="text-align:center" cell="date" />
		<ec:column width="140" property="status" title="任务状态" style="text-align:center">
			<c:choose>
			   <c:when test="${item.jointTask.remainDays >= 0}"><font color="red">剩余${item.jointTask.remainDays}天</font></c:when>
			   <c:otherwise><font color="red">逾期${-item.jointTask.remainDays}天</font></c:otherwise>
			</c:choose>
		</ec:column>        
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>