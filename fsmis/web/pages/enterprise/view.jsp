<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>企业信息查看</title>
<%@include file="/common/meta.jsp"%>
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-min.css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript">
$(function(){
	var hasReward = $('#rewardContent').val();
	var punishListSize = $('#psRecordSize').val();
	if (hasReward == "" || hasReward == null) {
		$('#reward').hide();
	}
	if (punishListSize == 0) {
		$('#punish').hide();
	}
	// Tabs
	$('#tabs').tabs();
});
</script>
<style type="text/css">
#mytable {
	border: 1px solid #A6C9E2;
	margin-left: -21px;
	margin-top: -10px;
	width: 80%;
	border-collapse: collapse;
}

#mytable td {
	border: 0px solid #A6C9E2;
	height: 26;
}
</style>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">诚信企业管理&nbsp;>&nbsp;企业列表&nbsp;>&nbsp;企业信息</div>
<div class="x-toolbar">
    <table width="100%">
      <tr>
        <td align="left">&nbsp;<font color="#15428B"><b>所属单位：${model.dept.name}</b></font></td>
        <td align="right">
          <a href="${ctx}/enterprise/index.do">
             <img src="${ctx}/images/icons/building_go.png" class="icon" border="0">企业列表&nbsp;&nbsp;
          </a>
        </td>
      </tr>
    </table>
</div>
<div class="x-panel-body">
<s:hidden id="psRecordSize" name="psRecordSize" />
<s:hidden id="rewardContent" name="model.integrityRecord" />
<div id="tabs">
<ul>
	<li><a href="#tabs-1">基本信息</a></li>
	<li><a href="#tabs-2">企业简介</a></li>
	<li id="reward"><a href="#tabs-3">奖励记录</a></li>
	<li id="punish"><a href="#tabs-4">处罚记录</a></li>
