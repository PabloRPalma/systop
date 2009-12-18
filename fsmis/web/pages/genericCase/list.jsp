<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<html>
<head>
<title>事件采集管理</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">
function examine(id){
	alert(id);
}
function removeNo(){
	alert('只能删除未派遣的事件！');
}


function remove(id){
	if (confirm("确认要删除事件信息吗?")){
		url="${ctx}/genericCase/remove.do?model.id=" + id;
		window.open(url,'main');
	}
}
function addSendMsg(seId){
	window.location.href = "${ctx}/genericCase/addSendMsg.do?model.id=" + seId;
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header"></div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td><s:form action="index" method="post">
						事件标题：
			<s:textfield name="model.title"></s:textfield>	
						事件编号：
			<s:textfield name="model.code"></s:textfield>
			             事件状态:
			<s:select name="model.status" list="stateMap" />		
			<s:submit value="查询" cssClass="button"></s:submit>
		</s:form></td>
		<td align="right">
		<stc:role ifNotGranted="ROLE_DEPT_OPER">
		<table>
			<tr>
				<td><a href="${ctx}/genericCase/index.do"><img
					src="${ctx}/images/icons/house.gif" />首页</a></td>
				<td><span class="ytb-sep"></span></td>
				<td><a href="${ctx}/pages/opengis/mapSingleEventLabel.jsp"><img
					src="${ctx}/images/fs_index/search.gif"/> 事件分布图</a></td>
				<td><span class="ytb-sep"></span></td>
				<td><a href="${ctx}/genericCase/indexSubSj.do"><img
					src="${ctx}/images/icons/arrow_turn_left.gif" />查看已上报市平台事件</a></td>
				<td><span class="ytb-sep"></span></td>
				<td><a href="${ctx}/genericCase/edit.do"><img
					src="${ctx}/images/icons/add.gif" /> 添加事件信息</a></td>
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
		<ec:column width="350" property="title" title="事件标题" sortable="false"/>
		<ec:column width="70" property="code" title="事件编号" sortable="false"/>
		<ec:column width="100" property="caseType.name" title="事件类别" sortable="false"/>
		<ec:column width="110" property="eventDate" title="事发时间"
			style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm" sortable="false"/>
		<ec:column width="120" property="_2" title="状态" style="text-align:center" sortable="false">
			<c:if test="${item.status == '0'}"><font color="red">未派遣</font></c:if>
			<c:if test="${item.status == '1'}"><font color="#FF9D07">已派遣</font></c:if>
			<c:if test="${item.status == '2'}"><font color="green">已处理</font></c:if>
			<c:if test="${item.status == '3'}"><font color="gray">已回退</font></c:if>
			<c:if test="${item.status == '4'}"><font color="blue">已核实</font></c:if>
			<a href="${ctx}/genericCase/look.do?model.id=${item.id}"> <img src="${ctx}/images/icons/resource.gif" border="0"/>查看 </a>
		</ec:column>
		<stc:role ifNotGranted="ROLE_DEPT_OPER">
		<ec:column width="150" property="_0" title="操作" style="text-align:left" sortable="false">
			 &nbsp;&nbsp;
			 <stc:role ifAnyGranted="ROLE_ADMIN">
			    <a href="${ctx}/genericCase/edit.do?model.id=${item.id}">
                 <img src="${ctx}/images/icons/modify.gif" border="0" title="编辑" />
               </a>
             </stc:role>
            <stc:role  ifNotGranted="ROLE_ADMIN">
            <c:if test="${item.status == '2' || item.status == '4'}">
                <a href="#">
                <img src="${ctx}/images/icons/modify_disable.gif" border="0" title="编辑" />
             </a>
            </c:if>
			<c:if test="${item.status == '0' || item.status == '1'}">
			   <a href="${ctx}/genericCase/edit.do?model.id=${item.id}">
                <img src="${ctx}/images/icons/modify.gif" border="0" title="编辑" />
             </a>	
            </c:if>
            </stc:role>
			<c:if test="${item.status != '0'}">
			<a href="#" onclick="removeNo()">
			       <img src="${ctx}/images/icons/delete_disable.gif" border="0" title="删除" />
			</a>
			</c:if>
			<c:if test="${item.status == '0'}">
			 <a href="#" onclick="remove(${item.id})" >
			       <img src="${ctx}/images/icons/delete.gif" border="0" title="删除" />
			 </a>
			</c:if>
				
			<img src="${ctx}/images/fs_index/search.gif" border="0" title="地图位置" />
			
				
		</ec:column>
		</stc:role>
	</ec:row>
</ec:table>

</div>
</div>
</div>


</body>
</html>