<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript">
jQuery.noConflict();
</script>
<script type='text/javascript' src='${ctx}/pages/admin/cms/links/sortSelect.js'></script>
<script type="text/javascript" src="${ctx}/dwr/interface/CatalogDwrAction.js"></script>
<%@include file="/common/dwr.jsp" %>
<title>文章排序</title>
<script type="text/javascript">

	function onQuery(){
		document.getElementById('queryValue').value = document.getElementById('parentId').value;
		document.getElementById("frmOrder").action = "listOrderCatalog.do";
		document.getElementById("frmOrder").submit();
	}
	/** set parentId */
	function setCatalog(){
	
		CatalogDwrAction.getCatalog("catalog","1",function(catalog){
			DWRUtil.removeAllOptions("parentId");
			DWRUtil.addOptions($("parentId"), catalog,
	    		function getValue(catalog) {
	    			return catalog.id;
	    		},
	    		function getText(catalog) {
	    			return catalog.text;
	    		});
			DWRUtil.setValue("parentId", $('queryValue').value);
			} 
		);
	}
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">栏目管理</div>
  <div class="x-toolbar">
  	&nbsp;
  </div>
 
	<table align="center">
	<s:form id="frmOrder" name="frmOrder" namespace="/admin/catalog" action="saveOrderCatalog" method="post" theme="simple">
		
		<tr>
			<td>
				<fieldset style="margin:10px; width: 300px">
              		<legend>栏目查询</legend>
             		 栏目名称:
             		<select id="parentId" name="parentId"/>
					<input type="button" value="查询" class="button" onclick="onQuery()" />
              	</fieldset>
			</td>
		</tr>
	
		<tr>
		 	
			<td>
			<fieldset style="margin:10px; width: 300px">
              <legend>栏目排序</legend>
                <table align="center">
				  <tr>
					<td>
				      <s:select id="order" name="order" list="orderCatalogs" listKey="id" listValue="name" multiple="true" size="20"/>
					</td>
					<td>
					  <input id="first" type="button" class="button" onclick="sl.fnFirst()" value="第一"/>
					  <br/>
					  <br/>
					  <input id="up" type="button" class="button" onclick="sl.sortUp()" value="上移"/>
					  <br/>
					  <br/>
					  <input id="down" type="button" class="button" onclick="sl.sortDown()" value="下移"/>
					  <br/>
					  <br/>
					  <input id="fnEnd" type="button" class="button" onclick="sl.fnEnd()" value="最后"/>
					  <br/>
					  <br/>
					  <input id="ok" type="button" class="button" onclick="sl.ok()" value="确定"/>
					</td>
				  </tr>
				</table> 
              </fieldset>
			</td>
		</tr>
		<s:hidden name="seqNoList"/>
		<s:hidden name="queryValue" id="queryValue"/>
		</s:form>
	</table>
</div>

<script type="text/javascript">
setCatalog();
var sl = new SortSelect('frmOrder','order','search','jumpNum');
</script>
</body>
</html>