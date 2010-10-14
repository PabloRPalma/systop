<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@include  file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>服务器内部错误</title>
</head>
<body bgcolor="#FFFFFF">

<div id="_title">
		<p>问题描述</p>
		<c:if test="${not empty exception}">
			<p>
				<h4><c:out value="${exception.details}" /></h4>
				
				<c:out value="${exception.message}" />
				<div id="_exception">
					<c:out value="${exception.localizedMessage}" />
				</div>
				
				<c:out value="${exception.stacktrace}" />
				<div id="_stacktrace">
					<c:forEach items="${exception.stackTrace}" var="trace">
						<c:out value="${trace}"/><br/>
					</c:forEach>
				</div>
			</p>
		</c:if>
</div>
</body>
</html>