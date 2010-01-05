<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; UTF-8">
<title>应急预案申请单</title>
<style>
	.tdBottom{
		border-bottom:2px solid black;
		padding:8px 5px 6px 5px;
	}
	.STYLE2 {
	font-size: 24px;
	font-weight: bold;
    }
    @media print{
    .noprint{display:none}
    }
</style>
</head>
<body>
<object ID='WebBrowser' WIDTH="0" HEIGHT="0"
	CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></object>
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
<tr><td align="right">
<input type=button value=打印
	onClick="document.all.WebBrowser.ExecWB(6,6)" class="button noprint">
<input type=button value=设置
	onClick="document.all.WebBrowser.ExecWB(8,1)" class="button noprint">
<input type=button value=预览
	onClick="document.all.WebBrowser.ExecWB(7,1)" class="button noprint">
<input type=button value=关闭
	onClick="window.close();" class="button noprint">
	</td></tr>
</table>
<br>
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td colspan="4" align="center"><h2>应急预案申请单</h2></td>
  </tr>
  <tr>
    <td colspan="4" height="20">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" align="right" class="tdBottom">编号：（　　　　　　）号</td>
  </tr>
  <tr>
    <td colspan="4" style="padding:7px 5px 6px 5px;">举报人\电话</td>
    
  </tr>
  <tr>
    <td colspan="4" style="padding:7px 5px 6px 5px;"><s:property value="model.reporter"/>\<s:property value="model.reporterPhone"/></td>
    
  </tr>
  <tr>
    <td colspan="4" class="tdBottom">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2"  style="padding:7px 5px 6px 5px;">事发时间：<s:property value="model.caseTime"/></td>     

    <td colspan="2"  style="padding:7px 5px 6px 5px;">事发地点：<s:property value="model.address"/></td>
      
  </tr>
   <tr>
    <td colspan="2"  style="padding:7px 5px 6px 5px;">受害人数：<s:property value="model.harmNum"/></td>

    <td colspan="2"  style="padding:7px 5px 6px 5px;">死亡人数：<s:property value="model.deathNum"/></td>
  </tr>
    <tr>
    <td colspan="4" class="tdBottom" style="padding:7px 5px 6px 5px;">建议预案等级：<s:property value="model.plansLevel"/></td>
  </tr>

  <tr>
    <td colspan="4" style="padding:7px 5px 6px 5px;">事件详情：</td>
  </tr>
  <tr>
    <td colspan="4"  style="padding:7px 5px 6px 5px;">${model.descn}	</td>
  </tr>
  
  <tr>
    <td colspan="4" height="200">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" class="tdBottom" align="left">受理人：　　　　&nbsp;&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" style="padding:7px 5px 6px 5px;">拟办意见：</td>

  </tr>
  <tr>
    <td colspan="4" class="tdBottom" height="100">&nbsp;</td>
  </tr>
  <tr>
    <td style="padding:7px 5px 6px 5px;">领导批示：</td>
    <td style="padding:7px 5px 6px 5px;">&nbsp;</td>
    <td style="padding:7px 5px 6px 5px;">&nbsp;</td>
    <td style="padding:7px 5px 6px 5px;">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" class="tdBottom" height="100">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="4" style="padding:10px 5px 5px 5px;" align="right">
		年　　月　　日&nbsp;&nbsp;	</td>
  </tr>
</table>
</body>
</html>
