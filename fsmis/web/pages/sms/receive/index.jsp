<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>接收短信管理</title>
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
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">短信管理</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
	
		<td width="398" align="right">
		<table>
			<tr>
				<td></td>				
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
	pageSizeList="30,50,100,200" 
	editable="false"
	sortable="true" 
	rowsDisplayed="30" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_no" value="${GLOBALROWCOUNT}" title="No." style="text-align:center"/>
		<ec:column width="230" property="mobileNum" title="电话号码" />
			
		<ec:column width="100" property="content" title="短信内容" />
		<ec:column width="80" property="receiveTime" title="系统接收时间"
			style="text-align:center" cell="date" format="yyyy-MM-dd"/>
		<ec:column width="220" property="isReport" title="类别">
		&nbsp;
		</ec:column>
		<ec:column width="220" property="isTreated" title="状态"/>
		<ec:column width="140" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="${ctx}/fscase/editFsCaseBySmsReceive.do?smsReceiveId=${item.id}">
				<img src="${ctx}/images/icons/application.gif" border="0" title="处理" />
			</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>