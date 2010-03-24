<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">权限管理</div>
    <div class="x-toolbar">
	  <table width="100%">	      
		<tr>	         
		  <td align="right">
			<table>
			  <tr>
      <td><a href="${ctx}/admin/security/permission/listPermissions.do"> 权限管理首页</a></td>
      <td><span class="ytb-sep"></span></td>
      <td><a href="#"> 编辑权限信息</a></td>
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
<s:form action="savePermission" validate="true" theme="simple">
<s:hidden name="model.id"/>
<s:hidden name="model.version"/>
<fieldset style="margin:10px;">
	<legend>编辑权限</legend>
	<table>
	<tr>
		<td>权限名称：</td>
		<td>
		<s:textfield id="model.name" name="model.name"></s:textfield><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td>权限类型：</td>
		<td align="left">
		<s:select list="operations" name="model.operation" cssStyle="width:150px"></s:select>
		</td>
	</tr>
	<tr>
		<td>权限描述：</td>
		<td>
		<s:textfield id="model.descn" name="model.descn"></s:textfield><font color="red">&nbsp;*</font>
		</td>
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