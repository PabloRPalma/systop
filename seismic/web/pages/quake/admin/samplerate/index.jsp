<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>

<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<s:form id="removeForm" action="remove" namespace="/quake/admin/samplerate" method="POST"></s:form>
<div class="x-panel">
  <div class="x-panel-header">采样率管理</div>
    <div class="x-toolbar">
      <table width="99%">
        <tr>
          <td> 

         </td>
         <td align="right">
         <table> 
           <tr>
             <td><span id="loading" style="display:none"><img src="${ctx}/images/grid/loading.gif"></span></td>
             <td><a href="${ctx}/quake/admin/samplerate/index.do"><img src="${ctx}/images/icons/house.gif"/> 采样率首页</a></td>
             <td><span class="ytb-sep"></span></td>
             <td><a href="${ctx}/quake/admin/samplerate/editNew.do"><img src="${ctx}/images/icons/add.gif"/> 新建采样率</a></td>
           </tr>
         </table>
         <td>
       </tr>
     </table>
   </div>   

   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="index.do"
	useAjax="true" doPreload="false"
	maxRowsExported="1000" 
	pageSizeList="5,10,20,100" 
	editable="false" 
	sortable="true"	
	rowsDisplayed="10"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="277px"	
	minHeight="200" 
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status"  
	>
	<ec:row>
	    <ec:column width="50" property="_s" title="服务" style="text-align:center" viewsAllowed="html" sortable="false">
	       <input type="checkbox" value="${item.id}" onclick="changeEnable(this)" id="enabled" ${item.enabled == 1 ? "checked" : ""} class="checkbox"/>
	    </ec:column>
	    <ec:column width="50" property="_r" title="订阅" style="text-align:center" viewsAllowed="html" sortable="false">
	       <input type="checkbox" value="${item.id}" onclick="changeMail(this)" id="forMail" ${item.forMail == 1 ? "checked" : ""} class="checkbox"/>
	    </ec:column>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}"  sortable="false"/>
		<ec:column width="40" property="id" title="代码" />
		<ec:column width="60" property="name" title="名称" />
		<ec:column width="120" property="dateFormat" title="时间格式" />
		<ec:column width="90" property="stockPeriod" title="flash时间间隔" />
		<ec:column width="90" property="stockPeriodName" title="flash间隔名称" />
		<ec:column width="120" property="stockDateFormat" title="flash间隔格式" />
		<ec:column width="40" property="_0" title="操作" style="text-align:center" viewsAllowed="html" sortable="false">
		   <a href="edit.do?model.id=${item.id}"> 
		      <img src="${ctx}/images/icons/modify.gif" style="border:0px" title="编辑"/>
		   </a>
		</ec:column>
	</ec:row>
	</ec:table>
  </div>
  </div>
  </div>
<script type="text/javascript">

function changeEnable(cbx) {
   var checked = cbx.checked ? '1' : '0';
   
   $.ajax({
      url:'${ctx}/quake/admin/samplerate/changeEnabled.do?model.id=' + $(cbx).val() + '&model.enabled=' + checked,
      beforeSend: function(){$('#loading').show();},
      success: function(){$('#loading').fadeOut();}
   });
}

function changeMail(cbx) {
   var checked = cbx.checked ? '1' : '0';
   $.ajax({
      url:'${ctx}/quake/admin/samplerate/changeForMail.do?model.id=' + $(cbx).val() + '&model.forMail=' + checked,
      beforeSend: function(){$('#loading').show();},
      success:function(){$('#loading').fadeOut();}
   });
}
</script>
</body>
</html>