<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.systop.fsmis.model.Supervisor,java.util.List" %>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>信息员手机号导出</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function copymobile(){
	var mobilenums = document.getElementById('mobilenum').innerText;
	window.clipboardData.setData("Text",mobilenums);
	alert("已复制到剪切板！");
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-toolbar" align="center">
<h3>手机号码导出成功&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button" name="copy" value="复制到剪切板" onclick="copymobile()"/></h3>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="left">
	<table width="800" align="center" border="0" cellpadding="3" cellspacing="5">
		<tr>
			<td align="left" id="mobilenum">
				<%
					int iMobileCount = 0;
					int iNoMobileCount = 0;
					List<Supervisor> items = (List)request.getAttribute("items");
					for(Supervisor s : items){
						if (s.getMobile() != null && !"".equals(s.getMobile())){
							iMobileCount++;
							if (iMobileCount % 10 != 0){
								out.print(s.getMobile() + ";");
							}else{
								out.println(s.getMobile() + ";");
							}
						}else{
							iNoMobileCount++;
						}
					}
				%>
			</td>
		</tr>
		<tr>
			<td ><br>
				<h3 style="color: green;">有号码人数:<%= iMobileCount%></h3>
				<h3 style="color: red;">无号码人数:<%= iNoMobileCount%></h3>			
			</td>
		</tr>
	</table>
</div>
</div>
</div>
</body>
</html>