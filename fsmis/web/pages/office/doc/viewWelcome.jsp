<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.systop.fsmis.model.Document" %>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>文章信息列表</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function view(aID){
	url = "view.do?model.id=" + aID;
	window.open(url);
}
</script>
</head>
<body>
<table width="250" border="0" align="center">
	<s:iterator value="items" var="doc">
		<tr valign="top">
		  <td><img src="${ctx}/images/exticons/bit.png">&nbsp;</td>
	      <td>
	      	<div style="display:inline;width:160px;white-space: nowrap;overflow:hidden;text-overflow:ellipsis;">
				<a href="#" title="${doc.title}" onclick="view('${doc.id}')">${doc.title}</a> 
			</div>
		  </td>
		  <td><s:date name="createTime" format="yyyy-MM-dd" /></td>
	</s:iterator>
</table>
</body>
</html>