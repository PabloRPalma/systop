<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/validator.jsp"%>
<script type="text/javascript">
function isRight(name) {
	var pattern = /(^[\u4e00-\u9fa5\da-zA-Z][\u4e00-\u9fa5\da-zA-Z ,]*[\u4e00-\u9fa5\da-zA-Z]$)|^[\da-zA-Z\u4e00-\u9fa5]$/;   
	if(!pattern.test(name)) {
		Ext.MessageBox.show({
			title :'提示',
			minWidth:260,
			msg : '请重新输入关键字，格式不正确!',
			buttons:Ext.MessageBox.OK,
			icon:Ext.MessageBox.INFO
		});
	}
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">多体事件汇总配置</div>
<div><%@ include file="/common/messages.jsp"%></div> 
	<div align="center">
	<s:form  action="save.do" id="save" method="post" theme="simple" validate="true">
	<s:hidden name="model.id"/>
	<s:hidden name="model.caseType.id"/>
	<fieldset style="width:510px; padding:10px 10px 10px 10px;">
    	<legend>编辑汇总配置</legend>
        <table width="510px" align="center" >
          <tr>
             <td align="right" width="90">级别：</td>
             <td align="left" width="300">
             	<s:select id="level" name="model.level" listKey="key" listValue="value" list="levelMap" headerKey="" headerValue="请选择配置级别" cssStyle="width:300px"/>
             </td>
          </tr>
          <tr>
             <td align="right" width="90"> </td>
             <td align="left" width="300">
             	<font color="red">级别：用以区分区县级和市级的不同汇总生成条件。</font>
             </td>
          </tr>
          <tr>
             <td align="right" width="90">关键字：</td>
             <td align="left" width="300">
             	<s:textfield id="name" name="model.keyWord" cssClass="required" cssStyle="width:300px" onblur="isRight(this.value)"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
          <tr>
             <td align="right" width="90"> </td>
             <td align="left" width="300">
             	<font color="red">关键字：汇总生成多体事件的单列事件标题所包含的内容；只允许输入字母、数字或中文，输入多个关键字时，每个关键字间需要用 "," 隔开(半角逗号)，如：事件,食品,肉类</font>
             </td>
          </tr>
           <tr>
             <td align="right" width="90">记录数：</td>
             <td align="left" width="300">
             	<s:textfield id="records" name="model.records" cssStyle="width:300px" cssClass="digits"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
             <tr>
             <td align="right" width="90"> </td>
             <td align="left" width="300">
             	<font color="red">记录数：汇总生成多体事件的单列事件个数；相同区县及事件类别的单列事件个数，等于或者大于此数时，汇总生成一起多体事件。</font>
             </td>
          </tr>
          <tr>
             <td align="right" width="90">天数：</td>
             <td align="left" width="300">
             	<s:textfield id="days" name="model.days" cssStyle="width:300px;" cssClass="digits"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
             <tr>
             <td align="right" width="90"> </td>
             <td align="left" width="300">
             	<font color="red">天数：从当前时间往前推算的天数；汇总单体事件的一个时间段。</font>
             </td>
          </tr>
        </table> 
    </fieldset>
    <table width="510px" style="margin-bottom:10px;">
		<tr>
			<td style="text-align:center;">
				<s:submit value="保存" cssClass="button" />
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
</body>
</html>