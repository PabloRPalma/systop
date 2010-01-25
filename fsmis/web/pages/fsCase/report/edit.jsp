<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>上报事件</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/autocomplete.jsp"%>
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
	height: 28;
}
</style>
</head>
<body>
<script type="text/javascript">
	Ext.onReady(function() {
		var tabs = new Ext.TabPanel( {
			renderTo : 'tabs',
			anchor : '100% 100%',
			height : 400,
			activeTab : 0,
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ {
				contentEl : 'basic',
				title : '事件信息'
			}, {
				contentEl : 'hospital',
				title : '处理结果'
			}, {
				contentEl : 'traffic',
				title : '处理企业'
			} ]
		});
	});
</script>
<div class="x-panel">
<div class="x-panel-header">上报事件</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td align="right"><a href="${ctx}/casereport/index.do">
				上报事件列表</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form id="save" action="save.do" method="post" theme="simple"
	validate="true">
<div id="tabs" style="margin-top: -1px;margin-left: -1px;margin-right: -1px;">
<div id="basic" class="x-hide-display"><s:hidden name="model.id" />
	<table id="mytable" height="360" style="margin-top: 5px; width: 800px"
		align="center">
		<s:hidden id="oneId" name="oneId"/>
		<s:hidden id="twoId" name="twoId"/>
		<tr>
			<td width="150" height="30" align="right">事件标题：</td>
			<td align="left" colspan="3"><s:textfield id="title"
				name="model.title" cssStyle="width:550px" cssClass="required" /><font
				color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right" height="30">事发时间：</td>
			<td align="left" colspan="3"><input type="text"
				name="model.caseTime" style="width: 150px"
				value='<s:property value="model.caseTime"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate required" /><font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right" height="30">案件类别：</td>
			<td align="left" colspan="3">
				 <select id="levelone" name="typeoneId" style="width:154px;" class="required">
					    <option value="">请选择...</option>
						<c:forEach items="${levelone}" var="item">
					       <option value="${item.id}" >${item.name}</option>
					    </c:forEach>
			      </select>
			     <select id="itemId" name="typetwoId" style="width:154px;">
					    <option value="">请选择...</option>
				 </select>
				 <font color="red">&nbsp;*</font>
			</td>
		</tr>
		<tr>
			<td align="right" >事发地点：</td>
			<td align="left" colspan="3"><s:textfield id="address"
				name="model.address" cssStyle="width:550px" cssClass="required" /><font
				color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right" height="30">举&nbsp;&nbsp;报&nbsp;&nbsp;人：</td>
			<td width="256" align="left"><s:textfield id="informer"
				name="model.informer" cssStyle="width:150px" />
			</td>
			<td width="136" height="30" align="right">举报人电话：</td>
			<td width="242" align="left"><s:textfield id="informerPhone"
				name="model.informerPhone" cssStyle="width:150px" />
			</td>
		</tr>
		<tr>
			<td align="right">事件描述：</td>
			<td align="left" colspan="3" style="vertical-align: top"><s:textarea
				id="descn" name="model.descn" cssStyle="width:550px; height:210px"
				cssClass="required" /> <font color="red">&nbsp;*</font></td>
		</tr>
	</table>
</div>
<div id="hospital" class="x-hide-display">
	<table id="mytable" height="360" style="margin-top: 5px; width: 800px"
		align="center">
		<s:hidden name="taskDetail.id" />
		<s:hidden name="task.id" />
		<tr>
			<td width="150" height="28" align="right">处&nbsp;&nbsp;理&nbsp;&nbsp;人：</td>
			<td align="left" colspan="3"><s:textfield id="processor"
				name="taskDetail.processor" cssStyle="width:550px" cssClass="required" /><font
				color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right" height="33">调查时间：</td>
			<td width="266" align="left"><input type="text"
				name="task.dispatchTime" style="width: 175px"
				value='<s:property value="task.dispatchTime"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate required" /><font color="red">&nbsp;*</font>
			</td>
			<td width="99" align="right">结案时间：</td>
			<td width="265" align="left">
				<input type="text"
				name="taskDetail.completionTime" style="width: 175px"
				value='<s:property value="taskDetail.completionTime"/>'
				onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm'})"
				class="Wdate required" /><font color="red">&nbsp;*</font>
			</td>
		</tr>
		<tr>
			<td align="right">处理过程：</td>
			<td align="left" colspan="3" style="vertical-align: top"><s:textarea
				id="process" name="taskDetail.process" cssStyle="width:550px; height:90px"
				cssClass="required" /> <font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">处理结果：</td>
			<td align="left" colspan="3" style="vertical-align: top"><s:textarea
				id="result" name="taskDetail.result" cssStyle="width:550px; height:90px"
				cssClass="required" /> <font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right">处理依据：</td>
			<td align="left" colspan="3" style="vertical-align: top"><s:textarea
				id="basis" name="taskDetail.basis" cssStyle="width:550px; height:90px"
				cssClass="required" /> <font color="red">&nbsp;*</font></td>
		</tr>
	</table>
