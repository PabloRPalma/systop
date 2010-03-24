<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title>用户反馈</title>
<script>
function mysubmit() {

var pass=document.getElementById("model.queryPass").value;
var passTwo=document.getElementById("queryPassTwo").value;

if (pass != passTwo) {
document.getElementById("model.queryPass").value="";
document.getElementById("queryPassTwo").value="";
alert("两次输入的查询密码不一致！");
}
}
</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<body>
<s:form action="save" theme="simple" method="POST" validate="true" >
<table width="555px" border="0" cellspacing="1" cellpadding="3" align="center">
	<tr>
      <td colspan="2" align="right"  style="border-bottom: 2px solid #6CA1D7; padding: 5 15 5 5;">
      	<a href="list.do" title="查看全部">
      		<img src="${ctx}/images/icons/zoom.gif"/>
      		查看全部反馈
      	</a>
      </td>
    </tr>
   <tr>
   	<td colspan="2">
   		<%@include file="/common/messages.jsp"%>
   	</td>
   </tr>
　　<tr>
    <td width="140px" align="right">标　　题：</td>
    <td width="415px">
		<s:hidden name="model.id"/>
	    <s:textfield name="model.title" id="model.title" maxlength="255" cssStyle="width:400px"/>
	    &nbsp;<span class="STYLE1">*</span> 
    </td>
  </tr>
  <tr>
    <td align="right">姓　　名：</td>
    <td>
    	<s:textfield name="model.name" maxlength="127"/>
        &nbsp;<span class="STYLE1">*</span>
    </td>
  </tr>
  <tr>
    <td align="right">联系电话：</td>
    <td>
    	<s:textfield name="model.phone" id="model.phone"/>
    </td>
  </tr>
  <tr>
    <td align="right">邮　　编：</td>
    <td>
    	<s:textfield name="model.posCode" id="model.posCode" onkeyup="this.value=this.value.replace(/\D/g,'')" onblur="this.value=this.value.replace(/\D/g,'')" maxlength="10"/>
    </td>
  </tr>
  <tr>
    <td align="right">联系地址：</td>
    <td>
    	<s:textfield name="model.address" id="model.address" size="40" maxlength="255" cssStyle="width:400px"/>
    </td>
  </tr>
  <tr>
    <td align="right" >内　　容：</td>
    <td>
    	<s:textarea name="model.content" cols="55" rows="7" wrap="virtual" id="model.content"/>&nbsp;<span class="STYLE1">*</span>
	</td>
  </tr>
  <tr>
    <td colspan="2" align="center" style="border-top: 2px solid #6CA1D7;">
      <s:submit value="保存" cssClass="button"/>
	  <s:reset value="重置" cssClass="button"/>
 	</td>
  </tr>
</table>
</s:form>
</body>
</html>