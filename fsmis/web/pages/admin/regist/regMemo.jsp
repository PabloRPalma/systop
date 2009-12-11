<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>

<style type="text/css">
<!--
.menu {
	font-size: 14px;
	font-weight: bold;
	color: #286e94;
}
-->
</style>

<script type="text/javascript">
  function addBookmark() {
     if (window.sidebar) { 
         window.sidebar.addPanel(document.title, location.href,""); 
     } else if( document.all ) {
         window.external.AddFavorite( location.href, document.title);
     } else if( window.opera && window.print ) {
         return true;
     }
  }
</script>
<title>用户注册须知</title>
</head>
<body>
<%@include file="/common/top.jsp" %>
<table align="center" border="0" cellspacing="0" cellpadding="0" width="1003">
	<tr>
    <td width="1003" height="35" align="left" valign="middle" background="${ctx}/ResRoot/index/images/index_02.gif">
    &nbsp;&nbsp;&nbsp;&nbsp;<span class="menu"><a href="${ctx}/index.shtml" style="color:#286e94">首页</a></span>
    &nbsp;&nbsp;&nbsp;<span class="menu"><a href="#" style="color:#286e94">用户注册</a></span>
  	</td>
  </tr>
  </table>
<div class="x-panel">
<div><%@ include file="/common/messages.jsp"%></div>
<table align="center" width="1003px" border="0" cellspacing="0" cellpadding="0"><tr><td>
<div class="x-panel-body">
<fieldset style="margin:20px;height: 450px;overflow:auto;">
	<legend>${regMemo.title }</legend>
	${regMemo.content }
</fieldset>
</div></td></tr>
</table>
<table width="100%" style="margin-bottom: 10px;">
  <tr>
	<td colspan="2" align="center" class="font_white">
		<input value="同　意" onclick="continueToReg()" size="6" style="text-align: center;cursor: auto;" type="button" class="button"/>
		&nbsp;&nbsp;
		<input value="不同意" size="6" onclick="javascript:history.go(-1)" style="text-align: center;cursor: auto;" type="button" class="button"/>
	</td>
  </tr>
</table>
</div>
<script type="text/javascript">
  /** 变更用户状态 */
  function continueToReg() {
    window.location = '${ctx}/regist/registNew.do';
  }
</script>

</body>
</html>