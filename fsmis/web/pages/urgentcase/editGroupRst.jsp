<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急事件处理结果</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
	function jsonFormValue() {
		var num = document.getElementsByName('jsonValue').length;
		var val = document.getElementsByName('jsonValue')[0].value;
		val = val.replace(/:/ig," ");
		for(i=1; i<num; i++) {
			var rval = document.getElementsByName('jsonValue')[i].value;
			rval = rval.replace(/:/ig," ");
			if(rval == "" || rval == null) {
				val = val + ":" + null;
			} else {
				val = val + ":" + rval;
			}
		}
		//将回车换行替换为分号加空格
		var reg = new RegExp(/\r\n/ig);
		var valRst = val.replace(reg,"; ");
		valRst = valRst.replace(/\n/ig,"; ");
		//alert(valRst);
		var caseId = document.getElementById('caseId').value;
		var groupId = document.getElementById('groupId').value;
		$.ajax({
			url: '${ctx}/urgentcase/saveGroupResult.do',
			type: 'post',
			dataType: 'json',
			data: {rstValue : valRst, caseId : caseId, groupId : groupId},
			success: function(rst, textStatus){
				window.location = '${ctx}/urgentcase/view.do?model.id='+ caseId +'&actId=3';
				//window.location = '${ctx}/urgentcase/index.do';
			}
	  	 });
	}
	function goBack(){
		var caseId = document.getElementById('caseId').value;
		window.location = '${ctx}/urgentcase/view.do?model.id='+ caseId +'&actId=3';
	}
    /**
     * 字段验证
	function checkFiled(filedVal) {
	  if (filedVal != null && filedVal != '') {
		document.getElementById('errorinfo').innerHTML = "";
		return true;
	  } else {
		document.getElementById('errorinfo').innerHTML = "必填字段";
		return false;
	  }
	}
	*/
</script>
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
<div class="x-panel">
<div class="x-panel-header">编辑处理结果</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td align="right"><a href="${ctx}/urgentcase/view.do?model.id=${caseId}&actId=3"><img
					src="${ctx}/images/icons/house.gif" /> 返回</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<div align="center">
	<fieldset style="width: 650px; padding: 10px 10px 10px 10px;">
	<legend>处理结果</legend>
	<s:hidden id="caseId" name="caseId" />
	<s:hidden id="groupId" name="groupId" />
	<table id="mytable" align="left" style="margin-top: 5px">
		<c:forEach var="entry" items="${resultMap}" varStatus="status">
			<tr>
				<td align="right" width="100">${entry.key }：</td>
				<c:if test="${entry.key eq '处理时间'}">
				  <td align="left" colspan="3">
					<c:if test="${entry.value == 'null'}">
						<input type="text" name="jsonValue" style="width: 148px"
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						class="Wdate" />
					</c:if>
					<c:if test="${entry.value != 'null'}">
						<input type="text" name="jsonValue" style="width: 148px"
						value='${entry.value }'
						onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						class="Wdate" />
					</c:if>
				  </td>
				</c:if>
				<c:if test="${entry.key eq '处理过程'}">
				  <td align="left" colspan="3">
					<c:if test="${entry.value == 'null'}">
						<textarea name="jsonValue" rows="6" style="width: 400px"></textarea>
					</c:if>
					<c:if test="${entry.value != 'null'}">
						<textarea name="jsonValue" rows="6" style="width: 400px">${entry.value }</textarea>
					</c:if>
				  </td>
				</c:if>
				<c:if test="${entry.key eq '处理结果'}">
				  <td align="left" colspan="3">
					<c:if test="${entry.value == 'null'}">
						<textarea name="jsonValue" rows="6" style="width: 400px" onblur="checkFiled(this.value)"></textarea>
					</c:if>
					<c:if test="${entry.value != 'null'}">
						<textarea name="jsonValue" rows="6" style="width: 400px">${entry.value }</textarea>
					</c:if>
					<!--<s:label id="errorinfo"></s:label>-->
				  </td>
				</c:if>
				<c:if test="${entry.key ne '处理时间' && entry.key ne '处理过程' && entry.key ne '处理结果'}">
				  <td align="left" colspan="3">
					<c:if test="${entry.value == 'null'}">
						<input name="jsonValue" style="width: 400px" />
					</c:if>
					<c:if test="${entry.value != 'null'}">
						<input name="jsonValue" style="width: 400px" value="${entry.value }" />
					</c:if>
				  </td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	</fieldset>
	</div>
	<table width="100%" style="margin-bottom: 0px;">
		<tr>
			<td height="5"></td>
		</tr>
		<tr>
			<td style="text-align: center;"> 
				<input value="保存" onclick="jsonFormValue()" size="9" style="text-align: center;cursor: auto;" type="button" class="button"/>&nbsp;
				<input value="返回" onclick="goBack()" size="9" style="text-align: center;cursor: auto;" type="button" class="button"/>		
			</td>
		</tr>
	</table>
</div>
</body>
</html>