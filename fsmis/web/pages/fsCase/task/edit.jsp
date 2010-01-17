<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>派遣任务</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/validator.jsp"%>
<script type="text/javascript"	src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
<script type="text/javascript" language="javascript"	src="${ctx}/scripts/fsmis/attachments.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/fsmis/ShowDeptName.js"></script>
<script type="text/javascript">
function deleAtt(id) {
		if (confirm("确定要删除吗？")) {
			deleteDiv('att');
			NoticeDwrAction.removeAtt(id, function(msg) {
				if (msg == 'success') {
					alert('删除成功！');
					document.getElementById('model.att').value = "";
				} else if (msg == 'error') {
					alert('附件删除不成功。');
				}
			});
		}
	}
	function deleteDiv(attachmentId) {
		document.getElementById(attachmentId).style.display = 'none';
		document.getElementById(attachmentId).innerHTML = "";
	}
	//附件操作初始化
	util = new fileUtil();
	util.style = "width:300px;";
	util.deleteIcon = "${ctx}/images/exticons/cross.gif";
</script>
</head>
<body onload="preFckEditor()">
<div class="x-panel">
<div class="x-panel-header">协调指挥&nbsp;&gt;&nbsp;${isMultipleCase eq '0' ? '一般':'综合'}案件管理&nbsp;&gt;&nbsp;事件列表&nbsp;&gt;&nbsp;事件查看&nbsp;&gt;&nbsp;派遣任务</div>
<div><%@ include file="/common/messages.jsp"%></div>
<div align="center"><s:form action="/task/save.do" method="post"
	theme="simple" enctype="multipart/form-data" validate="true"
	id="taskForm">
	<input type="hidden" name="model.fsCase.id" value="${model.fsCase.id}"></input>
	<input type="hidden" name="modelId" value="${param['modelId'] }"></input>
	<input type="hidden" name="isMultipleCase" value="${isMultipleCase}"></input>
	<s:hidden name="model.id"></s:hidden>
	<s:hidden name="isMultiple"></s:hidden>
	<fieldset style="width: 700px; padding: 10px 10px 10px 10px;">
	<legend> 派遣信息</legend>
	<table width="650px" align="left">
		<tr>
			<td align="right">任务标题：</td>
			<td align="left"><input type="text" name="model.title"
				value="${model.fsCase.title }" style="width: 400px;"
				class="required"></input> <font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">执行部门:</td>
			<td align="left">
			<s:hidden id="selectDeptNames" name="selectDeptNames"></s:hidden>
			<div id="showDiv"
				style="border-bottom: 1px dotted #97B7E7; padding: 2 7 2 7;"></div>
			<fs:selectDepts name="deptIds"  column="4" sendTypeId="${sendTypeId}"
				onclick="show()" itemClass="checkbox" /></td>
		</tr>
		<tr>
			<td align="right" width="180">规定完成时间：</td>
			<td align="left" width="500"><input type="text"
				name="model.presetTime"
				value='<s:date name="model.presetTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate required" /><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right" width="180">任务描述：</td>
			<td align="left" width="600"><s:textarea id="desc"
				name="model.descn" 
				cssStyle="width:600px; height:100px;" cssClass="required" /><font
				color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right" width="180">附件：</td>
			<td style="padding: 10 5 2 5;" width="500"><a href="#"
				onclick='util.add("fileUpload");'> <img
				src="${ctx}/images/icons/file_add.gif">增加附件</a></td>
		</tr>
		<tr>
			<td></td>
			<td colspan="2" width="500">
			<table>
				<tbody id="fileUpload"></tbody>
			</table>
			</td>
		</tr>
	</table>
	</fieldset>
	<table width="600px" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;"><s:submit value="派遣任务"
				cssClass="button" /> <s:reset value="重置" cssClass="button" /></td>
		</tr>
	</table>
</s:form></div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$("#taskForm").validate();
	});
	deptOperator.init("deptIds", "showDiv");

	function show() {
		deptOperator.showDeptName("deptIds", "showDiv");
	}
</script>
<script type="text/javascript">
	//文本编辑组件
	function preFckEditor() {
		var fckEditor = new FCKeditor('desc');
		fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
		fckEditor.ToolbarSet = 'BasicA';
		fckEditor.Height = 200;
		//fckEditor.width = 500;
		fckEditor.ReplaceTextarea();
	}
</script>
</body>
</html>