<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<link href="${ctx}/styles/treeSelect.css" type='text/css' rel='stylesheet'>
<style type="text/css">
#mytable {
	border: 0px solid #A6C9E2;
	margin-left: 0px;
	margin-top: 0px;
	width: 100%;
	border-collapse: collapse;
}

#mytable td {
	border: 0px solid #A6C9E2;
	height: 28;
}
</style>
</head>
<body >
<script type="text/javascript">
Ext.onReady(function(){
    var tabs = new Ext.TabPanel({
        renderTo: 'tabs',
        anchor : '100% 100%',
        height : 270,
        activeTab: 0,
        frame:false,
        defaults:{autoHeight: false},
        items:[
            {contentEl:'basic', title: '评估审核'},
            {contentEl:'fsCase', title: '事件信息'}
        ]
    });
});
</script>
<div class="x-panel">
<div class="x-panel-header">风险评估&nbsp;&gt;&nbsp;评估审核管理</div>
<div class="x-toolbar">
    <table width="100%">
      <tr>
        <td align="right">
          <a href="${ctx}/assessment/index.do">
             <img src="${ctx}/images/icons/building_go.png" class="icon" border="0">评估列表&nbsp;&nbsp;
          </a>
        </td>
      </tr>
    </table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form  action="auditSave.do" id="auditForm" method="post" theme="simple" validate="true" enctype="multipart/form-data">
<div id="tabs">
<div id="basic" class="x-hide-display">
	<s:hidden id="id" name="model.id" />
	<table id="mytable" >
	      <tr><td>&nbsp;</td></tr>
          <tr>
             <td align="right" >审&nbsp;&nbsp;核&nbsp;&nbsp;人：</td>
             <td align="left" >
             	<stc:username></stc:username>
             </td>
          </tr>
          <tr>
             <td align="right" width="90">审核日期：</td>
             <td align="left">
            	<input type="text" name="checkResult.checkTime" class="required" value='<s:date name="checkResult.checkTime" format="yyyy-MM-dd"/>'
				  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:127px;height:16px" readonly="readonly"/>
                <font color="red">*</font>
             </td>    
          </tr>
	     <tr>
			<td width="90" align="right">是否通过：</td>
			<td colspan="3" align="left">
				<s:radio list="#{'1':'是', '0':'否'}" id="isAgree" name="checkResult.isAgree"  cssClass="required" cssStyle="border:0;"></s:radio>
				<font color="red">*</font>
			</td>
	     </tr>          
          <tr>
	          <td align="right"  >审核意见：</td>
	          <td align="left" colspan="3">
	          	<s:textarea id="askCause" name="checkResult.result" cssStyle="width:400px; height:140px" cssClass="required"/>
                <font color="red">&nbsp;*</font>	          
	          </td>
          </tr>       
	</table>
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
                <c:if test="${empty model.fsCase.isMultiple}">
                                                  单体事件
                </c:if>
                <c:if test="${!empty model.fsCase.isMultiple}">
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
	$("#auditForm").validate();
});

</script>
</body>
</html>