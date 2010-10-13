<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/calendar.jsp"%>
<%@include file="/common/meta.jsp" %>
<title></title>
</head>
<body>
<s:form action="remove" theme="simple" id="removeForm"></s:form>
<div class="x-panel">
  <div class="x-panel-header">用户审核管理</div>
  <div><%@ include file="/common/messages.jsp"%></div> 
    <div class="x-toolbar">
      <table width="99%">
        <tr>
         <td> 
         	<s:form action="index" theme="simple">
	           用户名：<s:textfield theme="simple" name="model.loginId" size="15"></s:textfield>&nbsp;&nbsp;
	            注册日期：
			  <input size="12" class="Wdate" id="beginTime" name="beginTime" 
			  	value='<s:date name="beginTime" format="yyyy-MM-dd"/>'
				onclick="var endTime=$dp.$('endTime');WdatePicker({skin:'whyGreen',onpicked:function(){endTime.click();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
			   至 <input size="12" class="Wdate" id="endTime" name="endTime"
				value='<s:date name="endTime" format="yyyy-MM-dd"/>'
				onclick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'beginTime\')}'});"/>&nbsp;&nbsp;
	         <s:submit value="查询" cssClass="button"></s:submit>
         	</s:form>
         </td>
         <td align="right">
           <table>
          	  <tr>
         		<td><a href="${ctx}/regist/editNew.do"><img src="${ctx}/images/icons/add.gif"/> 新建用户</a></td>
         		<td><span class="ytb-sep"></span></td><!--
         		<td><a href="#" onclick="changeUser()"><img src="${ctx}/images/exticons/recommend.gif"/> 状态变更</a></td>
         		<td><span class="ytb-sep"></span></td>-->
         		<td><a href="#" onclick="onRemove({noneSelectedMsg:'请至少选择一个用户.',
             		confirmMsg:'确定要删除这些用户吗？'})"><img src="${ctx}/images/icons/delete.gif"/> 删除用户</a></td>
         		</tr>
            </table>
          <td>
        </tr>
      </table>
    </div>   
    <div class="x-panel-body">
    <div style="margin-left:-3px;" align="center">
	<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	  action="index.do"
	  useAjax="true" doPreload="false"
	  maxRowsExported="10000000" 
	  pageSizeList="30,50,100,500" 
	  editable="false" 
	  sortable="true"	
	  rowsDisplayed="30"	
	  generateScript="true"	
	  resizeColWidth="true"	
	  classic="false" 
	  width="100%" 
	  height="350px" 
	  minHeight="300"
	  excludeParameters="selectedItems"
	  toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
	    <ec:column width="40" property="_s" title="选择" sortable="false" style="text-align:center">
	       <input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}" class="checkbox"/>
	    </ec:column>
		<ec:column width="70" property="loginId" title="登录名" />
		<ec:column width="70" property="name" title="真实姓名" />
		<ec:column width="60" property="sex" title="性别" 
			viewsAllowed="html" mappingItem="sexMap" style="text-align:center"/>
		<ec:column width="120" property="registTime" title="注册时间" cell="date" format="yyyy-MM-dd HH:mm" style="text-align: center"/>
		<ec:column width="120" property="email" title="电子信箱" />
		<ec:column width="70" property="level" title="用户级别" mappingItem="userLevelMap" style="text-align:center"/>
		<ec:column width="70" property="status" title="审核结果" 
			viewsAllowed="html" mappingItem="userStatusMap" style="text-align:center">
			<c:if test="${item.status eq 0}"><font color="red">未审核</font></c:if>
			<c:if test="${item.status eq 1}"><font color="green">已通过</font></c:if>
			<c:if test="${item.status eq 2}"><font color="blue">待激活</font></c:if>
		</ec:column>
		<ec:column width="90" property="_ck" title="审核" style="text-align:center"  sortable="false">
		  <c:if test="${item.status eq 0}">
		   <a href="${ctx}/regist/checkupUser.do?uId=${item.id}">通过</a> |
		   <font color="gray">撤销</font>
		  </c:if>
		  <c:if test="${item.status eq 1}">
		   <font color="gray">通过</font> |
		   <a href="${ctx}/regist/checkupUser.do?uId=${item.id}">撤销</a>
		  </c:if>
		  <c:if test="${item.status eq 2}">
		   <font color="gray">通过</font> |
		   <a href="${ctx}/regist/checkupUser.do?uId=${item.id}">撤销</a>
		  </c:if>
		</ec:column>
		<ec:column width="40" property="_r" title="角色" style="text-align:center" sortable="false">
		 <a href="#" onclick="javascript:assignRoles(${item.id})">
		       <img src="${ctx}/images/icons/role.gif" border="0" title="分配角色"/>
		   </a>
		</ec:column>
		<ec:column width="40" property="_edit" title="编辑" style="text-align:center"  sortable="false">
		   <a href="edit.do?model.id=${item.id}">
		       <img src="${ctx}/images/icons/modify.gif" border="0" title="编辑"/>
		   </a>		  
		</ec:column>
	</ec:row>
	</ec:table>
	</div>
	</div>
</div>
<div id="win" class="x-hidden">
    <div class="x-window-header">角色列表</div>
    <div id="role_grid"></div>
</div>
<script type="text/javascript" src="${ctx}/pages/admin/security/user/user.js">
</script>
<div id='load-mask'></div>
<script type="text/javascript">
  /** 变更用户状态 */
  function changeUser() {
    var selectItem = document.getElementsByName("selectedItems");
    var j=0;
    for (var i = 0; i < selectItem.length; i ++) {
      if (selectItem[i].checked) {
        j++;
      }
    }
    if (j>0) {
      if(confirm("是否确定变更用户状态？")) {
        document.getElementById('ec').action = "changeUserStatus.do";
	    document.getElementById('ec').submit();
  	  }
　　 } else {
　　　　alert("请选择要变更状态的用户！");
　　 } 
  }
</script>
</body>
</html>