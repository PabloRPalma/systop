<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/dwr.jsp" %>
<%@include file="/common/calendar.jsp" %>
<title>咨询列表</title>
<script type="text/javascript">
function onRemove() {
    var sels = document.getElementsByName('selectedItems');
    var checked = false;
    for(i = 0; i < sels.length; i++) {
        if(sels[i].id == 'selectedItems' && sels[i].checked) {
           checked = true;
           break;
        }
    } 
    if(!checked) {
        alert('请至少选择一条咨询信息。');
        return;
    }
    
    if(confirm("您确定删除所选咨询吗？")) {
       var form = document.getElementById("ec");
       form.action = "remove.do";
       form.submit();
    } else {
       return false;
    }
}
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">网上反馈列表</div>
  <div class="x-toolbar">
     <table width="100%">
		     <tr>
			   <td><s:form action="list" namespace="/admin/advisory" theme="simple">
			   	  日期：
			   	  <input type="text" id="beginDate" style="width: 100;" name="beginDate" value='<s:date name="beginDate" format="yyyy-MM-dd"/>'
				  onfocus="WdatePicker({skin:'whyGreen'})" class="Wdate" />
			      到&nbsp;<input type="text" id="endDate" style="width: 100;" name="endDate" value='<s:date name="endDate" format="yyyy-MM-dd"/>'
				  onfocus="WdatePicker({skin:'whyGreen'})" class="Wdate"/> 
				  <input type="submit" value=" 查询 " class="button">
	       </s:form>
         </td>
             <td style=" padding-left:5px; padding-top:5px;" align="right">     
             	<a href="#" onclick="onRemove()">
             		<img src="${ctx}/images/icons/delete.gif" />删除</a>
             	</td>
       </tr>
     </table>
   </div>
   <div class="x-panel-body">
     <div style="margin-left:-3px;" align="center">
     <ec:table items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
	   action="list.do" 
	   useAjax="true"
	   doPreload="false"
	   pageSizeList="20,50,100" 
	   editable="false" 
	   sortable="true"
	   rowsDisplayed="20" 
	   generateScript="true" 
	   resizeColWidth="true"
	   classic="false" 
	   width="100%" 
	   height="400px" 
	   minHeight="350"
	   toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	   <ec:row>
	     <ec:column width="50" property="_s" title="选择"
			style="text-align:center" sortable="false">
			<input type="checkbox" name="selectedItems" id="selectedItems"
				value="${item.id}" />
		</ec:column>
		<ec:column width="220" property="title" title="标题" />
		<ec:column width="80" property="name" title="提交者" />
		<ec:column width="80" property="creatDate" title="提交日期" cell="date" />
		<ec:column width="90" property="phone" title="电话" />
		<ec:column width="70" property="phone" title="邮编" />
		<ec:column width="220" property="address" title="地址" />
		<ec:column width="60" property="status" title="状态">
		<c:if test="${item.status eq 1}">
		   <span style="color:red;">已回复</span>
		</c:if>
		<c:if test="${item.status eq 0}">
		  <span style="color:green;">未回复</span>
		</c:if>
		
		</ec:column>
		<ec:column width="50" property="_0" title="操作"
			style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">回复</a>
		</ec:column>
	  </ec:row>
    </ec:table>
    </div>
  </div>
</div>
<%@include file="/common/dwrLoadingMessage.jsp" %>
</body>
</html>