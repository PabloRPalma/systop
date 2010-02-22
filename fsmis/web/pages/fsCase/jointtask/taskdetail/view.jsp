<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/ec/ecside_style.css" />
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<style type="text/css">
.mytable{
	width:600px;
	margin-top:10px;
}
.title {
	padding:6 5 3 5;
	width:100px;
	font-size:12px;
	font-weight: bold;
	text-align: right;
}
.content {
	padding:6 5 3 5;
	font-size:12px;
	text-align: left;
}
/*去掉粗边框的样式*/
.x-tab-panel{
  margin: -1;
}
</style>
</head>
<body >
<script type="text/javascript">
function receive(){
    var id = document.getElementById('id').value;
    Ext.MessageBox.confirm('提示','确实要接收此任务吗？', function(btn){
        if (btn == 'yes') {
           window.location.href="${ctx}/jointTask/deptTaskDetail/receive.do?model.id=" + id;
        }
    });	
}
function result(id, jointTaskId){  
    $.ajax({
		url: '${ctx}/jointTask/deptTaskDetail/checkResult.do',
		type: 'post',
		dataType: 'json',
		data: {jointTaskId : jointTaskId},
		success: function(rst, textStatus){
	  		if(rst.result != null){
	  		  Ext.Msg.alert('', rst.result + '还没有查看任务，不能进行任务处理！');		   	  		   	  		
	  	  	}else{
	  	  	  window.location.href='${ctx}/jointTask/deptTaskDetail/result.do?model.id=' + id;
	  	  	}
		}
   });
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
      	    <c:if test="${model.status == '4' && model.isLeader =='1'}">
            {contentEl:'result', title: '处理结果'},   
            </c:if>                     
            {contentEl:'task', title: '任务信息'},                                           
            {contentEl:'fsCase', title: '事件信息'}
       
        ]
    });
});
</script>
<div class="x-panel">
<div class="x-panel-header">联合整治任务明细查看</div>
<!--  
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
-->
<div><%@ include file="/common/messages.jsp"%></div>
<s:form action="viewSave.do" id="viewForm" validate="true" method="post" enctype="multipart/form-data">
<div id="tabs" style="width: 100%;">
<div id="result" class="x-hide-display">
<table id="mytable" border="0" cellpadding="1" cellspacing="1"> 
	<tr>
	<td align="right" width="80">填&nbsp;写&nbsp;人：</td>
	<td align="left"><stc:username></stc:username></td>
	</tr>  
	<tr>
	<td align="right" width="80">处&nbsp;理&nbsp;人：</td>
	<td align="left">${model.processor}</td>
	</tr>  	  
	<tr>
	<td align="right" >处理过程：</td>
	   <td align="left" colspan="1">
	   	<div style="width:700px;word-break:break-all;">
	    	${model.process}
	  	</div>
	  </td>
	</tr> 
	<tr>
	<td align="right" >处理依据：</td>
	   <td align="left" colspan="1">
	   	<div style="width:700px;word-break:break-all;">
	    	${model.basis}
	  	</div>
	  </td>
	</tr> 
	<tr>
	  <td align="right">&nbsp;&nbsp;任务附件：</td>
		<td>
		   <table>
				<c:forEach var="mtd" items="${model.taskDetailAttachses}" >
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
	  <td align="right">&nbsp;&nbsp;附件信息：</td>
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

<div id="fsCase" class="x-hide-display" style="width: 100%">			
	<table class="mytable" align="center" cellpadding="0" cellspacing="0">		
		<tr>
			<td class="title">事件标题:</td>
			<td class="content" colspan="3"><b>${model.jointTask.fsCase.title}</b></td>
		</tr>
        <tr>
            <td class="title" >事件类型:</td>
            <td class="content" >
               <c:if test="${model.jointTask.fsCase.isMultiple eq '0'}">
                                                  单体事件
               </c:if>
               <c:if test="${model.jointTask.fsCase.isMultiple eq '1'}">
                                                  多体事件
               </c:if>
            </td>
        </tr>				
		<tr>
			<td class="title">事件类别:</td>
			<td class="content" colspan="3">${model.jointTask.fsCase.caseType.name}</td>
		</tr>
		<tr>
			<td class="title">事发时间:</td>
			<td class="content" width="100"><s:date name="model.jointTask.fsCase.caseTime"format="yyyy-MM-dd" /></td>
			
			<td class="title" width="300">事件地点:</td>
			<td class="content">${model.jointTask.fsCase.address}</td>
		</tr>
		<tr>
			<td class="title">事件报告人:</td>
			<td class="content">${model.jointTask.fsCase.informer}&nbsp;</td>
			
			<td class="title">报告人电话:</td>
			<td class="content">${model.jointTask.fsCase.informerPhone}&nbsp;</td>
		</tr>
		<tr>
			<td class="content" colspan="4" style="border-top:1px dashed #99BBE8; padding: 4 10 4 10;">
				${model.jointTask.fsCase.descn}
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
			<input type="button" class="button" value="接收" onclick="receive()" />
			<input type="button" class="button" value="返回" onclick="javascript:location.href='${ctx}/jointTask/deptTaskDetail/deptTaskDetailIndex.do;'" /></td>			
		</tr>
	</c:if>
	<c:if test="${model.status == '2' && model.isLeader =='1'}">
		<tr>
			<td style="text-align: center;">
			<input type="button" class="button" value="处理" onclick="result(${model.id},${model.jointTask.id})" />
			<input type="button" class="button" value="返回" onclick="javascript:location.href='${ctx}/jointTask/deptTaskDetail/deptTaskDetailIndex.do;'" /></td>
		</tr>
	</c:if>
    <c:if test="${model.status == '4' && model.isLeader =='1'}">
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