</div>
<div id="traffic" class="x-hide-display">
	<table id="mytable" style="margin-top: 5px; width: 800px"
		align="center">
	  <s:hidden id="corpId" name="corp.id" />
		<tr>
			<td width="151" align="right" height="26">单位名称：</td>
			<td width="637" align="left"><s:textfield id="companyName" onblur="compCorpInfo(this.value)"
				name="corpName" cssStyle="width:550px" cssClass="required"/>
			<font color="red">&nbsp;*</font></td>
		</tr>
		<tr>
			<td align="right" height="26"> 单位地址：</td>
			<td align="left"><s:textfield id="companyAddress"
				name="corp.address" cssStyle="width:550px" /></td>
		</tr>
		<tr>
			<td align="right" height="26">法人名称：</td>
			<td align="left"><s:textfield id="legalPerson"
				name="corp.legalPerson" cssStyle="width:550px" /></td>
		</tr>
		<tr>
			<td align="right" height="26">生产许可证：</td>
			<td align="left"><s:textfield id="produceLicense"
				name="corp.produceLicense" cssStyle="width:550px" /></td>
		</tr>
		<tr>
			<td align="right" height="26">经营许可证：</td>
			<td align="left"><s:textfield id="businessLicense"
				name="corp.businessLicense" cssStyle="width:550px" /></td>
		</tr>
		<tr>
			<td align="right" height="26">卫生许可证：</td>
			<td align="left"><s:textfield id="sanitationLicense"
				name="corp.sanitationLicense" cssStyle="width:550px" /></td>
		</tr>
		<tr>
			<td align="right" height="20">经营范围：</td>
			<td align="left">
				<s:textarea id="operateDetails" name="corp.operateDetails" cssStyle="width:550px; height:180px" />
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
			<td style="text-align: center;"><s:submit value="保存"
				cssClass="button" />&nbsp;&nbsp; <s:reset value="重置"
				cssClass="button" /></td>
		</tr>
	</table>
</s:form></div>
<script type="text/javascript">
  $(function(){
	  $('#levelone').change(function() {
			var subid = $("#levelone")[0].value;
			if (subid == null || subid == '') {
				$('#itemId').empty();
				$('<option value=\'\'>请选择...</option>').appendTo('#itemId');
				return;
			}
			$.ajax({
				url: '${ctx}/casereport/getLevelTwo.do',
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
				}
			});
	    });
  });
  Ext.onReady(function(){
		var subid = document.getElementById("oneId").value;
		var twoid = document.getElementById("twoId").value;
		if (subid != null && subid != '') {
			$('#levelone').val(subid);
		}
	      $.ajax({
			     url: '${ctx}/casereport/getLevelTwo.do',
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
</script>
<script type="text/javascript">
    window.onload = function(){
    // jQuery自动装配    
    $.ajax({
		url: '${ctx}/casereport/getCorpOfCounty.do',
		type: 'post',
		dataType: 'json',
		success: function(corps, textStatus){
			$("#companyName").autocomplete(corps, {
       			matchContains: true,
        		minChars:1,
        		width:'154px', 
        		scrollHeight: 200
      		});
		}
	});
  };
  function compCorpInfo(cName){
	//alert(cName);
    $.ajax({
		url: '${ctx}/casereport/getCorpByName.do',
		type: 'post',
		dataType: 'json',
		data: {corpName : cName},
		success: function(rst, textStatus){
			var companyAddress = document.getElementById('companyAddress');
			var legalPerson = document.getElementById('legalPerson');
			var produceLicense = document.getElementById('produceLicense');
			var businessLicense = document.getElementById('businessLicense');
			var sanitationLicense = document.getElementById('sanitationLicense');
			var operateDetails = document.getElementById('operateDetails');
			if (rst.id != null) {
				document.getElementById('corpId').value = rst.id;
				companyAddress.value = rst.address;
				legalPerson.value = rst.legalPerson;
				produceLicense.value = rst.produceLicense;
				businessLicense.value = rst.businessLicense;
				sanitationLicense.value = rst.sanitationLicense;
				operateDetails.value = rst.operateDetails;
				companyAddress.readOnly = 'isReadOnly';
				legalPerson.readOnly = 'isReadOnly';
				produceLicense.readOnly = 'isReadOnly';
				businessLicense.readOnly = 'isReadOnly';
				sanitationLicense.readOnly = 'isReadOnly';
				operateDetails.readOnly = 'isReadOnly';
			}else{
				document.getElementById('companyAddress').value = '';
				document.getElementById('legalPerson').value = '';
				document.getElementById('produceLicense').value = '';
				document.getElementById('businessLicense').value = '';
				document.getElementById('sanitationLicense').value = '';
				document.getElementById('operateDetails').value = '';
				companyAddress.readOnly = '';
				legalPerson.readOnly = '';
				produceLicense.readOnly = '';
				businessLicense.readOnly = '';
				sanitationLicense.readOnly = '';
				operateDetails.readOnly = '';
			}
		}
	});
  }
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#save").validate();
	});
</script>
</body>
</html>