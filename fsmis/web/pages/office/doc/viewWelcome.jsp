<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.systop.fsmis.model.Document" %>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>文章信息列表</title>
<script type="text/javascript">
function view(aID){
	url = "view.do?model.id=" + aID;
	window.open(url);
}
</script>
<style type="text/css">
a:link {
	font-size:12px;
	color: #000000;
    text-decoration:none;
}
a:visited {
	font-size:12px;
	color: #000000;
    text-decoration:none;
}
a:hover{
	font-size:12px;
    color:#FF6600;
	text-decoration:none;
	}
a:actvie{
	font-size:12px;
    color:#FF6600;
	text-decoration:none;
	}
</style>
</head>
<body>
<table width="250" border="0" align="center">
	<s:iterator value="items" var="doc">
		<tr>
	      <td width="150">
	      	<div style="display:inline;width:130px;white-space: nowrap;overflow:hidden;text-overflow:ellipsis;">
				<img width="9" height="9" src="${ctx}/images/exticons/bit.png">&nbsp;<a href="#" onclick="view('${doc.id}')">${doc.title}</a> 
			</div>
		  </td>
		  <td width="90" style="font-size:12px;">
				<s:date name="createTime" format="yyyy-MM-dd" />
		  </td>
	</s:iterator>
</table>
</body>
</html>