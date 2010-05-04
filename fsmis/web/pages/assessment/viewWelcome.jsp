<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>风险评估列表</title>
<%@include file="/common/meta.jsp"%>
</head>
<body>
<table width="250" border="0" align="center">
	<s:iterator value="items" var="item">
	    <tr> 
	        <td><img src="${ctx}/images/exticons/bit.png">&nbsp;</td>	    
	        <td>
	          <div style="display:inline;width:160px;white-space: nowrap;overflow:hidden;text-overflow:ellipsis;">
	             <a href="${ctx}/assessment/view.do?model.id=${item.id}" target="main" title="${item.fsCase.title}">${item.fsCase.title}&nbsp;</a>
	          </div>
	        </td>    
	        <td><s:date name="askDate" format="yyyy-MM-dd" /></td>
	    </tr>
	</s:iterator>
  </table>	
</body>
</html>
