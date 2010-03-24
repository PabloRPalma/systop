<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<html>
<head>
<%@include file="/common/meta.jsp"%>
<title>栏目信息</title>
<%@include file="/common/ec.jsp"%>
<style type="text/css">
.simple {
	font-size: 12px;
	background-color: #FFFFFF;
	padding: 4px;
	color: #0099FF;
}
input,textarea {
	border:1px #CECECE solid;
}

.td{
	border-bottom: 1px solid black;
	padding:10 0 3 5;
}
</style>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">栏目信息</div>
   <div class="x-toolbar">
	  <table width="100%">	      
		<tr>	         
		  <td align="right">
			<input type="button" class="button" value="关闭" onclick="javascript:window.close(1)">
		  </td>
		  <td width="15"></td>
		</tr>
	  </table>
	</div>
	<div class="x-panel-body">
	<div><%@ include file="/common/messages.jsp"%></div> 
    <fieldset style="margin:10px; width: 500">
      <legend>栏目信息</legend>
      <table width="100%" cellpadding="0" cellspacing="0">
		<tr>  
		  <td width="120" style="text-align:right;" class="td">所属栏目：</td>
		  <td style="text-align:left;" class="td">
		  	<s:if test="#attr.model.parentCatalog == null">
		  		根栏目
		  	</s:if>
		  	<s:else>
		  		<s:property value="model.parentCatalog.name" />&nbsp;
		  	</s:else>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;" class="td">栏目名称：</td>
		  <td style="text-align:left;" class="td">
		  	${model.name }&nbsp;
		  </td>
		</tr>
		<s:if test="model.type == 1">
			<tr>
			  <td style="text-align:right;" class="td">栏目模板：</td>
			  <td style="text-align:left;" class="td">
			  		<s:property value="model.cataTemplate.name" />&nbsp;
			  </td>
			</tr>
			<tr>
			  <td style="text-align:right;" class="td">文章模板：</td>
			  <td style="text-align:left;" class="td">
			  		<s:property value="model.artTemplate.name" />&nbsp;
			  </td>
			</tr>
			<tr>
		  		<td style="text-align:right;" class="td">目录名称：</td>
		  		<td style="text-align:left;" class="td">
		    	<s:property value="model.root"/>&nbsp;
		  	</td>
		  	<tr>
		  		<td style="text-align:right;" class="td">应用路径：</td>
		  		<td style="text-align:left;" class="td">
		    	<s:property value="model.rootPath"/>&nbsp;
		  	</td>
		</tr>
		</s:if>
		<s:else>
			<tr>
			  <td style="text-align:right;" class="td">链接地址：</td>
			  <td style="text-align:left;" class="td">
			     <s:property value="model.linkUrl"/>&nbsp;
			  </td>
			</tr>
		</s:else>
		<tr>
		  <td style="text-align:right;" class="td">栏目说明：</td>
		  <td style="text-align:left;" class="td">
	        <s:property value="model.descn"/>&nbsp;
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;" class="td">首页显示：</td>
		  <td style="text-align:left;" class="td">
		  	<s:if test="model.showOnIndex == 0">
		  		否
		  	</s:if>
		  	<s:else>
		  		是
		  	</s:else>
	      </td>		  
		</tr>
		<tr>
		  <td style="text-align:right;" class="td">栏目是否可用：</td>
		  <td style="text-align:left;" class="td">
		    <s:if test="model.isEnable == 0">
		  		否
		  	</s:if>
		  	<s:else>
		  		是
		  	</s:else>
		  </td>
		 </tr>
		<tr>
		  <td style="text-align:right;" class="td">顶部导航栏显示：</td>
		  <td style="text-align:left;" class="td">
		    <s:if test="model.showOnTop == 0">
		  		否
		  	</s:if>
		  	<s:else>
		  		是
		  	</s:else>
		  </td>
		 </tr>
		 <tr>
		  <td style="text-align:right;" class="td">父栏目导航栏显示：</td>
		  <td style="text-align:left;" class="td">
		    <s:if test="model.showOnParlist == 0">
		  		否
		  	</s:if>
		  	<s:else>
		  		是
		  	</s:else>
		  </td>
		 </tr>
	  </table>
	</fieldset>
</div>
</div>
</body>
</html>