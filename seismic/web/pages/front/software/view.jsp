<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title>软件详细信息</title>
<style type="text/css">
<!--
.style1 {	color: #FF0000;
	font-weight: bold;
}
-->
</style>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<br>
<table width="650px" border="0" cellspacing="1" cellpadding="3" bgcolor="#6CA1D7" align="center">
    <tr> 
		<td height="28" colspan="2" align="center" bgcolor="#DFE8F6" class="textm">
			<strong>${model.name} </strong>
		</td>
    </tr>
    <tr bgcolor="#FFFFFF"> 
		<td width="103" height="25" align="center">
			<strong>版　　本</strong>
		</td>
		<td width="760" height="25">&nbsp; 
			${model.softVersion}
		</td>
    </tr>
    <tr bgcolor="#FFFFFF"> 
	     <td align="center" height="25">
	     	<strong>授权方式</strong>
	     </td>
	     <td width="760" height="25" style="padding:8px">
	      	${model.authorization }
	     </td>
	</tr>
    <tr bgcolor="#FFFFFF"> 
		<td width="103" height="25" align="center">
			<strong>软件大小</strong>
		</td>
		<td width="760" height="25">&nbsp; 
			${model.size}&nbsp;KB
		</td>
    </tr>
	<tr bgcolor="#FFFFFF"> 
		<td align="center" height="25">
	     	<strong>下载次数</strong>
		</td>
		<td width="760" height="25" style="padding:8px">
			${model.downCount } 次
		</td>
    </tr>
    <tr bgcolor="#FFFFFF"> 
		<td align="center" height="25">
	     	<strong>下载地址</strong>
		</td>
		<td width="760" height="25" style="padding:8px">
			<a href="down.do?model.id=${model.id}">
				<img src="${ctx}/images/icons/down.gif"><font style="color: green; font-weight: bold;">点这里进行下载</font>
			</a>
		</td>
    </tr>
	<tr bgcolor="#FFFFFF"> 
	     <td align="center" height="25">
	     	<strong>软件介绍</strong>
	     </td>
	     <td width="760" height="25" style="padding:8px">
	      	${model.introduction }
	     </td>
	</tr>
	<tr bgcolor="#FFFFFF"> 
	     <td align="center" height="25">
	     	<strong>备　　注</strong>
	     </td>
	     <td width="760" height="25" style="padding:8px">
	      	${model.descn }
	     </td>
	</tr>
    <tr bgcolor="#FFFFFF"> 
      <td colspan="2" align="center" height="28">
      	<input type="button" class="button" value="关闭" onclick="javascript:window.close()">
      </td>
    </tr>
</table>
</body>
</html>