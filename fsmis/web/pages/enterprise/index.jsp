<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>企业信息管理</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<script type="text/javascript">
function removeCompany(cID){
	if(confirm("确实要删除该企业信息吗？")){
		location.href="remove.do?model.id="+cID;
	}
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">诚信企业管理&nbsp;&gt;&nbsp;企业管理</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>
		<s:form action="index.do" method="post">
		<td width="70" align="right">
			企业名称：
		</td>
		<td width="60">
			<s:textfield name="model.name" cssStyle="width:80px"/>
		</td>
		<td width="70" align="right">
			法&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人：
		</td>
		<td width="60">
			<s:textfield name="model.legalPerson" cssStyle="width:80px"/>
		</td>
		<td width="70" align="right">
			企业地址：
		</td>
		<td>
			<s:textfield name="model.address" cssStyle="width:80px"/>
			<input type="submit" value="查询" class="button" />
		</td>
		</s:form>
		</td>
		<td align="right">
		<table>
			<tr>
				<td><a href="edit.do" title="添加企业信息"><img
					src="${ctx}/images/icons/add.gif" /></a></td>
				<td valign="middle"><a href="edit.do" title="添加企业信息">添加</a></td>
				<!--
				<td><span class="ytb-sep"></span></td>
				<td><a href="${ctx}/pages/opengis/mapCompanyLabel.jsp"><img
					src="${ctx}/images/fs_index/search.gif" />企业分布图</a></td>
				-->
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
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
	<ec:row>
		<ec:column width="40" property="_no" value="${GLOBALROWCOUNT}" title="No." style="text-align:center"/>
		<ec:column width="230" property="name" title="企业名称" >
			<a href="view.do?model.id=${item.id}" target="_self" title="查看企业信息"><font color="blue">${item.name}</font></a>
		</ec:column>
		<ec:column width="120" property="code" title="企业编号" />
		<ec:column width="80" property="legalPerson" title="法人" />
		<ec:column width="220" property="address" title="企业地址"/>
		<ec:column width="140" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">编辑</a> |
			<a href="view.do?model.id=${item.id}" target="_self">查看</a> |
			<a href="#" onclick="removeCompany(${item.id})">删除</a>
			<!--
			<s:if test="#attr.item.locationType != null">
			    <a href="${ctx}/pages/opengis/mapCompany.jsp?value='${item.id}'">
				    <img src="${ctx}/images/company.gif" title="查看地理位置" board="0"/>
			    </a>
			</s:if>
			<s:else>
				<a href="${ctx}/pages/opengis/mapCompany.jsp?value='${item.id}'">
				    <img src="${ctx}/images/company_NoLocation.gif" title="标注地理位置" board="0"/>
			    </a>
			</s:else>
			-->
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>