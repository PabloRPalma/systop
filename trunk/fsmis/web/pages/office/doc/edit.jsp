<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>文章管理</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
</head>
<body onload="preFckEditor()">
<div class="x-panel">
<div class="x-panel-header">添加文章信息</div>
<div align="center">
<s:form action="save.do" id="save" method="post" theme="simple" validate="true">
<fieldset style="width: 800px; padding-top: 2px">
  <legend>添加文章信息</legend>
	<s:hidden name="model.id"/>
	<table width="800px" align="center" border="0" cellpadding="3" cellspacing="3">
		<tr>
			<td align="right" width="70">文章标题：</td>
			<td align="left">
				<s:textfield id="title" name="model.title" cssStyle="width:450px" cssClass="required"/>
			</td>
		</tr>
		<tr>
			<td align="right" width="70">作者：</td>
			<td align="left">
				<s:textfield id="author" name="model.author" cssStyle="width:100px" />
			</td>
		</tr>
		<tr>
			<td align="right" width="70">所属栏目：</td>
			<td align="left">
				<s:select name="model.documentType.id" listKey="id" listValue="name" list="DocumentTypeMap" headerKey=""  headerValue="选择栏目..." cssStyle="width:200px"></s:select>
			</td>
		</tr>
		<tr>
			<td align="right">内容：</td>
			<td align="left">
			<s:textarea id="content" name="model.content" cssStyle="width:550px;" />
		</tr>
	</table>
</fieldset>
<table width="500" align="center">
	<tr>
		<td style="text-align: center;" colspan="2">
			<s:submit value="保存" cssClass="button"/> 
			<s:reset value="重置" cssClass="button"/>
		</td>
	</tr>
</table>
</s:form>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#save").validate();
});
</script>
<script type="text/javascript">
function preFckEditor(){
	var fckEditor = new FCKeditor( 'content' ) ;
    fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
    fckEditor.ToolbarSet = 'Default';
    fckEditor.Height = 350;
    fckEditor.ReplaceTextarea();
}
</script>
</body>
</html>