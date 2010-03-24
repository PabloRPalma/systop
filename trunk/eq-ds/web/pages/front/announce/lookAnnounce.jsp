<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/extSkin.jsp" %>
<%@include file="/common/ec.jsp" %>
<title>网站公告</title>
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


<table width="90%" border="0" cellspacing="1" cellpadding="3" bgcolor="#999999" align="center">
    <tr bgcolor="#F7F7F7"> 
      <td height="28" colspan="2" align="center" bgcolor="#FFFFCC" class="textm"><strong>详 细 信 息 </strong></td>
    </tr>
    <tr bgcolor="#F7F7F7"> 
      <td width="103" height="25" align="center"><strong>标　　题</strong></td>

      <td width="760" height="25">&nbsp; <s:property value="model.title" /> </td>
    </tr>
    <tr bgcolor="#F7F7F7"> 
     <td align="center" height="25"><strong>内　　容</strong></td>
      <td width="760" height="25" style="padding:8px">${model.content} </td>
    </tr>
    <tr bgcolor="#F7F7F7">
      <td align="center" height="25"><strong>发布人</strong></td>
      <td height="25" style="padding:8px"><s:property value="model.author" /></td>
    </tr>
        <tr bgcolor="#F7F7F7">
      <td align="center" height="25"><strong>发布时间</strong></td>
      <td height="25">&nbsp;<s:property value="model.creatDate" /></td>
    </tr>  
     <tr bgcolor="#F7F7F7">
      <td align="center" height="25"><strong>有效天数</strong></td>
      <td height="25" style="padding:8px"><s:property value="model.outTime" /></td>
    </tr>  <tr bgcolor="#F7F7F7"> 
      <td colspan="2" align="center" height="28"><input type="button" class="button" value="返回" onclick="window.history.go(-1)"></td>
    </tr>
</table>
</body>
</html>

