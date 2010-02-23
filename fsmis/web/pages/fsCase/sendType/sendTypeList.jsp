<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/ec.jsp"%>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/dwr.jsp" %>
<script type="text/javascript" src="${ctx}/dwr/interface/SendTypeDwrAction.js"></script>
<title>派遣环节管理</title>

<style type="text/css">
	.newBorder{
		width:25px; 
		border: 2px solid green;
	}
</style>
</head>
<body>
<div class="x-panel">
<div class="x-panel-header">派遣环节管理</div>
<div class="x-toolbar">
	<div style="margin: 7 20 4 10;" align="right">
	  <img src="${ctx}/images/icons/add.gif"/>
      <a href="${ctx}/sendType/input.do">添加环节</a>
	</div>
</div>
	<div>
	<%@ include file="/common/messages.jsp"%>
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
			width="100%" height="360px"
			minHeight="360"
			toolbarContent="navigation|pagejump|pagesize|refresh|extend|status">
			<ec:row>
				<ec:column width="40" property="_s" title="No." value="${GLOBALROWCOUNT}" style="text-align: center" />
				<ec:column width="250" property="name" title="名称"/>
				<ec:column width="350" property="_d" title="描述">
					<div style="width: 350px; word-break: break-all;">
						${item.descn }
					</div>
				</ec:column>
				<ec:column width="50" property="_sortId" title="排序" style="text-align:center">
					<input type="text" name="itemSortId" id="${item.id}" value="${item.sortId}" style="width:25px;" disabled="disabled">
				</ec:column>
				<ec:column width="120" property="_0" title="操作" style="text-align:center">
					<a href="${ctx}/countySendType/edit.do?model.sendType.id=${item.id}">关联 </a>
					<a href="edit.do?model.id=${item.id}">编辑 </a>
					<a href="#" onclick='remove(${item.id})'>删除</a>
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
			SendTypeDwrAction.sort(items, function(str){
				  if(str == "success"){
					window.location.href = "${ctx}/sendType/index.do";		
				  }
				}
			);
		}
	}

	//获得所有排序ID的输入框 
	function getInputs(){
		return document.getElementsByName("itemSortId");
	}
	
	//删除
	function remove(id){
		 Ext.MessageBox.confirm('提示','确认重删除本条记录吗？删除后不能恢复！', function(btn){
		        if (btn == 'yes') {
		          window.location = "${ctx}/sendType/remove.do?model.id=" + id;
		        }
		    });
	}
</script>
</body>
</html>