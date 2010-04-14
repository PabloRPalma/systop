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
</head>
<body>

<s:iterator value="items" var="rrecord">
<div style="font-size:12px;width:100px;float:left;display:inline;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
		<a href="#" onclick="view('${rrecord.id}')">${rrecord.notice.title}</a>
</div>
</s:iterator>
</body>
</html>