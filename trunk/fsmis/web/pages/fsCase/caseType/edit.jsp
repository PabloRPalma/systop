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
<div class="x-panel-header">类别</div>
<div><%@ include file="/common/messages.jsp"%></div> 
	<div align="center">
	<s:form  action="save.do" id="save" method="post" theme="simple" validate="true" enctype="multipart/form-data">
	<s:hidden name="model.id"/>
	<s:hidden name="model.caseType.id"/>
	<fieldset style="width:510px; padding:10px 10px 10px 10px;">
    	<legend>编辑类别</legend>
        <table width="510px" align="center" >
           <c:if test="${model.caseType.id != null}">
           <tr>
             <td align="right" width="90">父类名称：</td>
             <td align="left" width="300">
             	${parentName}
             </td>
           </tr>
           </c:if>
          <tr>
             <td align="right" width="90">名称：</td>
             <td align="left" width="300">
             	<s:textfield id="name" name="model.name" cssStyle="width:300px" cssClass="required"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
           <tr>
             <td align="right" width="90">描述：</td>
             <td align="left" width="300">
             	<s:textfield id="descn" name="model.descn" cssStyle="width:300px;height:150px"/>
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