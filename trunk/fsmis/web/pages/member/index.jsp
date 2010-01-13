<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%@page import="java.util.*" %>
<html>
<head>
<title>委员会成员</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
function removeMember(mebId) {
    Ext.MessageBox.confirm('提示','确实要删除该成员的信息吗？', function(btn){
        if (btn == 'yes') {
        	location.href="remove.do?model.id=" + mebId;
        }
    });
  }
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">委员会成员管理</div>
<div class="x-toolbar">
<table width="99%">
	<tr><s:form action="index.do" method="post">
		<td width="30" align="left">
			姓名:
		</td>
		<td width="60">
			<s:textfield name="model.name" cssStyle="width:150px"/>&nbsp;&nbsp;
		</td>
		<td width="30" align="left">
			单位:
		</td>
		<td width="40">
			<s:textfield name="model.dept" cssStyle="width:150px"/>
		</td>
		<td width="80">
			<input type="submit" value="查询" class="button" />
		</td>
		</s:form>
		<td align="right">
		  <table>
			<tr>
				<td><a href="edit.do" title="添加成员信息">添加</a></td>
				<td><span class="ytb-sep"></span></td>
				<td valign="middle"><a href="#" onclick="NumWindow.show()">导出手机号码</a></td>
			</tr>
		  </table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table
	items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
	action="index.do" useAjax="false"
	doPreload="false" 
	pageSizeList="20" 
	editable="false"
	sortable="true" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="true" 
	classic="false" 
	width="100%" height="460px"
	minHeight="460"
	csvFileName="成员名单.csv"
	toolbarContent="navigation|pagejump|pagesize|export|refresh|extend|status">
	<ec:row>
		<ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align: center" />
		<ec:column width="100" property="name" title="姓名" />
		<ec:column width="260" property="dept" title="单位" />
		<ec:column width="100" property="jobs" title="职位" />
		<ec:column width="100" property="phone" title="固话" />
		<ec:column width="100" property="mobile" title="手机" />
		<ec:column property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">编辑</a> |
			<a href="#" onClick="removeMember(${item.id})"> 删除</a>
		</ec:column>
	</ec:row>
</ec:table></div>
</div>
</div>
<!-- 查看手机号码 -->
<div id="numWindow" class="x-hidden">
<div class="x-window-header">手机号码</div>
<div class="x-window-body">
	<table width="550" cellspacing="6">
	  <tr>
	    <td align="right"><font color="green">有号码成员数：</font></td>
		<td align="left" colspan="3">
		  ${hasNum}&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">无号码成员数：</font>${noNum}
	    </td>
	  </tr>
	  <tr>
	    <td height="5" colspan="4"></td>
	  </tr>
	  <tr>
	    <td width="90px" align="right"><font color="green">成员手机号码：</font></td>
	    <td width="320px">${mobileNums}</td>
        <td colspan="2">
        	<input type="button" class="button" onclick="copyNum('${mobileNums}')" style="text-align: center;width: 90px;" value="复制到剪切板" />
        </td>
	  </tr>
	</table>
	
 </div>
</div>
<!-- 查看手机号码 -->
<script type="text/javascript">
  var NumWindow = new Ext.Window({
      el: 'numWindow',
      width: 550,
      height: 250,
      layout : 'fit',
      closeAction:'hide',
      buttonAlign:'center',
      modal:'false',
      buttons:[
        {text:'关闭',
        	handler:function(){
        	NumWindow.hide();
        }
      }]
  });
</script>
<script type="text/javascript">
  function copyNum(copyText) {
	var reg = new RegExp(/\<br\/>/ig);
	copyText = copyText.replace(reg, "");
    if (window.clipboardData) {
        window.clipboardData.setData("Text", copyText);
    } 
  }
</script>
</body>
</html>