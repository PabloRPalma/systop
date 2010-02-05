<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title>单体事件核实信息确认</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/validator.jsp"%>
<script type="text/javascript">
	//去掉字符串两端的空格
	String.prototype.trim = function(){
	  return this.replace(/^\s*/,"").replace(/\s*$/,"");
	}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">核实信息审查</div>
<div align="center" style="margin-top:90px">
<s:form action="confirmCheckedMsg.do" id="confirmCheckedMsg" method="post" validate="true" >  
  <s:hidden name="model.id"/>
  <input type="hidden" name="modelId" value="${param['modelId'] }"></input>
  <input type="hidden" name="isMultipleCase" value="${isMultipleCase}"></input>
  <fieldset style="width: 700px; padding-top: 10px">
  <legend>单体事件核实确认信息</legend>
  <c:if test="${empty param.operType}">
  <table  border="0" cellpadding="0" cellspacing="0" style="padding-top: 10px">
  		<tr>
  			<td height="200px" valign="top" align="center" colspan="3">
  				<iframe height="210px"   src="${ctx}/smsreceive/checkedMsgIndex.do?model.fsCase.id=${param['model.id']}" width="800px" name="backMsges"></iframe> 			
  			</td>  			
  		</tr>
  </table>
  </c:if>
  <table width="750px">
        <tr>
  			<td width="120px" align="right"> 
  				<c:if test="${empty param.operType || param.operType == 'M'}">请填写</c:if>核实意见：
  			</td>
  			<td width="400px">  
  			<c:if test="${param.operType != 'V'}">				
  				<s:textarea id="checkedConfirmMsg" name="model.checkedConfirmMsg" cssClass="required" cssStyle="width:320px;height:80px" value="%{checkedConfirmMsg}"/><font color="red">&nbsp;*</font>
  			</c:if> 
  			<c:if test="${param.operType == 'V'}">	
  			    ${model.checkedConfirmMsg}
  			</c:if>
  			</td>
			<td width="60px" align="right">核实结果：</td>
			<td align="left" >	
			        <c:if test="${param.operType != 'V'}">	
					<select name="checked">
						<option value="1">同意</option>
						<option value="0">不同意</option>
					</select>
					</c:if> 
					<c:if test="${param.operType == 'V'}">	
					           同意
					</c:if>
			</td>
  		</tr>
  		<tr>
  		     <td style="text-align: center;" height="50" colspan="4">
  					<c:if test="${empty param.operType || param.operType == 'M'}">
	  					<s:submit value="保存"  cssClass="submit button" />  					
	  					<s:reset value="重置" cssClass="button"/>
  					</c:if>  					 
      	 			<input type="button" value="返回" class="button" onclick="location.href='index.do?modelId=0&isMultipleCase=${isMultipleCase}'"/>  			
  			</td>
  		</tr>
  	</table>
</fieldset>
</s:form>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#confirmCheckedMsg").validate();
});
</script>
</body>
</html>