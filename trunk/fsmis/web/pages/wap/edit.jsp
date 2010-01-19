<?xml version="1.0" encoding="utf-8"?>
<%@ page contentType="text/vnd.wap.wml; charset=utf-8" language="java" %>
<%@ include file="common.jsp" %>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.3//EN" "http://www.wapforum.org/DTD/wml13.dtd" >
<wml>
<card title="食品安全系统WAP举报平台 > 我要举报">
<p>
<b>请按照指定格式如实填写以下举报信息，所有项目均为必填！</b>
<table columns="2">	
	<tr>
		<td><b>事件标题：</b></td>
		<td>
			<input type="text" name="model.title" size="50" value="${model.title}"/>【必填】
		</td>
	</tr>
	<tr>
		<td><b>简要描述：</b></td>
		<td>
			<input type="text" name="model.descn" size="50" value="${model.descn}"/>【必填】
		</td>
	</tr>
	<tr>
		<td><b>事发时间：</b></td>
		<td>
			<input type="text" name="reportTime" size="20" />【必填，格式为2009-05-03 13:54，共16位（含空格）】
		</td>
	</tr>
	<tr>
		<td><b>事发地点：</b></td>		
		<td>
			<input type="text" name="model.addr" size="50" value="${model.addr}"/>【必填】
		</td>
	</tr>
	<tr>
		<td><b>举报人：</b></td>
		<td>
			<input type="text" name="model.reporter" size="20" value="${model.reporter}"/>【必填】
		</td>
	</tr>
	<tr>
		<td><b>举报电话：</b></td>		
		<td>
			<input type="text" name="model.phoneNo" size="20" value="${model.phoneNo}"/>【必填，格式为8-20位数字】
		</td>
	</tr>	
</table>
<hr/>
<table columns="2" width="100%">
<tr>
	<td>
		<anchor>
			返回首页
			<go href="${ctx}/wap/index.do"></go>			
		</anchor>		
	</td>
	<td>
		<anchor>
			举报
			<go href="${ctx}/wap/save.do" method="post">
				<postfield name="model.title" value="$(model.title)"/>
				<postfield name="model.descn" value="$(model.descn)"/>
				<postfield name="reportTime" value="$(reportTime)"/>
				<postfield name="model.addr" value="$(model.addr)"/>
				<postfield name="model.reporter" value="$(model.reporter)"/>
				<postfield name="model.phoneNo" value="$(model.phoneNo)"/>
			</go>
		</anchor>
	</td>
</tr>
</table>
<b>${message}</b>
</p>
</card>
</wml>