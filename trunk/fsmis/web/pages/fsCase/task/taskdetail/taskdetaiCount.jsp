<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<meta http-equiv="refresh" content="300" url="${ctx}/taskdetailCount/getTaskDetailCount.do"/>
<title></title>
</head>
<body>
<table width="250" border="0" align="center">
  
	    <tr>
	      <td width="130"><img src="${ctx}/images/exticons/bit.png">&nbsp;未接收单体任务</td>
	      <td width="70"><a href="${ctx}/taskdetail/index.do?status=0&isMultipleCase=0" target="main" style="color: red">${genericTaskCountNoReceive}</a></td>
	    </tr>
	    <tr>
	      <td><img src="${ctx}/images/exticons/bit.png">&nbsp;未接收多体任务</td>
	      <td><a href="${ctx}/taskdetail/index.do?status=0&isMultipleCase=1" target="main" style="color: red">${multipleTaskCountNoReceive}</a></td>
	    </tr>
  
  </table>
</body>
</html>