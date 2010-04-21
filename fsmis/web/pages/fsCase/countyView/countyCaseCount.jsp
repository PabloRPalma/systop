<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<title></title>
</head>
<body style="margin-top:3px;">
	<table width="140" border="0" align="center">  
	    <tr>
	      <td>
	        <img src="${ctx}/images/exticons/bit.png">&nbsp;单体事件
	        <a href="${ctx}/fscase/countyView/index.do?model.county.id=${model.county.id}&model.isSubmited=0&model.isJointCase=0&isMultipleCase=0&modelId=0" target="main" style="color: red">${singleFsCaseCount}</a>
	       </td>
	    </tr>
	    <tr>
	      <td>
	        <img src="${ctx}/images/exticons/bit.png">&nbsp;多体事件
	        <a href="${ctx}/fscase/countyView/index.do?model.county.id=${model.county.id}&isMultipleCase=1&model.isSubmited=0&model.isJointCase=0&modelId=0" target="main" style="color: red">${multipleFsCaseCount}</a>
	       </td>
	    </tr>
	    <tr>
	      <td>
	        <img src="${ctx}/images/exticons/bit.png">&nbsp;联合整治事件
	        <a href="${ctx}/fscase/countyView/index.do?model.county.id=${model.county.id}&model.isSubmited=0&isMultipleCase=0&model.isJointCase=1" target="main" style="color: red">${joinFsCaseCount}</a>
	      </td>
	    </tr> 
	</table>
</body>
</html>