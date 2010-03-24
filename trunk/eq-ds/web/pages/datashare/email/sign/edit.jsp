<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="${ctx}/styles/datashare.css">
<link type="text/css" href="${ctx}/scripts/jquery/layout/layout.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/treeview/treeview.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-min.css"	rel="stylesheet" />

<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/layout/jquery.layout.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/treeview/treeview.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/treeview/treeview.async.js" ></script>
<%@ include file="/common/validator.jsp"%>
<script type="text/javascript">
	$(function(){
		$("#saveFrm").validate();
		//电子邮件缺省值
		if($('#email').val() == '') {
		    if('${user.dataEmail}' == '') {
		    	$('#email').val('${user.email}');
		    }else {
		        $('#email').val('${user.dataEmail}');
		    }
		}
		$("#loading").bind("ajaxSend", function(){
	       $(this).show();
	    }).bind("ajaxComplete", function(){
	       $(this).hide();
	    });
	    
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
</script>
<style type="text/css">
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
	border:0px;
}
</style>
<title></title>
</head>
<body>

<div class="x-panel-body" align="center">
<table width="100%" border="0" cellpadding="5" cellspacing="1">
	<tr>
		<td><%@ include file="/common/messages.jsp"%></td>
	</tr>
</table>
<s:form action="save" theme="simple" id="saveFrm" method="POST">
	<fieldset style="width:600; margin: 25px; padding: 10px;">
	<legend>前兆数据订阅</legend>
	<table width="90%" align="center">
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td align="right">学科：</td>
			<td style="text-align: left;"><s:select id="methodIds"
				list="subjects" listKey="methodIdsStr" listValue="name"
				cssStyle="width:150px;" onchange="showStationsTree()" />
			</td>
		</tr>

		<tr>
			<td align="right">数据类型：</td>
			<td style="text-align: left;"><select id="tableCategory"
				name="model.dataType" style="width: 150px;"
				onchange="showStationsTree()">
				<option value="DYU">预处理数据</option>
				<option value="DYS">原始数据</option>
			</select>
			</td>
		</tr>
		<tr>
			<td align="right">采&nbsp;样&nbsp;率：</td>
			<td><s:select id="sampleRate" name="model.sampleRate" list="sampleRates" listKey="id"
				listValue="name" headerKey="" headerValue="请选择..."
				cssStyle="width:150px;" onchange="showStationsTree()" cssClass="required"/>
			</td>
		</tr>
		<tr>
			<td align="right">电子邮件：</td>
			<td style="text-align: left;">
			    <s:textfield id="email" name="model.emailAddr" cssClass="email required" cssStyle="width:150px;" />
			</td>
		</tr>
		<tr>
			<td align="right">测项分量：</td>
			<td>
			<table id="stations" width="100%">
				<tr>
					<td>
					<div id="loading" style="text-align: center;">
					  <img src="${ctx}/images/grid/loading.gif" border="0" />
					</div>
					</td>
				</tr>
				<tr>
					<td>
					<div id="tree" style="width: 100%; border: 1px solid #97b7e7;">
					<ul id="stationsTree"></ul>
					</div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</fieldset>
	<table width="90%" style="margin-bottom: 10px;">
		<tr>
			<td style="text-align: center;">
			<s:submit value="保存" cssClass="button"></s:submit> 
			<s:reset value="重置" cssClass="button" onclick="hiddenStationsTree()"></s:reset></td>
		</tr>
	</table>
</s:form></div>

</body>
</html>