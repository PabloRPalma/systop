<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript">
	function validate(){
		var date = document.getElementById('softCatalog.id').value;
		var softName = document.getElementById('name').value;
		if(softName == null || softName == ''){
			alert('请填写软件名称！');
			return false;
		}
		if(date == '0'){
			alert('请选择软件类别！');
			return false;
		}
		return true;
	}
</script>
</head>
<body>
<div class="x-panel">
    <div class="x-panel-header">软件下载管理</div>
    <div class="x-toolbar">
	 &nbsp;
    </div>
	<div><%@ include file="/common/messages.jsp"%></div> 
	<div align="center">
	<s:form  action="save.do" method="post" theme="simple" enctype="multipart/form-data">
	<s:hidden name="model.id"/>
	<s:hidden name="model.downUrl"/>
	<s:hidden name="model.size"/>
	<fieldset style="width:510px; padding:10px 10px 10px 10px;">
    	<legend>编辑软件信息</legend>
        <table width="500px" align="center">
          <tr>
             <td align="right" width="80">软件名称：</td>
             <td align="left" width="420">
             	<s:textfield id="name" name="model.name" cssStyle="width:400px"/><font color="red">&nbsp;*</font>
             </td>
          </tr>
          <tr>
            <td align="right">软件版本：</td>
            <td align="left">
            	<s:textfield id="softVersion" name="model.softVersion" cssStyle="width:400px"/>
            </td>
          </tr>
           <tr>
            <td align="right">操作系统：</td>
            <td align="left">
            	<s:textfield id="os" name="model.os" cssStyle="width:400px"/>
            </td>
          </tr>
          <tr>
            <td align="right">授权方式：</td>
            <td align="left">
            	<s:textfield id="authorization" name="model.authorization" cssStyle="width:400px"/>
            <td>
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
            		<s:file id="soft" name="soft" cssClass="FileText" cssStyle="width:400px"/> 
            	</s:else>
            </td>
          </tr>
          <tr>
            <td align="right">软件介绍：</td>
            <td align="left">
				<s:textarea id="introduction" name="model.introduction" cssStyle="width:400px; height:100px"/>
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
	            <s:select id="softCatalog.id" name="model.softCatalog.id" headerKey="0" list="allSoftCatas" headerValue="选择类别" listKey="id" listValue="name"/>
	        </td>
	       </tr>
        </table> 
    </fieldset>
    <table width="600px" style="margin-bottom:10px;">
		<tr>
			<td style="text-align:center;">
				<s:submit value="保存" cssClass="button" onclick="return validate();"/>
				<s:reset value="重置" cssClass="button"/>
		   </td>
		</tr>
	</table>
	</s:form>
	</div>
</div>
</body>
</html>