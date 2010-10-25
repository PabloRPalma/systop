<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript"
	src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
<%@include file="/common/validator.jsp"%>
<title></title>
</head>
<body onload="preFckEditor()">
<div class="x-panel">
<div class="x-panel-header">编辑用户注册说明</div>
<div class="x-panel-body"><s:form action="save" theme="simple"
	id="save" validate="true" method="POST" enctype="multipart/form-data">
	<s:hidden name="model.id" />
	<%@ include file="/common/messages.jsp"%>
	<table width="90%">
		<tr><td height="5"></td></tr>
		<tr><td width="5"></td>
			<td style="text-align: right;">标题：</td>
			<td style="text-align: left;"><s:textfield size="125" id="title"
				name="model.title" /></td>
		</tr>
		<tr><td></td>
			<td style="text-align: right;">说明：</td>
			<td style="text-align: left;"><s:textarea id="model.content"
				name="model.content" theme="simple" rows="5" cols="70"
				cssStyle="border:1px #cbcbcb solid"></s:textarea>
		</tr>
	</table>

	<table width="100%" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;">
				<s:submit value="保存" cssClass="button"></s:submit>&nbsp;&nbsp;
				<s:reset value="重置" cssClass="button"></s:reset></td>
		</tr>
	</table>

</s:form></div>
</div>
<script type="text/javascript">
function preFckEditor(){
	var fckEditor = new FCKeditor( 'model.content' ) ;
    fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
    fckEditor.ToolbarSet = 'Basic';
    fckEditor.Height = 400;
    fckEditor.ReplaceTextarea();
}
</script>
</body>
</html>