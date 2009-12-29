<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>

<title></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function send(){
	var id = document.getElementById('caseId').value;
	url="${ctx}/task/edit.do?caseId=" + id;
	window.open(url,'main'); 
}
function fastsend(sendtypeid){
	var id = document.getElementById('caseId').value;
	url="${ctx}/task/edit.do?sendTypeId=" + sendtypeid + "&caseId=" + id;
	window.open(url,'main'); 
}
</script>
</head>
<body>
<table align="center" width="567" border="0" cellspacing="0"
	cellpadding="0">
	<tr>
		<td align="center"><img
			src="${ctx}/images/choosesendtype/top.gif" width="567" height="35" /></td>
	</tr>
</table>
<table align="center" width="567" border="0" cellspacing="0"
	cellpadding="0">
	<tr>
		<td><img src="${ctx}/images/choosesendtype/table_top.gif"
			width="567" height="13" /></td>
	</tr>
</table>
<table align="center" width="567" height="199" border="0"
	cellpadding="0" cellspacing="0">
	<tr>
		<td background="${ctx}/images/choosesendtype/table_bg.gif">
		<table align="center" width="567" height="35" border="0"
			cellpadding="0" cellspacing="0">
			<tr>
				<td><br>
			</tr>
			<tr>
				<td><input id="caseId" type="hidden" value="${caseId}" /></td>
			</tr>
			<s:iterator value="items" var="type">
				<tr>
					<td style="cursor:crosshair"><input onclick="fastsend(${type.id})" type="button"
						value="${type.name}"
						style="width:555px; height:35px;  border: 0px; background-image: url(${ctx}/images/choosesendtype/01.gif);font-size:16px;color:#000" />
					</td>
				</tr>
				<tr>
					<td><br>
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td><br>
				</td>
			</tr>
			<tr>
				<td><input type="button" value="其他派遣" onclick="send()"
					style="width:555px; height:35px; border: 0px; background-image: url(${ctx}/images/choosesendtype/01.gif);font-size:16px;color:#000" />
				</td>
			</tr>
			<tr>
				<td><br>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<table align="center" width="567" border="0" cellspacing="0"
	cellpadding="0">
	<tr>
		<td><img src="${ctx}/images/choosesendtype/table_botom.gif"
			width="567" height="18" /></td>
	</tr>
</table>
</body>
</html>