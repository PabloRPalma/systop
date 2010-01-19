<?xml version="1.0" encoding="utf-8"?>
<%@ page contentType="text/vnd.wap.wml; charset=utf-8" language="java" %>
<%@ include file="common.jsp" %>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.3//EN" "http://www.wapforum.org/DTD/wml13.dtd" >
<wml>
<card title="食品安全系统WAP举报平台 > 企业列表 > 企业信息查看">
<p>
<table columns="2">
	<tr>
		<td><b>［企业名称］:</b></td>
		<td>${company.name}</td>
	</tr>
	<tr>
		<td><b>［企业地址］:</b></td>
		<td>${company.address}</td>
	</tr>
	<tr>
		<td><b>［企业法人］:</b></td>
		<td>${company.legal_person}</td>
	</tr>
	<tr>
		<td><b>［营业执照］:</b></td>		
		<td>${company.business_license}</td>
	</tr>
	<tr>
		<td><b>［生产许可证］:</b></td>
		<td>${company.produce_license }</td>
	</tr>
	<tr>
		<td><b>［卫生许可证］:</b></td>		
		<td>${company.sanitation_license }</td>
	</tr>
	<tr>
		<td><b>［企业编号］:</b></td>
		<td>${company.code }</td>
	</tr>
	<tr>
		<td><b>［固定电话］:</b></td>
		<td>${company.phone}</td>
	</tr>
	<tr>
		<td><b>［诚信等级］:</b></td>
		<td>${company.integrity_grade}</td>
	</tr>
	<tr>
		<td><b>［经营范围］:</b></td>
		<td>${company.operate_details }</td>
	</tr>
</table>
<table columns="2">
<tr>
	<td>
		<anchor>
			返回列表页
			<go href="${ctx}/wapcorp/index.do"></go>			
		</anchor>
		<anchor>
			返回首页
			<go href="${ctx}/wap/index.do"></go>			
		</anchor>		
	</td>
</tr>
</table>
</p>
</card>
</wml>