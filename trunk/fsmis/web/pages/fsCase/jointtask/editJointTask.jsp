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
            {contentEl:'task', title: '任务申请'},   
            {contentEl:'taskAttach', title: '任务附件'},                              
            {contentEl:'fsCase', title: '事件信息'}
       
        ]
    });
});
</script>
<div class="x-panel">
<div class="x-panel-header">联合整治任务申请</div>
<c:if test="${model.id != null}">
<div class="x-toolbar">
    <table width="100%">
      <tr>
        <td align="right">
          <a href="${ctx}/jointTask/index.do">
             <img src="${ctx}/images/icons/building_go.png" class="icon" border="0">任务列表&nbsp;&nbsp;
          </a>
        </td>
      </tr>
    </table>
</div>
</c:if>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form action="taskSave.do" id="taskSave" validate="true" method="post" enctype="multipart/form-data">
<div id="tabs">
<div id="task" class="x-hide-display">
	<s:hidden id="id" name="model.id" />
	<s:hidden id="fsCaseId" name="model.fsCase.id" />	
	<table id="mytable" > 
	  <tr>
	  	<td align="right" width="93">申&nbsp;请&nbsp;人：</td>
	  	<td width="5%" align="left" ><stc:username></stc:username></td>
	  	<td align="right">申请日期：</td>
	  	<td align="left"><input type="text" id="dt" style="border: 0"></td>
	  </tr>	 	
	  <tr>
		<td align="right">拟办意见：</td>
		<td align="left" colspan="3">
		<s:textarea name="model.opinion" id="opinion" cssStyle="width:500px; height:80px;" cssClass="required"/>
		<font color="red">&nbsp;*</font></td>
	  </tr>    
	</table>
	<table width="700px"  border="0">
		<tr>
			<td colspan="2" align="left" valign="middle" style="border-top: 1px dashed black; padding-top: 10px;">
				<h5>任务详细信息</h5>
			</td>
		</tr>
		<tr>
			<td align="right" width="100">任务标题：</td>
			<td align="left"><s:textfield id="title"
				name="model.title" cssStyle="width:496px" cssClass="required"/>
				<font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">规定完成时间：</td>
			<td align="left"><input id="date" type="text"   name="model.presetTime"
				value='<s:date name="model.presetTime" format="yyyy-MM-dd HH:mm:ss"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate" style="width:120px;height:18px" readonly="readonly" class="required"/><font color="red">&nbsp;*</font></td>
		</tr>	
        <tr>
			<td align="right" width="100px">牵头部门：</td>
			<td align="left">
                <fs:selectDepts name="deptLeaderId" defDeptIds="${deptLeaderId}" theme="select" /> 
			</td>
		</tr>	
		<tr>
			<td align="right">协作部门：</td>
			<td align="left">
		    <div id="showDiv" style="border-bottom: 1px dotted #97B7E7; padding: 2 7 2 7;" title="显示选择的协作部门"></div> 
			</td>
		</tr>		
		<tr>
			<td align="right">选择部门：</td>
			<td align="left"> 
		    <fs:selectDepts name="deptIds" onclick="show()" defDeptIds="${deptIdsStr}" itemClass="checkbox" theme="simple"/>  
			</td>
		</tr>
		<tr>
			<td align="right" >任务说明：</td>
			<td align="left" ><s:textarea id="descn"
				 name="model.descn" cssStyle="width:500px; height:80px;" cssClass="required"/>
				 <font color="red">&nbsp;*</font></td>
		</tr>								
	</table>	
</div>
<div id="taskAttach" class="x-hide-display">
<s:if test="model.taskAttachses.size > 0 ">
 <table border="0" cellpadding="1" cellspacing="1" >
	<tr>
	  <td >&nbsp;附件列表</td>
		<td>
		   <table>
				<c:forEach var="mtd" items="${model.taskAttachses}" >

		            <tr id="attach_${mtd.id}">
		              <td width="300" style="border-top: 1px dashed black; padding-top: 10px;">
						  <a href="${ctx}${mtd.path}" target="_blank"><font color="blue">${mtd.title}</font></a>&nbsp;&nbsp;
		               </td>
		               <td style="border-top: 1px dashed black; padding-top: 10px;">
						  <a href="#" onclick="javascript:deleteAttach(${mtd.id})">删除附件</a>
		               </td>
					</tr>
				</c:forEach>			
		  </table>
	  </td>
	</tr>
 </table>
