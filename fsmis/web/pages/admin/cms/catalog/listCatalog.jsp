<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>栏目列表</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/CatalogDwrAction.js"></script>
<script type="text/javascript">
function onRemove() {
    var sels = document.getElementsByName("selectedItems");
    var checked = false;;
    for(i = 0; i < sels.length; i++) {
        if(sels[i].checked) {
           checked = true;
           break;
        }
    } 
    if(!checked) {
        alert('请您至少选择一个栏目。');
        return;
    }
    if(confirm("您确定删除该栏目吗？")) {
		var catalog =new Array(); 
	    var j=0;
	    for (var i = 0; i < sels.length; i ++) {
	    	if (sels[i].checked) {
	    		catalog[j]=sels[i].value;
	    		j++;
	    	}
	    }
		CatalogDwrAction.removeCatalog(catalog,function(result){
			if(result.id == "1"){
				
				alert("你选择的栏目:"+result.name+"中有文章,不能删除!");
			}
			else if(result.id == "2"){
				
				alert("你选择的栏目:"+result.name+"下有子栏目,不能删除!");
			}
			else{
			   ECSideUtil.reload('ec');
			}
		});
    } 
}
</script>
</head>
<body>
<div class="x-panel">
    	<div class="x-panel-header">栏目管理</div>
    	<div class="x-toolbar">
	    	<table width="99%">	      
		      <tr>	   
		      	 <td>
		      	 	<s:form action="listCatalog" method="post">
						栏目名称：
						<s:hidden name="parentId"/>
						<input type="text" name="queryValue" value="${queryValue }" style=" width:150px"/>
						<input type="submit" value="查询" class="button"/>
					</s:form>
		      	 </td>      
		         <td align="right">
		         <table>
			         <tr>
			         <td><a href="${ctx}/admin/catalog/listCatalog.do"><img src="${ctx}/images/icons/house.gif"/>栏目管理首页</a></td>
			         <td><span class="ytb-sep"></span></td>
			         <td><a href="${ctx}/admin/catalog/newCatalog.do"><img src="${ctx}/images/icons/add.gif"/>添加栏目</a></td>
			         <td><span class="ytb-sep"></span></td>
		         	 <td><a href="#" onclick="onRemove()"><img src="${ctx}/images/icons/delete.gif"/> 删除栏目</a></td>
		         </tr>
		         </table>
		         </td>
		      </tr>
	       </table>
    	</div>
    	<div class="x-panel-body">
    	    <div style="margin-left:-3px;" align="center">
		    	<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
				action="listCatalog.do"
				useAjax="true" doPreload="false"
				pageSizeList="10,20,50,100" 
				editable="false" 
				sortable="true"	
				rowsDisplayed="10"	
				generateScript="true"	
				resizeColWidth="true"	
				classic="true"
				width="100%" 	
				height="350px"	
				minHeight="300"  
				toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
				<ec:row>
				    <ec:column width="40" property="_s" title="选择" style="text-align:center" sortable="false">
				    	<input id="selectedItems" type="checkbox" name="selectedItems" value='${item.id}'/>
				    </ec:column>
					<ec:column width="114" property="name" title="栏目名称" sortable="false">
							<a href="catalogDetails.do?model.id=${item.id}" target="_blank" title="查看详细信息"><font color="blue">${item.name}</font></a>
					</ec:column>
					<ec:column width="70" property="parentCatalog.name" title="所属栏目"  sortable="false"/>
					<ec:column width="40" property="type" style="text-align:center" title="类别" 
						 cell="com.systop.cms.catalog.webapp.ec.CatalogTypeCell"  sortable="false"/>
					<ec:column width="150" property="rootPath" title="栏目访问路径" sortable="false" />
					<ec:column width="80" property="cataTemplate.name" title="栏目模板" sortable="false">
						<a href="${ctx}/admin/template/editTemplate.do?model.id=${item.cataTemplate.id}" target="main" title="编辑模板">
							<font color="blue">${item.cataTemplate.name}</font>
						</a>&nbsp;
					</ec:column>
					<ec:column width="80" property="artTemplate.name" title="文章模板" sortable="false">
						<a href="${ctx}/admin/template/editTemplate.do?model.id=${item.artTemplate.id}" target="main" title="编辑模板">
							<font color="blue">${item.artTemplate.name}</font>
						</a>&nbsp;
					</ec:column>
					<ec:column width="80" property="edit" style="text-align:center" title="显示属性" sortable="false">
						<c:if test="${item.showOnIndex eq '1'}">
					   		 <a title="首页显示" href="#"><font color="green"><b>首</b></font></a>
					    </c:if>
					    <c:if test="${item.showOnTop eq '1'}">
					   		 <a title="顶部导航栏显示" href="#"><font color="blue"><b>导</b></font></a>
					    </c:if> 
					    <c:if test="${item.showOnParlist eq '1'}">
					   		 <a title="父栏目导航栏显示" href="#"><b>父</b></a>
					    </c:if>  
					    <c:if test="${item.isEnable eq '0'}">
					   		 <a title="栏目被禁用" href="#"><font color="red"><b>禁</b></font></a>
					    </c:if>  
					</ec:column>
					<ec:column width="70" property="_0" title="编辑" style="text-align:center" sortable="false">
					    <c:if test="${item.type eq '1'}">
						<a href="listCatalog.do?parentId=${item.id}"><img src="${ctx}/images/icons/folder_go.gif"/ title="查看子栏目"></a>
					   	<a href="addChildCatalog.do?model.id=${item.id}"><img src="${ctx}/images/icons/folder_add.gif"/ title="添加子栏目"></a>
					    </c:if>
					    <c:if test="${item.type eq '2'}">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    </c:if>					    
					   	<a href="editCatalog.do?model.id=${item.id}"><img src="${ctx}/images/icons/folder_edit.gif" border="0" title="编辑" /></a>
					</ec:column>
				</ec:row>
				</ec:table>
			</div>
    	</div>
</div>
</body>
<table>
<tr>
<td><font color="red">注:默认查询的栏目名称只显示二级栏目名称.</font></td>
</tr>
</table>
</html>
