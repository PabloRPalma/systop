<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title></title>
<%@ taglib prefix="stc" uri="/systop/common" %>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/welcome.css"/>
</head>
<body>
<c:forEach items="${deptList}" var="dept">
<div class="col">
  <div class="block">
     <table width="100%" class="toptdborder">
	   <tr>
		 <td class="block-title">
		   <img src="${ctx}/images/exticons/preview-right.gif" width="16" height="16">&nbsp;区县名称:${dept.name}
		 </td>
		 <td class="block-title"  align="right">
		   <a href="${ctx}/fscaseCount/getCaseCount.do" target="main">
			  <img src="${ctx}/images/icons/more2.gif" title="查看更多..." width="14" height="14">
		   </a>&nbsp;&nbsp;
		 </td>
	   </tr>
	 </table>
     <div class="block-body">
       <iframe src="${ctx}/fscase/countyView/deptCaseCount.do?model.county.id=${dept.id}" frameborder="0" scrolling="no" style="margin-left:-20px;height: 120px;"></iframe>
     </div>
   </div>  
 </div>
</c:forEach>

 
</body>
</html>