</s:if>
<table id="mytable" > 
     <tr>
	     <td align="right"></td>
		 <td bgcolor="#EBEBEB" style="padding: 10 5 2 5;">
				<a href="#" onclick="javascript:addFileInput();">
					<font color="red">上传附件</font>
				</a>
				<!--  
				|<a href="${ctx}/jointTask/attaches/index.do?jointTaskId=${param['model.id']}" >查看附件</a>
				-->
		
		</td>
	</tr>
	<tbody id="fileUpload"></tbody>
</table>
<table>
	<s:if test="errorMsg.size() > 0">

	<tr><td>提示信息:</td></tr>
	<s:iterator value="errorMsg">
      	<tr>
        <td align="left">			
			<s:property />		
         </td>
	   </tr>
	</s:iterator>

	</s:if>
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
</div>
<table width="100%" style="margin-bottom: 0px;">
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			<td style="text-align: center;">
			    <s:submit value="保存" cssClass="button" onclick="return checkRepetitiveLeader ();"/>&nbsp;&nbsp;
				<s:reset value="重置" cssClass="button"/>
			</td>
		</tr>
</table>
</s:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/fsmis/ShowDeptName.js"></script> 
<script type="text/javascript">
	$(document).ready(function() {
	$("#taskSave").validate();
    });
	mydate = new Date();
	year = mydate.getYear();
	month = mydate.getMonth()+1;
	day = mydate.getDate();
	
	str = year + "年" + month + "月" + day + "日";
	document.getElementById("dt").value = str;

	function show(){ 
		deptOperator.showDeptName("deptIds", "showDiv"); 
	} 

	deptOperator.init("deptIds", "showDiv"); 
	
	
	function checkRepetitiveLeader (){ 
		var leader = document.getElementById("deptLeaderId").value;
		if (leader == 0 ){
			alert("请选择牵头部门！");
			return false;
		}
	    var repetitiveLeader = deptOperator.getRepetitiveLeader("deptLeaderId", "deptIds"); 
	    if (repetitiveLeader.length > 1){ 
	        alert("【" + repetitiveLeader + "】为牵头部门，请重新选择！"); 
	        return false;
		} 
	} 

	  //增加文件输入框 
	  function addFileInput(){
	    var oFileInputTable = document.getElementById("fileUpload");
	    var fileIndex = oFileInputTable.childNodes.length + 1;
	    var oTR  = document.createElement("TR");
	    var oTD1 = document.createElement("TD");
	    var oTD2 = document.createElement("TD");

	    oTR.setAttribute("id","file_" + fileIndex);    
	    oTD1.setAttribute("style","text-align:right;");
	    oTD2.innerHTML = '<input type="file" name="attachments" cssClass="FileText" style="width:355px"/>&nbsp;<A href="javascript:deleteFileInput(' + fileIndex + ');">取消上传</A>';
	        
	    oTR.appendChild(oTD1);
	    oTR.appendChild(oTD2);
	    oFileInputTable.appendChild(oTR);
	  }

	  //删除文件输入框
	  function deleteFileInput(childId) {
	    var oTR = document.getElementById("file_" + childId);
	  	var oFileInputTable = document.getElementById("fileUpload");;
	  	oFileInputTable.removeChild(oTR);
	  }	

	  //删除附件
	  function deleteAttach(attachId){
	    if(confirm("确定要删除该附件信息吗?")){  
	    	var id = $("#id")[0].value;  
	        $.ajax({
	    		url: '${ctx}/jointTask/attaches/deleteAttach.do',
	    		type: 'post',
	    		dataType: 'json',
	    		data: {attachId : attachId},
	    		success: function(rst, textStatus){
	    	  		if(rst.result == "success"){	    	  		
	    	  			document.getElementById("attach_" + attachId).style.display = "none";
	    	  		  //window.location.href='${ctx}/jointTask/edit.do?model.id=' + id;
	    	  	  	}else{
	    	  	  	  alert("删除附件信息失败，请与管理员联系！");
	    	  	  	}
	    		}
	       });
	    }
	  }
</script>
</body>
</html>