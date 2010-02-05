<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<style type="text/css">
<!--

@media print{
.noprint{display:none}
}
-->
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<object ID='WebBrowser' WIDTH="0" HEIGHT="0" CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></object> 
<title>食品安全问题投诉登记表</title>
<style>
	.tdBottom{
		border-bottom:2px solid black;
		padding:8px 5px 6px 5px;
	}
</style>
</head>
<body>
<p align="right">
<input type=button value=打印 onClick="document.all.WebBrowser.ExecWB(6,6)" class="button noprint">
<input type=button value=设置 onClick="document.all.WebBrowser.ExecWB(8,1)" class="button noprint">
<input type=button value=预览 onClick="document.all.WebBrowser.ExecWB(7,1)" class="button noprint">
</p>
<br/>
<table width="650" border="0" align="center" cellpadding="0" cellspacing="0" >
  <tr>
    <td colspan="4" align="center"><h2>食品安全问题投诉登记表</h2></td>
  </tr>
  <tr>
    <td colspan="4" align="right" class="tdBottom">编号：(　　　　)号&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" style="padding:7px 5px 6px 5px;">&nbsp;&nbsp;&nbsp;&nbsp;举报人：${model.task.fsCase.informer}</td>
  </tr>
  <tr>
  	<td colspan="4" style="padding:7px 5px 6px 5px;">&nbsp;&nbsp;&nbsp;&nbsp;电话：${model.task.fsCase.informerPhone}</td>
  </tr>
  <tr>
    <td colspan="4" class="tdBottom">&nbsp;</td>
  </tr>
   <tr>
    <td width="270" class="tdBottom">
		&nbsp;&nbsp;
		报告、通报、举报时间：</td>
    <td width="300" class="tdBottom"><s:date name="model.task.fsCase.caseTime" format="yyyy-MM-dd HH:mm"/></td>
    <td width="50" align="right" class="tdBottom">方式：</td>
    <td width="50" class="tdBottom">&nbsp;</td>
   </tr>
   <tr>
    <td colspan="4" style="padding:7px 5px 6px 5px;">&nbsp;&nbsp;&nbsp;&nbsp;事件内容：</td>
   </tr>
  <tr>
    <td colspan="4" style="padding:7px 5px 6px 5px;" height="200">&nbsp;&nbsp;&nbsp;&nbsp;${model.task.fsCase.descn}</td>
  </tr>
   <tr>
     <td width="270" class="tdBottom">&nbsp;</td>
     <td width="250" class="tdBottom">&nbsp;</td>
    <td width="100"  class="tdBottom" align="right">受理人：</td>
    <td width="50" class="tdBottom">&nbsp;</td>
  </tr>
   <tr>
    <td width="211" class="tdBottom">
		&nbsp;&nbsp;
		任务派送时间：</td>
    <td width="282" class="tdBottom"><s:date name="model.task.dispatchTime" format="yyyy-MM-dd HH:mm"/></td>
    <td width="93" align="right" class="tdBottom">&nbsp;</td>
    <td width="196" class="tdBottom">&nbsp;</td>
  </tr>
   <tr>
    <td colspan="4" style="padding:7px 5px 6px 5px;">&nbsp;&nbsp;&nbsp;&nbsp;任务信息：</td>
  </tr>
  <tr>
    <td colspan="4" height="200">&nbsp;&nbsp;&nbsp;&nbsp;${model.task.descn}</td>
  </tr>
 <tr>
    <td colspan="4" class="tdBottom">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" style="padding:7px 5px 6px 5px;">&nbsp;&nbsp;&nbsp;&nbsp;拟办意见：</td>
  </tr>
  <tr>
    <td colspan="4" class="tdBottom" height="100">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" style="padding:7px 5px 6px 5px;">&nbsp;&nbsp;&nbsp;领导批示：</td>
  </tr>
  <tr>
    <td colspan="4" class="tdBottom" height="150">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" style="padding:10px 5px 5px 5px;" align="right">
		年　　月　　日&nbsp;&nbsp;	</td>
  </tr>
</table>

</body>
</html>
