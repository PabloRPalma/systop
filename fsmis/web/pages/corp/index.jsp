<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>企业信息管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function removeCompany(corpId) {
    Ext.MessageBox.confirm('提示','确实要删除该企业信息吗？删除后不能恢复！', function(btn){
        if (btn == 'yes') {
        	location.href="remove.do?model.id=" + corpId;
        }
    });
  }
function mapOfCorps() {
	var myform = document.getElementById("search");
	myform.action="mapOfCorps.do";
	myform.submit();
}
</script>
</head>
<body>
<div><%@ include file="/common/messages.jsp"%></div>
<div class="x-panel">
<div class="x-panel-header">企业管理</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<s:form id="search" action="index.do" method="post">
		<td width="83" align="right">
			企业名称：		</td>
		<td width="69">
			<s:textfield name="model.name" cssStyle="width:80px"/>
		</td>
		<td width="56" align="right">
			法&nbsp;&nbsp;人：		</td>
		<td width="68">
			<s:textfield name="model.legalPerson" cssStyle="width:80px"/>
		</td>
		<td width="80" align="right">
			企业地址：		</td>
		<td width="130">
			<s:textfield name="model.address" cssStyle="width:80px"/>
			<input type="submit" value="查询" class="button" />
		</td>
		<td>
			<input onclick="mapOfCorps()" type="button" value="企业分布图" class="button" />
		</td>
		</s:form>
		<td align="right">
		<table>
			<tr>
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
		<ec:column width="100" property="code" title="企业编号" />
		<ec:column width="80" property="legalPerson" title="法人" style="text-align:center"/>
		<ec:column width="220" property="address" title="企业地址"/>
		<ec:column width="170" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">编辑</a> |
			<a href="view.do?model.id=${item.id}" target="_self">查看</a> |
			<c:choose>
	     		<c:when test="${!empty item.fsCases}"> 
	        		<font color="#999999" title="该企业涉及食品安全事件，不能删除！">删除</font> |
	     		</c:when>
	     		<c:otherwise>
	        		<a href="#" onClick="removeCompany(${item.id})">删除</a> |
	     		</c:otherwise>
	   		</c:choose> 
	   		
	   		<c:if test="${item.coordinate eq null}">
				<a href="mark.do?model.id=${item.id}" title="地图标注信息">标注</a>
			</c:if>
			<c:if test="${item.coordinate ne null}">
				<a href="mark.do?model.id=${item.id}" title="地图标注信息">地图</a>
			</c:if>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>