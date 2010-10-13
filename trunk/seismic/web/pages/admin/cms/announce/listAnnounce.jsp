<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/dwr.jsp" %>
<%@include file="/common/calendar.jsp"%>
<%@include file="/common/ec.jsp" %>
<title>网站公告管理</title>
<script type="text/javascript">
function onRemove() {
    var sels = document.getElementsByName('selectedItems');
    var checked = false;;
    for(i = 0; i < sels.length; i++) {
        if(sels[i].id == 'selectedItems' && sels[i].checked) {
           checked = true;
           break;
        }
    } 
    if(!checked) {
        alert('请至少选择一个公告。');
        return;
    }
    
    if(confirm("您确定删除所选公告吗？")) {
       $('ec').action = "${ctx}/admin/announce/removeAnnounce.do";
       $('ec').submit();
    } else {
       return false;
    }
}
function remove(id) {
  if (confirm('您确定删除该公告吗？')) {
   $('ec').action = "${ctx}/admin/announce/removeAnnounce.do?model.id="+id;
   $('ec').submit();
  }
}
</script>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">网站公告管理</div>
  <div class="x-toolbar">
  	<table width="99%">
  	  <tr>
  		<td width="140">
  			<s:form name="announce" namespace="/admin/announce" action="listAnnounce" theme="simple">
	    		查询条件：<s:select list="#{'title':'标题','author':'发布人','creatdate':'发布时间'}" name="kind" onchange="test()" />
	   </td>
	   <td width="10">
   			<table id="tb_0"> 
				<tr> 
					<td> <s:textfield id="kindValue" name="kindValue" size="30" theme="simple"/></td> 
				</tr> 
			</table> 
		</td>
		<td width="10">
			<table id="tb_1"> 
				<tr> 
					<td> 
					从 <input type="text" name="beginDate" size="12" id="beginDate" class="Wdate" onfocus="WdatePicker({skin:'whyGreen'})" value='<s:date name="beginDate" format="yyyy-MM-dd"/>' />
					到 <input type="text" name="endDate" size="12" id="endDate" class="Wdate" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}',skin:'whyGreen'})" value='<s:date name="endDate" format="yyyy-MM-dd"/>' /> 
					</td> 
				</tr> 
			</table> 
		</td>
		<td>
			<s:submit value="查询" cssClass="button"/>
	    	</s:form>
		</td>
	    <td style="padding-left:5px; padding-top:5px;" align="left" width="4%"> 
	    	<a href="${ctx}/admin/announce/newAnnounce.do">
	    		<img src="${ctx}/images/icons/add.gif" />添加公告</a>&nbsp;&nbsp;|&nbsp;&nbsp;
	    	<a href="#" onclick="onRemove()">
	    		<img src="${ctx}/images/icons/delete.gif" />删除公告</a>&nbsp;&nbsp;
	    </td>
  	  </tr>
  	</table>
  <div>
  <div class="x-panel-body">
    <div style="margin-left:-3px;" align="center">
	  <ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
		action="listAnnounce.do"
		useAjax="true" doPreload="false"
		pageSizeList="10,20,50,100" 
		editable="false" 
		sortable="true"	
		rowsDisplayed="10"	
		generateScript="true"	
		resizeColWidth="true"	
		classic="true"
		width="100%" 	
		height="300px"	
		minHeight="255"  
		toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
		<ec:row>
			<ec:column width="40" property="_s" title="选择"
				style="text-align:center" sortable="false">
				<input type="checkbox" name="selectedItems" id="selectedItems" value="${item.id}" />
			</ec:column>
		    <ec:column width="200" property="title" title="标题" sortable="false" />
		    <ec:column width="80" property="dept.name" title="所属部门"/>  		    
		    <ec:column width="60" property="-8" title="显示方式" sortable="false">
		    <c:if test="${item.showType eq '0'}">
		       弹出
		      </c:if>
		      <c:if test="${item.showType eq '1'}">
		        滚动
		      </c:if>
		      </ec:column>
		    <ec:column width="60" property="_8" title="是否最新" sortable="false">
		     <c:if test="${item.isNew eq '0'}">
		       否
		      </c:if>
		      <c:if test="${item.isNew eq '1'}">
		        是
		      </c:if>
		      </ec:column>
		      
		    <ec:column width="70" property="-7" title="有效期(天)" sortable="false" >
		    <c:if test="${item.outTime == 0 }">
		       永久
		      </c:if>
		      <c:if test="${item.outTime != 0 }">
		        ${item.outTime}
		      </c:if>
		      </ec:column>		      
		    <ec:column width="70" property="author" title="发布人"  />
		    <ec:column width="74" property="creatDate" title="发布时间" cell="date"/>
			<ec:column width="150" property="_0" title="操作" style="text-align:center" sortable="false">
			  <a href="editAnnounce.do?model.id=${item.id}">
			       编辑
			   </a>|
			  <a href="editAnnState.do?model.id=${item.id}">
				<c:if test="${item.isNew eq '0'}">
			   	  设为最新
			    </c:if>
			    <c:if test="${item.isNew eq '1'}">
			       取消最新
			    </c:if>
			   </a>
			   
			</ec:column>
		</ec:row>
	  </ec:table>
	</div>
  </div>
</div>
</body>
<script type="text/javascript">
function test() { 
	if(document.announce.kind.value=='creatdate'){
		document.all("tb_1").style.display="block"; 
		document.all("tb_0").style.display="none"; 
	}else{
		document.all("tb_0").style.display="block"; 
		document.all("tb_1").style.display="none"; 
 
		}
} 
test();
</script>
</html>