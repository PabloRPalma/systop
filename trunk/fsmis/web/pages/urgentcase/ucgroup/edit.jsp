<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title>应急指挥组</title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/validator.jsp"%>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">应急指挥指挥组维护</div>
	<div><%@ include file="/common/messages.jsp"%></div> 
	<div align="center">
	<s:form id="ucGroupForm" action="save.do" method="post"  validate="true"  theme="simple">
	<s:hidden name="model.id"/>
	<s:hidden name="ucTypeId"/>
	<fieldset style="width:610px; padding:10px 10px 10px 10px;">
    	<legend>编辑指挥组</legend>
        <table width="600px" align="center">
          <tr>
             <td align="right" width="80">组名:</td>
             <td align="left" width="520">
             	<s:textfield id="name" name="model.name" cssStyle="width:300px" cssClass="required"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
            <tr>
             <td align="right" width="80">组类型:</td>
             <td align="left" width="520">
             	<s:select id="model.category" name="model.category" list="categoryMap" headerKey="" headerValue="请选择"  cssClass="required"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
          <!-- 外部组添加后，需要添加摩板
          <tr>
             <td align="right" width="80">类别:</td>
             <td align="left" width="520">
             	<s:select id="type" name="model.type" list="sortMap" cssStyle="border:0;" onchange="sortChange()"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
          <tr><td colspan="2">
          	<table id="tb_1"> 
          	
				<tr> <td align="right" width="80">模板路径:</td>
             		<td align="left" width="520">
             			<s:textfield id="template" name="model.template" cssStyle="width:300px"/>
             		</td> 
				</tr> 
			</table> 
			</td>
		</tr>
		-->
           <tr>
             <td align="right" width="80">显示内容：</td>
             <td align="left" width="520">
             	<s:textarea id="displays" name="model.displays" cssStyle="width:300px;height:50px"/>
             </td>
          </tr>
          
          <tr>
             <td align="right" width="80">描述：</td>
             <td align="left" width="520">
             	<s:textarea id="descr" name="model.descr" cssStyle="width:300px;height:50px"/>
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
	$("#ucGroupForm").validate();
});
	/**
	function sortChange() { 
		if(document.ucGroupForm.type.value=='0'){
			document.all("tb_1").style.display="block"; 
		}else{
			document.all("tb_1").style.display="none"; 
			}
	} 
	sortChange();
	*/
</script>
</body>
</html>