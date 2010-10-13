<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">链接类别管理</div>
    <div class="x-toolbar">
	   &nbsp;
    </div>
	<div><%@ include file="/common/messages.jsp"%></div> 
	<s:form namespace="/admin/links" action="saveCatas" method="post" theme="simple" validate="true">
	<s:hidden id="model.id" name="model.id"/>
	<table width="400px" align="center">
		<tr>
			<td align="center">
			<fieldset style="margin:10px;">
              <legend>编辑类别信息</legend>
                <table>
                  <tr>
                     <td>类别名称：</td>
                     <td><s:textfield id="model.name" name="model.name" maxlength="127"/><font color="red">&nbsp;*</font></td>
                  </tr>
                  <tr>
                    <td>类别描述：</td>
                    <td><s:textarea id="model.descn" name="model.descn" cssStyle="width:150"/></td>
                  </tr>
                </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
						<s:submit value="保存" cssClass="button"/>
						<s:reset value="重置" cssClass="button"/>
                    </td>
              	</tr>
              </table>
			</td>
		</tr>
	</table>
	</s:form>
</div>
</body>
</html>