</ul>
<div id="tabs-1" style="margin-bottom: -16px;">
  <table id="mytable" height="320">
	<tr>
		<td width="498">
		<table width="498" align="left" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td class="simple" width="119" align="right">企业名称：</td>
				<td class="simple" align="left" colspan="3"><s:textfield id="name"
					name="model.name" cssStyle="width:370px" readonly="true" theme="simple" /></td>
			</tr>
			<tr>
				<td width="119" align="right">地 址：</td>
				<td align="left" colspan="3"><s:textfield id="address"
					name="model.address" cssStyle="width:370px" cssClass="inputBorder"
					readonly="true" />
			</tr>
			<tr>
				<td width="119" align="right">法 人：</td>
				<td align="left" colspan="3"><s:textfield id="legalPerson"
					name="model.legalPerson" cssStyle="width:370px"
					cssClass="inputBorder" readonly="true" /></td>
			</tr>
			<tr>
				<td width="119" align="right">营业执照：</td>
				<td width="149" align="left"><s:textfield
					id="businessLicenseCode" name="model.businessLicenseCode"
					cssClass="inputBorder" readonly="true" /></td>
				<td align="right" width="80">有效期：</td>
				<td width="150" align="left"><input type="text"
					name="model.businessLicenseDate"
					value='<s:date name="model.businessLicenseDate" format="yyyy-MM-dd"/>'
					class="inputBorder" style="height: 16px"
					readonly="readonly" /></td>
			</tr>
			<tr>
				<td width="119" align="right">生产许可证：</td>
				<td width="149" align="left"><s:textfield
					id="produceLicenseCode" name="model.produceLicenseCode"
					cssClass="inputBorder" readonly="true" /></td>
				<td align="right" width="80">有效期：</td>
				<td width="150" align="left"><input type="text"
					name="model.produceLicenseDate"
					value='<s:date name="model.produceLicenseDate" format="yyyy-MM-dd"/>'
					class="inputBorder" style="height: 16px"
					readonly="readonly" /></td>
			</tr>
			<tr>
				<td width="119" align="right">卫生许可证：</td>
				<td width="149" align="left"><s:textfield
					id="sanitationLicenseCode" name="model.sanitationLicenseCode"
					cssClass="inputBorder" readonly="true" /></td>
				<td align="right" width="80">有效期：</td>
				<td width="150" align="left"><input type="text"
					name="model.sanitationLicenseDate"
					value='<s:date name="model.sanitationLicenseDate" format="yyyy-MM-dd"/>'
					class="inputBorder" style="height: 16px"
					readonly="readonly" /></td>
			</tr>
			<tr>
				<td width="119" align="right">企业编号：</td>
				<td width="149" align="left"><s:textfield id="code"
					name="model.code" cssClass="inputBorder" readonly="true" /></td>
				<td width="80" align="right">部 门：</td>
				<td width="150" align="left"><s:textfield
					name="model.dept.name"  cssClass="inputBorder" readonly="true" /></td>
			</tr>
			<tr>
				<td width="119" align="right">固 话：</td>
				<td width="149" align="left"><s:textfield id="phone"
					name="model.phone" cssClass="inputBorder" readonly="true" /></td>
				<td width="80" align="right">手 机：</td>
				<td width="150" align="left"><s:textfield id="mobile"
					name="model.mobile"  cssClass="inputBorder"
					readonly="true" /></td>
			</tr>
			<tr>
				<td width="119" align="right">诚信等级：</td>
				<td width="149" align="left"><s:textfield id="integrityGrade"
					name="model.integrityGrade" cssClass="inputBorder" readonly="true" />
				</td>
				<td width="80" align="right">邮 编：</td>
				<td width="150" align="left"><s:textfield id="zip"
					name="model.zip"  cssClass="inputBorder"
					readonly="true" /></td>
			</tr>
			<tr>
				<td width="119" align="right">经营范围：</td>
				<td align="left" colspan="3"><s:textfield id="operateDetails"
					name="model.operateDetails" cssStyle="width:370px;"
					cssClass="inputBorder" readonly="true" /></td>
			</tr>
			<tr>
				<td width="119" align="right">备 注：</td>
				<td align="left" colspan="3"><s:textfield id="remark"
					name="model.remark" cssStyle="width:370px;" cssClass="inputBorder"
					readonly="true" /></td>
			</tr>
		</table>
		</td>
		<td width="350" valign="top" align="left" style="border-left: 1px dotted #099EBD;">
		  <table width="100%" border="0">
			<tr>
				<td><c:if test="${not empty model.photoUrl}">
					<img width="300" height="225" src="${ctx}/${model.photoUrl}"
						onclick="openImg(this)" title="点击查看图片" />
				</c:if> <c:if test="${empty model.photoUrl}">
					<img width="300" height="225"
						src="${ctx}/images/corp/com_nophoto_big.gif" />
				</c:if></td>
			</tr>
			<tr>
				<td align="center" height="26" style="border-top: 1px dotted #099EBD; padding-top: 10px">
				企业照片</td>
			</tr>
		  </table>
		</td>
	</tr>
  </table>
</div>
<div id="tabs-2" style="margin-bottom: -16px;">
  <table id="mytable" height="320">
	<tr>
	  <td height="200" align="left" valign="top">
		<div style="line-height: 20px; padding: 10px 10px 10px 10px;">
			${model.descn}</div>
	  </td>
	</tr>
  </table>
</div>
<div id="tabs-3" style="margin-bottom: -16px;">
  <table id="mytable" height="320">
	<tr>
	  <td height="200" align="left" valign="top">
		<div style="line-height: 20px; padding: 10px 10px 10px 10px;">
			${model.integrityRecord}</div>
	  </td>
	</tr>
  </table>
</div>
<div id="tabs-4" style="margin-bottom: -16px;">
  <table id="mytable" height="320">
	<tr>
	  <td height="200" align="left" valign="top">
		<div style="line-height: 20px; padding: 10px 10px 10px 10px;"></div>
	  </td>
	</tr>
  </table>
</div>
</div>
</div>
</div>
</body>
</html>