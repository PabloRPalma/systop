<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp" %>
<%@include file="/common/validator.jsp"%>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">软件下载管理</div>
    <div class="x-toolbar">
	 &nbsp;
    </div>
	<div><%@ include file="/common/messages.jsp"%></div> 
	<div align="center">
	<s:form id="save"  action="save.do" method="post" theme="simple" validate="true" enctype="multipart/form-data">
	<s:hidden name="model.id"/>
	<s:hidden name="model.downUrl"/>
	<s:hidden name="model.size"/>
	<fieldset style="width:600px; padding:10px 10px 10px 10px;">
    	<legend>编辑软件信息</legend>
      <table width="606" align="center">
          <tr>
             <td align="right" width="79">软件名称：</td>
             <td align="left" width="507">
             	<s:textfield id="name" name="model.name" cssClass="required" cssStyle="width:400px"/><font color="red">&nbsp;*</font>
            </td>
          </tr>
          <tr>
            <td align="right">软件版本：</td>
            <td align="left">
            	<s:textfield id="softVersion" name="model.softVersion" cssClass="required" cssStyle="width:400px"/><font color="red">&nbsp;*</font>
            </td>
          </tr>
           <tr>
            <td align="right">操作系统：</td>
            <td align="left">
            	<s:textfield id="os" name="model.os" cssStyle="width:400px" cssClass="required"/><font color="red">&nbsp;*</font>
            </td>
          </tr>
          <tr>
            <td align="right">授权方式：</td>
            <td align="left">
            	<s:textfield id="authorization" name="model.authorization" cssStyle="width:400px"/>
          </tr>
          <tr>
            <td align="right">上传文件：</td>
            <td align="left">
            	<s:if test="#attr.model.downUrl != null">
         			<img src="${ctx}/images/icons/compressed.gif"/>
         			<a href="${ctx}/software/down.do?model.id=${model.id}" title="${model.downUrl}"> 
         				查看文件
         			</a>
            	</s:if>
            	<s:else>
            		<s:file id="soft" name="soft" cssClass="FileText required"  cssStyle="width:400px"/> 
            	</s:else>
            	<font color="red">&nbsp;*</font>
            </td>
          </tr>
          <tr>
            <td align="right">软件介绍：</td>
            <td align="left">
				<s:textarea id="introduction" name="model.introduction" cssClass="required" cssStyle="width:400px; height:100px"/><font color="red">&nbsp;*</font>
			</td>
          </tr>
          <tr>
            <td align="right">备　　注：</td>
            <td align="left">
				<s:textarea id="descn" name="model.descn" cssStyle="width:400px; height:50px"/>
		   </td>
          </tr>
          <tr>
            <td align="right">软件类别：</td>
            <td align="left">
	            <s:select id="softCatalog.id" name="model.softCatalog.id" cssClass="required" headerKey="" list="allSoftCatas" headerValue="选择类别" listKey="id" listValue="name"/>
	        	<font color="red">&nbsp;*</font>
	        </td>
          </tr>
      </table> 
    </fieldset>
    <table width="600px" style="margin-bottom:10px;">
		<tr>
			<td style="text-align:center;">
				<s:submit value="保存" cssClass="button" />
				<s:reset value="重置" cssClass="button"/>
		   </td>
		</tr>
	</table>
	</s:form>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#save").validate();
});
</script>
</body>
</html>