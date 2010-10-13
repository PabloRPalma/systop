<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" href="${ctx}/styles/style.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/layout/layout.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/treeview/treeview.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-min.css"	rel="stylesheet" />

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
		
		$('#selAll').click(function() {
			var isCheck = this.checked;
			$('input[name="model.stationId"]').each(function(i, obj) {
				obj.checked = isCheck;
			});
		});
	});
	
	/** 
	 * 显示台站树型列表:台站->测点
	 */
	function showStationsTree(){
		var staId = document.getElementsByName("model.stationId");
		var sIds = "null";
		for(i=0;i<staId.length;i++) {
			if(staId[i].checked) {
				sIds = staId[i].value + "," + sIds;
			}
		}
		sIds = sIds.substring(0, sIds.lastIndexOf(','));
	 	//把上次显示的树型列表清楚
	 	$("#stationsTree").remove(); 
	 	//创建存放树型列表的元素
	 	$("#tree").append("<ul id='stationsTree'></ul>"); 
		
		//构建树型列表
		$("#stationsTree").treeview({
			collapsed: true,
			unique: false,
			url:"${ctx}/quake/sign/station/staionsForStaTree.do?model.stationId="+sIds
		});
		
		//显示存放树型列表的元素
		$('#stations').fadeIn();
	}
	function queryForXML(url, target) {
	 	$("#queryFrm").attr("action", url);
		$("#queryFrm").attr("target", target);
		$("#queryFrm").submit();
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
	margin-bottom: 0px;
}

fieldset legend {
	font-size:12px;
	color: #15428b;
}

</style>
<title></title>
</head>
<body style="padding:0px;margin:0px;">

<div class="ui-layout-west" style="padding:0px;overflow: auto;">
	<form theme="simple" id="queryFrm">
	  <fieldset>
		<legend>台站信息查询</legend>
		<table width="100%">
			<tr>
				<td align="left">
					<div style="width: 170px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="selAll" title="全选">&nbsp;<b>全选</b>
					</div>
				</td>
			</tr>
			<tr>
				<td align="center">
					<div style="width: 170px; height:95px; overflow: auto;">
			      		<s:checkboxlist id="stationId" name="model.stationId" list="stationsMap" listKey="STATIONID" listValue="STATIONNAME"
							templateDir="templates" theme="eqds/stat" />
					</div>
				</td>
			</tr>
			<tr>
			   <td align="center" colspan="2">
			       <input type="button" value="查询" class="button" onclick="showStationsTree()">
			       <input type="button" value="台站分布图" onclick="queryForXML('${ctx}/quake/sign/station/stationForStaGis.do','_blank')" style="width:80px;" class="button">
			   </td>
			</tr>
		</table>		
	  </fieldset>
	</form>
	<fieldset id="stations">
		<legend>台站基本信息</legend>
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
		</table>
	</fieldset>
</div>
<div class="ui-layout-center" style="padding:0px;margin:0px;">
	<iframe src="" id="pointInfo" name="pointInfo" style="width:100%; height:100%; border:0px;" frameborder="0">
</div>
</body>
</html>