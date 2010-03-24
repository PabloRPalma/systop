<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@ taglib prefix="ec"  uri="http://www.ecside.org" %>
<script type="text/javascript" src="${ctx}/scripts/ec/ecside_msg_utf8_cn.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/ec/ecside-unpack.js" ></script>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/ec/cms_style.css" />
<title></title>
</head>
<body bgcolor="#FFFFFF" text="#000000">
<s:form action="list" theme="simple">
  <table width="650" border="0" align="center">
   <tr>
      <td align="right"  style="border-bottom: 1px dashed #6CA1D7;padding: 10 5 5 5;">
      	<a href="list.do" title="查看全部">
      		<img src="${ctx}/images/icons/zoom.gif"/>查看全部反馈
      	</a>&nbsp;&nbsp;
      	<a href="new.do" title="我要留言">
      		<img src="${ctx}/images/icons/add.gif"/>我要留言
      	</a>
      </td>
    </tr>
    <tr>
      <td style="border-bottom: 2px solid #6CA1D7">
     	 标题：<s:textfield name="model.title" cssStyle="width:250px"/>&nbsp;|&nbsp;
         从&nbsp;<input type="text" id="beginDate" name="beginDate" style="width:85px" 
         value='<s:date name="beginDate" format="yyyy-MM-dd"/>'
         onfocus="WdatePicker({skin:'whyGreen'})" class="Wdate" />
         到&nbsp;<input type="text" id="endDate" name="endDate" style="width:85px"  
         value='<s:date name="endDate" format="yyyy-MM-dd"/>' 
         onfocus="WdatePicker({skin:'whyGreen'})" class="Wdate"/> 
         &nbsp;&nbsp;&nbsp;&nbsp;
         <input type="submit" value=" 查询 " class="button">
       </td>
    </tr>
  </table>
</s:form>
<table width="690" border="0" align="center">
  <tr>
  	<td align="center" style="border-bottom: 2px solid #6CA1D7">
    <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
	    action="list.do"
	    useAjax="true" doPreload="false"
	    maxRowsExported="10000000"
	    pageSizeList="10,20,50" 
		editable="false" 
		sortable="false"	
		rowsDisplayed="10"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="false"	
		width="690" 	
		height="250px"
		minHeight="250"
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
		<ec:row>
			<ec:column width="56" property="_s" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
			<ec:column width="330" property="title" title="标题">
			<img src="${ctx}/images/icons/124.gif" width="8" height="8" />
				<a href="look.do?model.id=${item.id}" target="_blank" title="查看详细信息">
				${item.title}
				</a>
			</ec:column>
			<ec:column width="90" property="name" title="提交人" style="text-align:center"/>
			<ec:column width="90" property="creatDate" title="提交日期" style="text-align:center" cell="date"/>
			<ec:column width="65" property="status" title="状态" mappingItem="answer" style="text-align:center">
				<c:if test="${item.status eq 1}">
			    	<span style="color:red;">已回复</span>
				</c:if>
				<c:if test="${item.status eq 0}">
			  		<span style="color:green;">未回复</span>
				</c:if>
			</ec:column>
		</ec:row>
	</ec:table>
  </td>
  </tr>
</table>
</body>
</html>