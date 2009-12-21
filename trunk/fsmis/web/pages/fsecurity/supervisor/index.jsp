<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>监管员信息管理</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">

function removeSupervisor(sID){
	if(confirm("确实要删除改监管员信息吗？")){
		location.href = "remove.do?model.id="+sID;
	}
}

function index(){
	var superVisorForm = document.getElementById('superVisorForm');
	superVisorForm.setAttribute('action','index.do','0');
	superVisorForm.setAttribute('target','_self','0');
	superVisorForm.submit();
}

function exportMobileNum(){
	var superVisorForm = document.getElementById('superVisorForm');
	superVisorForm.setAttribute('action','exportMobileNum.do','0');
	superVisorForm.setAttribute('target','_blank','0');
	superVisorForm.submit();
}
</script>
</head>

<body>
<div class="x-panel">
<div class="x-panel-header">监管员管理&nbsp;>&nbsp;监管员列表</div>
<div class="x-toolbar">
<table width="100%" border="0">
	<tr>
		<s:form id="superVisorForm" action="" method="post" target="">
			<td width="60" align="right">姓名：</td>
			<td width="80"><s:textfield name="model.name"
				cssStyle="width:80px" /></td>
			<td width="60" align="right">手机：</td>
			<td width="80"><s:textfield name="model.mobile"
				cssStyle="width:80px" /></td>
			<td width="60" align="right">所属街道：</td>
			<td width="80"><s:textfield name="model.dept.name"
				cssStyle="width:80px" /></td>
			<td width="60" align="right">监管区域：</td>
			<td width="80"><s:textfield name="model.superviseRegion" 
				cssStyle="width:80px" /></td>
			<td width="60" align="right">负责人：</td>
			<td width="20"><input type="checkbox" name="model.isLeader" 
				value="1"/></td>
		</s:form>
		<td align="left">
			<input type="button" value="查询" class="button" onclick="index()" style="width: 40px"/>
		</td>
		<td align="right">
		<table>
			<tr>
				<td><a href="${ctx}/pages/opengis/mapSupervisorLabel.jsp"><img
					src="${ctx}/images/fs_index/search.gif" />监管员分布图</a></td>
				<td><span class="ytb-sep"></span></td>
				<td><a href="editNew.do"><img
					src="${ctx}/images/icons/add.gif" />添加</a></td>
		</table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
	action="index.do" 
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
	height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:extend><td align="left"><input type="button" value="导出手机号" class="button" onclick="exportMobileNum()" style="width:70px"/></td></ec:extend>
	<ec:row>
		<ec:column width="35" property="_No" title="No." value="${GLOBALROWCOUNT}" style="text-align:center" />
		<ec:column width="70" property="name" title="姓名">
			<c:if test="${item.isLeader == '1'}"><font color="red" title="红色代表该监管员为负责人">${item.name}</font></c:if>
		</ec:column>
		<ec:column width="35" property="gender" title="性别" style="text-align:center">
			<c:if test="${item.gender == 'M'}">
				男&nbsp;
			</c:if>
			<c:if test="${item.gender == 'F'}">
				女&nbsp;
			</c:if>
		</ec:column>
		<ec:column width="55" property="code" title="编号" style="text-align:center"/>
		<ec:column width="95" property="mobile" title="手机号码" style="text-align:center"/>
		<ec:column width="120" property="dept.name" title="所属街道"/>
		<ec:column width="250" property="superviseRegion" title="监管区域" />
		<ec:column width="120" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">
				<img src="${ctx}/images/icons/modify.gif" border="0" title="编辑" />
			</a>
			<a href="look.do?model.id=${item.id}" target="_blank">
				<img src="${ctx}/images/icons/resource.gif" border="0" title="查看" />
			</a>
			<a href="#">
				<img src="${ctx}/images/icons/delete.gif" title="删除" border="0" onclick="removeSupervisor(${item.id})"/>
			<a href="${ctx}/pages/opengis/mapSupervisor.jsp?value='${item.id}'">
				<s:if test="#attr.item.locationType != null">
					<img src="${ctx}/images/superVisor02.gif" title="查看地域位置" board="0"/>
				</s:if>
				<s:else>
					<img src="${ctx}/images/superVisor_NoLocation.gif" title="标注地域位置" board="0"/>
				</s:else>
			</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>