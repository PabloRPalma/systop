<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<meta http-equiv="refresh" content="300" url="${ctx}/jointtaskCount/getJointTaskCount.do"/>
<title></title>
</head>
<body>
<table width="250" border="0" align="center">
  
	    <tr>
	      <td width="130"><img src="${ctx}/images/exticons/bit.png">&nbsp;未接收联合任务</td>
	      <td width="70"><a href="${ctx}/jointTask/deptTaskDetail/deptTaskDetailIndex.do" target="main" style="color: red">${jointTaskCountNoReceive}</a></td>
	    </tr>
  
  </table>
</body>
</html>