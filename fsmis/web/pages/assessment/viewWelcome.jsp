<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>风险评估列表</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function view(aID){
	url = "view.do?model.id=" + aID;
	window.open(url);
}
</script>
</head>

<body>
	<s:iterator value="items" var="item">
		<div style="width:300px;float:left;display:inline;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
			<a href="#" style="width:200px;" onclick="view('${item.id}')">${item.fsCase.title}&nbsp;</a>
			<s:date name="askDate" format="yyyy-MM-dd" />
		</div><br>
	</s:iterator>
</body>
</html>
