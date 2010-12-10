<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/dwr.jsp" %>
<%@include file="/common/ec.jsp" %>
<script type='text/javascript' src='${ctx}/dwr/interface/TemplateAction.js'></script>
<title></title>
<script type="text/javascript">
function remove(id, name) {
  if (!confirm('您确定删除名称为："' + name + '" 的模板吗？')) {
    return;
  }
  
  TemplateAction.dwrRemove(id,
  	function (msg) {
  	  if (msg == 'success') {
  	    ECSideUtil.reload('ec');
  	  } else if(msg == 'error') {
  	    alert('"' + name + '" 模板下存在栏目或文章不能删除。');
  	  }
  	}
  );
}
</script>
</head>
<body>
<div class="x-panel">
    	<div class="x-panel-header">模板管理</div>
    	<div class="x-toolbar">
    		<table width="99%">
		  	  <tr>
		  		<td width="300">
		  		<s:form id="frmQuery" namespace="/admin/template" action="listTemplate" theme="simple">
		  			<s:radio name="queryType" list="types" onclick="document.getElementById('frmQuery').submit();"  theme="simple"/>
			    </s:form></td>
			    <td>|</td>
			    <td> 
		  		<s:form namespace="/admin/template" action="listTemplate" theme="simple">
			         模板名称：<s:textfield name="model.name" size="15" theme="simple"/>
			        <s:submit value="查询" cssClass="button" theme="simple"/>
			    </s:form>
		  		</td>
		  		<td width="180" align="right">
		  			<a href="${ctx}/admin/template/newTemplate.do">
		  				<img src="${ctx}/images/icons/add.gif">添加模板</a>&nbsp;&nbsp;|&nbsp;  &nbsp;
					<a href="${ctx}/admin/template/listTemplate.do">
						<img src="${ctx}/images/icons/xhtml_go.gif">模板列表</a>&nbsp;&nbsp;&nbsp;
		  		</td>
		  	  </tr>
		  	</table>
    	</div>
    	<div class="x-panel-body">
    	    <div style="margin-left:-3px;" align="center">
		    	<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
				action="listTemplate.do"
				useAjax="false" doPreload="false"
				pageSizeList="30,50,100,200" 
				editable="false" 
				sortable="true"	
				rowsDisplayed="30"	
				generateScript="true"	
				resizeColWidth="true"	
				classic="true"
				width="100%" 	
				height="400"	
				minHeight="400"  
				toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
				<ec:row>
				    <ec:column width="40" property="_s" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
					<ec:column width="160" property="name" title="模板名称" />
					<ec:column width="80" property="type" mappingItem="types" title="模板类别" style="text-align:center"/>
					<ec:column width="80" property="isDef" mappingItem="yns" title="默认模板" style="text-align:center"/>
					<ec:column width="80" property="_0" title="操作" style="text-align:center" sortable="false">
					   <a href="editTemplate.do?model.id=${item.id}">
					   	<img src="${ctx}/images/icons/modify.gif" border="0" title="编辑" />
					   </a>
					   |
					   <s:if test="#attr.item.isDef != 1">
						   <a href="#" onclick="remove(${item.id},'${item.name}')">
						     <img src="${ctx}/images/icons/template_delete.gif" border="0" title="删除"/>
						   </a>	
					   </s:if>
					   <s:else>
					   		<img src="${ctx}/images/icons/template_delete.gif" border="0" title="删除"
					   		 onclick="javascript:alert('[${item.name}]是默认模板不能删除!');"/>
					   </s:else>	  
					</ec:column>
				</ec:row>
				</ec:table>
			</div>
    	</div>
</div>
</body>
</html>