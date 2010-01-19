<?xml version="1.0" encoding="utf-8"?>
<%@ page contentType="text/vnd.wap.wml; charset=utf-8" language="java" %>
<%@ include file="common.jsp" %>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.3//EN" "http://www.wapforum.org/DTD/wml13.dtd" >
<wml>
<card title="食品安全系统WAP举报平台 > 身份验证">
<p>
<b>请在下面的输入框中填入系统返回的身份验证码！</b>
<table columns="2">	
	<tr>
		<td><b>验证码：</b></td>
		<td>
			<input type="text" name="secureStr" size="50" value="${secureStr}"/>【必填】
		</td>
	</tr>	
</table>
<table columns="2">
<tr>
	<td>
		<anchor>
			返回首页
			<go href="${ctx}/wap/index.do"></go>			
		</anchor>		
	</td>
	<td>
		<anchor>
			身份验证
			<go href="${ctx}/wap/identitComp.do" method="post">
				<postfield name="secureStr" value="$(secureStr)"/>
			</go>
		</anchor>
	</td>
</tr>
</table>
<b>${message}</b>
</p>
</card>
</wml>