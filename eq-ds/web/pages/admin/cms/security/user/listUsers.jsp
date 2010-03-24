<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<html>
<head>
<%@include file="/common/meta.jsp" %>

<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<script type="text/javascript" src="${ctx}/js/dojo/dojo/dojo.js" djConfig="isDebug:true, parseOnLoad: true"></script>

</head>
<body>
<script type="text/javascript">

function onRemove() {
  var selectitem = document.getElementsByName("selectedItems");
  var j=0;
  for (var i = 0; i < selectitem.length; i ++) {
	if (selectitem[i].checked) {
	  j++;
    }
  }
  if (j>0) {
    if(confirm('您正在禁用用户,是否继续?')) {
        UserManager.remove();
    }
  }else {
    alert("请选择要删除的记录!");
  }
}
</script>
<div class="x-panel">
  <div class="x-panel-header">资源管理</div>
    <div class="x-toolbar">
      <table width="99%">
        <tr>
          <td> 
        <s:form action="listUsers" theme="simple">
	         用户名：<s:textfield theme="simple" name="model.name" size="15"></s:textfield>
	        &nbsp;&nbsp;<s:submit value="查询" cssClass="button"></s:submit>
         </s:form>
        </td>
         <td align="right">
         <table>
          	  <tr>
         <td><a href="${ctx}/admin/security/user/listUsers.do"><img src="${ctx}/images/icons/home_1.gif"/>用户管理首页</a></td>
         <td><span class="ytb-sep"></span></td>
         <td><a href="${ctx}/admin/security/user/newUser.do"><img src="${ctx}/images/icons/user.jpg"/> 新建用户</a></td>
         <td><span class="ytb-sep"></span></td>
         <td><a href="#" onclick="onRemove()"><img src="${ctx}/images/icons/delete.gif"/> 禁用用户</a></td>
         </tr>
            </table>
          <td>
        </tr>
      </table>
    </div>   
    <div class="x-panel-body">
    <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	action="listUsers.do"
	useAjax="true" doPreload="false"
	xlsFileName="用户列表.xls" 
	maxRowsExported="10000000" 
	pageSizeList="10,20,50,100,500,1000" 
	editable="false" 
	sortable="true"	
	rowsDisplayed="10"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="277px"	
	minHeight="200"  
	>
	<ec:row>
	    <ec:column width="50" property="_s" title="选择" style="text-align:center">
	       <input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}"/>
	    </ec:column>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" />
		<ec:column width="80" property="loginId" title="登录名" />
		<ec:column width="120" property="email" title="电子信箱" />
		<ec:column width="70" property="status" title="状态" value="可用"/>
		<ec:column width="80" property="userType" title="用户类别"/>
		<ec:column property="_0" title="角色" style="text-align:center">
		 <a href="#" onclick="javascript:UserManager.assignRoles(${item.id}, 1)">
		       <img src="${ctx}/images/icons/role.gif" border="0" title="分配角色"/>
		   </a>
		</ec:column>
		<ec:column width="60" property="_0" title="编辑" style="text-align:center">
		   <a href="editUser.do?model.id=${item.id}">
		       <img src="${ctx}/images/icons/modify.gif" border="0" title="编辑"/>
		   </a>		  
		</ec:column>
	</ec:row>
	</ec:table>
	</div>
	</div>
</div>
<%@include file="/pages/admin/security/role/assignRoles.jsp" %>
</html>