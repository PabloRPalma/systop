<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>短信发送列表</title>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript">
/**
 * 删除提交
 */
function remove(){
    var sels = document.getElementsByName("selectedItems");
    var checked = false;;
    for(i = 0; i < sels.length; i++) {
        if(sels[i].checked) {
           checked = true;
           break;
        }
    } 
    if(!checked) {
    	Ext.MessageBox.show({
            title:'提示',
            minWidth:260,
            msg:'<div style=\'width:180\';><br/>请选择要删除的短信！</div>',
            buttons:Ext.MessageBox.OK,
            icon:Ext.MessageBox.INFO
       });
       return;
    }
	Ext.MessageBox.confirm('提示','确定要删除所选择的短信息吗？删除后不能恢复！', function(btn){
        if (btn == 'yes') {
        	var from = document.getElementById("ec");
    		from.target="_self";
    		from.action="remove.do";
    		from.submit();
        }
    });
}
</script>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">短信发送</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td style="padding: 5px 0px 3px 10px;"> 
			<form action="index.do" method="post" target="">
				姓名：<s:textfield name="model.name"/>&nbsp;&nbsp;
				手机号：<s:textfield name="model.mobileNum"/>&nbsp;&nbsp;
				最新：<input type="checkbox" style="border: 0px;" name="model.isNew" value="1" <s:if test="model.isNew != null">checked="checked"</s:if>>&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" value="查询" class="button">
			</form>
		</td>
		<td align="right">
		<table>
			<tr>
				<td><a href="edit.do"><img src="${ctx}/images/icons/add.gif" />添加</a></td>
				<td><span class="ytb-sep"></span></td>
				<td><a href="#" onclick="remove()"><img src="${ctx}/images/icons/delete.gif" />删除</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div class="x-panel-body">
<div style="margin-left: -3px;" align="center">
<ec:table items="items" var="item" retrieveRowsCallback="limit" sortRowsCallback="limit" 
	action="index.do" 
	useAjax="false"
	doPreload="false" 
	pageSizeList="20,50,100,200" 
	editable="false"
	sortable="true" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" 
	height="462px"
	minHeight="462"
	csvFileName="短信发送纪录.csv"
	toolbarContent="navigation|pagejump|pagesize|export|refresh|extend|status">
	<ec:row style="height: 50;">
		<ec:column width="40" property="_select" title="选择"
			style="text-align:center" viewsAllowed="html">
			<input type="checkbox" name="selectedItems" value="${item.id}" style="margin: -1px 0px -1px 0px; border: 0px;"/>
		</ec:column>
		<ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" style="text-align:center"/>
		<ec:column width="80" property="mobileNum" title="电话号码" style="text-align:center"/>
		<ec:column width="80" property="name" title="接收人" style="text-align:center"/>
		<ec:column width="260" property="content" title="短信内容" />
		<ec:column width="120" property="createTime" title="创建时间" cell="date" format="yyyy-MM-dd HH:mm" style="text-align:center"/>
		<ec:column width="80" property="_type" title="发送状态" style="text-align:center">
			<s:if test="#attr.item.isNew eq 1"><font color="red">未发</font></s:if>
			<s:if test="#attr.item.isNew eq 0"><font color="green">已发</font></s:if>
		</ec:column>
		<stc:role ifAnyGranted="ROLE_ADMIN,ROLE_CENT_OPER">
		<ec:column width="80" property="_isReceive" title="接收状态" style="text-align:center">
			<s:if test="#attr.item.isReceive eq 1"><font color="green">成功</font></s:if>
			<s:if test="#attr.item.isReceive eq 0"><font color="red">失败</font></s:if>
			<s:if test="#attr.item.isReceive eq 2"><font color="blue">未知</font></s:if>
		</ec:column>
		</stc:role>      
		<ec:column width="40" property="_option" title="操作" style="text-align:center">
			查看
		</ec:column>
	</ec:row>
</ec:table>
</div>
</div>
</div>
</body>
</html>