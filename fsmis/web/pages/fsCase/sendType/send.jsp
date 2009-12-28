<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp"%>

<script type="text/javascript"><!--
function validate(bb){
		var alreadycheck = false;
		for(var i=0;i<bb.deptsID.length;i++){
	  		if(bb.deptsID[i].checked) {
	  			alreadycheck=true;
	  			break;
	  		}
		}
		/*去除主办部门
		var hasdeptleader = false;
		if(document.getElementById('deptleaderId').value !=''){
			hasdeptleader = true;
		}
		
		if(!(hasdeptleader || alreadycheck)){
			alert('请选择部门或主办部门！');
			return false;
		}*/
		if(!alreadycheck){
			alert('请选择执行部门！');
			return false;
		}
		/*
		for(var j=0;j<bb.deptsID.length;j++){
			if(bb.deptsID[j].checked) {
	  			if(bb.deptsID[j].value == document.getElementById('deptleaderId').value) {
	  				
	  				alert('主办部门与部门重复！');
	  				return false;
	  			}
	  		}
		}*/
		
		var date = document.getElementById('date').value;
		if(date == ''){
			alert('请填写任务完成时间！');
			return false;
		}
		var title = document.getElementById('title').value;
		if(title == ''){
			alert('请填写任务标题！');
			return false;
		}
		var desc = document.getElementById('desc').value;
		if(desc == ''){
			alert('请填写任务描述！');
			return false;
		}
		return true;
}

--></script>
<script type="text/javascript"> 

  //增加文件输入框 
  function addFileInput(){
    var oFileInputTable = document.getElementById("fileUpload");
    var fileIndex = oFileInputTable.childNodes.length + 1;
    var oTR  = document.createElement("TR");
    var oTD1 = document.createElement("TD");
    var oTD2 = document.createElement("TD");

    oTR.setAttribute("id","file_"+fileIndex);    
    oTD1.setAttribute("style","text-align:right;");
    oTD2.innerHTML = '<input type="file" name="attachments" cssClass="FileText" style="width:200px"/>&nbsp;<A href="javascript:deleteFileInput('+fileIndex+');"><img src="${ctx}/images/icons/delete.gif"></A>';
        
    oTR.appendChild(oTD1);
    oTR.appendChild(oTD2);
    oFileInputTable.appendChild(oTR);
  }

  //删除文件输入框
  function deleteFileInput(childId) {
    var oTR = document.getElementById("file_"+childId);
  	var oFileInputTable = document.getElementById("fileUpload");;
  	oFileInputTable.removeChild(oTR);
  }
</script>
<script type="text/javascript">
  function deleteAttachment(attachmentId) {
  	if (confirm("确定要删除吗？")) {
    	deleteDiv(attachmentId);
        ArticleAction.removeAttachments(attachmentId,
  	function (msg) {
  	  if (msg == 'success') {
  	    alert('删除成功！');
  	  } else if(msg == 'error') {
  	    alert('文章下存在附件不能删除。');
  	  }
  	}
  );
}
}
	function deleteDiv(attachmentId) {
		document.getElementById('attachments'+attachmentId).style.display='none'; 
		document.getElementById('attachments'+attachmentId).innerHTML=""; 
		document.getElementById('attachments'+attachmentId).removeNode(); 
	}
	
	function getDept(currProvince)
    {  
      document.getElementById("selDept").value=currProvince;
           
    }
</script>
<script type="text/javascript" language="javascript"
	src="${ctx}/scripts/fsecurity/single_multi_task_dept_oper.js"></script>
<script language="javascript">
function init(){ 
	var title = document.getElementById("title");
	title.value = "${singleTitle}";
	//初始化部门操作管理对象
	deptsOperManager.init(document.getElementsByName("deptsID"));
	document.getElementById("selectDepts").innerHTML =  deptsOperManager.getSelectedDepts();
	document.getElementById("selectDeptNames").value = deptsOperManager.getSelectedDepts();
} 
</script>
</head>
<body onload="init();">
<div class="x-panel">
<div class="x-panel-header">协调指挥&nbsp;>&nbsp;单体事件管理&nbsp;>&nbsp;事件列表&nbsp;>&nbsp;事件查看&nbsp;>&nbsp;派遣任务</div>
<div class="x-toolbar">&nbsp;</div>
<div><%@ include file="/common/messages.jsp"%></div>
<div align="center"><s:form action="/task/save.do" method="post"
	theme="simple" enctype="multipart/form-data">

	<input type="hidden" name="model.fsCase.id" value="${fsCase.id}"></input>
	<fieldset style="width: 800px; padding: 10px 10px 10px 10px;" class="">
	<legend> 派遣信息</legend>
	<table width="550px" align="center">
		<tr>
			<td align="right">执行部门：</td>
			<td align="left">
			<div id="selectDepts"
				style="width: 400px; word-break: break-all; color: red;"></div>
			<s:hidden id="selectDeptNames" name="selectDeptNames"></s:hidden></td>
		</tr>
		<tr>
			<td><input type="text" name="deptIds" value="524289"></input></td>
		</tr>
		<tr>
			<td align="right">请点选执行部门：</td>
			<td align="left">
			<div
				style="border: 1px solid #099EBD; OVERFLOW-Y: scroll; width: 400px; SCROLLBAR-ARROW-COLOR: #e8e8e8; SCROLLBAR-BASE-COLOR: #e8e8e8; HEIGHT: 90px">
			<%--fs:deptCheckbox senttypeId="${model.id}" name="deptsID" col="4"></fs:deptCheckbox>--%>
			<c:forEach items="${depts}" var="dept">
				<tr>
					<td><input type="checkbox" name="deptIds" value="${dept.id }"></input></td>
				</tr>
			</c:forEach>
			</div>
			</td>
		</tr>
		<tr>
			<td align="right">任务标题：${fsCase.title }</td>
			<td align="left"><s:textfield id="title" name="title"
				cssStyle="width:400px;" /><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">任务完成时间：</td>
			<td align="left"><input id="date" type="text"
				name="model.endTime"
				value='<s:date name="model.endTime" format="yyyy-MM-dd HH:mm:ss"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				class="Wdate" /><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">任务描述：</td>
			<td align="left"><s:textarea id="desc" name="model.desc"
				cssStyle="width:400px; height:100px;" /><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">附件：</td>
			<td bgcolor="#EBEBEB" style="padding: 10 5 2 5;"><a href="#"
				onclick="javascript:addFileInput();"> <img
				src="${ctx}/images/icons/file_add.gif">增加附件&nbsp; </a>
			<tbody id="fileUpload"></tbody>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
	</table>
	</fieldset>
	<table width="600px" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;"><s:submit value="确认派遣任务"
				cssClass="button" onclick="return validate(this.form);" /> <s:reset
				value="重置" cssClass="button" /></td>
		</tr>
	</table>
</s:form></div>
</div>
<script type="text/javascript">
/*$(function() {
   if($('#desc').val() == '') {
      $('#desc').val("检查该经营户的工商执照及卫生许可证，并对该问题进行处理。");
   }
 
});*/
</script>
</body>
</html>