<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<html>
<head>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript" src="${ctx}/scripts/extjs/diy/ColumnNodeUI.js"></script>
<script type="text/javascript" src="${ctx}/scripts/extjs/diy/ColumnTreeLoader.js"></script>
<script type="text/javascript" src="${ctx}/pages/admin/dept/index.js"></script>
<LINK href="${ctx}/scripts/extjs/resources/css/column-tree.css" type='text/css' rel='stylesheet'>
<style type="text/css">
.task {
    background-image:url(${ctx}/images/icons/file.png) !important;
}
.task-folder {
    background-image:url(${ctx}/images/icons/foldericon.png) !important;
}
.x-tree-node-cb {
    border:0;
    height:14px;
}
.x-tree-col-text {
   padding:0px;
   margin:0px;
   font-size:12px;
}
.x-tree-hd-text {
   font-size:12px;
}
#tree-dept {
border-right:1px #99bbe8 solid;
border-bottom:1px #99bbe8 solid;
height:400px;
}
</style>
<title></title>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">部门管理</div>
    <div class="x-toolbar">
      <table width="99%">	      
		      <tr>	         
		         <td align="right">
		         <table>
			         <tr>
			         <td><a href="${ctx}/admin/dept/index.do"><img src="${ctx}/images/icons/home_1.gif"/> 部门管理首页</a></td>
			         <td><span class="ytb-sep"></span></td>
			         <stc:role ifAllGranted="ROLE_ADMIN">
			         <td><a href="${ctx}/admin/dept/updateDeptSerialNo.do"><img src="${ctx}/images/grid/clear.png"/> 重置部门编号</a></td>
			         <td><span class="ytb-sep"></span></td>
			         </stc:role>
			         <td><a href="${ctx}/admin/dept/editNew.do"><img src="${ctx}/images/icons/users_1.gif"/> 新建部门</a></td>
			         <td><span class="ytb-sep"></span></td>
			         <td><a href="#" id="remove-btn">
			         <img src="${ctx}/images/icons/delete.gif"/> 删除部门</a></td>
			         
		         </tr>
		         </table>
		         </td>
		      </tr>
	       </table>
	</div>
    <div class="x-panel-body">
    <s:form action="/admin/dept/remove.do" id="killer" method="POST">
        <div id="tree-dept">
        </div>
    </s:form>
    </div>
</div>
<s:form action="/admin/dept/edit.do" id="operator" method="GET">
   <input type="hidden" name="id" id="targetId">
</s:form>
</body>
</html>