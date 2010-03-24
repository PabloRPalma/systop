<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>

<%@ taglib prefix="ec"  uri="http://www.ecside.org" %>

<script type="text/javascript" src="${ctx}/js/ec/ecside_msg_utf8_cn.js" ></script>
<script type="text/javascript" src="${ctx}/js/ec/ecside.js" ></script>
<script type="text/javascript" src="${ctx}/js/ec/prototype_mini.js" ></script>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/ec/cms_style.css" />
<title>网站公告列表</title>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<br>
<table width="40%" border="0" cellspacing="3" cellpadding="3" align="center">
<tr>
  		<td>
      <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
	    action="listAnnouncefront.do"
	    useAjax="false" doPreload="false"
	    maxRowsExported="10000000"
	    pageSizeList="15,30,50,100,500,1000" 
		editable="false" 
		sortable="true"	
		rowsDisplayed="15"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"	
		width="100%" 	
		height="400px"	
		minHeight="200" 
		>
		<ec:row>
			<ec:column width="400" property="title" title="标 题"><img src="${ctx}/tmpRes/index/images/124.gif" width="8" height="8" />
			<a href="lookAnnouncefront.do?id=${item.id}" title="查看详细信息"><font color="blue">${item.title}</font></a>
			</ec:column>
			<ec:column width="400" property="content" title="内 容" />
			<ec:column width="70" property="creatDate" title="发布时间" sortable="false" cell="date"/>
		</ec:row>
		</ec:table>
		</td>
  	</tr>
</table>


</body>
</html>