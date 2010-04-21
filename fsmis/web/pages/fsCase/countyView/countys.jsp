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
<div style="width: 850px;">
<c:forEach items="${deptList}" var="dept">
	<div class="block" style="width:140px; float: left; margin-left: 10px;">
		&nbsp;<img src="${ctx}/images/exticons/preview-right.gif" width="16" height="16">&nbsp;区县:${dept.name}
		<div style="width: 140px;">
    		<iframe width="140" height="90" src="${ctx}/fscase/countyView/deptCaseCount.do?model.county.id=${dept.id}" frameborder="0" scrolling="no"></iframe>
    	</div>
	</div>  
</c:forEach>
</div>

 
</body>
</html>