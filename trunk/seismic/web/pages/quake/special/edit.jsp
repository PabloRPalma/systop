<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<html>
<head>
<title>专题地震</title>
<%@include file="/common/extjs.jsp"%>
<%@include file="/common/meta.jsp"%>
<%@include file="/common/validator.jsp"%>
<%@include file="/common/quake.jsp" %>
<script type="text/javascript" src="${ctx}/scripts/fckeditor/fckeditor.js"></script>
<script type="text/javascript"
	src="${ctx}/pages/quake/special/selectQuakeCatalog.js">
</script>
<style type="text/css">
#mytable {
	border: 0px solid #A6C9E2;
	margin-left: 0px;
	margin-top: 0px;
	width: 100%;
	border-collapse: collapse;
}

#mytable td {
	border: 0px solid #A6C9E2;
	height: 28;
}
</style>
</head>
<script type="text/javascript">
//文本编辑组件
function preFckEditor(){
	desn();
	area();
	epifocus();
	history_pic();
	m_t();
	mechanism();
	fracture();
	intensity();
	event_wave();
	station_wave();
}
function desn(){
	var fckEditor = new FCKeditor( 'desn' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 180;
	  fckEditor.Width = 455;
	  fckEditor.ReplaceTextarea();
}
function area(){
	var fckEditor = new FCKeditor( 'area' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 300;
	  fckEditor.Width = 700;
	  fckEditor.ReplaceTextarea();
}
function epifocus(){
	var fckEditor = new FCKeditor( 'epifocus' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 300;
	  fckEditor.Width = 700;
	  fckEditor.ReplaceTextarea();
}
function history_pic(){
	var fckEditor = new FCKeditor( 'history_pic' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 300;
	  fckEditor.Width = 700;
	  fckEditor.ReplaceTextarea();
}
function m_t(){
	var fckEditor = new FCKeditor( 'm_t' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 300;
	  fckEditor.Width = 700;
	  fckEditor.ReplaceTextarea();
}
function mechanism(){
	var fckEditor = new FCKeditor( 'mechanism' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 300;
	  fckEditor.Width = 700;
	  fckEditor.ReplaceTextarea();
}
function fracture(){
	var fckEditor = new FCKeditor( 'fracture' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 300;
	  fckEditor.Width = 700;
	  fckEditor.ReplaceTextarea();
}
function intensity(){
	var fckEditor = new FCKeditor( 'intensity' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 300;
	  fckEditor.Width = 700;
	  fckEditor.ReplaceTextarea();
}
function event_wave(){
	var fckEditor = new FCKeditor( 'event_wave' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 300;
	  fckEditor.Width = 700;
	  fckEditor.ReplaceTextarea();
}
function station_wave(){
	var fckEditor = new FCKeditor( 'station_wave' ) ;
	  fckEditor.BasePath = "${ctx}/scripts/fckeditor/";
	  fckEditor.ToolbarSet = 'Basic';
	  fckEditor.Height = 300;
	  fckEditor.Width = 700;
	  fckEditor.ReplaceTextarea();
}
</script>
<script type="text/javascript">

	Ext.onReady(function() {
		var tabs = new Ext.TabPanel( {
			renderTo : 'tabs',
			anchor : '100% 100%',
			height : 420,
			activeTab : 0,
			frame : false,
			defaults : {
				autoHeight : false
			},
			items : [ {
				contentEl : 'basic',
				title : '地震参数'
			},{
				contentEl : 'area',
				title : '影响地区'
			}, {
				contentEl : 'epifocus',
				title : '震中分布图'
			}, {
				contentEl : 'history_pic',
				title : '历史地震分布图'
			}, {
				contentEl : 'm_t',
				title : '震中区M-T图'
			}
			, {
				contentEl : 'mechanism',
				title : '震源机制解'
			}
			, {
				contentEl : 'fracture',
				title : '震源破裂过程'
			}
			, {
				contentEl : 'intensity',
				title : '烈度分布'
			}
			, {
				contentEl : 'event_wave',
				title : '事件波形数据'
			}, {
				contentEl : 'station_wave',
				title : '台站波形记录图'
			}  ]
		});
	});
</script>
<body onload="preFckEditor()">
<div class="x-panel" style="margin-top: -5px;margin-left: -2px;margin-right: -2px;">
<div class="x-panel-header">编辑专题地震</div>
<div class="x-toolbar">
<table width="99%">
	<tr>
		<td align="right">
		<table>
			<tr>
				<td align="right" height="20"><a href="${ctx}/admin/special/index.do"> 专题地震列表</a></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</div>
<div><%@ include file="/common/messages.jsp"%></div>
<s:form id="save" action="save.do" method="post" theme="simple" validate="true" enctype="multipart/form-data">
<div id="tabs" style="margin-top: 0px;margin-left: 0px;margin-right: 0px;">
<div id="basic" class="x-hide-display"><s:hidden name="model.id" />
<s:hidden name="model.qc_id" id="model.qc_id"/>
<s:hidden name="model.tableName" id="model.tableName"/>
	<table border="0">
	<tr><td width="10px">&nbsp;</td>
		<td width="80px">选择地震目录:</td>
		<td>
		<s:iterator value="catalogs">
					<a href="#" onclick="javascript:selectQc('<s:property value="cltName"/>','${model.id}')">
						<s:property value="clcName"/>&nbsp;&nbsp;&nbsp;&nbsp;
					</a>		
		</s:iterator>
		</td>
		<td width="60px">&nbsp;</td>
	</tr>
	<tr><td width="10px">&nbsp;</td>
		<td width="80px">地  点:</td>
		<td>
		<s:textfield id="location" name="model.location" cssStyle="width:155px"/>
		</td>
		<td width="60px">&nbsp;</td>
	</tr>
	<tr><td width="10px">&nbsp;</td>
		<td>经  度:</td>
		<td>
		<s:textfield id="longitude" name="model.longitude"  cssStyle="width:155px"/>
			
		</td>
		<td width="60px">&nbsp;</td>
	</tr>
	<tr><td width="10px">&nbsp;</td>
		<td>纬  度:</td>
		<td>
		<s:textfield id="latitude" name="model.latitude" cssStyle="width:155px"/>
			
		</td>
		<td width="60px">&nbsp;</td>
	</tr>
	<tr><td width="10px">&nbsp;</td>
		<td>震  级:</td>
		<td>
		<s:textfield id="magnitude" name="model.magnitude" cssStyle="width:155px"/>
			
		</td>
		<td width="60px">&nbsp;</td>
	</tr>
	<tr><td width="10px">&nbsp;</td>
		<td>深 度:</td>
		<td>
		<s:textfield id="depth" name="model.depth" cssStyle="width:155px"/>
			
		</td>
		<td width="60px">&nbsp;</td>
	</tr>
	<tr><td width="10px">&nbsp;</td>
		<td>发震时刻:</td>
		<td>
		<s:textfield id="quakeTime" name="model.quakeTime" cssStyle="width:155px"/>
		</td>
		<td width="60px">&nbsp;</td>
	</tr>
	
	<tr><td width="10px">&nbsp;</td>
		<td>专题标题:</td>
		<td>
		<s:textfield id="model.title" name="model.title" cssStyle="width:355px" cssClass="required" />
		</td>
		<td width="60px"><font color="red">&nbsp;*</font></td>
	</tr>
	<tr><td width="10px">&nbsp;</td>
		<td>专题图片:</td>
		<td width="50">
			<s:file id="pic" name="pic" cssClass="FileText" cssStyle="width:355px"/> 
			<s:hidden id="model.front_pic" name="model.front_pic"/>
			<c:if test="${model.front_pic != null && model.front_pic != ''}">
			              <a href="${ctx}${model.front_pic}" target="_blank">
			                <img src="${ctx}${model.front_pic}" width="20" height="20" border="0" title="查看大图"/>  
			              </a>
			</c:if>
		</td>
		<td width="60px">&nbsp;</td>
	</tr>
	<tr><td width="10px">&nbsp;</td>
		<td>地震描述:</td>
		<td>
		<s:textarea id="desn" name="model.desn" cssStyle="width:355px;height:100px" cssClass="required" />
		</td>
		<td width="60px"><font color="red">&nbsp;*</font></td>
	</tr>
	</table>
	
</div>
<div id="area" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">影响地区：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="area" name="model.area" cols="65" rows="4">${model.area}</textarea>
		</td>
	</tr>
</table>
</div>
<div id="epifocus" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">震中分布图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="epifocus" name="model.epifocus" cols="65" rows="4">${model.epifocus}</textarea>
		</td>
	</tr>
</table>
</div>
<div id="history_pic" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">历史地震分布图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="history_pic" name="model.history_pic" cols="65" rows="4">${model.history_pic}</textarea></td>
	</tr>
</table>
</div>
<div id="m_t" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">震中区M-T图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="m_t" name="model.m_t" cols="65" rows="4">${model.m_t}</textarea></td>
	</tr>
</table>
</div>
<div id="mechanism" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">震源机制解：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="mechanism" name="model.mechanism" cols="65" rows="4">${model.mechanism}</textarea></td>
	</tr>
</table>
</div>
<div id="fracture" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">震源破裂过程：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="fracture" name="model.fracture" cols="65" rows="4">${model.fracture}</textarea></td>
	</tr>
</table>
</div>
<div id="intensity" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">烈度分布：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="intensity" name="model.intensity" cols="65" rows="4">${model.intensity}</textarea></td>
	</tr>
</table>
</div>
<div id="event_wave" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">事件波型数据：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="event_wave" name="model.event_wave" cols="65" rows="4">${model.event_wave}</textarea></td>
	</tr>
</table>
</div>
<div id="station_wave" class="x-hide-display">
<table id="mytable" height="380" style="margin-top: 5px;width: 800px" align="center">
	<tr>
		<td width="15%" align="right" valign="top">台站波形记录图：</td>
		<td align="left" style="vertical-align: top;" width="85%">
		<textarea id="station_wave" name="model.station_wave" cols="65" rows="4">${model.station_wave}</textarea></td>
	</tr>
</table>
</div>
</div>
<table width="100%" style="margin-bottom: 0px;">
	<tr>
		<td height="5"></td>
	</tr>
	<tr>
		<td style="text-align: center;"><s:submit value="保存"
			cssClass="button" />&nbsp;&nbsp; <s:reset value="重置"
			cssClass="button" /></td>
	</tr>
</table>
</s:form>
</div>
<div id="win" class="x-hidden">
<div class="x-window-header">地震目录列表</div>
<div id="grid"></div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	$("#save").validate();
});
</script>
</body>
</html>