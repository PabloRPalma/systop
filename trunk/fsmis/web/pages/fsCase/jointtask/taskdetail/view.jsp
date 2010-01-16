<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
</head>
<body >
<script type="text/javascript">
function receive(){
	if(confirm("确实要接收此任务吗？")){
	    var id = document.getElementById('id').value;
	    location.href="${ctx}/jointTask/deptTaskDetail/receive.do?model.id=" + id;
     }
}
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo: 'tabs',
        anchor : '100% 100%',
        height : 440,
        activeTab: 0,
        frame:false,
        defaults:{autoHeight: false},
        items:[
            {contentEl:'task', title: '任务信息'},                                           
            {contentEl:'fsCase', title: '事件信息'}
       
        ]
    });
});
</script>
<div class="x-panel">
<div class="x-panel-header">联合整治任务接收&nbsp;&gt;&nbsp;任务查看</div>
<div class="x-toolbar">
    <table width="100%">
      <tr>
        <td align="right">
          <a href="${ctx}/jointTask/deptTaskDetail/deptTaskDetailIndex.do">
             <img src="${ctx}/images/icons/building_go.png" class="icon" border="0">任务列表&nbsp;&nbsp;
          </a>
        </td>
      </tr>
    </table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form action="viewSave.do" id="viewForm" validate="true" method="post" enctype="multipart/form-data">
<div id="tabs">
<div id="task" class="x-hide-display">
<table id="mytable" border="0" cellpadding="1" cellspacing="1"> 
	<s:hidden id="id" name="model.id" />
	<tr>
	<td align="right" width="80">任务标题：</td>
	<td align="left">${model.jointTask.title}</td>
	 </tr>    
	 <tr>
	<td align="right" >任务说明：</td>
	   <td align="left" colspan="1">
	   	<div style="width:700px;word-break:break-all;">
	    	${model.jointTask.descn}
	  	</div>
	  </td>
	</tr> 
	<tr>
		<td align="right">派送时间：</td>
		<td align="left" width="420"><s:date
			 name="model.jointTask.createDate" 
			format="yyyy-MM-dd hh:mm" />
			</td>
	</tr>  
	<tr>
	  <td align="right">&nbsp;&nbsp;任务附件：</td>
		<td>
		   <table>
				<c:forEach var="mtd" items="${model.jointTask.taskAttachses}" >
		            <tr>
		              <td width="300" >
						  <a href="${ctx}${mtd.path}" target="_blank"><font color="blue">${mtd.title}</font></a>&nbsp;&nbsp;
		               </td>
					</tr>
				</c:forEach>			
		  </table>
	  </td>
	</tr>       
</table>
</div>

<div id="fsCase" class="x-hide-display">
<table id="mytable" >
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">事件标题：</span></td>
             <td align="left" >
             	<s:property value="model.jointTask.fsCase.title" />
             </td>
          </tr>
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">事件类型：</span></td>
             <td align="left" >
                <c:if test="${model.jointTask.fsCase.isMultiple eq '0'}">
                                                  单体事件
                </c:if>
                <c:if test="${model.jointTask.fsCase.isMultiple eq '1'}">
                                                  多体事件
                </c:if>
             </td>
          </tr>
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">事件类别：</span></td>
             <td align="left" >
             	<s:property value="model.jointTask.fsCase.caseType.name" />
             </td>
          </tr> 
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">事发地点：</span></td>
             <td align="left" >
             	<s:property value="model.jointTask.fsCase.address" />
             </td>
          </tr>                    
          <tr>
             <td align="right" width="90" style="height:20px"><span style="font-weight:bold">事发时间：</span></td>
             <td align="left">
                 <s:date name="model.jointTask.fsCase.caseTime" format="yyyy-MM-dd" />
             </td>    
          </tr>
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">事件报告人：</span></td>
             <td align="left" >
             	<s:property value="model.jointTask.fsCase.informer" />
             </td>
          </tr> 
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">报告人电话：</span></td>
             <td align="left" >
             	<s:property value="model.jointTask.fsCase.informerPhone" />
             </td>
          </tr>                       
          <tr>
	          <td align="right" style="height:20px"><span style="font-weight:bold">事件描述：</span></td>
	          <td align="left" colspan="3">
	          	<div style="width:700px;word-break:break-all;">
		          	${model.jointTask.fsCase.descn}
	          	</div>
	          </td>
          </tr>     
</table>
</div>
</div>
<table width="100%" style="margin-bottom: 10px;">
	<c:if
		test="${(model.status == '1' || model.status == '0')&&(model.isLeader == '1')}">
		<tr>
			<td style="text-align: center;">
			<input type="button" class="button" value="接收" onclick="receive()" /></td>
		</tr>
	</c:if>
	<c:if test="${model.status == '2' and model.isLeader =='1'}">
		<tr>
			<td style="text-align: center;">
			<input type="button" class="button" value="处理结果" onclick="result()" /></td>
		</tr>
	</c:if>
    <c:if test="${model.status == '4' and model.isLeader =='1'}">
		<tr>
			<td style="text-align: center;">
			   <input type="button" class="button" value="返回" onclick="javascript:history.back();" /></td>
		</tr>
	</c:if>
    <c:if test="${model.isLeader !='1'}">
	    <tr>
			<td style="text-align: center;">
			   <input type="button" class="button" value="返回" onclick="javascript:location.href='${ctx}/jointTask/deptTaskDetail/deptTaskDetailIndex.do;'" /></td>
		</tr>
	</c:if>
</table>
</s:form>
</div>
</body>
</html>