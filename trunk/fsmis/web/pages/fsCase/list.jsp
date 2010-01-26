<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>事件采集管理</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function remove(id){
	if (confirm("确认要删除事件信息吗?")){
		window.location.href="${ctx}/fscase/remove.do?model.id=" + id+"&modelId=0&isMultipleCase="+${isMultipleCase};
		
	}
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">协调指挥&nbsp;&gt;&nbsp;${isMultipleCase eq 0?'单体事件':'多体事件'}列表</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td><s:form action="index" method="post">
			<s:hidden name="isMultipleCase"></s:hidden>
						事件标题:
			<s:textfield name="model.title" cssStyle="width:100px"></s:textfield>	
						事件编号:
			<s:textfield name="model.code" cssStyle="width:70px"></s:textfield>
						事发时间:
			<input type="text" name="caseBeginTime" style="width: 120px"
				value='<s:date name="caseBeginTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />
						至
			<input type="text" name="caseEndTime" style="width: 120px"
				value='<s:date name="caseEndTime" format="yyyy-MM-dd HH:mm"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" />
			                              事件状态:
			<s:select name="model.status" list="stateMap" headerKey="" headerValue="请选择"/>		
			<s:submit value="查询" cssClass="button"></s:submit>
		</s:form></td>
		<td align="right">
		<stc:role ifNotGranted="ROLE_DEPT_OPER">
		<table>
			<tr>
				<td><span class="ytb-sep"></span></td>
				<td>
					<c:if test="${isMultipleCase eq 0}"><a href="${ctx}/fscase/edit.do?isMultipleCase=${isMultipleCase}&modelId=${modelId}"> 添加事件</a></c:if>			
				</td>
			</tr>
		</table>
		</stc:role>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit"
	action="index.do" useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="false" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="30" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align:center"/>	
		<ec:column width="200" property="title" title="事件标题" tipTitle="${item.title}" sortable="false"/>
		<ec:column width="50" property="code" title="事件编号" sortable="false"/>
		<ec:column width="80" property="caseType.name" title="事件类别" sortable="false"/>
		<ec:column width="120" property="caseTime" title="事发时间"
			style="text-align:center" cell="date" format="yyyy-MM-dd HH:mm" sortable="false"/>
		<ec:column width="50" property="status" title="事件状态"  mappingItem="stateColorMap" style="text-align:center" sortable="false"/>
		<c:if test="${param['isMultipleCase'] eq 0}">
		<ec:column width="80" property="_3" title="事件来源" style="text-align:center" sortable="false">
			<c:if test="${item.caseSourceType == 'generic'}">普通添加</c:if>
			<c:if test="${item.caseSourceType == 'jointask'}">联合整治添加</c:if>
			<c:if test="${item.caseSourceType == 'deptreport'}">部门上报</c:if>
		</ec:column>
		</c:if>
		<ec:column width="80" property="_4" title="事件处理流程" style="text-align:center" sortable="false">
			<c:if test="${item.processType == 'task'}">普通派遣处理</c:if>
			<c:if test="${item.processType == 'jointask'}">联合整治处理</c:if>
		</ec:column>
		<ec:column width="50" property="_6" title="查看" style="text-align:center" sortable="false">
			<a  title="查看事件" href="${ctx}/fscase/view.do?fsCaseId=${item.id}&modelId=0&isMultipleCase=${isMultipleCase}">看 </a> |
			<a title="地图" href="#">图</a>
		</ec:column>
		<stc:role ifNotGranted="ROLE_DEPT_OPER">
		<ec:column width="115" property="_0" title="操作" style="text-align:center" sortable="false">
		<!-- 0未派遣1已派遣2已处理3回退4已核实完成5忽略6核实不过-->	
			<c:if test="${empty item.status or item.status eq '0' or item.status eq '3' or item.status eq '5' or item.status eq '6'}">		
			    <a title="修改事件" href="${ctx}/fscase/edit.do?model.id=${item.id}&modelId=0&isMultipleCase=${isMultipleCase}">改 </a> |
			    <a title="派遣任务" href="#" onclick="showChooseSendTypeWindow(${item.id},'${item.title}')">派</a> |
			    <a title="删除任务"href="#" onclick="remove(${item.id})" >删</a> 
			    <c:if test="${param['isMultipleCase'] eq 0}">
			     | <font color="silver" >核</font>	
			    </c:if>		    
            </c:if>
            <c:if test="${item.status eq '1'}">		
			    <font color="silver" >改</font> |
			    <font color="silver" >派</font> |
			    <font color="silver" >删</font> 
			    <c:if test="${param['isMultipleCase'] eq 0}">
			     | <font color="silver" >核</font> 	
			    </c:if>		  
            </c:if>
            <c:if test="${item.status eq '2'}">		
			    <a title="修改事件" href="${ctx}/fscase/edit.do?model.id=${item.id}&modelId=0&isMultipleCase=${isMultipleCase}">改 </a> |
			    <font color="silver" >派</font> |
			    <font color="silver" >删</font> 
			    <c:if test="${param['isMultipleCase'] eq 0}">
			    <c:choose>
				    <c:when test="${empty item.msgCheckedFlag or item.msgCheckedFlag == '0'}">
				    	| <a title="核实" href="addSendMsg.do?model.id=${item.id}&modelId=0&isMultipleCase=${isMultipleCase}">核</a>
				    </c:when>	
				     <c:when test="${item.msgCheckedFlag == '1'}">
				    	| <a title="核实反馈" href="confirmBackMsg.do?model.id=${item.id}&modelId=0&isMultipleCase=${isMultipleCase}">核</a>
				    </c:when>		    
				    <c:otherwise>
				    	| <font color="silver" >核</font> 	
				    </c:otherwise>
			    </c:choose>	
			    </c:if>	   
            </c:if>
            <c:if test="${item.status eq '4'}">		
			    <font color="silver" >改</font> |
			    <font color="silver" >派</font> |
			    <font color="silver" >删</font> 
			    <c:if test="${param['isMultipleCase'] eq 0}">
			     | <a title="查看核实反馈" href="confirmBackMsg.do?model.id=${item.id}&operType='v'&modelId=0&isMultipleCase=${isMultipleCase}">核</a>
			    </c:if>	
            </c:if>
            <%--
            <c:if test="${item.status == '2' || item.status == '4'}">
                <a href="#">
                <font color="#999999">编辑</font>
             </a>
            </c:if>
			<c:if test="${item.status == '0' || item.status == '1'}">
			   <a href="${ctx}/fscase/edit.do?model.id=${item.id}&modelId=0&isMultipleCase=${isMultipleCase}">
                                               编辑
             </a>	
            </c:if>

			<c:if test="${item.status != '0'}">
			     <font color="#999999" title="已派遣事件不能删除">删 |</font>
			</c:if>
			
			<c:if test="${item.status == '0'}">
			
			</c:if>							         
			<c:if test="${item.status == '2'}" >
			  <a href="addSendMsg.do?model.id=${item.id}">核实</a>
			</c:if>
			<c:if test="${item.status == '2' && item.msgCheckedFlag == '1'}">
			    <a href="confirmBackMsg.do?model.id=${item.id}">
					核实反馈
				</a>	
			</c:if>
			<c:if test="${item.status == '4'}">
			     <a href="confirmBackMsg.do?model.id=${item.id}&operType='v'">
					查看核实反馈
				</a>				
			</c:if>	
			 --%>	
		</ec:column>
		</stc:role>
	</ec:row>
</ec:table>
</div>
</div>
</div>
<jsp:include page="chooseSendType.jsp"></jsp:include>
</body>
</html>
