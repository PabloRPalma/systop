<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<%@page import="java.util.*"%>
<html>
<head>
<title></title>
<%@include file="/common/meta.jsp" %>

</head>
<body>
<div class="x-panel">
    <div class="x-toolbar">
	 &nbsp;
    </div>
	<div>
	<%@ include file="/common/messages.jsp"%></div> 
	<div align="center">
	<form action="save.do" method="post">
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
          <tr>
             <td align="center" colspan="2">
             	<input type="submit" value="保存"/>
             	<input type="reset" value="重置"/>
             </td>
          </tr>
        </table> 
	
	</form>
	</div>
</div>
</body>
</html>