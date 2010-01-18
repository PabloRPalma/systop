<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>短信发送列表</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">短信通知</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td>&nbsp;应急事件：${model.title }</td>
		<td align="right">
		<table>
			<tr>
				<td align="right"><a href="#" onclick="backToCase()">返回</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<form id="saveFrom" action="sendSms.do" method="post">
<table align="center">
	<s:hidden name="model.id" id="caseId" />
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
	<tr>
		<td>
		<fieldset style="width: 790"><legend>短信内容编辑</legend>
		<table>
			<tr>
				<td width="80" align="center">短信号码：</td>
				<td width="570"><textarea id="mobiles" name="mobelNums" onBlur="checkNum()"
					style="width: 550px; height: 100px;">${mobelNums}</textarea> <font color="red">*</font>
				</td>
				<td width="132" id="numInfo">多个号码请用分号隔开</td>
			</tr>
			<tr>
				<td align="center">短信内容：</td>
				<td><textarea id="content" name="smsContent" onBlur="checkContent()"
					style="width: 550px; height: 200px;"></textarea>
					<font color="red">*</font>
				</td>
				<td id="contInfo">编辑短信内容</td>
			</tr>
		</table>
		</fieldset>
		</td>
	</tr>
</table>
<table width="100%" style="margin-bottom: 0px;">
	<tr>
		<td style="text-align: center;">
			<input type="button" value="保存" onClick="sendSms()" class="button">&nbsp;&nbsp;
			<input type="button" value="返回" onclick="backToCase()" class="button"></td>
	</tr>
</table>
</form>
</div>
</div>
</div>

<script type="text/javascript">
function checkNum(){
	var mobiles = document.getElementById("mobiles").value;
	if (mobiles == null || mobiles == ""){
		document.getElementById('numInfo').innerHTML = '<font color="red">'+'请输入手机号'+'</font>';
		return false;
	}
	if (mobiles.indexOf(";") < 0){
		if ( isNaN(mobiles)){
			document.getElementById('numInfo').innerHTML = '<font color="red">'+'手机号码必须是数字'+'</font>';
			return false;
		}
		if (mobiles.length != 11){
			document.getElementById('numInfo').innerHTML = '<font color="red">'+'手机号码位数不正确'+'</font>';
			return false;
		}
	}else{
		arrayMobile = mobiles.split(";");
		if (arrayMobile.length > 0){
			for(i = 0; i < arrayMobile.length; i++){
				if ( isNaN(arrayMobile[i])){
					document.getElementById('numInfo').innerHTML = arrayMobile[i] + '<font color="red">'+' 手机号码必须是数字'+'</font>';
					return false;
				}
				if (arrayMobile[i].length != 11 && arrayMobile[i].length !=0){
					document.getElementById('numInfo').innerHTML = arrayMobile[i] + '<font color="red">'+' 手机号码位数不正确'+'</font>';
					return false;
				}
			}
		}
	}
	document.getElementById('numInfo').innerHTML = '多个号码请用分号隔开';
	return true;
}
function checkContent() {
	var content = document.getElementById("content").value;
	if (content == null || content == ""){
		document.getElementById('contInfo').innerHTML = '<font color="red">'+'请输入短信内容'+'</font>';
		return false;
	}
	document.getElementById('contInfo').innerHTML = '编辑短信内容';
	return true;
}
function sendSms() {
	if(checkNum()&&checkContent()) {
		document.getElementById("saveFrom").submit();
	}
}

function backToCase() {
	var caseId = document.getElementById('caseId').value;
	window.location = '${ctx}/urgentcase/view.do?model.id='+ caseId + '&actId=3';
}
</script>
</body>
</html>