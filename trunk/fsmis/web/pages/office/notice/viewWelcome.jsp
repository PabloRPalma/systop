<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.systop.fsmis.model.Document" %>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>通知信息列表</title>
<script type="text/javascript">
function view(aID){
	url = "view.do?model.id=" + aID;
	window.open(url);
}
</script>
<style type="text/css">
a:link {
	color: #000000;
    text-decoration:none;
}
a:visited {
	color: #000000;
    text-decoration:none;
}
a:hover{
    color:#FF6600;
	text-decoration:none;
	}
a:actvie{
    color:#FF6600;
	text-decoration:none;
	}
</style>
</head>
<body>

<s:iterator value="items" var="rrecord">
<div>
	<div style="font-size:12px;display: inline;width:100px;white-space: nowrap;overflow:hidden;text-overflow:ellipsis;">
			<a href="#" onclick="view('${rrecord.id}')">${rrecord.notice.title}</a>
	</div>
	<div style="font-size:12px;display: inline;width:59px;white-space: nowrap;overflow:hidden;text-overflow:hidden;">
		${rrecord.notice.createTime}
	</div>
</div>
</s:iterator>
</body>
</html>