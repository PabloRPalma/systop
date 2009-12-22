<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>企业信息查看</title>
<%@include file="/common/meta.jsp"%>
<style type="text/css">
	.inputBorder{
		border: 0px;
		border-bottom: 1px solid #099EBD;
	}
</style>
</head>
<body>
<script type="text/javascript">
function openImg(img){
	window.open(img.src);
}
</script>
<div class="x-panel">
<div class="x-panel-header">诚信企业管理&nbsp;>&nbsp;企业列表&nbsp;>&nbsp;企业信息</div>
<div align="center" >
  <fieldset style="width: 1002px; padding: 30px 0px 10px 0px;">
	<legend><font style="font-size: 15px; font-weight: bold;">企业信息</font></legend>
	<table width="990" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
		<td width="640">
		  <table width="498" align="center" border="0" cellpadding="2" cellspacing="1">
          <tr>
            <td width="119" align="right">企业名称：</td>
            <td align="left" colspan="3">
            	<s:textfield id="name" name="model.name" cssStyle="width:350px" cssClass="inputBorder" readonly="true" />
            </td>
          </tr>
          <tr>
            <td width="119" align="right">地　　址：</td>
            <td align="left" colspan="3">
            	<s:textfield id="address" name="model.address" cssStyle="width:350px" cssClass="inputBorder" readonly="true"/>
          </tr>
          <tr>
            <td width="119" align="right">法　　人：</td>
            <td align="left" colspan="3">
            	<s:textfield id="legalPerson" name="model.legalPerson" cssStyle="width:350px" cssClass="inputBorder" readonly="true"/>
            </td>
          </tr>
          <tr>
            <td width="119" align="right">工商营业执照：</td>
            <td width="149" align="left">
            	<s:textfield id="businessLicenseCode" name="model.businessLicenseCode" cssClass="inputBorder" readonly="true"/>
            </td>
            <td align="right" width="80">有效期：</td>
            <td width="150" align="left">
            	<input type="text" name="model.businessLicenseDate" value='<s:date name="model.businessLicenseDate" format="yyyy-MM-dd"/>'
				  class="inputBorder" style="width:115px;height:16px" readonly="readonly"/>
            </td>
          </tr>
          <tr>
            <td width="119" align="right">生产许可证：</td>
            <td width="149" align="left">
            	<s:textfield id="produceLicenseCode" name="model.produceLicenseCode" cssClass="inputBorder" readonly="true"/>
            </td>
            <td align="right" width="80">有效期：</td>
            <td width="150" align="left">
            	<input type="text" name="model.produceLicenseDate" value='<s:date name="model.produceLicenseDate" format="yyyy-MM-dd"/>'
				class="inputBorder" style="width:115px;height:16px" readonly="readonly"/>
            </td>
          </tr>
          <tr>
            <td width="119" align="right">卫生许可证：</td>
            <td width="149" align="left">
            	<s:textfield id="sanitationLicenseCode" name="model.sanitationLicenseCode" cssClass="inputBorder" readonly="true"/>
            </td>
            <td align="right" width="80">有效期：</td>
            <td width="150" align="left">
            	<input type="text" name="model.sanitationLicenseDate" value='<s:date name="model.sanitationLicenseDate" format="yyyy-MM-dd"/>'
				class="inputBorder" style="width:115px;height:16px" readonly="readonly"/>
            </td>
          </tr>
          <tr>
            <td width="119" align="right">企业编号：</td>
            <td width="149" align="left">
            	<s:textfield id="code" name="model.code" cssClass="inputBorder" readonly="true"/>
            </td>
            <td width="80" align="right">部　门：</td>
            <td width="150" align="left">
            	<s:textfield name="model.dept.name" cssClass="inputBorder" readonly="true"/>
            </td>
          </tr>
          <tr>
            <td width="119" align="right">固　话：</td>
            <td width="149" align="left">
            	<s:textfield id="phone" name="model.phone" cssClass="inputBorder" readonly="true"/>
            </td>
            <td width="80" align="right">手　机：</td>
            <td width="150" align="left">
            	<s:textfield id="mobile" name="model.mobile" cssStyle="width:115px" cssClass="inputBorder" readonly="true"/>
            </td>
          </tr>
          <tr>
            <td width="119" align="right">诚信等级：</td>
            <td width="149" align="left">
            	<s:textfield id="integrityGrade" name="model.integrityGrade" cssClass="inputBorder" readonly="true"/>
            </td>
            <td width="80" align="right">邮　编：</td>
            <td width="150" align="left">
            	<s:textfield id="zip" name="model.zip" cssStyle="width:115px" cssClass="inputBorder" readonly="true"/>
            </td>
          </tr>
          <tr>
            <td width="119" align="right">经营范围：</td>
            <td align="left" colspan="3">
            	<s:textfield id="operateDetails" name="model.operateDetails" cssStyle="width:350px;" cssClass="inputBorder" readonly="true"/>
            </td>
          </tr>
          <tr>
            <td width="119" align="right">备　　注：</td>
            <td align="left" colspan="3">
            	<s:textfield id="remark" name="model.remark" cssStyle="width:350px;" cssClass="inputBorder" readonly="true"/>
            </td>
          </tr>
        </table>
        </td>
		<td width="350" valign="top" style="border-left: 1px dotted #099EBD;">
			<table width="100%" border="0" >
				<tr>
					<td>
						<c:if test="${not empty model.photoUrl}">
							<img width="300" height="225" src="${ctx}/${model.photoUrl}" onclick="openImg(this)" title="点击查看图片"/>
						</c:if>
						<c:if test="${empty model.photoUrl}">
							<img width="300" height="225" src="${ctx}/images/corp/com_nophoto_big.gif"/>
						</c:if>
					</td>
				</tr>
				<tr>
		            <td align="center"  style="border-top: 1px dotted #099EBD; padding-top: 10px">
		            	企业照片
		            </td>
		        </tr>
			</table>
		</td>
	  </tr>
	  <tr>
	  	<td colspan="2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<div class="x-toolbar" style="padding:5px 5px 5px 5px">诚信记录</div>
					</td>
				</tr>
				<tr>
					<td height="" align="left">
						<table width="100%" border="0">
							<tr>
								<td align="center" style="border-bottom: 1px dashed #C0C0C0;border-right: 1px dashed #C0C0C0;">奖励记录</td>
								<td align="center" style="border-bottom: 1px dashed #C0C0C0;">处罚记录</td>
							</tr>
							<tr>
								<td width="50%" style="border-right: 1px dashed #C0C0C0;">
									<div style="line-height: 20px; padding: 10px 10px 10px 10px;">
										${model.integrityRecord}&nbsp;
									</div>
								</td>
								<td	width="50%" height="240">
									<!--  
									<iframe height="240" src="${ctx}/company/singleevents/index.do?companyId=${param['model.id']}" frameborder="0" width="100%" name="singleEvents"></iframe>
									-->
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	  	</td>
	  </tr>
	  <tr>
	  	<td colspan="2">
			<table width="100%">
				<tr>
					<td>
						<div class="x-toolbar" style="padding:5px 5px 5px 5px">企业简介</div>
					</td>
				</tr>
				<tr>
					<td height="200" align="left" valign="top">
						<div style="line-height: 20px; padding: 10px 10px 10px 10px;">
						${model.descn}
						</div>
					</td>
				</tr>
			</table>
	  	</td>
	  </tr>
	</table>
  </fieldset>
</div>
</div>
</body>
</html>