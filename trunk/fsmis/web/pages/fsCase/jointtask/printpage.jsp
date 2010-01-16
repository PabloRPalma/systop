<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>联合整治申请表 </title>
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

<table width="800" border="0" align="center" style="border:0px;">
  <tr>
    <td align="center" style="padding:5px 5px 10px 5px; border:0px">
	  <span class="STYLE2">联合整治申请表</span>
	</td>
  </tr>
</table>
<c:if test="${model.fsCase.isMultiple eq '1'}"> 
<table width="800" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:10px">
  <tr>
    <td width="120" align="right">事件标题：</td>
    <td colspan="3"><input type="text" class="input" style="width: 400px" value="${model.fsCase.title}"/></td>
    
  </tr>
  <tr>
    <td width="120" align="right">开始时间：</td>
    <td width="150"><input type="text" class="input" value="${model.fsCase.beginDate}"/></td>
    <td width="120" align="right">结束时间：</td>
    <td width="150"><input type="text" class="input" value="${model.fsCase.endDate}" /></td>
  </tr>
  <tr>
    <td width="120" align="right">任务标题：</td>
    <td colspan="3"><input type="text" class="input" style="width: 400px" value="${model.title}" /></td>
  </tr>
  <tr>
    <td width="120" align="right">任务描述：</td>
    <td  colspan="3" >
    	<textarea style="border-width:0px; overflow-y:hidden;overflow:auto; width:470px; height:100px;">${model.descn}</textarea>
    </td>
  </tr>
  <tr>
    <td width="120" align="right">任务派送时间：</td>
    <td colspan="3"><input type="text" class="input" style="width: 400px" value="${model.createDate}"/></td>
  </tr>
  <tr>
    <td width="120" align="right">领导批示：</td>
    <td colspan="3">
        <textarea style="border-width:0px; overflow-y:hidden;overflow:auto; width:470px; height:140px;">${checkResult.result}</textarea>
    </td>
  </tr>
</table>
</c:if>
<c:if test="${model.fsCase.isMultiple eq '0'}"> 
  <table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="120" align="right">事件标题：</td>
    <td colspan="3"><input type="text" class="input" style="width: 400px" value="${model.fsCase.title}"/></td>   
  </tr>
  <tr>
    <td width="120" align="right">事件类别：</td>
    <td width="150"><input type="text" class="input" value="${model.fsCase.caseType.name}"/></td>
    <td width="120" align="right">事发时间：</td>
    <td width="150"><input type="text" class="input" value="${model.fsCase.caseTime}" /></td>
  </tr>
  <tr>
    <td width="120" align="right">任务标题：</td>
    <td colspan="3"><input type="text" class="input" style="width: 400px" value="${model.title}" /></td>
  </tr>
  <tr>
    <td width="120" align="right" >任务描述：</td>
    <td  colspan="3" >
    	<textarea style="border-width:0px; overflow-y:hidden;overflow:auto; width:470px; height:140px;">${model.descn}</textarea>
    </td>
  </tr>
  <tr>
    <td width="120"  align="right">任务派送时间：</td>
    <td colspan="3"><input type="text" class="input" style="width: 400px" value="${model.createDate}"/></td>
  </tr>
  <tr>
    <td width="120" align="right">领导批示：</td>
    <td colspan="3">
       <textarea style="border-width:0px; overflow-y:hidden;overflow:auto; width:470px; height:140px;">${checkResult.result}</textarea>    
    </td>
  </tr>
</table>
</c:if>
</body>
</html>