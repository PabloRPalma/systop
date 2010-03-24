<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp" %>
<html>
<head>
<title></title>
<script type="text/javascript" src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
<link type="text/css" href="${ctx}/styles/style.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/layout/layout.css" rel="stylesheet" />
<link type="text/css" href="${ctx}/scripts/jquery/ui/css/jquery-ui-min.css"	rel="stylesheet" />
<script type="text/javascript" src="${ctx}/scripts/jquery/jquery-1.3.2.js" ></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/ui/jquery-ui-1.7.1.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/layout/jquery.layout.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery/bgiframe/jquery.bgiframe.js"></script>
<style type="text/css">
b {
   color:#2e6e9e;
   margin-bottom:20px;
}
div#divMemo {  width: 350px; margin: 10px 0; }
div#divMemo table { margin: 1em 0; border-collapse: collapse; width: 100%; }
div#divMemo table td, div#divMemo table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }

.ui-button { width:150px;outline: 0; margin:10px; padding: 2px; text-decoration:none;  !important; cursor:pointer; position: relative; text-align: center; }
.ui-dialog .ui-state-highlight, .ui-dialog .ui-state-error { padding: .3em;  }

#title{text-align:center;font-size:16px}
</style>
<script type="text/javascript">
	$(function() {
		$("#dialog").dialog({
			bgiframe: true,
			autoOpen: false,
			height: 320,
			width: 360,
			modal: true,
			buttons: {
				'保存': function() {
					var metaSet = $("#metaSet")[0].value;
					var dataFile = $("#dataFile")[0].value;
					var fileTag = dataFile.substring(dataFile.lastIndexOf('.')).toLowerCase();
					if (metaSet == null || metaSet == '') {
						alert("请输入数据集！");
						return;
					}
					if (dataFile == null || dataFile == '') {
						alert("请选择要保存的元数据文件！");
						return;
					}
					if (fileTag != '.xml') {
						alert("元数据文件必须是.xml格式的文件！");
						return;
					}
					$('#saveFrm').submit();
					$(this).dialog('close');
				  },
				'取消': function() {
					$(this).dialog('close');
				  }
				}
				
		});
		
		$('#meta').click(function() {
			var mType = $("#type")[0].value;
			$.ajax({
				url: '${ctx}/datashare/metadata/getMetadataInfo.do',
				type: 'post',
				dataType: 'json',
				data: {type : mType},
				success: function(result, textStatus){
					if (result != null && result != '') {
						$("#metaId")[0].value = result.id;
						$("#mType")[0].value = result.type;
						$("#czOrQz")[0].value = result.czOrQz;
						$("#metaSet")[0].value = result.metaSet;
					}
				}
			});
			$('#dialog').dialog('open');
		});
		
		$('#delmeta').click(function() {
			var mType = $("#type")[0].value;
			$.ajax({
				url: '${ctx}/datashare/metadata/deleteMetadata.do',
				type: 'post',
				dataType: 'json',
				data: {type : mType},
				success: function(result, textStatus){
					if(result == 'delok') {
						alert("元数据删除成功！");
					}
					if(result == 'nometa') {
						alert("没有元数据！");
					}
					if(result == 'delerr') {
						alert("元数据删除失败，请重试！");
					}
				}
			});
		});
		
		$("#dlg").dialog({
			bgiframe: false,
			autoOpen: false,
			height: 520,
			width: 650,
			modal: true,
			buttons: {
				'保存': function() {
					var textTitle = $("#textTitle")[0].value;
					//var textDescn = $("#descn")[0].value;
					var textDescn = FCKeditorAPI.GetInstance('descn').GetXHTML() ;
					var type = $("#type")[0].value;
					var desId = $("#desId")[0].value;
					var hasData = $("#hasData")[0].value;
					var accUrl = '${ctx}/datashare/descr/queryDescribe.do';
					if (textTitle == null || textTitle == '') {
						alert("请输入标题！");
						return;
					}
					$.ajax({
						url: '${ctx}/datashare/descr/save.do',
						type: 'post',
						dataType: 'json',
						data: {id:desId, title : textTitle, descn:textDescn,
						 	   type:type, accessUrl:accUrl, hasMetadata:hasData},
						success: function(result, textStatus){
				   			$('#content').html(textDescn);
					   		$('#title').html(textTitle);
						}
					  });
					 $(this).dialog('close');
					},
				'取消': function() {
					$(this).dialog('close');
				  }
				}
		});
		$('#memo').click(function() {
			preFckEditor();
			var mType = $("#type")[0].value;
			var accUrl = '${ctx}/datashare/descr/queryDescribe.do';
			$.ajax({
				url: '${ctx}/datashare/descr/getDescrInfo.do',
				type: 'post',
				dataType: 'json',
				data: {type : mType, accessUrl:accUrl},
				success: function(result, textStatus){
					if (result != null && result != '') {
						$("#desId")[0].value = result.id;
						$("#textTitle")[0].value = result.title;
						//$("#descn")[0].value = result.descn;
						FCKeditorAPI.GetInstance('descn').SetHTML(result.descn);
					}
				}
			});
			$('#dlg').dialog('open');
		});
		
		$('#queryMeta').click(function() {
			var mType = $("#mType")[0].value;
			window.location = '${ctx}/datashare/metadata/queryMetadataInfo.do?model.type='+mType;
		});
		$('#downMeta').click(function() {
			var mType = $("#mType")[0].value;
			window.location = '${ctx}/datashare/metadata/downloadMetadataInfo.do?model.type='+mType;
		});
		
		//屏蔽回车提交，防止乱码		
		$('#textTitle').bind('keydown', function(e){return cantRet(e);});
		$('#metaSet').bind('keydown', function(e){return cantRet(e);});		
	});
