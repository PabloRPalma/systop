<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/TemplateDwrAction.js"></script>
<script type="text/javascript" src="${ctx}/scripts/TabPanel/tabpanel.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/TabPanel/css/style.css" />
<title></title>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">模板管理</div>
    <div class="x-toolbar">
	    &nbsp;
    </div>
	<div><%@ include file="/common/messages.jsp"%></div> 
	<s:form id="templateForm" namespace="/admin/template" action="saveTemplate" method="post" theme="simple" validate="true">
	<table width="98%" border="0" align="center">
	  <tr><td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="TabBarLevel" id="TabPage">
	      <tr align="center">
	        <td width="130" id="SystopTab1" valign="middle" class="SelectedTab" onclick=switchTab(this,2)>
				基本信息
			</td>
	        <td width="7" class="Black">&nbsp;</td>
	        <td width="80" id="SystopTab2" valign="middle" class="mousehand" onclick=switchTab(this,2)>
	        	资源文件
			</td>
	        <td width="7" class="Black">&nbsp;</td>
	        <td width="7" class="Black">&nbsp;</td>
	
	        <td width="" align="right" class="Black">&nbsp;&nbsp;</td>
	      </tr>
	    </table>
 	    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Content">
	        <tr>
	          <td height="110">
                <div id='SystopTabDiv1'>
					 <%@include file="baseinfo.jsp"  %>
				</div>
	
				<div id='SystopTabDiv2' style=" display:none">
					 <iframe name="filesList" id="filesList" src="filesList.do" width="100%" height="490"
					 scrolling="yes" frameborder="0"></iframe>
				</div>
				
				<div id='SystopTabDiv3' style=" display:none">
					 <!-- nothing -->
				</div>
			  </td>
	        </tr>
	    </table>		
	  </td></tr>
	</table>
	<table width="98%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td align="center">
	    <!-- 
	    	<s:submit value="保存"></s:submit>
	    	<s:reset value="取消"></s:reset>
		 -->		
	    	<input type="button" class="button" value="保存" onclick="setSubmit()">  	
	    	<input type="button" class="button" value="取消" onclick="javaScript:history.go(-1)">  	    
	    </td>
	  </tr>
	</table>
	</s:form>
</div>

<script type="text/javascript">
	var form = document.getElementById("templateForm");
	function setSubmit(){
		
		form.submit();
	}
	
	function setReset(){
		form.reset();
	}
</script>
</body>
</html>