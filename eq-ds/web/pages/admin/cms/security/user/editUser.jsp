<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>

<title>编辑用户</title>
<style type="text/css">

.simple {
	font-size: 12px;
	background-color: #FFFFFF;
	padding: 4px;
	color: #0099FF;
}

input,textarea {
	border:1px #CECECE solid;
}


</style>


</head>

<body>
<div class="x-panel">
  <div class="x-panel-header">角色管理</div>
    <div class="x-toolbar">
	  <table width="100%">	      
		<tr>	         
		  <td align="right">
			<table>
			  <tr>
      <td><a href="${ctx}/admin/security/user/listUsers.do"> 人员管理首页</a></td> 
      <td><span class="ytb-sep"></span></td>
      <td><a href="#"> 编辑人员信息</a></td>
 </tr>
            </table>
          </td>
        </tr>
      </table>
    </div>
<table width="100%" border="0" cellpadding="5" cellspacing="1">
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
</table>
<div class="x-panel-body">
<table width="50%" align="center"><tr><td align="center">
<s:form action="saveUser" theme="simple" validate="true" method="POST">
	   <s:hidden name="model.id"/>
	   <s:hidden name="model.version"/>
	   <s:hidden name="model.status"/>
	   <fieldset style="margin:10px;">
	<legend>编辑用户</legend>
	<table>
  <tr>
    <td class="simple">登录名</td>
	    <td class="simple"><s:textfield name="model.loginId" theme="simple" size="30"/>*</td>
  </tr>
  <tr>
	<td class="simple">密码</td>
	<td class="simple"><s:password name="model.password" id="pwd" theme="simple" size="31"/>*</td>
  </tr>
  <tr>
    <td class="simple">重复密码</td>
	<td class="simple"><s:password name="model.confirmPwd" id="repwd" theme="simple" size="31"/>*</td>
  </tr>
  <tr>
    <td class="simple">电子邮箱</td>
    <td class="simple"><s:textfield name="model.email" id="email" theme="simple" size="30"></s:textfield></td>
  </tr>
  </table>
	</fieldset>
	<table width="100%" style="margin-bottom:10px;">
	<tr>
		<td colspan="2" align="center" class="font_white">
		<s:submit value="保存" cssClass="button"></s:submit>
		</td>
	</tr>
</table>
</s:form>
 </td></tr></table>
        </div></div>
</body>
</html>