var cantRet = function(e) {
		    var e = e || window.event;   
            var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;   
            if(keyCode == 0x0D){
                return false;
            }   
            return true;
};
</script>
</head>
<body>
<div class="demo">
<div id="divMemo" style="width:100%;">
<table class="ui-widget ui-widget-content" width="100%" style="margin-top:0px;">
<thead>
   <tr class="ui-widget-header ">
   <td>
   <div id="title">${descr.title }</div>
   </td>
   </tr>
</thead>
<tbody>
   <tr>
   <td>
   <div id="content">${descr.descn }</div>
   </td>
   </tr>
</tbody>
</table>
</div>
<br>
<c:if test="${model.hasMetadata eq 1}">
<button id="queryMeta" class="ui-button ui-state-default ui-corner-all">查看元数据</button>
<button id="downMeta" class="ui-button ui-state-default ui-corner-all">下载元数据文件</button>
</c:if>
<stc:role ifAllGranted="ROLE_ADMIN">
	<c:if test="${model.hasMetadata eq 1}">
	<button id="meta" class="ui-button ui-state-default ui-corner-all">创建/编辑元数据</button>
	<button id="delmeta" class="ui-button ui-state-default ui-corner-all">删除元数据</button>
	</c:if>
	<button id="memo" class="ui-button ui-state-default ui-corner-all">创建/编辑本页说明</button>
</stc:role>
<br> 
<div id="dialog" title="元数据管理">
	<form action="${ctx}/datashare/metadata/save.do" method="POST" 
	   id="saveFrm" enctype="multipart/form-data">
		<s:hidden id="mType" name="model.type" />
		<s:hidden id="metaId" name="model.id" />
       <table>
           <tr>
              <td>类别：</td>
              <td>
              	<select id="czOrQz" name="model.czOrQz" style="width:200px">
              		<option value="CZ">测震</option>
              		<option value="QZ">前兆</option>
              	</select>
              </td>
           </tr>
           <tr>
              <td>数据集：</td>
              <td><s:textfield id="metaSet" name="model.metaSet" style="width:200px" /></td>
           </tr>
           <tr>
              <td>元数据文件：</td>
              <td><input type="file" id="dataFile" name="meta"  style="width:210px"></td>
           </tr>
       </table>
	</form>
</div>
<div id="dlg" title="说明信息">
	<form>
	<s:hidden id="type" name="model.type" />
	<s:hidden id="desId" name="model.id" />
	<s:hidden id="hasData" name="model.hasMetadata" />
	<table>
	   <tr>
	   <td>
	   <b>请输入标题：</b><br>
       <s:textfield id="textTitle" name="model.title" cssStyle="width:580px;"/>
       </td>
       </tr>
	   <tr>
	   <td>
	   <b>请输入关于本页的说明：</b><br>
       <s:textarea id="descn" name="model.descn" theme="simple" rows="" cols="" style="width:580px;border:1px solid #cecece;" />
       </td>
       </tr>
    </table>
	</form>
</div>
</div>
<script type="text/javascript">
function preFckEditor(){
	var fckEditor = new FCKeditor( 'descn' ) ;
    fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
    fckEditor.ToolbarSet = 'Default';
    fckEditor.Height = 360;
    fckEditor.ReplaceTextarea();
}
</script>
</body>
</html>