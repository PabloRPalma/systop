<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>派遣环节执法部门配置</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
</head>
<body>
<div class="x-panel">
	<div class="x-panel-header">派遣环节执法部门配置</div>
	<div class="x-toolbar">
		<div style="padding: 5 20 3 10;" align="right">
		  <img src="${ctx}/images/icons/square.gif"/>
	      <a href="${ctx}/sendType/index.do">派遣环节查看</a>
		</div>
	</div>
	<div>
	<%@ include file="/common/messages.jsp"%>
	</div> 
	<div align="center" style="margin-top:20px;">
		<form id="countySendTypeSaveForm" action="save.do" method="post">
		  <fieldset style="width:710px; padding:10px 10px 10px 10px;">
		  <legend>执法部门配置</legend>
			<s:hidden name="model.id"/>
	        <table width="100%" align="center">
	          <tr>
	             <td align="right" width="100">环节名称：</td>
	             <td align="left" width="600">
	             	<span style="font: 12px bold;">${model.sendType.name}</span>
	             </td>
	            </tr>
	          <tr>
	             <td align="right">执法部门：</td>
	             <td align="left">
	             	<fs:selectDepts name="deptIds" sendTypeId="${model.sendType.id}" splitLineStyle="1px dotted blue;" itemClass="checkbox"/>
	             </td>
	          </tr>
	        </table> 
		  </fieldset>
		  <br>
		  <table align="center">
			<tr><td>
			  <div class="button" align="center" style="float:left; width: 55px; padding: 5 5 5 5;">
           		<a href="#" onclick="save()">保&nbsp;存</a>
           	  </div>
           	  <div class="button" align="center" style="float: left; width: 135px; padding: 5 5 5 5; margin-left: 20px">
           		<a href="${ctx}/sendType/index.do">继续配置其他派遣环节</a>
           	  </div>
			</td></tr>
		  </table>
		</form>
	</div>
</div>
<script type="text/javascript">
	function save(){
		document.getElementById("countySendTypeSaveForm").submit();
	}
</script>
</body>
</html>