<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>派遣快捷方式维护</title>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/SendTypeDwrAction.js"></script>

<style type="text/css">
	.newBorder{
		width:25px; 
		border: 2px solid green;
	}
</style>
</head>
<body>
<div class="x-panel">
<div class="x-toolbar">
<table width="100%">
  <tr>
    <td align="right">
      <a href="${ctx}/sendType/input.do">添加派遣环节</a>
    </td>
  </tr>
</table>
</div>
	<div class="x-panel-body">
		<ec:table
			items="items" var="item" retrieveRowsCallback="process" sortRowsCallback="process"
			action="index.do" useAjax="false"
			doPreload="false" 
			pageSizeList="20,50,100,200" 
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
				<ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" style="text-align: center" />
				<ec:column width="250" property="_name" title="名称">
					<span style="font-size: 14px; font-weight: bold;">${item.name}</span>
				</ec:column>
				<ec:column width="350" property="_d" title="描述">
					<div style="width: 350px; word-break: break-all;">
						${item.descn }
					</div>
				</ec:column>
				<ec:column width="50" property="_sortId" title="排序" style="text-align:center">
					<input type="text" name="itemSortId" id="${item.id}" value="${item.sortId}" style="width:25px;" disabled="disabled">
				</ec:column>
				<ec:column width="50" property="_0" title="操作" style="text-align:center">
					<a href="edit.do?model.id=${item.id}">编辑 </a>
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
				<div style="margin: 10 10 10 10;"><b>注</b>：点击右下角的<span style="color: green; font-weight: bold; cursor: hand;">我要排序</span>，对派遣环节进行排序操作。</div>
			</ec:extendbar>
		</ec:table>
	</div>
</div>
<script type="text/javascript">

	/**
	 * 为排序做准备
	 */
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

	/**
	 * 确认排序
	 */
	function confirmSort(){
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
		if (confirm("确认重新排序吗？")){
			//alert(items.length);
			SendTypeDwrAction.sort(items, function(str){
				  if(str == "success"){
					window.location.href = "${ctx}/sendType/index.do";		
				  }
				}
			);
		}
	}

	function getInputs(){
		return document.getElementsByName("itemSortId");
	}
	
		
</script>
</body>
</html>