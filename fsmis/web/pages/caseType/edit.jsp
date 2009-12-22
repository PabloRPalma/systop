<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/validator.jsp"%>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header"></div>
    <div class="x-toolbar">
	 &nbsp;
    </div>
	<div><%@ include file="/common/messages.jsp"%></div> 
	<div align="center">
	<s:form  action="save.do" id="save" method="post" theme="simple" validate="true" enctype="multipart/form-data">
	<s:hidden name="model.id"/>
	<fieldset style="width:510px; padding:10px 10px 10px 10px;">
    	<legend>编辑类别</legend>
        <table width="520px" align="center">
          <tr>
             <td align="right" width="90">选择上级类别：</td>
             <td align="left" width="420">
             	<s:select id="levelOne" list="levelOne" name="model.caseType.id" headerKey="" headerValue="请选择"  cssStyle="width:150px;border:0px"/>
             </td>
          </tr>
          <tr>
             <td align="right" width="90">名称：</td>
             <td align="left" width="420">
             	<s:textfield id="name" name="model.name" cssStyle="width:400px" cssClass="required"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
           <tr>
             <td align="right" width="90">描述：</td>
             <td align="left" width="420">
             	<s:textfield id="name" name="model.descn" cssStyle="width:400px;height:150px"/>
             </td>
          </tr>
          
        </table> 
    </fieldset>
    <table width="600px" style="margin-bottom:10px;">
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