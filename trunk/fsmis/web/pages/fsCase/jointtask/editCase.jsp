<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.List"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title></title>
<%@include file="/common/extjs.jsp" %>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
<link href="${ctx}/styles/treeSelect.css" type='text/css' rel='stylesheet'>
<script type="text/javascript">
  function valileader(){
    var code = document.getElementById("code").value;
    var informerPhone = document.getElementById("informerPhone").value;
    var informer = document.getElementById("informer").value;
   
    if(isNaN(code)){
      alert("事件编号必须为数字！");
      return false;
    }else if(isNaN(informerPhone)){
      alert("报告人电话必须为数字！");
      return false;
    }else if(parseInt(informer.length)>=10){
      alert("报告人字数太多！");
      return false;
    }
	
  }

  $(function(){
	  $('#levelOne').change(function() {
			var subid = $("#levelOne")[0].value;
			if (subid == null || subid == '') {
				$('#itemId').empty();
				$('<option value=\'\'>请选择...</option>').appendTo('#itemId');
				return;
			}
			
			$.ajax({
				url: '${ctx}/fscase/getLevelTwo.do',
				type: 'post',
				dataType: 'json',
				data: {typeId : subid},
				success: function(rows, textStatus){
				   $('#itemId').empty();
				   $('<option value=\'\'>请选择...</option>').appendTo('#itemId');
				   for (var i = 0; i < rows.length; i ++) {
				   	var row = rows[i];
				   	$('<option value=' + row.id + '>' + row.name + '</option>').appendTo('#itemId');
				   }
					//alert(data.length);
				}
			});
	    });
  });

 
  Ext.onReady(function(){

			var subid = document.getElementById("oneId").value;
			var twoid = document.getElementById("twoId").value;
			if (subid != null && subid != '') {
				$('#levelOne').val(subid);
			}
	      $.ajax({
			     url: '${ctx}/fscase/getLevelTwo.do',
			     type: 'post',
				 dataType: 'json',
				 data: {typeId : subid},
				 success: function(rows, textStatus){
				 $('#itemId').empty();
				 $('<option value=\'\'>请选择...</option>').appendTo('#itemId');
				 for (var i = 0; i < rows.length; i ++) {
					  var row = rows[i];
					   $('<option value=' + row.id + '>' + row.name + '</option>').appendTo('#itemId');
				 }
				 if (twoid != null && twoid != '') {
						$('#itemId').val(twoid);
				 }
			}
	     }); 		    
	
  });

  //文本编辑组件
  function preFckEditor(){
	var fckEditor = new FCKeditor( 'descn' ) ;
    fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
    fckEditor.ToolbarSet = 'BasicA';
    fckEditor.Height = 300;
    fckEditor.Width = 520;
    fckEditor.ReplaceTextarea();
  }
</script>
</head>
<body onload="preFckEditor()">
<div class="x-panel">
<div class="x-panel-header">案件信息</div>
<div><%@ include file="/common/messages.jsp"%></div>
<div align="center">
<s:form action="fsCaseSave.do" id="fsCaseSave" method="post" theme="simple" validate="true" onsubmit="return valileader()">
    <input type="hidden" name="isMultipleCase" value="${isMultipleCase}"></input>
    <input type="hidden" name="modelId" value="${param['modelId'] }"></input>
    <input type="hidden" name="smsReceiveId" value="${smsReceiveId}"></input>
    <s:hidden name="isMultiple"></s:hidden>
	<s:hidden name="model.fsCase.id" />
	<s:hidden name="mesId"/>
	<s:hidden id="oneId" name="oneId"/>
	<s:hidden id="twoId" name="twoId"/>
	<fieldset style="width: 700px; padding: 10px 10px 10px 10px;">
	<legend>编辑案件</legend>
	<table width="700px" align="left">
		<tr>
			<td align="right" width="15%">案件标题：</td>
			<td align="left" colspan="3"><s:textfield id="title"
				name="model.fsCase.title" cssStyle="width:515px" cssClass="required"/><font color="red">&nbsp;*</font>
			</td>
		</tr>
		
		<tr>
			<td align="right" >案件编号：</td>
			<td align="left" width="40%"><s:textfield id="code"
				name="model.fsCase.code" cssStyle="width: 135px" cssClass="required"/><font color="red">&nbsp;*</font></td>		
			
			<td align="right">案发时间：</td>
			<td align="left" width="40%"><input type="text" name="model.fsCase.caseTime"  style="width: 158px" 
				value='<s:date name="model.fsCase.caseTime" format="yyyy-MM-dd HH:mm:ss"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				class="Wdate" readonly="readonly" class="required"/><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right" >案发地点：</td>
			<td align="left" colspan="3"><s:textfield id="address"
				name="model.fsCase.address" cssStyle="width:515px" cssClass="required"/><font color="red">&nbsp;*</font></td>
		</tr>

		<tr>
			<td align="right" >报&nbsp;&nbsp;告&nbsp;&nbsp;人：</td>
			<td align="left" width="40%"><s:textfield id="informer"
				name="model.fsCase.informer" cssStyle="width: 135px" cssClass="required"/><font color="red">&nbsp;*</font></td>		
			
			<td align="right">报告人电话：</td>
            <td align="left" width="40%"><s:textfield id="informerPhone"
				name="model.fsCase.informerPhone" cssStyle="width:158px" cssClass="required"/><font color="red">&nbsp;*</font></td>
		</tr>					
		<tr>
			<td align="right" >案件类别：</td>
			<td align="left" colspan="3">
				 <select id="levelOne" name="typeOneId" style="width:141px;" class="required">
					    <option value="">请选择...</option>
						<c:forEach items="${levelOne}" var="item">
					       <option value="${item.id}" >${item.name}</option>
					    </c:forEach>
			      </select>
			     <select id="itemId" name="typeTwoId" style="width:141px;">
					    <option value="">请选择...</option>
				 </select>
				 <font color="red">&nbsp;*</font>
			</td>
		</tr>	
		<tr>
			<td align="right">案件描述：</td>
			<td align="left" colspan="3">
				<textarea id="descn" name="model.fsCase.descn" cols="45" rows="3" class="required" >${model.fsCase.descn}</textarea>
				<font color="red">*</font>
			</td>
		</tr>								
	</table>
	</fieldset>
	<table width="500px" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;">
			  <s:submit value="保存" cssClass="button" />&nbsp;&nbsp; 
			  <s:reset value="重置" cssClass="button" />
			</td>
		</tr>
	</table>
</s:form></div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
	$("#fsCaseSave").validate();
});
</script>
</body>
</html>