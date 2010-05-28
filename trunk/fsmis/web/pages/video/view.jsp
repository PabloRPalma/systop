<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>会议记录</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>

</head>
<body>

<div  class="x-panel" style="width: 100%">
     <div class="x-panel-header"style="width: 100%">会议信息</div>
	 <div class="x-toolbar" style="padding: 5 5 0 5;width: 100%">
		<table width="100%" >
			<tr>
				<td class="simple" width="300" align="right">会议记录：</td>
			</tr>
		    <tr>
		       <td>
		       <div style="overflow: auto; height: 520px " id="meetingRecord" >
		        ${model.meetingRecord }
		       </div>
		       </td>
		    </tr>
		</table>
	</div>
</div>

</body>
</html>