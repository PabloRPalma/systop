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
		$('queryValue').value = $('catalogId').value;
		$("frmOrder").action = "orderArticle.do";
		$("frmOrder").submit();
	}
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
		    	//设置默认栏目，用于修改时
				DWRUtil.setValue("catalogId", $('queryValue').value);
				} 
			);
		}
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">文章排序</div>
  <div class="x-toolbar">
  	<table width="100%"><tr>
	  		<td>
	  			&nbsp;&nbsp;&nbsp;&nbsp;
		  		<img src="${ctx}/images/icons/folder_table.gif">
		  		<a href="${ctx}/admin/article/listArticles.do" target="main">文章管理</a>
	  		</td>
	  	</tr></table>
  </div>
  <s:form id="frmOrder" name="frmOrder" namespace="/admin/article" action="saveOrderArticle" method="post" theme="simple">
    <s:hidden name="seqNoList"/>
	<s:hidden name="queryValue" id="queryValue"/>
	<table width="400px" align="center" border="0">
	<tr>
			<td width="20">
				<fieldset style="margin:10px; width: 370px">
              		<legend>文章查询</legend>
             		 栏目名称:
             		<select id="catalogId" name="catalogId"/>
					
					<input type="button" value="查询" class="button" onclick="onQuery()" />
              	</fieldset>
			</td>
		</tr>
		<tr>
			<td align="center">
			<fieldset style="margin:10px;">
              <legend>文章信息</legend>
                <table align="center">
				  <tr>
				  <td>
				      <s:select cssStyle="width:370px" id="order" name="order" list="orderCatalogArt" listKey="id" listValue="title" multiple="true" size="20"/>
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
	</table>
  </s:form>
</div>
<script type="text/javascript">
setCatalog();
var sl = new SortSelect('frmOrder','order','search','jumpNum');
</script>
</body>
</html>