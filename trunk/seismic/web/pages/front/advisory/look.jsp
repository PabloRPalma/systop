<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<title>反馈内容查看</title>
<style type="text/css">
<!--
.style1 {	color: #FF0000;
	font-weight: bold;
}
-->
</style>
</head>
<body>
<br>
<table width="650px" border="0" cellspacing="1" cellpadding="3" bgcolor="#6CA1D7" align="center">
    <tr> 
      <td height="28" colspan="2" align="center" bgcolor="#DFE8F6" class="textm"><strong>详 细 信 息 </strong></td>
    </tr>
    <tr bgcolor="#F7F7F7"> 
      <td width="103" height="25" align="center">
      	<strong>标　　题</strong>
      </td>
      <td width="760" height="25">&nbsp; 
      	${model.title }
      </td>
    </tr>
    <tr bgcolor="#F7F7F7"> 
      <td width="103" height="25" align="center">
      	<strong>提交时间</strong>
      </td>
      <td width="760" height="25">&nbsp; 
      	<s:date name="model.creatDate" format="yyyy-MM-dd"/>
      </td>
    </tr>
    <tr bgcolor="#F7F7F7"> 
     <td align="center" height="25"><strong>内　　容</strong></td>
      <td width="760" height="25" style="padding:8px">
      	${model.content }
      </td>
    </tr>
    <tr bgcolor="#F7F7F7">
      <td height="25" colspan="2" class="textm"><div align="center"><strong>回 复 信 息</strong></div></td>
    </tr>
    <tr bgcolor="#F7F7F7">
      <td align="center" height="25">
      	<strong>回复日期</strong>
      </td>
      <td height="25">&nbsp;
      	<s:date name="model.reDate" format="yyyy-MM-dd"/>
      </td>
    </tr> 
    <tr bgcolor="#F7F7F7">
      <td align="center" height="25">
      	<strong>回复内容</strong>
      </td>
      <td height="25" style="padding:8px">
      	${model.reContent}
      </td>
    </tr>   
    <tr bgcolor="#F7F7F7"> 
      <td colspan="2" align="center" height="28">
      	<input type="button" class="button" value="关闭" onclick="javascript:window.close()">
      </td>
    </tr>
</table>
</body>
</html>