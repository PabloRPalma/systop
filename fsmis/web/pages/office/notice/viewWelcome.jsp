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
	<s:iterator value="items" var="rrecord">
		<tr valign="top">
	      <td width="150">
	      	<div style="display: inline;width:130px;white-space: nowrap;overflow:hidden;text-overflow:ellipsis;">
				<img src="${ctx}/images/icons/124.gif">&nbsp;<a href="#" onclick="view('${rrecord.id}')">${rrecord.notice.title}</a>
			</div>
	      </td>
	      <td width="90" style="font-size:12px;">
	      	<s:date name="notice.createTime" format="yyyy-MM-dd" />
	      </td>
	    </tr>
	</s:iterator>
</table>
</body>
</html>