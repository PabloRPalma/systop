<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/extjs.jsp" %>
<link href="${ctx}/styles/treeSelect.css" type='text/css' rel='stylesheet'>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
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
	height: 22;
}
</style>
<script type="text/javascript">
  function validate(){
	  if(checkCode()&&checkmobile()){
		  return true;
	  }
	  return false;
  }

  //异步删除照片
   function deletePhoto(){
    if(confirm("确定要删除该信息员的照片吗?")){           
    	var spsId = $("#spId")[0].value;
        $.ajax({
    		url: '${ctx}/supervisor/deletePhoto.do',
    		type: 'post',
    		dataType: 'json',
    		data: {spId : spsId},
    		success: function(rst, textStatus){
    	  		if(rst.result == "success"){
    	  		  document.getElementById("photoOperSpan1").innerHTML=
    	            "<div align='left'><img alt='照片' align='center' width='106' height='142' id='imgphoto' src='${ctx}/images/noSupervisor.gif'/></div><input type='file' id='photo' name='photo' class='FileText' onchange='changeImg()'/>";
    	  	  	}else{
    	  	  	  alert("删除信息员照片失败，请与管理员联系！");
    	  	  	}
    		}
    	  });
    }
  }
  
  function changeImg(){
  	var img = document.getElementById("photo");
  	var imgphoto = document.getElementById("imgphoto");
  	imgphoto.src = img.value;
  }

  function checkCode(){
	  var code = document.getElementById("code").value;
	  if(isNaN(code)){
		  document.getElementById("codeinfo").innerHTML = '<font color="red">'+'<I>'+'必须是数字'+'</I>'+'</font>';
		  return false;
	  }
	  document.getElementById("codeinfo").innerHTML = '';
	  return true;
  }

  function checkmobile(){
	  var mobile = document.getElementById("mobile").value;
	  if(mobile == ""){
		  document.getElementById("mobileinfo").innerHTML = '';
		  return true;
	  }
	  if(isNaN(mobile)){
		  document.getElementById("mobileinfo").innerHTML = '<font color="red">'+'<I>'+'必须是数字'+'</I>'+'</font>';
		  return false;
	  }else{
		  if(mobile.lenght != 11){
			  document.getElementById("mobileinfo").innerHTML = '<font color="red">'+'<I>'+'位数不正确'+'</I>'+'</font>';
			  return false;
		  }
	  }
	  document.getElementById("mobileinfo").innerHTML = '';
	  return true;
  }
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">信息员添加</div>
<div class="x-toolbar">
	<table width="100%">
		<tr>
			<td align="right" height="22">
				<a href="${ctx}/supervisor/index.do">信息员列表</a>				
			</td>
		</tr>
	</table>
