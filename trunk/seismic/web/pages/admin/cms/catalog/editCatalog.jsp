<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp" %>
<%@include file="/common/autocomplete.jsp" %>
<script type="text/javascript">
	jQuery.noConflict();
</script>
<%@include file="/common/ec.jsp" %>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/CatalogDwrAction.js"></script>
<script type="text/javascript" src="${ctx}/scripts/TabPanel/tabpanel.js"></script>
<script type="text/javascript" src="${ctx}/pages/admin/cms/catalog/catalog.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/TabPanel/css/style.css" />
<LINK href="${ctx}/styles/style.css" type='text/css' rel='stylesheet'>
<title>编辑栏目</title>
</head>
<body  onload="init()">
<s:form namespace="/admin/catalog" action="save" theme="simple" method="POST" validate="true">
	<div class="x-panel">
	    <div class="x-panel-header">栏目管理</div>
	    <div class="x-toolbar">
		    &nbsp;
	    </div>
	    <table width="100%" border="0" cellpadding="5"
			cellspacing="1">
			<tr>
				<td><%@ include file="/common/messages.jsp"%></td>
			</tr>
		</table>
		<table width="100%" border="0" align="center">
		  <tr><td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="TabBarLevel" id="TabPage">
		      <tr align="center">
		        <td width="80" id="SystopTab1" valign="middle" class="SelectedTab" onclick=switchTab(this,2)>
					栏目信息
				</td>
		        <td width="7" class="Black">&nbsp;</td>
		        <td width="80" id="SystopTab2" valign="middle" class="mousehand" onclick=switchTab(this,2)>

		        	属性设置
				</td>
		        <td width="7" class="Black">&nbsp;</td>
		
<!--		        <td width="80" id="SystopTab3" align="middle" class="mousehand" onclick=switchTab(this,3)>-->
<!--		        	权限设置-->
<!--		        </td>-->
		        
		        <td width="7" class="Black">&nbsp;</td>
	
	        	<td width="" align="right" class="Black">&nbsp;&nbsp;</td>
		      </tr>
		    </table>
	    
		    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Content">
		        <tr>
		          <td height="110">
					<div id='SystopTabDiv1'>
						<%@include file="catalogInfo.jsp" %>
					</div>
					<div id='SystopTabDiv2' style=" display:none">
						<%@include file="catalogAttribute.jsp" %>
					</div>
<!--					<div id='SystopTabDiv3' style=" display:none">-->
<!--						<%@include file="catalogPermission.jsp" %>-->
<!--					</div>-->
				  </td>
		        </tr>
		    </table>		
		  </td></tr>
		</table>
		<table width="100%" style="margin-bottom:10px;">
            <tr>
                <td style="text-align:center;">
                	<s:hidden name="model.id" id="model.id"/>
                	<s:hidden name="model.orderId" id="model.orderId"/>
                	<s:hidden name="model.version"/>
                    <s:submit value="保存" cssClass="button"></s:submit>
                    <s:reset value="重置" cssClass="button"></s:reset>
                </td>
            </tr>
        </table>
	</div>
</s:form>
</body>
</html>