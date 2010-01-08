<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title>应急指挥组</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extjs.jsp" %>
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
       idsEl:'userIds',
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
    <div class="x-panel-header">应急指挥指挥组维护</div>
	<div><%@ include file="/common/messages.jsp"%></div> 
	<div align="center">
	<s:form id="ucGroupForm" action="save.do" method="post"  validate="true" >
	<s:hidden name="model.id"/>
	<s:hidden name="ucTypeId"/>
	<fieldset style="width:610px; padding:10px 10px 10px 10px;">
    	<legend>编辑指挥组</legend>
        <table width="600px" align="center">
        <tr>
             <td align="right" width="80">组类型:</td>
             <td align="left" width="520">
             	<s:select id="category" name="model.category" list="categoryMap" headerKey="" headerValue="请选择"  cssClass="required" onchange="categoryChange()"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
          <tr>
             <td align="right" width="80">组名:</td>
             <td align="left" width="520">
             	<s:textfield id="name" name="model.name" cssStyle="width:300px" readonly="true"/>
             </td>
          </tr>
           <tr>
             <td align="right" width="80">负责人:</td>
             <td align="left" width="520">
             	<s:textfield id="principal" name="model.principal" cssStyle="width:300px" cssClass="required"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
          <tr>
			<td align="right" width="80">操作人:</td>
			<td align="left">
					<s:textfield id="person" name="person" cssStyle="width:300px" />
					<s:hidden name="userIds"/>
			</td>
		</tr> 
		<tr>
			<td align="right" width="80"></td>
			<td align="left">
					<input onclick="showWin()" value="添加人员" type="button">
			</td>
		</tr> 
           <tr>
             <td align="right" width="80">手机号:</td>
             <td align="left" width="520">
             	<s:textfield id="mobel" name="model.mobel" cssStyle="width:300px" cssClass="digits"/>
             </td>
          </tr>
           <tr>
             <td align="right" width="80">固话:</td>
             <td align="left" width="520">
             	<s:textfield id="phone" name="model.phone" cssStyle="width:300px" cssClass="digits"/>
             </td>
          </tr>
          <tr>
             <td align="right" width="80">描述：</td>
             <td align="left" width="520">
             	<s:textarea id="descr" name="model.descr" cssStyle="width:300px;height:50px"/>
             </td>
          </tr>
          </table>  
    </fieldset>
     <table width="600px" style="margin-bottom:10px;">
		<tr>
			<td style="text-align:center;">
				<s:submit value="保存" cssClass="button" />
				<s:reset value="重置" cssClass="button"/>
		   </td>
		</tr>
	</table>
	</s:form>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#ucGroupForm").validate();
});
	function categoryChange() { 
		newOptions= document.getElementById("category").options;
		for(i = 0; i < newOptions.length; i++){
			if (newOptions[i].selected ){

				if(newOptions[i].text=="指挥部"||newOptions[i].text=="办公室"){
					document.getElementById("name").value=newOptions[i].text;
				}else{
					document.getElementById("name").value=newOptions[i].text+"组";		
					}
				if(newOptions[i].value==""){
					document.getElementById("name").value="";
				}
			}
		}
	} 
</script>
</body>
</html>