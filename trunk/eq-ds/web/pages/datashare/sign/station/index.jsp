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
		
		$('#subjects').change(function() {
			var subid = $("#subjects")[0].value;
			if (subid == null || subid == '') {
				$('#itemId').empty();
				$('<option value=\'\'>全部...</option>').appendTo('#itemId');
				$('#instrCode').empty();
				$('<option value=\'\'>全部...</option>').appendTo('#instrCode');
				return;
			}
			
			$.ajax({
				url: '${ctx}/datashare/sign/station/queryMethods.do',
				type: 'post',
				dataType: 'json',
				data: {subjectId : subid},
				success: function(rows, textStatus){
				   $('#itemId').empty();
				   $('<option value=\'\'>全部...</option>').appendTo('#itemId');
				   for (var i = 0; i < rows.length; i ++) {
				   	var row = rows[i];
				   	$('<option value=' + row.id + '>' + row.name + '</option>').appendTo('#itemId');
				   }
					//alert(data.length);
				}
			});
			
			$.ajax({
				url: '${ctx}/datashare/sign/station/queryInstruments.do',
				type: 'post',
				dataType: 'json',
				data: {subjectId : subid},
				success: function(rows, textStatus){
				   $('#instrCode').empty();
				   $('<option value=\'\'>全部...</option>').appendTo('#instrCode');
				   for (var i = 0; i < rows.length; i ++) {
				   	var row = rows[i];
				   	$('<option value=' + row.id + '>' + row.name + '</option>').appendTo('#instrCode');
				   }
					//alert(data.length);
				}
			});
		});
		
		$('#itemId').change(function(){
			var subid = $("#subjects")[0].value;
			var methodId = $('#itemId')[0].value;
			if(methodId == null || methodId == ''){
				$('#instrCode').empty();
				$.ajax({
					url:'${ctx}/datashare/sign/station/queryInstruments.do',
					type:'post',
					dataType:'json',
					data:{subjectId:subid},
					success:function(rows,textStatus){
						$('#instrCode').empty();
					    $('<option value=\'\'>全部...</option>').appendTo('#instrCode');
						for (var i = 0; i < rows.length; i ++) {
				   			var row = rows[i];
				   			$('<option value=' + row.id + '>' + row.name + '</option>').appendTo('#instrCode');
				   		}
					} 
				});
			}
			$.ajax({
				url:'${ctx}/datashare/sign/station/queryInstrumentsByMethod.do',
				type:'post',
				dataType:'json',
				data:{methodId:methodId},
				success:function(rows,textStatus){
						$('#instrCode').empty();
						$('<option value=\'\'>全部...</option>').appendTo('#instrCode');
						for (var i = 0; i < rows.length; i ++) {
				   			var row = rows[i];
				   			$('<option value=' + row.id + '>' + row.name + '</option>').appendTo('#instrCode');
				   		}
				 }				 
			});
		});
		
	});
	
	/** 
	 * 显示台站树型列表:台站->测点
	 */
	function showStationsTree(){
		var subjectId = $("#subjects")[0].value;
	 	var instrCode = $("#instrCode")[0].value;
	 	var itemId = $("#itemId")[0].value;
	 	//把上次显示的树型列表清楚
	 	$("#stationsTree").remove(); 
	 	//创建存放树型列表的元素
	 	$("#tree").append("<ul id='stationsTree'></ul>"); 
		
		//构建树型列表
		$("#stationsTree").treeview({
			collapsed: true,
			unique: false,
			url:"${ctx}/datashare/sign/station/staionsTree.do?model.subjectId="+subjectId+"&model.instrCode=" + instrCode + "&model.itemId=" + itemId
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
				<td width="45px" align="right">学科：</td>
				<td>
				  <select id="subjects" name="model.subjectId" style="width:130px;">
					    <option value="">全部...</option>
						<c:forEach items="${subjects}" var="item">
					       <option value="${item.id}">${item.name}</option>
					    </c:forEach>
			      </select>
				</td>
			</tr>
			<tr>
				<td align="right">测项：</td>
				<td>
				  <select id="itemId" name="model.itemId" style="width:130px;">
					    <option value="">全部...</option>
				  </select>
				</td>
			</tr>
			<tr>
				<td align="right">仪器：</td>
				<td>
				  <select id="instrCode" name="model.instrCode" style="width:130px;">
					    <option value="">全部...</option>
					    <!--  
						<c:forEach items="${instruments}" var="item">
					       <option value="${item.INSTRCODE}">${item.INSTRTYPE}_${item.INSTRNAME}(${item.INSTRCODE})</option>
					    </c:forEach>
					    -->
			      </select>
				</td>
			</tr>	
			<tr>
			   <td align="center" colspan="2">
			       <input type="button" value="查询" class="button" onclick="showStationsTree()">
			       <input type="button" value="台站分布图" onclick="queryForXML('${ctx}/datashare/sign/station/stationGis.do','_blank')" style="width:80px;" class="button">
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
			   <td><div style="height: 400px; overflow: auto;">
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