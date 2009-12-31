<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/dwr.jsp" %>
<%@include file="/common/validator.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="${ctx}/dwr/interface/NoticeDwrAction.js"></script>
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
</script>
</head>
<body onload="preFckEditor()">
<div class="x-panel">
<div class="x-panel-header">通知</div>
<div class="x-toolbar">&nbsp;</div>
<div><%@ include file="/common/messages.jsp"%></div>
<div align="center">
  <s:form action="save.do" id="save" method="post" theme="simple" enctype="multipart/form-data">
	<s:hidden name="model.id"/>
	<fieldset style="width: 650px; padding: 10px 10px 10px 10px;" class="">
	<legend>通知信息</legend>
	<table width="650px" align="center">
		<tr>
			<td align="right">标题：</td>
			<td align="left"><s:textfield id="title" name="model.title"
				cssStyle="width:400px;" cssClass="required"/><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">请点选发送部门：</td>
			<td align="left">
			<c:if test="${!empty model.recRecordses}">
				<c:forEach items="${model.recRecordses}" var="rr">
					【${rr.dept.name}】
				</c:forEach>
			</c:if>
			<c:if test="${empty model.recRecordses}">
			<div
				style="border: 1px solid #099EBD; OVERFLOW-Y: scroll; width: 400px; SCROLLBAR-ARROW-COLOR: #e8e8e8; SCROLLBAR-BASE-COLOR: #e8e8e8; HEIGHT: 90px">
			<fs:selectDepts name="deptIds" splitLineStyle="1px dotted blue;" itemClass="checkbox"/>
			</div>
			</c:if>
			</td>
		</tr>
		<tr>
			<td align="right">内容：</td>
			<td align="left"><s:textarea id="content"  name="model.content"
				cssStyle="width:400px; height:100px;" /></td>
		</tr>
		<tr>
			<td align="right">附件：</td>
			<td align="left">
					<s:file id="attachment" name="attachment" cssClass="FileText"/>
                    	<s:hidden id="model.att" name="model.att"/>
                    	<c:if test="${model.att != null && model.att != ''}">
                    	  <div id="att">
			              <a href="${ctx}${model.att}" target="_blank">
			                	查看附件
			              </a>
			              <a href="#" onclick="deleAtt(${model.id})">删除已有附件</a>
			              </div>
			            </c:if>
			</td>
		</tr>
	</table>
	</fieldset>
	<table width="600px" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;">
			<s:submit value="保存" cssClass="button" /> 
		    <s:reset value="重置" cssClass="button" />
		    </td>
		</tr>
	</table>
</s:form></div>
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