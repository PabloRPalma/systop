<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function valileader(){
	var supervisorName = document.getElementById("supervisorName").value;
	if(supervisorName == ''){
		alert("请填写接收人姓名！");
		return false;
	}
	var supervisorMobile = document.getElementById("supervisorMobile").value;
	if(supervisorMobile == ''){
		alert("请填写接收人电话");
		return false;
	}else{
         
         if (supervisorMobile.length != 11){ 
             alert("对不起，您输入的电话号码格式有错误。"); 
             return false; 
         } 
	}
	
	var msgContent = document.getElementById("msgContent").value;
	if(msgContent == ''){
		alert("请填写短信内容");
		return false;
	}
	return true;
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
        <a  href="confirmBackMsg.do?model.id=${model.id}">手动核实</a>
      </td>
   </tr>
</table>
</div>
<div align="center">
	<s:form action="sendMsg" method="post" theme="simple" onsubmit="return valileader()">
		<s:hidden name="model.id"/>
		<fieldset  style="width:500px; padding: 50px 10px 10px 10px;">
			<legend>编辑短信息</legend>
		<table width="450" align="center">
		  <tr>
			<td colspan="4"><%@ include file="/common/messages.jsp"%></td>
		  </tr>
		</table>
		<table width="450px" align="center">
			<tr>
				<td align="right">接收人姓名：</td>
				<td align="left"><s:textfield name="supervisorName" id="supervisorName" value="%{supervisorName}"/></td>
				<td align="right">电&nbsp;&nbsp;&nbsp;&nbsp;话：</td>
				<td align="left"><s:textfield name="supervisorMobile" id="supervisorMobile" value="%{supervisorMobile}"/></td>
			</tr>
			<tr>
				<td align="right">短信内容：</td>
				<td colspan="3" align="left" width="340"><s:textarea id="msgContent" name="msgContent" cssStyle="width:340px;height:40px;"/></td>
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


</body>
</html>