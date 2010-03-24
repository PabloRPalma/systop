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
  <div class="x-panel-header">资源管理</div>
    <div class="x-toolbar">
	  <table width="100%">	      
		<tr>	         
		  <td align="right">
			<table>
			  <tr>
      <td><a href="${ctx}/admin/security/resource/listResources.do"> 资源管理首页</a></td> 
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
<s:form action="saveResource" validate="true" theme="simple">
<s:hidden name="model.id"/>
<s:hidden name="model.version"/>
<fieldset style="margin:10px;">
	<legend>编辑资源</legend>
	<table>
	<tr>
		<td>资源名称：</td>
		<td>
		<s:textfield id="model.name" name="model.name"></s:textfield><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td>资源字符串：</td>
		<td>
		<s:textfield id="model.name" name="model.resString"></s:textfield><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td>资源类型：</td>
		<td>
		<s:select list="resourceTypes" name="model.resType" cssStyle="width:150px"></s:select>
		</td>
	</tr>
	<tr>
		<td>资源描述：</td>
		<td bgcolor="#FFFFFF">
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