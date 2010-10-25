<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/validator.jsp" %>
<script type="text/javascript">
$(document).ready(function() {
	$("#save").validate();
	if($('#minM').val() == '') {
	    $('#minM').val('2');
	}
});


</script>
<title></title>
</head>
<body>
<div class="x-panel">
    	<div class="x-panel-header">&nbsp;</div>
    	<div class="x-panel-body">
    		<table width="700" align="center">
				<tr>
					<td align="left"><%@ include file="/common/messages.jsp"%></td>
				</tr>
			</table>
			<s:form action="save" theme="simple" id="save" validate="true" method="POST">
		   		<s:hidden name="model.id"/>
		   		<fieldset style="margin:10px;">
				    <legend>地震数据相关配置</legend>
				    
					<table width="700" align="center" cellpadding="2" cellspacing="3">
	                     <tr>
	                        <td width="200" style="text-align:right;">台网代码：</td>
	                        <td width="500" style="text-align:left;">
	                        <s:textfield size="30" name="model.cmsCode" cssClass="required"/>&nbsp;<font color="red">*</font>
	                        </td>
	                     </tr>
	                     <tr>
	                        <td style="text-align:right;">本省名称：</td>
	                        <td style="text-align:left;">
	                        <s:textfield size="30" name="model.provinceName" cssClass="required"/>&nbsp;<font color="red">*</font>
	                        </td>
	                     </tr>
	                     <tr>
	                        <td style="text-align:right;">地震目录：</td>
	                        <td style="text-align:left;">
	                        <s:select list="allCzCat" name="model.provinceCat.id" listKey="id" listValue="clcName" headerValue="请选择..." headerKey="" cssClass="required"/>
	                        <font color="red">*</font>
	                        </td>
	                     </tr>
	                     <tr>
	                        <td style="text-align:right;">最小震级：</td>
	                        <td style="text-align:left;">
	                        <s:textfield size="30" id = "minM" name="model.minM" cssClass="required digit"/>&nbsp;<font color="red">*</font>
	                        </td>
	                     </tr>
	                </table>
                </fieldset>
                <br>
                <fieldset style="margin:10px;">
				    <legend>网站基本信息相关配置</legend>
				    <table width="700" align="center" cellpadding="2" cellspacing="3">
				    	<tr>
				    		<td width="200px" style="text-align:right;">网站名称：</td>
				    		<td width="500px" style="text-align:left;">
				    			<s:textfield size="70" name="model.cmsName" cssClass="required"/>&nbsp;<font color="red">*</font>
				    		</td>
				    	</tr>
	                     <tr>
	                        <td style="text-align:right;">版权信息：</td>
	                        <td style="text-align:left;">
	                        <s:textfield size="70" id = "copyright" name="model.copyright" cssClass="required"/>&nbsp;<font color="red">*</font>
	                        </td>
	                     </tr>
	                     <tr>
	                        <td style="text-align:right;">电子邮件：</td>
	                        <td style="text-align:left;">
	                        <s:textfield size="70" id = "email" name="model.email" cssClass="required"/>&nbsp;<font color="red">*</font>
	                        </td>
	                     </tr>
	                     <tr>
	                        <td style="text-align:right;">ICP备号：</td>
	                        <td style="text-align:left;">
	                        <s:textfield size="70" id = "icpCode" name="model.icpCode" cssClass="required"/>&nbsp;<font color="red">*</font>
	                        </td>
	                     </tr>
	                     <tr>
	                        <td style="text-align:right;">联系地址：</td>
	                        <td style="text-align:left;">
	                        <s:textfield size="70" id = "address" name="model.address" cssClass="required"/>&nbsp;<font color="red">*</font>
	                        </td>
	                     </tr>
	                      <tr>
	                        <td style="text-align:right;">邮政编码：</td>
	                        <td style="text-align:left;">
	                        <s:textfield size="70" id = "zipCode" name="model.zipCode" cssClass="required"/>&nbsp;<font color="red">*</font>
	                        </td>
	                     </tr>
				    </table>
			    </fieldset>
                <table width="100%" style="margin-bottom:10px;">
                       <tr>
                        <td style="text-align:center;">
                            <s:submit value="保存" cssClass="button"></s:submit>
                            <s:reset value="重置" cssClass="button"></s:reset></td>
                        </tr>
                </table>
                </s:form>
                <img src="${ctx}/images/exticons/icon-info.gif"/>带&nbsp;<font color="red">*</font>的为必填项。
    	</div>
  </div>
</body>
</html>