<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>应急指挥派遣类别维护</title>
<style type="text/css">
	.newBorder{
		width:25px; 
		border: 2px solid green;
	}
</style>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/UcTypeDwrAction.js"></script>

</head>
<body>
<div class="x-panel">
<div class="x-panel-header">应急指挥派遣类别维护</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td></td>
		<td align="right">
		<table>
			<tr>
				<td><a href="edit.do"><img
					src="${ctx}/images/icons/add.gif" />添加</a></td>
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
	sortable="false" 
	rowsDisplayed="20" 
	generateScript="true"
	resizeColWidth="false" 
	classic="false" 
	width="100%" height="460px"
	minHeight="460"
	toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
	<ec:row>
		<ec:column width="30" property="_s" title="No." value="${GLOBALROWCOUNT}" sortable="false" style="text-align: center" />
		<ec:column width="300" property="name" title="名称" />
		<ec:column width="200" property="remark" title="备注" />
		<ec:column width="50" property="_sortId" title="排序" style="text-align:center">
					<input type="text" name="itemSortId" id="${item.id}" value="${item.sortId}" style="width:25px;" disabled="disabled">
				</ec:column>
		<ec:column width="150" property="_0" title="操作" style="text-align:center" sortable="false">
			<a href="edit.do?model.id=${item.id}">编辑</a> |
			<a href="${ctx}/ucgroup/index.do?ucTypeId=${item.id}">查看组</a> |
			<a href="#" onClick="removeUcType(${item.id})">删除</a>
		</ec:column>
	</ec:row>
	<ec:extend>
		<div align="right" style="padding-right: 10px;">
			<div id="ready" style="cursor: hand;" onclick="readySort(this)">
					<span style="color: green; font-weight: bold;">↑我要排序↓</span>
			</div>
			<div id="confirm" style="display: none; cursor: hand;" onclick="confirmSort()">
					<span style="color: red; font-weight: bold;">↑确认排序↓</span>
			</div>
		</div>
	</ec:extend>
	<ec:extendbar>
			<div style="margin: 10 10 10 10;"><b>注</b>：点击右下角的<span style="color: green; font-weight: bold; cursor: hand;">我要排序</span>，对类别进行排序操作。</div>
	</ec:extendbar>
</ec:table></div>
</div>
</div>
<script type="text/javascript">

/**
 * 删除提交
 */
 
 function removeUcType(utID){
 	if(confirm("确实要删除该类别吗？")){
 		location.href="remove.do?model.id="+utID;
 	}
 }
	//为排序做准备
	function readySort(obj){
		inputs = getInputs();
		if(inputs.length == 0){
			alert("暂无记录，无法排序");
			return;
		}
		obj.style.display = "none";
		document.getElementById("confirm").style.display = "";

		for (i = 0; i < inputs.length; i++){
			inputs[i].disabled = "";
			inputs[i].className = "newBorder";
		}
		inputs[0].select();
	}

	// 确认排序
	function confirmSort(){
		if (confirm("确认重新排序吗?")){
			inputs = getInputs();
			var items = new Array();
			for (i = 0; i < inputs.length; i++){
				if(isNaN(inputs[i].value)){
					alert("[" + inputs[i].value + "] 不是有效数字,请修改。");
					inputs[i].select();
					return;
				}
				var itemStr = inputs[i].id + ":" + inputs[i].value;
				items[i] = itemStr;
			}
			//dwr调用进行排序操作
			UcTypeDwrAction.sort(items, function(str){
				  if(str == "success"){
					window.location.href = "${ctx}/uctype/index.do";		
				  }
				}
			);
		}
	}

	//获得所有排序ID的输入框 
	function getInputs(){
		return document.getElementsByName("itemSortId");
	}
 </script>
</body>
</html>