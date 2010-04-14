<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<meta http-equiv="refresh" content="300" url="${ctx}/fscaseCount/getCaseCount.do"/>
<title></title>
</head>
<body>
<table width="250" border="0" align="center">
  
	    <tr>
	      <td width="130"><img src="${ctx}/images/exticons/bit.png">&nbsp;未派遣单体事件</td>
	      <td width="70"><a href="${ctx}/fscase/index.do?status=0&isMultipleCase=0&isSubmited=0&isJointCase=0" target="main" style="color: red">${genericCasesCountNoSend}</a></td>
	    </tr>
	    <tr>
	      <td><img src="${ctx}/images/exticons/bit.png">&nbsp;未派遣多体事件</td>
	      <td><a href="${ctx}/fscase/index.do?status=0&isMultipleCase=1&isSubmited=0&isJointCase=0" target="main" style="color: red">${multipleCasesCountNoSend}</a></td>
	    </tr>
	    <tr>
	      <td><img src="${ctx}/images/exticons/bit.png">&nbsp;未派遣联合整治事件</td>
	      <td><a href="${ctx}/fscase/index.do?status=0&isMultipleCase=0&isSubmited=0&isJointCase=1" target="main" style="color: red">${jointCasesCountNoSend}</a></td>
	    </tr>


   
  </table>
</body>
</html>