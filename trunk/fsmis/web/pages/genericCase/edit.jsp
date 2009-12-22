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
	  $('#levelone').change(function() {
			var subid = $("#levelone")[0].value;
			if (subid == null || subid == '') {
				$('#itemId').empty();
				$('<option value=\'\'>请选择...</option>').appendTo('#itemId');
				return;
			}
			
			$.ajax({
				url: '${ctx}/genericcase/getLevelTwo.do',
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
  
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header"></div>
<div class="x-toolbar">&nbsp;</div>
<div><%@ include file="/common/messages.jsp"%></div>
<div align="center">
<s:form action="save.do" id="save" method="post" theme="simple" validate="true" onsubmit="return valileader()">
	<s:hidden name="model.id" />
	<s:hidden name="mesId"/>
	<s:hidden name="eventInfoId"/>
	<fieldset style="width: 800px; padding: 10px 10px 10px 10px;">
	<legend>编辑事件</legend>
	<table width="750px" align="center">
		<tr>
			<td align="right" width="200">事件标题：</td>
			<td align="left"><s:textfield id="name"
				name="model.title" cssStyle="width:380px" cssClass="required"/><font color="red">&nbsp;*</font>
			</td>
		</tr>
		
		<tr>
			<td align="right">事发时间：</td>
			<td align="left"><input type="text" name="model.eventDate"  style="width: 160px"
				value='<s:date name="model.eventDate" format="yyyy-MM-dd HH:mm:ss"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				class="Wdate" /><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">事发地点：</td>
			<td align="left"><s:textfield id="softVersion"
				name="model.address" cssStyle="width:380px" cssClass="required"/><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">事件编号：</td>
			<td align="left"><s:textfield id="code"
				name="model.code" cssStyle="width:380px" cssClass="required"/><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">事件类别：</td>
			<td align="left">
				 <select id="levelone" name="typeoneId" style="width:120px;">
					    <option value="">请选择...</option>
						<c:forEach items="${levelone}" var="item">
					       <option value="${item.id}">${item.name}</option>
					    </c:forEach>
			      </select>
			     <select id="itemId" name="typetwoId" style="width:120px;">
					    <option value="">请选择...</option>
				 </select>
				 <font color="red">&nbsp;*</font>
			</td>
		</tr>
		<tr>
			<td align="right">所属区县：</td>
			<td align="left">		
				<div id="comboxWithTree"  style="float: left;margin-left:2px;"></div><font color="red" style="margin-left:2px;">*</font>
				<s:hidden name="model.dept.id" id="deptId"></s:hidden>
			</td>
		</tr>
		<tr>
			<td align="right">报告人：</td>
			<td align="left"><s:textfield id="informer"
				name="model.informer" cssStyle="width:120px" /></td>
		</tr>
		<tr>
			<td align="right">报告人电话：</td>
			<td align="left"><s:textfield id="informerPhone"
				name="model.informerPhone" cssStyle="width:120px" /></td>
		</tr>
		<tr>
			<td align="right">事件描述：</td>
			<td align="left"><s:textarea id="descn" name="model.descn"
				cssStyle="width:380px; height:150px" cssClass="required"/><font color="red">&nbsp;*</font></td>
		</tr>
		<c:if test="${model.status == '2'}">
			<td align="right">是否核实：</td>
			<td align="left">否<input name="isDone" value="0" type="radio" />是<input name="isDone" value="1" type="radio" /></td>
		</c:if>
	</table>
	</fieldset>
	<table width="600px" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;">
			  <s:submit value="保存" 	cssClass="button" /> 
			  <s:reset value="重置" cssClass="button" />
			</td>
		</tr>
	</table>
</s:form></div>
</div>

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
<script type="text/javascript">
	$(document).ready(function() {
	$("#save").validate();
});
</script>
</body>
</html>