<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/quake.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#saveFrm").validate();
	//初始化测震schema缺省值
	$('#minM').attr("min", '${emailDef.minM}');
	$('#maxM').attr("min", '${emailDef.minM}');
	//电子邮件缺省值
	if($('#email').val() == '') {
	    if('${user.dataEmail}' == '') {
		    $('#email').val('${user.email}');
		}else {
		    $('#email').val('${user.dataEmail}');
		}
	}
	
	$('#lon1').attr('min', -180);
	$('#lon1').attr('max', 180);
	$('#lon2').attr('min', -180);
	$('#lon2').attr('max', 180);        
	
	$('#lat1').attr('min', -90);
	$('#lat1').attr('max', 90);
	$('#lat2').attr('min', -90);
	$('#lat2').attr('max', 90);
	
	$('#minM').attr('min', ${emailDef.minM});
	if($('#minM').val() == '') {
	    $('#minM').val(${emailDef.minM});
	}
	if($('#maxM').val() == '') {
	    $('#maxM').val(10);
	}
	
	//验证震级范围
	$.validator.addMethod("mCheck", function(value, element) {
        var res;
        var m1 = parseInt(document.getElementById('minM').value);
  		var m2 = parseInt(document.getElementById('maxM').value);
  		if(m1 !=null & m2 !=null & m1 !=NaN & m2 !=NaN) {
  			if(m2 < m1) {
  				res = "err";
  			}else{
  				res = "ok";
  			}
  		}
        return res != "err";
    },"震级上限不能小于下限");
    
    //验证维度范围
	$.validator.addMethod("epiLatCheck", function(value, element) {
        var res;
        var l1 = parseInt(document.getElementById('lat1').value);
  		var l2 = parseInt(document.getElementById('lat2').value);
  		if(l1 !=null & l2 !=null & l1 !=NaN & l2 !=NaN) {
  			if(l2 < l1) {
  				res = "err";
  			}else{
  				res = "ok";
  			}
  		}
        return res != "err";
    },"维度结束值不能小于起始值");
    
    
    //验证经度范围
	$.validator.addMethod("lonCheck", function(value, element) {
        var res;
        var lo1 = parseInt(document.getElementById('lon1').value);
  		var lo2 = parseInt(document.getElementById('lon2').value);
  		if(lo1 !=null & lo2 !=null & lo1 !=NaN & lo2 !=NaN) {
  			if(lo2 < lo1) {
  				res = "err";
  			}else{
  				res = "ok";
  			}
  		}
        return res != "err";
    },"经度结束值不能小于起始值");
	
});


</script>
<title></title>
</head>
<body>
<div class="x-panel-body" align="center"><s:form action="save"
	theme="simple" id="saveFrm" method="POST">
	<s:hidden name="model.id" />
	<fieldset style="width: 700; margin: 25px; padding: 10px;">
	<legend>测震数据订阅</legend>

	<table width="90%" align="center">
		<tr>
			<td colspan="3"><%@ include file="/common/messages.jsp"%></td>
		</tr>
		<tr>
			<td align="right" width="150">地震目录：</td>
			<td style="text-align: left;"><s:select list="catalogs"
				name="model.catalog" id="catalog" cssStyle="width:180px;"
				listKey="cltName" listValue="clcName"></s:select></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">震级：</td>
			<td style="text-align: left;"><s:textfield size="8"
				name="model.minM" cssClass="number mCheck" id="minM" /> 至： <s:textfield
				size="8" name="model.maxM" cssClass="number mCheck" id="maxM" /></td>
			<td>(大于等于${emailDef.minM}的正数)</td>
		</tr>

		<tr>
			<td align="right">纬度：</td>
			<td style="text-align: left;"><s:textfield size="8"
				name="startEpiLat" cssClass="number epiLatCheck" id="lat1" /> 至： <s:textfield
				size="8" name="model.endEpiLat" cssClass="number epiLatCheck"
				id="lat2" /></td>
			<td>(度.度，-90至90)</td>
		</tr>
		<tr>
			<td align="right">经度：</td>
			<td style="text-align: left;"><s:textfield size="8"
				name="model.startEpiLon" cssClass="number lonCheck" id="lon1" /> 至：
			<s:textfield size="8" name="model.endEpiLon"
				cssClass="number lonCheck" id="lon2" /></td>
			<td>(度.度，-180至180)</td>
		</tr>
		<tr>
			<td align="right">电子邮件：</td>
			<td style="text-align: left;"><s:textfield size="30"
				name="model.emailAddr" cssClass="email required" id="email" /></td>
			<td></td>
		</tr>

	</table>
	</fieldset>
	<table width="90%" style="margin-bottom: 10px;margin-top: 10px;">
		<tr>
			<td style="text-align: center;"><s:submit value="保存"
				cssClass="button">&nbsp;&nbsp;</s:submit> <s:reset value="重置" cssClass="button"></s:reset></td>
		</tr>
	</table>
</s:form></div>

</body>
</html>