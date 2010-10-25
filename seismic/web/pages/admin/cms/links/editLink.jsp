<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp" %>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">友情链接管理</div>
    <div class="x-toolbar">
	 &nbsp;
    </div>
	<div><%@ include file="/common/messages.jsp"%></div> 
	<s:form namespace="/admin/links" action="saveLink" method="post" theme="simple" enctype="multipart/form-data" validate="true">
	<s:hidden id="model.id" name="model.id"/>
	<s:hidden id="model.orderId" name="model.orderId"/>
	<table width="600px" align="center">
		<tr>
			<td align="center">
			<fieldset style="margin:10px;">
              <legend>编辑链接信息</legend>
                <table>
                  <tr>
                     <td align="right">网站名称：</td>
                     <td align="left"><s:textfield id="model.siteName" name="model.siteName" size="40" maxlength="25"/><font color="red">&nbsp;*</font></td>
                  </tr>
                  <tr>
                    <td align="right">网站地址：</td>
                    <td align="left"><s:textfield id="model.siteUrl" name="model.siteUrl" size="55" maxlength="127"/><font color="red">&nbsp;*</font></td>
                  </tr>
                  <tr>
                    <td align="right">网站Logo地址：</td>
                    <td align="left">
                    	<s:file id="logo" name="logo" cssClass="FileText"/>
                    	<s:hidden id="model.siteLogo" name="model.siteLogo"/>
                    	<c:if test="${model.siteLogo != null && model.siteLogo != ''}">
			              <a href="${ctx}${model.siteLogo}" target="_blank">
			                <img src="${ctx}${model.siteLogo}" width="20" height="20" border="0" title="查看大图"/>  
			              </a>
			            </c:if>
                    </td>
                  </tr>
                  <tr>
                    <td align="right">电子邮件：</td>
                    <td align="left"><s:textfield id="model.sitMail" name="model.sitMail" size="40" maxlength="127"/></td>
                  </tr>
                  <tr>
                    <td align="right">网站简介：</td>
                    <td align="left"><s:textarea id="model.siteInfo" name="model.siteInfo" cols="55" rows="5"></s:textarea><td>
                  </tr>
                  <tr>
                    <td align="right">是否推荐站点：</td>
                    <td align="left"><s:radio list="states" name="model.isElite" cssStyle="border:0"/></td>
                  </tr>
                  <!-- tr>
                    <td align="right">是否通过审核：</td>
                    <td align="left"><s:radio list="states" name="model.isPassed"  cssStyle="border:0" /></td>
                  </tr-->
                  <tr>
                    <td align="right">友情链接类别：</td>
                    <td align="left">
                    	<s:select id="model.linkCatas.id" name="model.linkCatas.id" list="catas" headerKey="" headerValue="选择类别" listKey="id" listValue="name"/>
                    </td>
                  </tr>
                </table> 
              </fieldset>
              <table width="100%" style="margin-bottom:10px;">
				<tr>
					<td style="text-align:center;">
						<s:submit value="保存" cssClass="button"/>
						<s:reset value="重置" cssClass="button"/>
                    </td>
              	</tr>
              </table>
			</td>
		</tr>
	</table>
	</s:form>
</div>
</body>
</html>