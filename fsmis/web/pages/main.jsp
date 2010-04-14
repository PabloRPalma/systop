<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title></title>
<%@ taglib prefix="stc" uri="/systop/common" %>
</head>
<body>
	<div style="padding: 50 10 10 100;">
		信息提示
	</div>
	<iframe src="${ctx}/fscaseCount/getCaseCount.do" frameborder="0" scrolling="no" style="width:280px;height:170px;margin-left:15px" ></iframe>
</body>
</html>