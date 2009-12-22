<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">软件类别管理</div>
    <div class="x-toolbar">
    </div>
	<div><%@ include file="/common/messages.jsp"%></div> 
	<s:form  action="savecatas.do" method="post" theme="simple" enctype="multipart/form-data">
	<s:hidden id="id" name="model.id"/>
	<table width="600px" align="center">
		<tr>
			<td align="center">
			<fieldset style="margin:10px;">
              <legend>编辑软件类别信息</legend>
                <table>
                  <tr>
                     <td align="right">软件类别名称：</td>
                     <td align="left">
                     	<s:textfield id="name" name="model.name" size="40" maxlength="25"/><font color="red">&nbsp;*</font>
                     </td>
                  </tr>
                  <tr>
                    <td align="right">软件类别描述：</td>
                    <td align="left">
                    	<s:textarea id="description" name="model.description" cssStyle="width:280px; height:50px;"/>
                    </td>
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