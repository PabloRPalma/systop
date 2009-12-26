<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>派遣环节编辑</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">派遣环节编辑</div>
<div class="x-toolbar">
	<div style="padding: 5 20 3 10;" align="right">
	  <img src="${ctx}/images/icons/add.gif"/>
      <a href="${ctx}/sendType/index.do">派遣环节管理</a>
	</div>
</div>
<div>
	<%@ include file="/common/messages.jsp"%>
</div> 
<div align="center" style="margin-top:20px;">
	<form action="save.do" method="post">
	  <fieldset style="width:470px; padding:10px 10px 10px 10px;">
	  <legend>派遣环节编辑</legend>
		<s:hidden name="model.id"/>
        <table width="450px" align="center">
          <tr>
             <td align="right" width="100">环节名称：</td>
             <td align="left" width="350">
             	<s:textfield id="name" name="model.name" cssStyle="width:280px"/><font color="red">&nbsp;*</font>
             </td>
            </tr>
          <tr>
             <td align="right">环节描述：</td>
             <td align="left">
             	<s:textarea id="descn" name="model.descn" cssStyle="width:280px; height:100px;"></s:textarea>
             </td>
          </tr>
          <tr>
             <td align="right">排列序号：</td>
             <td align="left">
             	<s:textfield id="sortId" name="model.sortId" cssStyle="width:30px;"></s:textfield>
             </td>
          </tr>
        </table> 
	  </fieldset>
		<div align="center" style="margin: 10 0 0 0;">
			<input type="submit" value="保存"/>&nbsp;&nbsp;
           	<input type="reset" value="重置"/>
		</div>
	</form>
</div>
</div>
</body>
</html>