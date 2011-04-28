<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>专题地震管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function remove(id){
	if (confirm("确认要删除该专题吗?")){	
		 window.location.href="remove.do?model.id=" + id;		   
	}
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">专题地震管理</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td><s:form action="index" method="post">
				创建时间:
			<input type="text" name="beginDate" style="width: 80px"
				value='<s:date name="beginDate" format="yyyy-MM-dd"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				class="Wdate" />
						</>至
			<input type="text" name="endDate" style="width: 80px"
				value='<s:date name="endDate" format="yyyy-MM-dd"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				class="Wdate" />
			                              专题标题:
			<s:textfield name="model.title" cssStyle="width:100px"></s:textfield>	
			<s:submit value="查询" cssClass="button"></s:submit>
		</s:form></td>
		<td align="right">
		<table>
			<tr>
				<td>
					<a href="${ctx}/admin/special/edit.do"> 添加专题</a>
				</td>
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
	sortable="false" 
	rowsDisplayed="30" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="420px"
	minHeight="420"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_no" value="${GLOBALROWCOUNT}" title="No." style="text-align:center"/>
		<ec:column width="130" property="title" title="标题">
			<a href="view.do?model.id=${item.id}" target="_black">
				${item.title}
			</a>
			</ec:column>
		<ec:column width="140" property="quakeTime" title="发震时刻" style="text-align:center"/>
		<ec:column width="50" property="longitude" title="经度" style="text-align:center"/>
		<ec:column width="50" property="latitude" title="纬度" style="text-align:center"/>
		<ec:column width="50" property="magnitude" title="震级" style="text-align:center"/>
		<ec:column width="80" property="location" title="地点"/>
		<ec:column width="115" property="createDate" title="创建时间" style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm"/>
		 <ec:column width="50" property="_8" title="状态" sortable="false" style="text-align:center">
		     <c:if test="${item.state eq '0'}">
		       <font color="red">未发布</font>
		      </c:if>
		      <c:if test="${item.state eq '1'}">
		        <font color="blue">已发布</font>
		      </c:if>
		</ec:column>
		<ec:column width="130" property="_0" title="操作" style="text-align:center" sortable="false">
			  <a href="verify.do?model.id=${item.id}">
				<c:if test="${item.state eq '0'}">
			   	发布
			    </c:if>
			    <c:if test="${item.state eq '1'}">
			           取消
			    </c:if>
			   </a> |
			   <a href="edit.do?model.id=${item.id}">编辑</a> |
			   <a href="#" onClick="remove(${item.id})">删除</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
</body>
</html>