<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑文章</title>
<%@include file="/common/meta.jsp" %>
<script type="text/javascript">
jQuery.noConflict();
</script>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/CatalogDwrAction.js"></script>
<script type="text/javascript" src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
<script type="text/javascript" src="${ctx}/scripts/TabPanel/tabpanel.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/TabPanel/css/style.css" />
</head>
<body onload="preFckEditor()">
<s:form namespace="/admin/article" action="saveArticle" theme="simple"  validate="true" method="POST" enctype="multipart/form-data"> 
<div class="x-panel">
	<div class="x-panel-header">
		文章管理
	</div>
    <div class="x-toolbar">
		    &nbsp;
	</div>
    <table width="100%" border="0" cellpadding="5"
		cellspacing="1">
		<tr>
			<td><%@ include file="/common/messages.jsp"%></td>
		</tr>
	</table>
    
	<s:hidden name="model.id"/>
	<s:hidden name="model.version"/>
	<s:hidden name="model.createTime"/>
	<s:hidden name="model.updateTime"/>
	<s:hidden name="model.inputer.id"/>
	<table width="98%" border="0" align="center">
	  <tr><td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="TabBarLevel" id="TabPage">
	      <tr align="center">
	        <td width="130" id="SystopTab1" valign="middle" class="SelectedTab" onclick=switchTab(this,3)>
			基本信息</td>
	        <td width="7" class="Black">&nbsp;</td>
	
	        <td width="80" id="SystopTab2" valign="middle" class="mousehand" onclick=switchTab(this,3)>属性设置
			</td>
	        <td width="7" class="Black">&nbsp;</td>
	        
	         <td width="80" id="SystopTab3" valign="middle" class="mousehand" onclick=switchTab(this,3)>附件设置
			</td>
	        <td width="7" class="Black">&nbsp;</td>
	
	        <td width="" align="right" class="Black">&nbsp;&nbsp;</td>
	      </tr>
	    </table>
 	    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Content">
	        <tr>
	          <td height="110">
                <div id='SystopTabDiv1'>
					<%@include file="bascInfo.jsp" %>
				</div>
	
				<div id='SystopTabDiv2' style=" display:none">
					<%@include file="attribute.jsp" %>
				</div>
				
				<div id='SystopTabDiv3' style=" display:none">
					<%@include file="attachment.jsp" %>
				</div>
			  </td>
	        </tr>
	    </table>		
	  </td></tr>
	</table>
	<table width="98%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td align="center">
	    	<s:if test="model.id == null">
		    <s:submit value="添加" cssClass="button"/>
		    </s:if>
			 <s:else>
      		<s:submit value="修改" cssClass="button"/>
    		</s:else>
            <s:reset value="清空" cssClass="button" onClick="clearFCK();"/>
	    </td>
	  </tr>
	</table>
</div>
</s:form>
<script type="text/javascript">
function clearFCK(){
	var editor = FCKeditorAPI.GetInstance("model.content");
	editor.EditorDocument.body.innerHTML="";
}
</script>
</body>
</html>
