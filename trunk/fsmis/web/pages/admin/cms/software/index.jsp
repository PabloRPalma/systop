<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title>软件下载管理</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
<script type="text/javascript">

/**
 * 删除提交
 */
function remove(){
	if (confirm("确认要删除所选择的软件信息吗?")){
		var from = document.getElementById("ec");
		from.action="remove.do";
		from.submit();
	}
}
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">软件下载管理</div>
  <div class="x-toolbar">
  	<table width="99%">
  	  <tr>
  		<td>
  		<s:form action="index.do" theme="simple">
  			软件名称：
  			<s:textfield id="name" name="model.name" cssStyle="width:150px"/>
	    	<s:submit value="查询" cssClass="button"/>
	    </s:form>
  		</td>
  		<td>
	  	  <table align="right">
	  	    <tr>
	  	      <td>
	  	      	<a href="newSoft.do"><img src="${ctx}/images/icons/add.gif">添加软件</a>
			  </td>
			  <td><span class="ytb-sep"></span></td>
		      <td>
		        <a href="#" onclick="remove()"><img src="${ctx}/images/icons/delete.gif">删除软件</a>
		      </td>
		      <td><span class="ytb-sep"></span></td>
		      <td> 
		      	<a href="${ctx}/admin/software/listcatas.do"><img src="${ctx}/images/icons/softcatalog.gif">软件类别</a>
			  </td>
	  		</tr>
	  	  </table>
  		</td>
  	  </tr>
  	</table>
  </div>
  <div class="x-panel-body">
    <div style="margin-left:-3px;" align="center">
	  <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
		action="index.do"
		useAjax="false" doPreload="false"
		pageSizeList="20,50,100,200" 
		editable="false" 
		sortable="true"	
		rowsDisplayed="20"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"
		width="100%" 	
		height="477px"	
		minHeight="470"  
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
		<ec:row>
			<ec:column width="50" property="_select" title="选择" style="text-align:center">
				<input type="checkbox" name="selectedItems" value="${item.id}"/>
			</ec:column>
		    <ec:column width="250" property="name" title="软件名称"/>
		    <ec:column width="90" property="pubDate" title="加入日期" cell="date"/>
		    <ec:column width="100" property="size" title="软件大小KB" style="text-align:center"/>
		    <ec:column width="80" property="downCount" title="下载次数" style="text-align:center"/>
		    <ec:column width="150" property="softCatalog.name" title="所属类别"/>
		    <ec:column width="270" property="downUrl" title="相对路径"/>
		    <ec:column width="65" property="_op" title="操  作" style="text-align:center">
		    	<a href="edit.do?model.id=${item.id}">
		    		<img src="${ctx}/images/icons/modify.gif">
				</a>
		    </ec:column>
		</ec:row>
	  </ec:table>
	</div>
  </div>
</div>
</body>
</html>