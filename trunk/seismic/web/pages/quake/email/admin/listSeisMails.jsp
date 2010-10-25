<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<style>
.buttonImg {
   border:0px;
   cursor:pointer;
   cursor:hand;
}
</style>

<html>
<head>
<title></title>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript">
$(function() {
   $('#process').hide();
   var users = [
       <c:forEach items="${subscribers}" var="user">
       {'${user.id}':'${user.loginId}'},
       </c:forEach>
       {'x':'y'}
   ]; 
   $('option').each(function(idx, item) {
       if($(item).html() == '') {
          for(var i = 0; i < users.length; i++) {
             var temp = users[i][$(item).val()];
             if(temp) {
                $(item).html(temp);
             }
          }
       }
   });
});
var verify = function(id, passed) {
   if(passed == 'false') {
       if(!confirm("您正在拒绝一个邮件订阅项，此操作将删除该订阅项，是否继续?")) {
           return;
       }
   }
   $.ajax({
       url:'verify.do?mailId=' + id + '&isPassed=' + passed,
       success: function(){
         $('#process').fadeOut();
         ECSideUtil.reload('ec');
       },
       beforeSend: function() {
          $('#process').show();
       }
   });
};
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">测震邮件审核</div>
    <div class="x-toolbar">
	<table width="99%" style="margin:0px;">
			
				<tr>
				    <td align="left">
				    <s:form action="list" validate="false" method="get">
				    订阅用户：<s:select list="subscribers" listKey="id"
				    listValue="name" headerValue="请选择..." headerKey="" 
				    name="model.subscriber.id">
				    </s:select>&nbsp;&nbsp;&nbsp;订阅时间：
				    <s:textfield readOnly="true" size="11" cssClass="Wdate" id="startTime" name="model.startTime"
			onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});"/>&nbsp;至&nbsp;
		  <s:textfield readOnly="true" size="11" cssClass="Wdate" id="endTime" name="model.endTime"
			onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'});"/>
					&nbsp;&nbsp;&nbsp;地震行业：
					<s:select list="industryMap" headerValue="请选择..." headerKey="" name="model.industry"></s:select>
				    <s:submit value="查询" cssClass="button"/>
				    </s:form>
				    
				    </td>
					<td align="right">
					   <div id="process"><img src="${ctx}/images/grid/loading.gif"></div>
					</td>
				</tr>
		
	</table>
  </div>
   <div class="x-panel-body">
     
	 <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process" 
	action="list.do"
		useAjax="true" doPreload="false"
	maxRowsExported="10000000" 
	pageSizeList="10,20,30" 
	editable="false" 
	sortable="true"	
	rowsDisplayed="10"	
	generateScript="true"	
	resizeColWidth="true"	
	classic="false"	
	width="100%" 	
	height="477px"	
	minHeight="300"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status"   
	>
	<ec:row>
		<ec:column width="40" property="_0" title="序号" value="${GLOBALROWCOUNT}" style="text-align:center" sortable="false"/>
		<ec:column width="60" property="_1" title="审核" sortable="false">
		
		       <c:if test="${item.state == '0'}">
		          <img onclick="verify('${item.id}','true')" src="${ctx}/images/icons/accept.gif" class="buttonImg" title="通过"/>
		       </c:if>
		
		       <img onclick="verify('${item.id}','false')" src="${ctx}/images/icons/delete.gif" class="buttonImg" title="不通过"/>
		</ec:column>
		<ec:column width="60" property="_2" title="审核状态">
		   <c:if test="${item.state == '0'}">
		      <span style="color:red">未审核</span>
		   </c:if>
		   <c:if test="${item.state == '1'}">
		      <span style="color:green">审核通过</span>
		   </c:if>
		</ec:column>
		<ec:column width="80" property="_3" title="订阅者">
		   <c:if test="${empty item.subscriber.name}">
		       ${item.subscriber.loginId}
		   </c:if>		   
		   <c:if test="${!empty item.subscriber.name}">
		       ${item.subscriber.name}
		   </c:if>
		</ec:column>
		<ec:column width="120" property="catalogName" title="地震目录名称" />
		<ec:column width="120" property="emailAddr" title="电子邮件"/>
		<ec:column width="140" property="createDate" title="订阅时间" cell="quake.base.webapp.DateTimeCell"/>
		<ec:column width="60" property="startEpiLon" title="起始经度"  sortable="false"/>
		<ec:column width="60" property="endEpiLon" title="结束经度"  sortable="false"/>
		<ec:column width="60" property="startEpiLat" title="起始纬度"  sortable="false"/>
		<ec:column width="60" property="endEpiLat" title="结束纬度"  sortable="false"/>
		<ec:column width="60" property="minM" title="最小震级"  sortable="false"/>
		<ec:column width="60" property="maxM" title="最大震级"  sortable="false"/>
		<ec:column width="120" property="lastSendDate" title="最后发送时间" cell="quake.base.webapp.DateTimeCell" />
		
			
		
	</ec:row>
	</ec:table>
  
  </div>
  </div>
</body>
</html>