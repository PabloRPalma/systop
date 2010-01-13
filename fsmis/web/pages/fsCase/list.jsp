<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>事件采集管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function examine(id){
	alert(id);
}


function remove(id){
	if (confirm("确认要删除事件信息吗?")){
		window.location.href="${ctx}/fscase/remove.do?model.id=" + id+"&modelId=0&isMultipleCase="+${isMultipleCase};
		
	}
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">协调指挥&nbsp;&gt;&nbsp;${param['isMultipleCase'] eq 0?'一般案件':'综合案件'}列表</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td><s:form action="index" method="post">
			<s:hidden name="isMultipleCase"></s:hidden>
						事件标题:
			<s:textfield name="model.title" cssStyle="width:100px"></s:textfield>	
						事件编号:
			<s:textfield name="model.code" cssStyle="width:70px"></s:textfield>
						事发时间:
			<input type="text" name="caseBeginTime" style="width: 100px"
				value='<s:date name="caseBeginTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />
						至
			<input type="text" name="caseEndTime" style="width: 100px"
				value='<s:date name="caseEndTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />
			                              事件状态:
			<s:select name="model.status" list="stateMap" headerKey="" headerValue="请选择"/>		
			<s:submit value="查询" cssClass="button"></s:submit>
		</s:form></td>
		<td align="right">
		<stc:role ifNotGranted="ROLE_DEPT_OPER">
		<table>
			<tr>
				<td><span class="ytb-sep"></span></td>
				<td>
					<c:if test="${param['isMultipleCase'] eq 0}"><a href="${ctx}/fscase/edit.do?isMultipleCase=${param['isMultipleCase'] }&modelId=${param['modelId']}"> 添加事件</a></c:if>			
				</td>
			</tr>
		</table>
		</stc:role>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
	action="index.do" useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="false" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="30" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>	
		<ec:column width="260" property="title" title="事件标题" sortable="false"/>
		<ec:column width="70" property="code" title="事件编号" sortable="false"/>
		<ec:column width="100" property="caseType.name" title="事件类别" sortable="false"/>
		<ec:column width="110" property="caseTime" title="事发时间"
			style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm" sortable="false"/>
		<ec:column width="60" property="_2" title="状态" style="text-align:center" sortable="false">
			<c:if test="${item.status == '0'}"><font color="red">未派遣</font></c:if>
			<c:if test="${item.status == '1'}"><font color="#FF9D07">已派遣</font></c:if>
			<c:if test="${item.status == '2'}"><font color="green">已处理</font></c:if>
			<c:if test="${item.status == '3'}"><font color="gray">已回退</font></c:if>
			<c:if test="${item.status == '4'}"><font color="blue">已核实</font></c:if>
		</ec:column>
		<ec:column width="40" property="_6" title="查看" style="text-align:center" sortable="false">
			<a href="${ctx}/fscase/view.do?fsCaseId=${item.id}&modelId=0&isMultipleCase=${isMultipleCase}">
			                查看
			 </a> 
		</ec:column>
		<stc:role ifNotGranted="ROLE_DEPT_OPER">
		<ec:column width="180" property="_0" title="操作" style="text-align:left" sortable="false">
			 &nbsp;&nbsp;
			  
			 <stc:role ifAnyGranted="ROLE_ADMIN">
			    <a href="${ctx}/fscase/edit.do?model.id=${item.id}&modelId=0&isMultipleCase=${isMultipleCase}">
                                                 改 |
               </a>
                    	图 |
             </stc:role>
            <stc:role  ifNotGranted="ROLE_ADMIN">
            <c:if test="${item.status == '2' || item.status == '4'}">
                <a href="#">
                <font color="#999999">编辑</font>
             </a>
            </c:if>
			<c:if test="${item.status == '0' || item.status == '1'}">
			   <a href="${ctx}/fscase/edit.do?model.id=${item.id}&modelId=0&isMultipleCase=${isMultipleCase}">
                                               编辑
             </a>	
            </c:if>
            </stc:role>
			<c:if test="${item.status != '0'}">
			     <font color="#999999" title="已派遣事件不能删除">删 |</font>
			</c:if>
			<a
				href="#" onclick="showChooseSendTypeWindow(${model.id})"> 派 |</a>
			<c:if test="${item.status == '0'}">
			 <a href="#" onclick="remove(${item.id})" >
			               删 |
			 </a>
			</c:if>							         
			<c:if test="${item.status == '2'}" >
			  <a href="addSendMsg.do?model.id=${item.id}">核实</a>
			</c:if>
			<c:if test="${item.status == '2' && item.msgCheckedFlag == '1'}">
			    <a href="confirmBackMsg.do?model.id=${item.id}">
					核实反馈
				</a>	
			</c:if>
			<c:if test="${item.status == '4'}">
			     <a href="confirmBackMsg.do?model.id=${item.id}&operType='v'">
					查看已核实信息
				</a>				
			</c:if>		
		</ec:column>
		</stc:role>
	</ec:row>
</ec:table>
</div>
</div>
</div>
<jsp:include page="chooseSendType.jsp"></jsp:include>
</body>
</html>