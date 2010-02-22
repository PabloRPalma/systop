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
window.onload = function(){
	var enId = $("#id")[0].value;
    $.ajax({
		url: '${ctx}/assessment/getLeaders.do',
		type: 'post',
		dataType: 'json',
		data: {id : enId},
		success: function(leaders, textStatus){
		  if(leaders != null){
			$("#leader")[0].value = leaders;
		  }
		}
    });	
    	
    $.ajax({
		url: '${ctx}/assessment/getMembers.do',
		type: 'post',
		dataType: 'json',
		data: {id : enId},
		success: function(members, textStatus){
		  if(members != null){
			$("#member")[0].value = members;
		  }
		}
	});
};
  
	   
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo: 'tabs',
        anchor : '100% 100%',
        height : 270,
        activeTab: 0,
        frame:false,
        defaults:{autoHeight: false},
        items:[
            {contentEl:'basic', title: '评估启动'},
            {contentEl:'fsCase', title: '事件信息'}
        ]
    });
});
</script>
<div class="x-panel">
<div class="x-panel-header">评估启动</div>
<div class="x-toolbar">
    <table width="100%">
      <tr>
        <td align="right">
          <a href="${ctx}/assessment/index.do">评估列表&nbsp;&nbsp;
          </a>
        </td>
      </tr>
    </table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form action="startSave.do" id="startForm" method="post" theme="simple" validate="true" enctype="multipart/form-data">
<div id="tabs" style="width: 100%;">
<div id="basic" class="x-hide-display">
	<s:hidden id="id" name="model.id" />
	<s:hidden id="fsCaseId" name="model.fsCase.id" />	
	<table id="mytable" >
	    <tr>
		<td width="520">
		  <table width="562" align="left" border="0" cellspacing="2">
		  <tr>
            <td colspan="1"></td>
          </tr>
          <tr>
             <td align="right" width="90">申&nbsp;请&nbsp;人：</td>
             <td align="left" >
             	<stc:username></stc:username>
             </td>
          </tr>       
          <tr>
             <td align="right" width="90">申请日期：</td>
             <td align="left">
                <s:date name="model.askDate" format="yyyy-MM-dd" />
             </td>    
          </tr>     
          <tr>
	          <td align="right" width="90">申请原因：</td>
	          <td align="left" colspan="3">
	            <pre>${model.askCause}</pre>
	          </td>
          </tr>  
          <tr>
             <td align="right" width="90" style="height:20px"><span style="font-weight:bold">专家组组长：</span></td>
             <td>
             <s:textarea id="leader" name="leader" cssStyle="width:360px; height:70px" cssClass="required" readonly="true"/>
             <font color="red">*</font>    
			 </td>
			 <td align="left" width="90" style="height:20px"><span style="font-weight:bold">
             <a href="#" onclick="javascript:membersOfAssessment(${model.id}, 1);"> <img
				src="${ctx}/images/icons/user.gif" border="0" title="设置专家组组长" /> 选择</a>	
			 </span></td>
          </tr> 	                     
          <tr>
             <td align="right" width="90" style="height:20px"><span style="font-weight:bold">专家组成员：</span></td>
             <td>
             	<s:textarea id="member" name="member" cssStyle="width:360px; height:70px;" cssClass="required" readonly="true"/>
             <font color="red">*</font>	    
			 </td>
			 <td align="left" width="90" style="height:20px"><span style="font-weight:bold">
             <a href="#" onclick="javascript:membersOfAssessment(${model.id}, 2);"> <img
				src="${ctx}/images/icons/user.gif" border="0" title="设置专家组成员" /> 选择</a>	
			 </span></td>			 
          </tr>  
          </table></td></tr>                    
	</table>
</div>
<div id="fsCase" class="x-hide-display" style="width: 100%">			
	<table class="mytable" align="center" cellpadding="0" cellspacing="0">		
		<tr>
			<td class="title">事件标题:</td>
			<td class="content" colspan="3"><b>${model.fsCase.title}</b></td>
		</tr>
        <tr>
            <td class="title" >事件类型:</td>
            <td class="content">
               <c:if test="${model.fsCase.isMultiple eq '0'}">
                                                  单体事件
               </c:if>
               <c:if test="${model.fsCase.isMultiple eq '1'}">
                                                  多体事件
               </c:if>
            </td>
        </tr>				
		<tr>
			<td class="title">事件类别:</td>
			<td class="content" colspan="3">${model.fsCase.caseType.name}</td>
		</tr>
		<tr>
			<td class="title">事发时间:</td>
			<td class="content" width="100"><s:date name="model.fsCase.caseTime"format="yyyy-MM-dd" /></td>
			
			<td class="title" width="300">事件地点:</td>
			<td class="content">${model.fsCase.address}</td>
		</tr>
		<tr>
			<td class="title">事件报告人:</td>
			<td class="content">${model.fsCase.informer}&nbsp;</td>
			
			<td class="title">报告人电话:</td>
			<td class="content">${model.fsCase.informerPhone}&nbsp;</td>
		</tr>
		<tr>
			<td class="content" colspan="4" style="border-top:1px dashed #99BBE8; padding: 4 10 4 10;">
				${model.fsCase.descn}
			</td>
		</tr>						
	</table>
</div>
</div>
<table width="100%" style="margin-bottom: 0px;">
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			<td style="text-align: center;">
				<s:submit value="保存" cssClass="button"/>&nbsp;&nbsp;
				<s:reset value="重置" cssClass="button"/>
			</td>
		</tr>
</table>
</s:form>
</div>
<div id="win" class="x-hidden">
<div class="x-window-header">专家列表</div>
<div id="role_grid"></div>
</div>

<script type="text/javascript"
	src="${ctx}/pages/assessment/expertMembers.js">
</script>
<script type="text/javascript">
	$(document).ready(function() {
	$("#startForm").validate();
});
</script>
</body>
</html>