<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<s:hidden id="model.id" name="model.id"/>
<s:hidden id="model.version" name="model.version"/>
<s:hidden id="queryType" name="queryType"/>
<table width="100%" cellpadding="2" cellspacing="1">
	<tr bgcolor="#EBEBEB">
		<td width="10%" align="right">模板名称：</td>
		<td align="left" colspan="3">
			<s:textfield id="model.name" name="model.name"  size="30" />
			<font color="red">&nbsp;*</font></td>
	</tr>
	<tr bgcolor="#EBEBEB">
		<td align="right" width="70">模板类型：</td>
		<td width="40%">
	  <s:select id="model.type" name="model.type"	list="types" /></td>
		<td width="10%" align="right">是否默认：</td>
		<td>
			<s:radio id="model.isDef" name="model.isDef" list="yns" />
	  </td>
	</tr>
	<tr bgcolor="#EBEBEB">
		<td align="right">模板说明：</td>
		<td colspan="3">
			<s:textarea id="model.descn"
			name="model.descn" cssStyle="width:550px;height:30px;"/>
		</td>
	</tr>
	<tr bgcolor="#EBEBEB">
		<td align="right">模板内容：</td>
		<td colspan="3">
			<s:textarea id="model.content"
			name="model.content" cssStyle="width:800px;height:350px;" />
		</td>
	</tr>
</table>
