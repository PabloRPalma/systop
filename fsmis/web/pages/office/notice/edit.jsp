<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/common/taglibs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<html>
<head>
<title>编辑通知</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/dwr.jsp" %>

<script type="text/javascript" src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="${ctx}/dwr/interface/NoticeDwrAction.js"></script>
<script type="text/javascript" src="${ctx}/scripts/fsmis/ShowDeptName.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#save").validate();
});
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

	function preFckEditor(){
		var fckEditor = new FCKeditor( 'content' ) ;
	    fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	    fckEditor.ToolbarSet = 'BasicA';
	    fckEditor.Height = 280;
	    fckEditor.Width = 600;
	    fckEditor.ReplaceTextarea();
	}
	function show(){
		deptOperator.showDeptName("deptIds", "showDiv");
	}
	deptOperator.init("deptIds", "showDiv");
</script>
</head>
<body onload="preFckEditor()">
<div class="x-panel">
<div class="x-panel-header">编辑通知</div>
<div><%@ include file="/common/messages.jsp"%></div>
<div align="center">
  <s:form action="save.do" id="save" method="post" theme="simple" validate="true" enctype="multipart/form-data">
	<s:hidden name="model.id"/>
	<fieldset style="width: 700px; padding-top: 10px;">
	<legend>通知信息</legend>
	<table width="650px" align="center">
		<tr>
			<td align="right">标题：</td>
			<td align="left">
			<s:textfield id="title" name="model.title"	cssStyle="width:400px;" cssClass="required"/><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">发送部门：</td>
			<td align="left">
			
				<div id="showDiv" style="border-bottom: 1px dotted #97B7E7; padding: 2 7 2 7;"></div>
				<fs:selectDepts name="deptIds" column="4" onclick="show()" defDeptIds="${receDeptIds}" itemClass="checkbox"/>
		
			</td>
		</tr>
		<tr>
			<td align="right">内容：</td>
			<td align="left"><s:textarea id="content"  name="model.content"
				cssStyle="width:300px; height:100px;" /></td>
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
	<table width="650px" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;">
			<s:submit value="保存" cssClass="button" /> 
		    <s:reset value="重置" cssClass="button" />
		    </td>
		</tr>
	</table>
</s:form>
</div>
</div>
</body>
</html>