</div>
<div align="center">
  <s:form id="supervisorForm" action="save.do" method="post" theme="simple" validate="true" enctype="multipart/form-data" onsubmit="return validate()" >
  <fieldset style="width: 600px; padding: 50px 10px 10px 10px;">
	<legend>信息员信息</legend>
		<s:hidden id="spId" name="model.id" />
		<table width="532" align="center">
		  <tr>
			<td colspan="4"><%@ include file="/common/messages.jsp"%></td>
		  </tr>
		</table>
		<table id="mytable" width="532" align="center">
		  <tr>
			<td width="100" align="right">编　　号：</td>
			<td align="left">
		  		<s:textfield id="code" name="model.code" cssStyle="width:100px" cssClass="required" onblur="checkCode()"/>
		  		<span id="codeinfo"></span>
		  		<font color="red">&nbsp;*</font>
		  	</td>
	      </tr>
		  <tr>
			<td width="100" align="right">姓　　名：</td>
			<td colspan="2" align="left">
				<s:textfield id="name" name="model.name" cssClass="required" cssStyle="width:100px"/><font color="red">&nbsp;*</font>						
			</td>
		    <td width="150" rowspan="8" id="photoOperSpan1">
		    	<div align="left"> 
		    		<img alt="照片"  align="center" width="106" height="142" id="imgphoto" 
						src="<c:if test='${not empty model.photoUrl}'>${ctx}${model.photoUrl}</c:if>
							 <c:if test='${empty model.photoUrl}'> ${ctx}/images/noSupervisor.gif</c:if>"/>
	                  <c:choose>
	                    <c:when test="${empty model.photoUrl}">
	                      <s:file id="photo" name="photo" cssClass="FileText" onchange="changeImg()"></s:file>
	                    </c:when>
	                    <c:otherwise>
	                      <input name="button" type="button" onClick="deletePhoto()" value="删除照片"/>
	                    </c:otherwise>
	                  </c:choose>
            	</div>
            </td>
		  </tr>
		<c:if test="${empty model.id}">
			<tr>
			  <td align="right">身份证号：</td>
			  <td colspan="2" align="left">
			  	    <s:textfield id="idNumber" name="model.idNumber" cssStyle="width:180px" /></td>
			 </tr>
		 </c:if>
		  <tr>
				<td width="100" align="right">性　　别：</td>
				<td colspan="2" align="left"><s:radio id="gender" name="model.gender" list="{'男','女'}" cssStyle="border:0;"></s:radio></td>
		  </tr>
		  <tr>
			  <td align="right">出生日期：</td>
			  <td colspan="2" align="left"><input type="text" name="model.birthday" value='<s:date name="model.birthday" format="yyyy-MM-dd"/>'
					  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width:100px;height:16px" readonly="readonly"/></td>
		 </tr>
		<tr>
			<td width="100" align="right">单　　位：</td>
			<td colspan="3" align="left"><s:textfield id="unit" name="model.unit" cssStyle="width:180px"/></td>
	    </tr>
		<tr>
			<td width="100" align="right">职　　务：</td>
			<td colspan="3" align="left"><s:textfield id="duty" name="model.duty" cssStyle="width:180px" /></td>
	      </tr>
		<tr>
			<td width="100" align="right">所属部门：</td>
			<td class="simple" align="left" colspan="3">			
				<div id="comboxWithTree"  style="float: left"></div>
				<s:hidden name="model.dept.id" id="deptId" cssClass="required"></s:hidden><font color="red" style="margin-left:2px;">*</font>
			</td>
	    </tr>
	    <tr>
			<td width="100" align="right">负责人：</td>
			<td colspan="3" align="left">
			    <c:if test="${not empty model.isLeader}">
			    	<s:radio list="#{'1':'是', '0':'否'}" name="model.isLeader" id="model.isLeader" cssStyle="border:0;"></s:radio>
			    </c:if>
			    <c:if test="${empty model.isLeader}">
			    	<s:radio list="#{'1':'是', '0':'否'}" value="0" name="model.isLeader" id="model.isLeader" cssStyle="border:0;"></s:radio>
			    </c:if>
			</td>
	    </tr>
		<tr>
			<td width="100" align="right">移动电话：</td>
			<td colspan="2" align="left">
		  		<s:textfield id="mobile" name="model.mobile" cssStyle="width:180px" onblur="checkmobile()"/>
		  		<span id="mobileinfo"></span>
		  	</td>
		 </tr>
		 <tr>
			<td width="100" align="right">固定电话：</td>
			<td colspan="3" align="left">
				<s:textfield id="phone" name="model.phone" cssStyle="width:180px" />			
			</td>
		</tr>
		<tr>
			<td width="100" align="right">&nbsp;&nbsp;&nbsp;&nbsp;监管区域：</td>
			<td align="left" colspan="3">
				<s:textfield id="superviseRegion" name="model.superviseRegion" cssStyle="width:380px"/>
			</td>
		</tr>
	</table>
	</fieldset>
	<table width="532" align="center" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;">
				<s:submit value="保存" cssClass="button" /> 
				<s:reset value="重置" cssClass="button" />
			</td>
		</tr>
	</table>
  </s:form>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#supervisorForm").validate();
});
</script>
<script type="text/javascript" src="${ctx}/pages/admin/dept/edit.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	var dtree = new DeptTree({
		url : '/admin/dept/deptTree.do',
		parent : '<stc:loginUserDept showPath="false" propertyName="id" showTopDept="true"></stc:loginUserDept>',
		initValue : '${model.dept.name}',
		el : 'comboxWithTree',
		innerTree :'inner-tree',
		onclick : function(nodeId) {
		  Ext.get('deptId').dom.value = nodeId;
		}
	});
	dtree.init();	
});
</script>
</body>
</html>