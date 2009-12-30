<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>风险评估申请表 </title>
<style type="text/css">
<!--
.STYLE2 {
	font-size: 24px;
	font-weight: bold;
}

table{
	border-top:1px solid black;
	border-left:1px solid black;
}

td{
	border-bottom:1px solid black;
	border-right:1px solid black;
	padding:7px 3px 3px 3px;
}
.input{
	border-width:0;
}
@media print{
.noprint{display:none}
}
-->
</style>
</head>
<body>
<object ID='WebBrowser' WIDTH="0" HEIGHT="0" CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></object> 
<p align="right">
<input type=button value=打印 onClick="document.all.WebBrowser.ExecWB(6,6)" class="button noprint">
<input type=button value=设置 onClick="document.all.WebBrowser.ExecWB(8,1)" class="button noprint">
<input type=button value=预览 onClick="document.all.WebBrowser.ExecWB(7,1)" class="button noprint">
<input type=button value=关闭 onClick="if(confirm('确认关闭本窗体吗？'))window.close();" class="button noprint">
</p>
<br>
<table width="640" border="0" align="center" style="border:0px;">
  <tr>
    <td align="center" style="padding:5px 5px 10px 5px; border:0px">
	  <span class="STYLE2">风险评估申请表</span>
	</td>
  </tr>
</table>

<table width="640" height="481" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px">
  <tr>
  	<s:hidden id="id" name="model.id" />
    <td height="37" colspan="2" align="right">申&nbsp;&nbsp;请&nbsp;&nbsp;人：</td>
    <td width="299">&nbsp;&nbsp;${model.proposer.name}</td>
    <td width="87" align="right">申请时间：</td>
    <td width="175">&nbsp;
    	<fmt:formatDate value="${model.askDate}"  pattern="yyyy年MM月dd日"/>
    </td>
  </tr>
  <tr>
    <td height="38" colspan="2" align="right">评估事件：</td>
    <td>&nbsp;${model.fsCase.title}</td>
    <td align="right">事件类型：</td>
    <td>&nbsp;&nbsp;
        <c:if test="${empty model.fsCase.isMultiple}">
                                          单体事件
        </c:if>
        <c:if test="${!empty model.fsCase.isMultiple}">
                                          多体事件
        </c:if> 	
    </td>
  </tr>  
  <tr>
    <td colspan="2" align="center" valign="middle"><p>申请原因：</p>    </td>
    <td height="400" colspan="3" align="left" valign="top" style="word-break:break-all;">
    	&nbsp;
    	${model.askCause}    
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">领导批示：</td>
    <td height="200" colspan="3">&nbsp;&nbsp;${checkResult.result}</td>
  </tr>
</table>
</body>
</html>