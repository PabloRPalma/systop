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
		<fieldset style="width: 750"><legend>短信内容编辑</legend>
		<table>
			<tr>
				<td width="80" align="center">短信号码：</td>
				<td width="410"><textarea id="mobiles" name="mobelNums" class="required"
					style="width: 500px; height: 100px;">${mobelNums}</textarea>
				</td>
				<td width="140"><font color="red">*</font><br><br>如果发送多个手机号码请用分号隔开。例：<br>
				号码1;号码2;号码3</td>
			</tr>
			<tr>
				<td align="center">短信内容：</td>
				<td><textarea id="content" name="smsContent" class="required"
					style="width: 500px; height: 200px;"></textarea></td>
				<td width="140"><font color="red">*</font><br><br>编辑短信内容</td>
			</tr>
		</table>
		</fieldset>
		</td>
	</tr>
</table>
<table width="100%" style="margin-bottom: 0px;">
	<tr>
		<td style="text-align: center;">
			<input type="button" value="保存" onclick="save()" class="button">&nbsp;&nbsp;
			<input type="button" value="返回" onclick="backToCase()" class="button"></td>
	</tr>
</table>
</form>
</div>
</div>
</div>

<script type="text/javascript">
	function save() {
		mobiles = document.getElementById("mobiles").value;
		if (mobiles == null || mobiles == "") {
			alert("请输入手机号");
			return;
		}
		if (mobiles.indexOf(";") < 0) {
			if (mobiles.length != 11) {
				alert("手机号码位数不正确");
				return;
			}
			if (isNaN(mobiles)) {
				alert(mobiles + "此号码错误，手机号码应全部是数字");
				return;
			}
		} else {
			arrayMobile = mobiles.split(";");
			if (arrayMobile.length > 0) {
				for (i = 0; i < arrayMobile.length; i++) {
					if (arrayMobile[i].length != 11
							&& arrayMobile[i].length != 0) {
						alert(arrayMobile[i] + "手机号码位数不正确");
						return;
					}
					if (isNaN(arrayMobile[i])) {
						alert(arrayMobile[i] + "此号码错误，手机号码应全部是数字");
						return;
					}
				}
			}
		}

		content = document.getElementById("content").value;
		if (content == null || content == "") {
			alert("请输入短信内容");
			return;
		}
		document.getElementById("saveFrom").submit();
	}

	function backToCase() {
		var caseId = document.getElementById('caseId').value;
		window.location = '${ctx}/urgentcase/view.do?model.id='+ caseId + '&actId=3';
	}
</script>
</body>
</html>