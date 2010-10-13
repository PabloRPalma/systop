<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title>上传文件错误</title>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">文件上传失败</div>
    <div class="x-toolbar">
    	<div style="width: 90%">
	    <%@ include file="/common/messages.jsp"%>
	    </div>
    	<br>
    	<div style="width: 90%">&nbsp;&nbsp;
	    	<a href="#" onclick="javascript:history.go(-1);">
	    	<img src="${ctx}/images/icons/arrow_turn_left.gif">返回上一页
	    	</a>
	    </div>
    </div>
</div>
</body>
</html>