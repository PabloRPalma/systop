<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title>链接管理</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
<script type="text/javascript">
function remove(id, name) {
  if (confirm('您确定删除名称为："' + name + '" 的友情链接吗？')) {
  	document.getElementById("model.id").value = id;
    $("model.id").value = id;
    document.getElementById("frmRem").submit();
  }
}
</script>
</head>
<body>
<c:set var="Y">
	<s:property value="@com.systop.cms.CmsConstants@Y"/>
</c:set>
<c:set var="N">
	<s:property value="@com.systop.cms.CmsConstants@N"/>
</c:set>
<div class="x-panel">
  <div class="x-panel-header">友情链接管理</div>
  <div class="x-toolbar">
  	<table width="99%">
  	  <tr>
  		<td>
  		<s:form namespace="/admin/links" action="listLink" theme="simple">
		    链接类别：<s:select name="model.linkCatas.id" list="catas" listKey="id" listValue="name" headerKey="" headerValue="选择类别"/>
		    推荐状态：<s:select list="#{@com.systop.cms.CmsConstants@Y:'已推荐',@com.systop.cms.CmsConstants@N:'未推荐'}" name="model.isElite" headerKey="" headerValue="选择状态"/>
		    网站名称：<s:textfield id="model.siteName" name="model.siteName" size="15" theme="simple"/>
	    <s:submit value="查询" cssClass="button"/>
	    </s:form>
  		</td>
  		<td align="right">
			<a href="${ctx}/admin/links/newLink.do">
				<img src="${ctx}/images/icons/add.gif">添加链接</a>&nbsp;&nbsp;
			<a href="${ctx}/admin/links/orderLink.do">
				<img src="${ctx}/images/icons/link_go.gif">链接排序</a> 
		</td>
  	  </tr>
  	</table>
  </div>
  <div class="x-panel-body">
    <div style="margin-left:-3px;" align="center">
	  <ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
		action="listLink.do"
		useAjax="false" doPreload="false"
		pageSizeList="10,20,50,100" 
		editable="false" 
		sortable="true"	
		rowsDisplayed="20"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"
		width="100%" 	
		height="350px"	
		minHeight="350"  
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
		<ec:row>
			<ec:column width="40" property="_s" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center"/>
		    <ec:column width="200" property="siteName" title="网站名称"/>
		    <ec:column width="85" property="siteLogo" title="网站Logo" sortable="false">
		      <c:if test="${item.siteLogo != null && item.siteLogo != ''}">
		        <img src="${ctx}${item.siteLogo}" style="width: 85;height: 30;"/>
		      </c:if>
		    </ec:column>
		    <ec:column width="100" property="linkCatas.name" title="所属类别"/>
		    <ec:column width="50" property="isElite" title="推荐"  style="text-align:center">
		      <c:if test="${item.isElite eq Y}">
		        <span><b>√</b></span>
		      </c:if>
		      <c:if test="${item.isElite eq N}">
		        <span style="color:red;"><b>×</b></span>
		      </c:if>
		    </ec:column>
			<ec:column width="150" property="_0" title="操作" style="text-align:center" sortable="false">
			  <a href="eliteLink.do?model.id=${item.id}">
				<c:if test="${item.isElite eq N}">
			   	  设为推荐
			    </c:if>
			    <c:if test="${item.isElite eq Y}">
			       取消推荐
			    </c:if>
			   </a>
			   <a href="editLink.do?model.id=${item.id}">
			       <img src="${ctx}/images/icons/modify.gif" border="0" title="编辑" />
			   </a>
			    <a href="#" onclick="remove(${item.id}, '${item.siteName}')">
			       <img src="${ctx}/images/icons/delete.gif" border="0" title="删除"/>
			   </a>  
			</ec:column>
		</ec:row>
	  </ec:table>
	</div>
  </div>
</div>
<s:form id="frmRem" action="removeLink" namespace="/admin/links" method="post">
  <s:hidden id="model.id" name="model.id"/>
</s:form>
</body>
</html>