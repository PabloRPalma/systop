<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<script type="text/javascript">
function valileader(){
	
	var supervisorMobile = document.getElementById("supervisorMobile").value;     
    if (supervisorMobile.length != 11){ 
         alert("对不起，您输入的电话号码格式有错误。"); 
          return false; 
    } 
	
}
function init(){
	document.getElementById("supervisorName").value = "${supervisorName}";
	document.getElementById("supervisorMobile").value = "${supervisorMobile}";
}
</script>
</head>
<body onload="init()">
<div class="x-panel">
<div class="x-panel-header"></div>
<div class="x-toolbar">
<table width="100%">
   <tr>
      <td align="right">
        <a  href="confirmBackMsg.do?model.id=${model.id}&operType=M&modelId=0&isMultipleCase=${isMultipleCase}">手动核实</a>
      </td>
   </tr>
</table>
</div>
<div align="center">
	<s:form action="sendMsg" id="sendMsg" method="post" theme="simple" validate="true" onsubmit="return valileader()">
		<s:hidden name="model.id"/>
		<input type="hidden" name="modelId" value="${param['modelId'] }"></input>
		<input type="hidden" name="isMultipleCase" value="${isMultipleCase}"></input>
		<fieldset  style="width:700px; padding: 50px 10px 10px 10px;">
			<legend>编辑短信息</legend>
		<table width="450" align="center">
		  <tr>
			<td colspan="4"><%@ include file="/common/messages.jsp"%></td>
		  </tr>
		</table>
		<table width="700px" align="center">
			<tr>
				<td align="right" width="120">接收人姓名：</td>
				<td align="left" >
				   <s:textfield name="supervisorName" id="supervisorName" cssClass="required" value="%{supervisorName}"/>
				</td>
				<td align="right">电&nbsp;&nbsp;&nbsp;&nbsp;话：</td>
				<td align="left">
		           <s:textfield name="supervisorMobile" id="supervisorMobile" cssClass="required" value="%{supervisorMobile}"/>
		        </td>
			</tr>
			<tr>
				<td align="right">短信内容：</td>
				<td colspan="4" align="left" width="650px"><s:textarea id="msgContent" name="msgContent" cssClass="required" cssStyle="width:500px;height:50px;"/></td>
			</tr>
		</table>
		</fieldset>
		<table align="center" width="450">
			<tr>
				<td align="right">
					<s:submit value="发送" cssClass="button"></s:submit>
				</td>
				<td align="left">
					<s:reset value="重置" cssClass="button"></s:reset>
				</td>
			</tr>
		</table>
	</s:form>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#sendMsg").validate();
});
</script>

</body>
</html>