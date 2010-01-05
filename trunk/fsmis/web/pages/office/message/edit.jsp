<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>内部消息管理</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/extjs/diy/TreeCheckNodeUI.js"></script>
<script type="text/javascript" src="${ctx}/pages/admin/security/user/selector.js"></script>
<style type="text/css">
.dept-tree-node-icon {
background-image:url(${ctx}/images/icons/dept.gif);
}
.emp-M-tree-node-icon,.emp-F-tree-node-icon {
background-image:url(${ctx}/images/icons/users_1.gif);
}
</style>
</head>
<body>
<div id="emp_sel_container" class="x-hidden">
    <div class="x-window-header">员工列表</div>
    <div id="emp_grid"></div>
</div>


<script>
var emp;
   emp = new UserSelector({
       url: '${ctx}/security/user/userTree.do',
       el: 'emp_sel_container',
       idsEl:'userId',
       textEl:'person',
       width : 250,
	   height : 450,
	   multiSel : true
   });
   
function showWin() {   
   emp.show(true);
}

</script>
<div class="x-panel">
<div class="x-panel-header">发送内部消息</div>
<div class="center">
<s:form action="save.do" id="save" method="post" theme="simple" validate="true">
<fieldset style="width: 500px; padding-top: 2px ;">
  <legend>编辑内部消息</legend>
	<s:hidden name="model.id"/>
	<table width="500px" align="center" border="0" cellpadding="3" cellspacing="3">
		<tr>
			<td align="right" width="70">收信人：</td>
			<td align="left">
				
				<s:textfield id="person" name="person" cssStyle="width:450px" />
				<s:hidden name="userId"></s:hidden>
				<input onclick="showWin()" value="添加人员" type="button">
			</td>
		</tr>
		<tr>
			<td align="right">内容：</td>
			<td align="left">
			<s:textarea id="content" name="model.content" cssStyle="width:450px; height:50px" cssClass="required"/>
		</tr>
	</table>
</fieldset>
<table width="500" align="center">
	<tr>
		<td style="text-align: center;" colspan="2">
			<s:submit value="发送" cssClass="button"/> 
			<s:reset value="重置" cssClass="button"/>
		</td>
	</tr>
</table>
</s:form>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#save").validate();
});
</script>
</body>
</html>