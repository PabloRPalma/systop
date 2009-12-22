<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<%@include file="/common/meta.jsp" %>
<script type='text/javascript' src='${ctx}/pages/admin/cms/links/sortSelect.js'></script>
<title></title>
</head>
<body>
<div class="x-panel">
  <div class="x-panel-header">链接排序</div>
  <div class="x-toolbar">
  	<table width="100%"><tr>
  		<td style="padding: 10 5 5 5;">
			<img src="${ctx}/images/icons/link.gif" class="icon">
			<a href="${ctx}/admin/links/listLink.do" target="main">链接管理</a>
  		</td>
  	</tr></table>
  </div>
  <s:form id="frmOrder" name="frmOrder" namespace="/admin/links" action="saveOrderLink" method="post" theme="simple">
    <s:hidden name="seqNoList"/>
	<table width="400px" align="center">
		<tr>
			<td align="center">
			<fieldset style="margin:10px;">
              <legend>编辑类别信息</legend>
                <table align="center">
				  <tr>
					<td>
					  <input id="search" name="search" type="text" size="10" onkeydown="return window.event.keyCode == 13?false:true">
			          <input TYPE="button" onClick="sl.Search()" value="查 询" class="button"/><br/>
				      <s:select id="order" name="order" list="orderLinks" listKey="id" listValue="siteName" multiple="true" size="20" cssStyle="width:150px"/>
				      <br/>
					  <input type="text" id="jumpNum" name="jumpNum" size="10"/>
					  <input type="button" class="button" onclick="sl.jump()" value="跳 转"/>
					</td>
					<td>
					  <input id="first" type="button" class="button" onclick="sl.fnFirst()" value="第一"/>
					  <br/>
					  <br/>
					  <input id="up" type="button" class="button" onclick="sl.sortUp()" value="上移"/>
					  <br/>
					  <br/>
					  <input id="down" type="button" class="button" onclick="sl.sortDown()" value="下移"/>
					  <br/>
					  <br/>
					  <input id="fnEnd" type="button" class="button" onclick="sl.fnEnd()" value="最后"/>
					  <br/>
					  <br/>
					  <input id="ok" type="button" class="button" onclick="sl.ok()" value="确定"/>
					</td>
				  </tr>
				</table> 
              </fieldset>
			</td>
		</tr>
		<tr>
			<td align="left">
			&nbsp;&nbsp;
			&nbsp;&nbsp;
			<font color="red">注：选中某个链接后在跳转输入框中输入要跳转到的位置。</font>
			</td>
		</tr>
	</table>
  </s:form>
</div>
<script type="text/javascript">
var sl = new SortSelect('frmOrder','order','search','jumpNum');
</script>
</body>
</html>