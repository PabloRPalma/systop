<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<html>
<head>
<%@include file="/common/meta.jsp"%>
<title>查看文章信息</title>
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
</style>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">查看文章信息</div>
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
    <s:hidden name="model.id"/>
    <fieldset style="margin:10px;">
      <legend>文章信息</legend>
      <table width="80%">
      	<tr>
		  <td style="text-align:right;">标&nbsp;&nbsp;题：</td>
		  <td style="text-align:left;">
		    <s:property value="model.title" />
		  </td>
		</tr>
		<tr>  
		  <td style="text-align:right;">简短标题：</td>
		  <td style="text-align:left;">
		    <s:property value="model.shortTitle" />
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">副&nbsp;标&nbsp;题：</td>
		  <td style="text-align:left;">
		    <s:property value="model.subtitle" />
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">文章简介：</td>
		  <td style="text-align:left;">
		    <s:property value="model.summary"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">转向链接：</td>
		  <td style="text-align:left;">
		     <s:property value="model.linkUrl"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">作&nbsp;&nbsp;者：</td>
		  <td style="text-align:left;">
		     <s:property value="model.author" />
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">录入者：</td>
		  <td style="text-align:left;">
	        <s:property value="model.inputer.name"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">修改者：</td>
		  <td style="text-align:left;">
	        <s:property value="model.updater"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">固&nbsp;&nbsp;顶：</td>
		  <td style="text-align:left;">
		  	<s:if test="model.onTop == 0">
		  		否
		  	</s:if>
		  	<s:else>
		  		是
		  	</s:else>
	      </td>		  
		</tr>
		<tr>
		  <td style="text-align:right;">推&nbsp;&nbsp;荐：</td>
		  <td style="text-align:left;">
		    <s:if test="model.isElite == 0">
		  		否
		  	</s:if>
		  	<s:else>
		  		是
		  	</s:else>
		  </td>
		 </tr>
		<tr>
		  <td style="text-align:right;">所属栏目：</td>
		  <td style="text-align:left;">
		    <s:property value="model.catalog.name"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;">内容模板：</td>
		  <td style="text-align:left;">
		    <s:property value="model.template.name"/>
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;"></td>
		  <td style="text-align:left;">
		  </td>
		</tr>
		<tr>
		  <td style="text-align:right;"> 内&nbsp;&nbsp;容：</td>
		  <td style="text-align:left;">
		  	<br>
		    ${model.content}
		    <br>
		  </td>
		</tr>
	  </table>
	</fieldset>
	<table width="97.7%" align="center">
       <div class="tundra">
		   <div dojoType="dijit.TitlePane" title="附件信息" class="height:12px">
		    <c:forEach var="att" items="${model.attachmentses}">
	  	  	  <a href="${ctx}${att.path}">
	  	      <img src="${ctx}/images/icons/attachment.gif" />&nbsp;<font size="2">${att.name}</font>
	  	     </a>&nbsp;
	  	    </c:forEach>
		   </div>
	   </div>
     </table> 

</div>
</div>
</body>
</html>