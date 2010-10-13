<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>

<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<s:form id="removeForm" action="remove" namespace="/quake/admin/czcatalog" method="POST"></s:form>
<div class="x-panel">
  <div class="x-panel-header">地震目录管理</div>
    <div class="x-toolbar">
      <table width="99%">
        <tr>
          <td> 

         </td> 
         <td align="right">
         <table> 
           <tr>
             <td><a href="${ctx}/quake/admin/czcatalog/index.do"><img src="${ctx}/images/icons/house.gif"/> 地震目录管理首页</a></td>
             <td><span class="ytb-sep"></span></td>
             <td><a href="${ctx}/quake/admin/czcatalog/editNew.do"><img src="${ctx}/images/icons/add.gif"/> 新建地震目录</a></td>
             <td><span class="ytb-sep"></span></td>
             <td><a href="#" onclick="onRemove({noneSelectedMsg:'请至少选择一个角色.',
             confirmMsg:'确认要删除地震目录吗？'})"><img src="${ctx}/images/icons/delete.gif"/> 删除地震目录</a></td>
           </tr>
         </table>
         <td>
       </tr>
     </table>
   </div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="index.do"
	useAjax="true" doPreload="false"
	maxRowsExported="1000" 
	pageSizeList="5,10,20,100" 
	editable="false" 
	sortable="true"	
	rowsDisplayed="10"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="277px"	
	minHeight="200" 
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status" 
	>
	<ec:row>
	    <ec:column width="50" property="_s" title="选择" style="text-align:center" viewsAllowed="html" sortable="false">
	       <input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}" class="checkbox"/>
	    </ec:column>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="100" property="cltName" title="地震目录表名" />
		<ec:column width="100" property="magTname" title="目录震级表名" />
		<ec:column width="100" property="phaseTname" title="目录震相表名" />
		<ec:column width="100" property="clcName" title="地震目录名称" />
		<ec:column width="110" property="disType" title="震级列显示类型" mappingItem="disTypeMap" style="text-align:center" sortable="false">
			<c:if test="${item.disType eq ''}">
				<c:if test="${item.magTname eq ''}">
					<font color="green">M</font>
				</c:if>
				<c:if test="${item.magTname ne ''}">
					<font color="green">全部类型</font>
				</c:if>
			</c:if>
		</ec:column>
		<ec:column width="110" property="seedDis" title="是否显示事件波形" mappingItem="seedDisMap" style="text-align:center" sortable="false">
			<c:if test="${item.seedDis eq 0}">
				<font color="red">不显示</font>
			</c:if>
			<c:if test="${item.seedDis eq 1}">
				<font color="green">显示</font>
			</c:if>
		</ec:column>
		<ec:column width="40" property="_0" title="操作" style="text-align:center" viewsAllowed="html" sortable="false">
		   <a href="edit.do?model.id=${item.id}"> 
		      <img src="${ctx}/images/icons/modify.gif" style="border:0px" title="编辑"/>
		   </a>
		</ec:column>
	</ec:row>
	</ec:table>
  </div>
  </div>
  </div>
</body>
</html>