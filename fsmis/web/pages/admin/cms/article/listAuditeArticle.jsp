<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<html>
<head>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
jQuery.noConflict();
</script>
<%@include file="/common/calendar.jsp"%>
<title>文章管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/CatalogDwrAction.js"></script>
<script type="text/javascript">
function onRemove() {
    var sels = document.getElementsByTagName('input');
    var checked = false;;
    for(i = 0; i < sels.length; i++) {
        if(sels[i].id == 'selectedItems' && sels[i].checked) {
           checked = true;
           break;
        }
    } 
    if(!checked) {
        alert('请至少选择一个文章。');
        return;
    }
    
    if(confirm("您确定删除文章吗？")) {
       $('ec').action = "${ctx}/admin/article/removeArticle.do";
       $('ec').submit();
    } else {
       return false;
    }
}
function onAudited() {
    var sels = document.getElementsByTagName('input');
    var checked = false;;
    for(i = 0; i < sels.length; i++) {
        if(sels[i].id == 'selectedItems' && sels[i].checked) {
           checked = true;
           break;
        }
    } 
    if(!checked) {
        alert('请至少选择一个文章。');
        return;
    }
    
    if(confirm("您确定审核通过吗？")) {
       $('ec').action = "${ctx}/admin/article/batchAuditArticle.do";
       $('ec').submit();
    } else {
       return false;
    }
}
/** set parentId */
function setCatalog(){
	CatalogDwrAction.getCatalog("catalog","1",function(catalog){
		DWRUtil.removeAllOptions("catalogId");
		DWRUtil.addOptions($("catalogId"), catalog,
    		function getValue(catalog) {
    			return catalog.id;
    		},
    		function getText(catalog) {
    			return catalog.text;
	   		});
	   		DWRUtil.setValue("catalogId", "${catalogId}");
		} 
	);
}
		setCatalog();
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">文章管理</div>

<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>
			<s:form name="frmQuery" id="frmQuery" method="post">
			  <table border="0">
				<tr>
					<td width="200">
					  标题：<input type="text" name="articleName" value="${articleName}" style="width:150px"/>&nbsp;&nbsp;
					</td>
					<td width="200">
					  所属栏目：<select id="catalogId" name="catalogId" />
					</td>
					<td>
						从：
						<input type="text" id="beginDate" name="beginDate" popupDateFormat="yyyy-MM-dd"
						style="width:70px;"
						class="Wdate" onfocus="WdatePicker({skin:'whyGreen'})" value='<s:date name="beginDate" format="yyyy-MM-dd"/>'/>
						到：
						<input type="text" id="endDate" name="endDate" popupDateFormat="yyyy-MM-dd"
						style="width:70px;" 
						class="Wdate"  onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',skin:'whyGreen'})" value='<s:date name="endDate" format="yyyy-MM-dd"/>'/>
					</td>
					<td>
						<input type="submit" value="查 询" class="button"/>
					</td>
				</tr>
			  </table>
			</s:form>
		</td>
		<td align="right">
		<table>
			<tr>
				<td>
					<a href="#" onclick="onAudited()">
						<img src="${ctx}/images/icons/tick.gif" />审核</a>
				</td>
				<td><span class="ytb-sep"></span></td>
				<td>
					<a href="#" onclick="onRemove()">
						<img src="${ctx}/images/icons/delete.gif" />删除</a>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left:-3px;" align="center"><ec:table
	items="items" var="item" 
	retrieveRowsCallback="limit"
	sortRowsCallback="limit"
	action="listAuditArticles.do" 
	useAjax="false" 
	doPreload="false"
	pageSizeList="10,20,50,100" 
	editable="false" 
	sortable="true"
	rowsDisplayed="10" 
	generateScript="true" 
	resizeColWidth="true"
	classic="true" width="100%" height="300px" minHeight="300"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="50" property="_s" title="选择"
			style="text-align:center" sortable="false">
			<input type="checkbox" name="selectedItems" id="selectedItems"
				value="${item.id}" />
		</ec:column>
		<ec:column width="200" property="title" ellipsis="true" title="文章标题">
			<a href="lookArticle.do?id=${item.id}" target="_blank" title="查看详细信息"><font color="blue">${item.title}</font></a>
		</ec:column>
		<ec:column width="90" property="author" title="作者" />
		<ec:column width="110" property="catalog.name" title="所属栏目" sortable="false" />
		<ec:column width="90" property="createTime" cell="date" title="发布时间" />
		<ec:column width="85" property="audited" title="审核状态" style="text-align:center">
			<c:if test="${item.audited eq 1}">
				<img src="${ctx}/images/icons/accept.gif" title="已审核"/>
			</c:if>
			<c:if test="${item.audited eq 0}">
				<img src="${ctx}/images/icons/stop.gif" title="未审核"/>
			</c:if>
		</ec:column>
		<ec:column width="200" property="_0" title="编辑" style="text-align:center" sortable="false">
			<a href="auditArticle.do?model.id=${item.id}&catalogId=${catalogId}">
			<c:if test="${item.audited eq '0'}">
			   	 审核通过
			    </c:if> 
			    <c:if test="${item.audited eq '1'}">
			      取消审核
			    </c:if>
			</a> |
				<a href="onTopArticle.do?model.id=${item.id}&catalogId=${catalogId}">
				<c:if test="${item.onTop eq '0'}">
			   	 设为固顶
			    </c:if> 
			    <c:if test="${item.onTop eq '1'}">
			      取消固顶
			    </c:if> 
			</a> | 
			    <a href="eliteArticle.do?model.id=${item.id}&catalogId=${catalogId}">
			    <c:if test="${item.isElite eq '0'}">
			   	 设为推荐
			    </c:if>
			    <c:if test="${item.isElite eq '1'}">
			      取消推荐
			    </c:if> 
			    </a>
		</ec:column>
	</ec:row>
</ec:table></div>

</div>
</div>
</body>
</html>

