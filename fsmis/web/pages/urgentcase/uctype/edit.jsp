<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<%@page import="java.util.*"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp" %>

</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">应急指挥&nbsp;>&nbsp;派遣类别维护</div>
	<div><%@ include file="/common/messages.jsp"%></div> 
	<div align="center">
	<s:form  action="save.do" method="post" theme="simple">
	<s:hidden name="model.id"/>
	<fieldset style="width:700px; padding:10px 10px 10px 10px;">
    	<legend>编辑派捷方式</legend>
        <table width="700px" align="center">
          <tr>
             <td align="right" width="100">名称：</td>
             <td align="left" width="600">
             	<s:textfield id="name" name="model.name" cssStyle="width:400px"/>
             </td>
          </tr>
           <tr>
             <td align="right" width="100">备注：</td>
             <td align="left" width="600">
             	<s:textfield id="remark" name="model.remark" cssStyle="width:400px"/>
             </td>
          </tr>
    </fieldset>
    <table width="700px" style="margin-bottom:10px;">
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
</body>
</html>