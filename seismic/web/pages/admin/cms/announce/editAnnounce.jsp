<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/AnnounceDwrAction.js"></script>
<script type="text/javascript" src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
<title>网站公告管理</title>
</head>
<body onload="preFckEditor()">
<div class="x-panel">
    <div class="x-panel-header">网站公告管理</div>
    <div class="x-toolbar">
	    &nbsp;
    </div>
	<div><%@include file="/common/messages.jsp"%></div> 
</div>

<table width="100%" align="center">
<s:form namespace="/admin/announce" action="saveAnnounce" theme="simple" method="POST" validate="true"> 
	<s:hidden name="model.id"/>
	<s:hidden name="model.creatDate"/>
	<s:hidden name="model.author"/>
	<tr>
		<td bgcolor="#EBEBEB" align="right" width="80">标&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
		<td bgcolor="#EBEBEB">
		<s:textfield name="model.title" theme="simple" size="70" id="model.title" maxlength="70"/><font color="red">&nbsp;*</font>
		</td>
	</tr>
	<tr>
		<td align="right" bgcolor="#EBEBEB">内&nbsp;&nbsp;&nbsp;&nbsp;容：</td>
		<td bgcolor="#EBEBEB">
			<s:textarea id="model.content" name="model.content"></s:textarea>
		</td>
	</tr>
	<!--  
	<tr>
		<td align="right" bgcolor="#EBEBEB">发布人：</td>
		<td bgcolor="#EBEBEB">
		<s:textfield name="model.author" theme="simple" size="30" id="model.author" maxlength="30" readonly="true" /></td>
	</tr>
	-->
  <tr>
		<td align="right"  bgcolor="#EBEBEB">有效期：</td>
	<td bgcolor="#EBEBEB"><s:textfield name="model.outTime" theme="simple" maxlength="5" size="30" id="model.outTime" onkeyup="this.value=this.value.replace(/\D/g,'')" onblur="this.value=this.value.replace(/\D/g,'')"/>天 <span class="STYLE1"> <font color="red">*（为0时，表示永远有效）</font> </span></td> 
  </tr>
	<tr>
		<td align="right" bgcolor="#EBEBEB">显示类型：</td>
		<td bgcolor="#EBEBEB">
		<s:radio list="states" id="model.showType" name="model.showType" cssStyle="border:0"/> 
		</td>
	</tr>
	<tr>
		<td align="right" bgcolor="#EBEBEB">是否最新：</td>
		<td bgcolor="#EBEBEB">
		<input type="checkbox" id="model.isNew" name="model.isNew" value="1" ${model.isNew == 1?"checked":""}/>
		</td>
	</tr>
</table>
<table width="100%">
	<tr>
		<td align="center">
			<s:submit value="保存" cssClass="button"/>
			<s:reset value="重置" cssClass="button"/>
		</td>
	</tr>
</table>
</s:form>
<script type="text/javascript">
function preFckEditor(){
	var fckEditor = new FCKeditor( 'model.content' ) ;
    fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
    fckEditor.ToolbarSet = 'Default';
    fckEditor.Height = 440;
    fckEditor.ReplaceTextarea();
}
</script>
</body>
</html>

