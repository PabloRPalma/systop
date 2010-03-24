<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="${ctx}/styles/style.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/layout/layout.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/treeview/treeview.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-min.css"	rel="stylesheet" />

<script type="text/javascript"	src="${ctx}/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/layout/jquery.layout.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/treeview/treeview.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/treeview/treeview.async.js" ></script>

<script type="text/javascript">
	$(function(){
		$("#loading").bind("ajaxSend", function(){
	       $(this).show();
	    }).bind("ajaxComplete", function(){
	       $(this).hide();
	    });
	
		$('body').layout({west__size: 230});
		$('#stations').hide();
	});
	
	/** 
	 * 显示台站树型列表:台站->测点->测项分量
	 */
	function showStationsTree(){
	 	var sampleRate = $("#sampleRate")[0].value;
	 	if (sampleRate == null || sampleRate == "00"){
	 		return;
	 	}
	 	var methodId = $("#methodIds")[0].value;
	 	var tableCategory = $("#tableCategory")[0].value;
	 	//把上次显示的树型列表清楚
	 	$("#stationsTree").remove(); 
	 	//创建存放树型列表的元素
	 	$("#tree").append("<ul id='stationsTree'></ul>"); 
		
		//构建树型列表
		$("#stationsTree").treeview({
			collapsed: false,
			unique: false,
			url:"${ctx}/datashare/prequery/staionsTree.do?tableCategory=" + tableCategory + "&sampleRate=" + sampleRate + "&methodId=" + methodId
		});
		
		//显示存放树型列表的元素
		$('#stations').fadeIn();
	}
	
	/* 隐藏台站树型列表，并将树型列表内容清空 */
	function hiddenStationsTree() {
	   $('#stations').fadeOut();
	}

	/**
	 * 多节点查询提交
	 */
	function query(){	
	    if($('#endDate').val() == '' || $('#startDate').val() == '') {
	       alert('请输入开始时间和结束时间。');
	       return;
	    }
		var unSelected = true;
		//selectItemIds是树型列表测checkbox的name属性
		$("input[name='selectItemIds']:checked").each(
			function(){
				unSelected = false;
			}
		);
		
		if (unSelected) {
			alert("请至少选择一个要查询的测项分量");
			return;
		}
			
		$("#querySignData")[0].action="${ctx}/datashare/sign/data/list.do";
		$("#querySignData")[0].target="signData";
		$("#querySignData")[0].submit();
	}
	
	
</script>
<style type="text/css">
.ui-layout-pane-west{
	border-left:none;
	border-top:none;
}
.ui-layout-pane-center {
	border-top:none;
}
fieldset {
	border: 1px solid #97b7e7;
	padding: 2px;
	margin-bottom: 10px;
}

fieldset legend {
	font-size:12px;
	color: #15428b;
}

#itemId{
	border:0px;$('#endDate').val() == ''
}
</style>
<title></title>
</head>
<body style="padding:0px;margin:0px;">

<div class="ui-layout-west" style="padding:5px;overflow: auto;">
	<form id="querySignData" action="${ctx}/datashare/sign/data/list.do" target="signData" method="post">
	<fieldset style="margin: 0px;">
		<legend>前兆数据查询</legend>
		<table width="100%">
			<tr>
				<td align="right">查询类别：</td>
				<td>
					${subject.name}
					<input type="hidden" id="methodIds" value="${methodIds}" style="width: 100px">
				</td>
			</tr>
			<tr>
				<td width="70px" align="right">数据类型：</td>
				<td>
					<select name="model.tableCategory" id="tableCategory" style="width:100px;" onchange="showStationsTree()">
			    		<option value="DYU">预处理数据</option>
			    		<option value="DYS">原始数据</option>
			    	</select>
				</td>
			</tr>
			<tr>
				<td align="right">采&nbsp;样&nbsp;率：</td>
				<td>
					<select name="model.sampleRate" id="sampleRate" style="width:100px;" onchange="showStationsTree()">
					    <option vlaue="00">请选择...</option>
						<c:forEach items="${sampleRateList}" var="item">
					       <option value="${item.id}">${item.name}</option>
					    </c:forEach>
			       	</select>
				</td>
			</tr>	
			<tr>
			    <td align="right">开始时间：</td>
			    <td>
			    <input name="model.startDate" id="startDate" style="width:100px;" onclick="WdatePicker({minDate:'2002-01-01',maxDate:'#F{$dp.$D(\'endDate\')||\'now\'}',skin:'whyGreen'})" >
			    </td>
			</tr>
			<tr>
			    <td align="right">结束时间：</td>
			    <td>
			    	<input name="model.endDate" id="endDate" style="width:100px;" onclick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'now',skin:'whyGreen'})" >
			    </td>
			</tr>
		</table>
	</fieldset>
	<fieldset id="stations" style="margin: 0px;">
		<legend>测项分量</legend>
		<div id="loading" style="text-align:center;">
		<img src="${ctx}/images/grid/loading.gif" border="0"/>
		</div>
		<table width="100%">
			<tr>
			   <td><div style="height: 360px; overflow: auto;">
			   	<div id="tree" style="width:100%;">
			        <ul id="stationsTree"></ul>
			    </div></div>
			   </td>
			</tr>
			<tr>
			   <td align="center">
			   <input type="button" value="查询" class="button" onclick="query()">
			   <input type="reset" value="重填"  class="button" onclick="hiddenStationsTree()"></td>
			</tr>
		</table>
	</fieldset>
	</form>
</div>

<div class="ui-layout-center" style="padding:0px;margin:0px;">
	<iframe src="" id="signData" name="signData" style="width:100%; height:100%; border:0px;" frameborder="0">
</div>
</body>
</html>