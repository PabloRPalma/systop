<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/validator.jsp"%>
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
            {contentEl:'basic', title: '评估上报'},
            {contentEl:'attach', title: '上传附件'},            
            {contentEl:'fsCase', title: '事件信息'}
        ]
    });
});
</script>
<div class="x-panel">
<div class="x-panel-header">评估上报</div>
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
<s:form  action="resultSave.do" id="resultForm" method="post" theme="simple" validate="true" enctype="multipart/form-data">
<div id="tabs">
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
             <td align="right" width="90">申&nbsp;&nbsp;请&nbsp;&nbsp;人：</td>
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
             <td align="right" width="90" >专家组长：</td>
             <td>
             <s:textfield id="leader" name="leader" size="120" readonly="true" cssStyle="border:0"/>		    
			 </td>
          </tr> 	                                  
          <tr>
             <td align="right" width="90" >专家成员：</td>
             <td>
             <s:textfield id="member" name="member" size="120" readonly="true" cssStyle="border:0"/>			    
			 </td>
          </tr> 
          <tr>
             <td align="right" width="90" style="height:20px">评估等级：</td>
             <td>
                <s:select id="assessmentLevel" list="%{assessmentLevels}" name="model.level" headerKey="" headerValue="--请选择--"
	              cssStyle="width:100px;" cssClass="required"/><font color="red">*</font>
             </td>
          </tr>   
          <tr>
             <td align="right" width="90" style="height:20px">处理意见：</td>
             <td align="left" colspan="3">
            	<s:textarea id="result" name="model.result" cssStyle="width:360px; height:90px" cssClass="required"/><font color="red">*</font>
             </td>
          </tr>  
          </table></td></tr>                                    
	</table>
</div>
<div id="attach" class="x-hide-display">
<iframe  height="450" src="${ctx}/assessment/attaches/index.do?assessmentId=${param['model.id']}" width="100%" name="reskEvAttaches" frameborder="0"></iframe>
</div>
<div id="fsCase" class="x-hide-display">
<table id="mytable" >
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">事件标题：</span></td>
             <td align="left" >
             	<s:property value="model.fsCase.title" />
             </td>
          </tr>
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">事件类型：</span></td>
             <td align="left" >
                <c:if test="${model.fsCase.isMultiple eq '0'}">
                                                  单体事件
                </c:if>
                <c:if test="${model.fsCase.isMultiple eq '1'}">
                                                  多体事件
                </c:if>
             </td>
          </tr>
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">事件类别：</span></td>
             <td align="left" >
             	<s:property value="model.fsCase.caseType.name" />
             </td>
          </tr> 
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">事发地点：</span></td>
             <td align="left" >
             	<s:property value="model.fsCase.address" />
             </td>
          </tr>                    
          <tr>
             <td align="right" width="90" style="height:20px"><span style="font-weight:bold">事发时间：</span></td>
             <td align="left">
                 <s:date name="model.fsCase.caseTime" format="yyyy-MM-dd" />
             </td>    
          </tr>
          <tr>
             <td align="right" style="height:20px"><span style="font-weight:bold">事件报告人：</span></td>
             <td align="left" >
             	<s:property value="model.fsCase.informer" />
             </td>
          </tr>  	                     
          <tr>
	          <td align="right" style="height:20px"><span style="font-weight:bold">事件描述：</span></td>
	          <td align="left" colspan="3">
	          	<div style="width:700px;word-break:break-all;">
		          	${model.fsCase.descn}
	          	</div>
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
<script type="text/javascript">
$(document).ready(function() {
	$("#resultForm").validate();
});

</script>
</body>
</html>