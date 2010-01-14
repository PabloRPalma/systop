<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<html>
<head>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/meta.jsp" %>
<title></title>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">用户登录历史记录</div>
     <div class="x-toolbar">
      <table width="99%">
        <tr>
          <td> 
        <s:form action="/userHistory/userHistoryList.do" theme="simple">
        	
	         用户名:<s:textfield name="queryUsername" size="15"/>
	         部门名:<s:textfield name="queryLogindept" size="15"/>
	          时间:
      <input type="text" id="beginDate" name="beginDate" value="${param.beginDate}"
          onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate required"  readonly/>      
      <!-- 选择查询结束时间 --> 
     至
        <input type="text" id="endDate" name="endDate" value="${param.endDate}"
          onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})" class="Wdate required"  readonly/>      
	        &nbsp;&nbsp;<s:submit value="查询" cssClass="button"></s:submit>
         </s:form>
        </td>
        
        </tr>
      </table>
    </div>     
    <div class="x-panel-body"> 
	<ec:table items="items" 
	var="item" 
	retrieveRowsCallback="process"
	sortRowsCallback="process" 
	action="userHistoryList.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="true" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
	    
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>
		<ec:column width="80" property="user.name" title="用户名称" />
		<ec:column width="80" property="dept.name" title="部门" />
		<ec:column width="120" property="loginTime" title="登录时间" cell="date" format="yyyy-MM-dd HH:mm" />
		<ec:column width="120" property="loginIp" title="登录地址" />
		
	</ec:row>
	</ec:table>
	</div>
</div>
</body>
</html>