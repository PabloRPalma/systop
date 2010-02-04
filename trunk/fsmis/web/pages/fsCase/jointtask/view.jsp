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
            {contentEl:'fsCase', title: '事件信息'},               
            {contentEl:'task', title: '任务信息'},    
            <c:if test="${model.status == '1' || model.status == '2'}">           
            {contentEl:'audit', title: '审核信息'},              
            </c:if>
            {contentEl:'taskDetail', title: '任务明细'}                            
       
        ]
    });
});
</script>
<div class="x-panel">
<div class="x-panel-header">联合整治任务查看</div>
<c:if test="${model.id != null}">
<div class="x-toolbar">
    <table width="100%">
      <tr>
        <td align="right">
          <a href="${ctx}/jointTask/index.do">任务列表&nbsp;&nbsp;
          </a>
        </td>
      </tr>
    </table>
</div>
</c:if>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form action="auditSave.do" id="auditForm" validate="true" method="post" enctype="multipart/form-data">
<div id="tabs">
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
             <td align="right" style="height:20px"><span style="font-weight:bold">报告人电话：</span></td>
             <td align="left" >
             	<s:property value="model.fsCase.informerPhone" />
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
<div id="task" class="x-hide-display">
<table id="mytable" border="0" cellpadding="1" cellspacing="1"> 
  <tr>
  	<td align="right" width="80">申&nbsp;请&nbsp;人：</td>
  	<td align="left" ><stc:username></stc:username></td>
  </tr>
  <tr>
	<td align="right">拟办意见：</td>
    <td align="left" colspan="1">
    	<div style="width:700px;word-break:break-all;">
     	${model.opinion}
    	</div>
    </td>
  </tr>  
  <tr>
	<td align="right">任务标题：</td>
	<td align="left">${model.title}</td>
  </tr>    
  <tr>
	<td align="right">任务说明：</td>
    <td align="left" colspan="1">
    	<div style="width:700px;word-break:break-all;">
     	${model.descn}
    	</div>
    </td>
  </tr> 
	<tr>
	  <td width="80" align="right">&nbsp;&nbsp;任务附件：</td>
		<td>
		   <table>
				<c:forEach var="mtd" items="${model.taskAttachses}" >
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
<div id="audit" class="x-hide-display">
   <iframe  height="450" src="${ctx}/jointTask/listCheckResult.do?jointTaskId=${param['model.id']}" width="100%" name="checkRsts" frameborder="0"></iframe>
</div>

<div id="taskDetail" class="x-hide-display">
   <iframe  height="450" src="${ctx}/jointTask/details/index.do?jointTaskId=${param['model.id']}" width="100%" name="taskDetails" frameborder="0"></iframe>
</div>

</div>
</s:form>
</div>
</body>
</html>