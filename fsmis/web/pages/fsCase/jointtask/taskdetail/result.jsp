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
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo: 'tabs',
        anchor : '100% 100%',
        height : 440,
        activeTab: 0,
        frame:false,
        defaults:{autoHeight: false},
        items:[
            {contentEl:'result', title: '任务处理'},  
            {contentEl:'taskAttach', title: '任务附件'},               
            {contentEl:'task', title: '任务信息'},                              
            {contentEl:'fsCase', title: '事件信息'}
       
        ]
    });
});
</script>
<div class="x-panel">
<div class="x-panel-header">联合整治&nbsp;&gt;&nbsp;任务处理管理</div>
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
<s:form action="resultSave.do" id="resultForm" validate="true" method="post" enctype="multipart/form-data">
<div id="tabs">
<div id="result" class="x-hide-display">
	<s:hidden id="id" name="model.id" />
	<s:hidden id="jointTaskId" name="model.jointTask.id" />
	<s:hidden id="fsCaseId" name="model.jointTask.fsCase.id" />	
	<table id="mytable" >
          <tr>
             <td align="right" width="80">填&nbsp;写&nbsp;人：</td>
             <td align="left" >
             	<stc:username></stc:username>
             </td>
          </tr>
          <tr>
             <td align="right" width="80">处&nbsp;理&nbsp;人：</td>
             <td align="left" >
                <s:textfield id="processor" name="model.processor" cssStyle="width: 135px" cssClass="required"/>
                <font color="red">&nbsp;*</font>	 
             </td>
          </tr>                 
          <tr>
	          <td align="right" >处理过程：</td>
	          <td align="left" colspan="3">
	          	<s:textarea id="process" name="model.process" cssStyle="width:450px; height:175px" cssClass="required"/>
                <font color="red">&nbsp;*</font>	          
	          </td>
          </tr> 
          <tr>
	          <td align="right" >处理依据：</td>
	          <td align="left" colspan="3">
	          	<s:textarea id="basis" name="model.basis" cssStyle="width:450px; height:175px" cssClass="required"/>
                <font color="red">&nbsp;*</font>	          
	          </td>
          </tr>                 
	</table>
</div>

<div id="task" class="x-hide-display">
<table id="mytable" border="0" cellpadding="1" cellspacing="1">  
  <tr>
	<td align="right" width="80">任务标题：</td>
	<td align="left">${model.jointTask.title}</td>
  </tr>    
  <tr>
	<td align="right">任务说明：</td>
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
</table>
</div>

<div id="taskAttach" class="x-hide-display">
<iframe  height="450" src="${ctx}/jointTask/attaches/index.do?jointTaskId=${622592}" width="100%" name="taskDetails" frameborder="0"></iframe>
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
<table width="100%" style="margin-bottom: 0px;">
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			<td style="text-align: center;">
			    <s:submit value="保存" cssClass="button" />&nbsp;&nbsp;
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